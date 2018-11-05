package com.zcpure.foreign.trade;

import com.zcpure.foreign.trade.dto.user.UserDTO;
import lombok.Data;

/**
 * @author ethan
 * @create_time 2018/10/23 16:30
 */
@Data
public class RequestThroughInfo {
	private Long userId;
	private String userName;
	private String phone;
	private Integer userLevel;
	private String groupCode;
	private String groupName;
	private String ip;
	private String requestId;

	public static RequestThroughInfo defaultInfo() {
		RequestThroughInfo info = new RequestThroughInfo();
		info.setGroupCode("GC001");
		info.setUserId(1L);
		info.setUserName("ethan");
		return info;
	}

	public static RequestThroughInfo form(UserDTO dto) {
		RequestThroughInfo info = new RequestThroughInfo();
		info.setUserId(dto.getId());
		info.setUserName(dto.getName());
		info.setGroupCode(dto.getGroupCode());
		info.setGroupName(dto.getGroupName());
		info.setPhone(dto.getPhone());
		info.setUserLevel(dto.getUserLevel());
		return info;
	}

}
