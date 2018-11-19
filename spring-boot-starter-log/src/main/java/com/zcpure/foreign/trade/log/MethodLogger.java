package com.zcpure.foreign.trade.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class MethodLogger {
    protected Logger logger = LoggerFactory.getLogger("com.zcpure.ML");

    public static MethodLogger newLog() {
        return new MethodLogger();
    }

    private MethodLogger() {
    }

    private static final String LOG_KEY = "\t|\t{}\t|\t{}";
    /**
     * 输出方法调用的log
     * @param className
     * @param methodName
     * @param time
     * @param parameter
     * @param result
     */
    public void i(String className, String methodName, Long time, String parameter, String result) {
        setMDC(className, methodName, time);
        logger.info(LOG_KEY, parameter, result);
    }




    public void e(String className, String methodName, Long time, String parameter, Throwable e) {
        setMDC(className, methodName, time);

        if(parameter != null) {
            logger.error(LOG_KEY, parameter,  e == null ? "cause is null" : e.getMessage(), e);
        } else {
            logger.error("{}", e == null ? "cause is null" : e.getMessage(), e);
        }

    }

    protected void setMDC(String className, String methodName, Long time) {
        MDC.put("class", className + ".");
        if(methodName != null) {
            MDC.put("method", methodName);
        } else {
            MDC.put("method", getMethodName());
        }

        MDC.put("sequence", String.valueOf(LogContext.getMethodSequence()));
        if(time != null) {
            MDC.put("time", String.valueOf(time));
        } else {
            MDC.put("time", "0");
        }
        MDC.put("requestId", LogContext.getRequestId());

    }

    protected String getMethodName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        if(stackTraceElements.length < 4 || stackTraceElements[3] == null) {
            return null;
        }

        return stackTraceElements[3].getMethodName();

    }
}
