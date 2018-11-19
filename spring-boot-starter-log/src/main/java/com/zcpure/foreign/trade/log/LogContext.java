package com.zcpure.foreign.trade.log;

import java.util.UUID;

public class LogContext {

    // userId上下文    暂时不使用
    private static ThreadLocal<String> userIdContext = new ThreadLocal<>();
    // requestId上下文
    private static ThreadLocal<String> requestIdContext = new ThreadLocal<>();

    // 发生异常时,要保留RequestId ,因为在调用链结束后, 全局异常处理器GlobalExceptionHandler还需要获取
    private static ThreadLocal<String> errRequestId = new ThreadLocal<>();

    // 调用链开始时的时间戳
    private static ThreadLocal<Long> stackStartTime = new ThreadLocal<>();

    public static void setRequestToErr() {
        errRequestId.set(requestIdContext.get());
    }



    /**
     * 获取requestId, 如果当前requestId为空,则返回errRequestId
     * @return
     */
    public static String getRequestIdOrErr() {
        String requestId = requestIdContext.get();
        if(requestId != null) {
            return requestId;
        }

        return errRequestId.get();
    }

    /**
     * 获取requestId
     * @return
     */
    public static String getRequestId() {
        String id = requestIdContext.get();
        if(id == null) {    // 如果当前为空, 会创建requestId以免出null异常
            id = UUID.randomUUID().toString().replace("-", "");
        }
        return id;
    }

    /**
     * 获取顺序
     * @return
     */
    public static Long getMethodSequence() {
        Long startTime = stackStartTime.get();
        if(startTime == null) {
            return 0L;
        }
        // 用当前时间减调用链开始时间
        return System.currentTimeMillis() - startTime;
    }



    public static String getUserId() {
        return userIdContext.get();
    }

    public static void setUserId(String userId) {
        userIdContext.set(userId);
    }

    public static void setRequestId(String requestId) {
        requestIdContext.set(requestId);
    }


    /**
     * 调用链开始时, 需要调用createRequestIfNecessary创建log的上下文
     * @return
     */
    public static void createRequestIfNecessary() {
        String id = requestIdContext.get();
        if(id == null) {    // 创建requestId
            id = UUID.randomUUID().toString().replace("-", "");
            requestIdContext.set(id);
        }

        if(stackStartTime.get() == null) {
            stackStartTime.set(System.currentTimeMillis());
        }
    }

    /**
     * 调用链结束后, 需要调用clearLogContext清理上下文
     */
    protected static void clearLogContext() {
        requestIdContext.set(null);

        stackStartTime.set(null);
    }

}
