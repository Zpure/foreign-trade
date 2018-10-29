package com.zcpure.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@Intercepts({
    @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
    @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
            RowBounds.class, ResultHandler.class }) })
public class MyBatisLogInterceptor implements Interceptor {
    protected Logger logger = LoggerFactory.getLogger("com.zcpure.SL");

    private Method requestQueryMethod;
    public MyBatisLogInterceptor() {
        try {
            Class logContextClass = Class.forName("com.zcpure.log.LogContext", false, this.getClass().getClassLoader());
            requestQueryMethod = logContextClass.getMethod("getRequestId");
            requestQueryMethod.invoke(null);
        } catch (ClassNotFoundException e) {
            logger.warn("log context not found", e.getMessage());
            requestQueryMethod = null;
        } catch (NoSuchMethodException e) {
            logger.warn("query requestId method not found", e.getMessage());
            requestQueryMethod = null;
        } catch (IllegalAccessException |InvocationTargetException e) {
            logger.warn("query requestId error", e.getMessage());
            requestQueryMethod = null;
        }
    }

    private void setMDC() {
        if(requestQueryMethod != null) {
            try {
                MDC.put("requestId", (String) requestQueryMethod.invoke(null));
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.warn("query requestId error", e.getMessage());
            }
        }
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object returnValue = null;
        i(invocation, returnValue);
//        long start = System.currentTimeMillis();
        returnValue = invocation.proceed();
//        long end = System.currentTimeMillis();
//        long time = end - start;
        i(invocation, returnValue);
        return returnValue;
    }

    private void i(Invocation invocation, Object returnValue) {
        if(logger.isDebugEnabled()) {
            setMDC();
            try {
                MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
                Object parameter = null;
                if (invocation.getArgs().length > 1) {
                    parameter = invocation.getArgs()[1];
                }
                String sqlId = mappedStatement.getId();
                BoundSql boundSql = mappedStatement.getBoundSql(parameter);
                Configuration configuration = mappedStatement.getConfiguration();

                String sql = formatSql(configuration, boundSql);
                logger.debug(sqlId + "\t|\t" + sql + "\t|\t" + returnValue);
            } catch (Exception e) {
                logger.error("print mybatis mybatis error", e);
            }
        }
    }
 
    
 
    private static String formatSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }
    
    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
 
        }
        return value;
    }
    
    @Override
    public Object plugin(Object target) {
        if(target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        
    }
    
}