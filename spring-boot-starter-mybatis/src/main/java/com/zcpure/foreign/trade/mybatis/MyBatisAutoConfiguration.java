package com.zcpure.foreign.trade.mybatis;


import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.pugwoo.dbhelper.impl.SpringJdbcDBHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * mybatis配置文件,采用spring boot 加载问题
 * @author zhouwen
 */
@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ConditionalOnProperty(name = "jdbc.base.dao")
public class MyBatisAutoConfiguration
{
    
   /**
    * DB_BASE_PACKAGE
    */
   private static final String DB_DAO_PACKAGE="jdbc.base.dao";
   
   private static final String DB_DIALECT ="spring.jpa.database";
   

    /**
     * 
     * @param
     * @return
     */
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


   /**
    * 创建sqlSessionTemplate
    * @param dataSource
    * @param env
    * @return
    * @throws Exception
    */
    @Bean(name = "sqlSessionFactory")
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource,Environment env) throws Exception
    {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

        // 开启查询结果驼峰自动切换
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(DisabledMybatisDefaultLog.class);   // 关闭原来的mybatis日志
        factoryBean.setConfiguration(configuration);

        factoryBean.setDataSource(dataSource);
        //factoryBean.setMapperLocations(context.getResources(EvnUtils.getStringValue(env,DB_MAPPER_PACKAGE)));
        //factoryBean.setTypeAliasesPackage(EvnUtils.getStringValue(env,DB_DOMAIN_PACKAGE));

        //page helper
        Interceptor[] interceptors = new Interceptor[2];
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        //暂时写死统一禁止修改
        properties.setProperty("dialect", env.getProperty(DB_DIALECT, "mysql"));
        //根据条件查询出所有的数据 当limit 为0的时候
        properties.setProperty("pageSizeZero", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        pageHelper.setProperties(properties);
        interceptors[0] = pageHelper;
        interceptors[1] = new MyBatisLogInterceptor();
        factoryBean.setPlugins(interceptors);



        return  factoryBean.getObject();
    }

    /**
     * mybatis扫描DAO包接口
     *
     * @return mapperScannerConfigurer
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(Environment env)
    {
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setSqlSessionTemplateBeanName("sqlSessionTemplate");
        msc.setBasePackage(Preconditions.checkNotNull(env.getProperty(DB_DAO_PACKAGE)));
        return msc;
    }

    
    /**
     * 加载事物控制器
     * @param datesource
     * @return
     */
    @Bean
	public DataSourceTransactionManager transactionManager(DataSource datesource) {
    	  DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
          transactionManager.setDataSource(datesource);
          return transactionManager;
	}
    
    @Bean(name="jdbcTemplate")
    public JdbcTemplate	jdbcTemplate(DataSource datesource){
    	JdbcTemplate template = new JdbcTemplate();
    	template.setDataSource(datesource);
    	return template;
    }
  
    @Bean(name="namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedJdbcTemplate(DataSource datasource){
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(datasource);
    	return template;
    }
    
    @Bean(name="dbHelper")
    public SpringJdbcDBHelper dbHelper(JdbcTemplate jdbcTemplate,NamedParameterJdbcTemplate namedParameterJdbcTemplate){
    	SpringJdbcDBHelper helper = new SpringJdbcDBHelper();
    	helper.setJdbcTemplate(jdbcTemplate);
    	helper.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);
    	helper.setTimeoutWarningValve(600);
    	return helper;
    }
    
}
