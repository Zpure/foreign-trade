package com.zcpure.foreign.trade;

import lombok.Data;

/**
 * @author ethan
 * @create_time 2018/10/23 16:30
 */
@Data
public class RequestThroughInfo {
	private String groupCode;
	private Long userId;
	private String userName;
	private String ip;

	public static RequestThroughInfo defaultInfo() {
		RequestThroughInfo info = new RequestThroughInfo();
		info.setGroupCode("GC001");
		info.setUserId(1L);
		info.setUserName("ethan");
		return info;
	}

}
