package com.zcpure.foreign.trade.log;

import org.slf4j.MDC;

public class MDCContext {
    
    private String requestId;
    private String className;
    private String methodName;
    private Long time;

    protected void applyAll() {
        MDC.put("class", className);
        MDC.put("method", methodName);
        if(time != null) {
            MDC.put("time", String.valueOf(time));
        } else {
            MDC.put("time", "0");
        }
        MDC.put("requestId", requestId);
    }
    
    protected void applyTime(Long time) {
        this.time = time;
        MDC.put("time", String.valueOf(time));
    }
    
    protected static void clear() {
        MDC.remove("class");
        MDC.remove("method");
        MDC.remove("time");
        MDC.remove("requestId");
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

}
