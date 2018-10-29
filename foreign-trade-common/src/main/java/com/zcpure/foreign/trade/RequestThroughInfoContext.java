package com.zcpure.foreign.trade;

/**
 * @author ethan
 * @create_time 2018/10/23 16:29
 */
public class RequestThroughInfoContext {
	private static ThreadLocal<RequestThroughInfo> threadInfo = new ThreadLocal<>();
	public static String KEY_INFO_IN_HTTP_HEADER = "X-AUTO-FP-INFO";

	public static RequestThroughInfo getInfo() {
		return threadInfo.get();
	}

	public static void setInfo(RequestThroughInfo info) {
		threadInfo.set(info);
	}

	public static void remove() {
		threadInfo.remove();
	}
}
