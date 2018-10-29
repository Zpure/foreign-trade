package com.zcpure.foreign.trade.enums;

public enum UserLevelEnum {

	ADMIN(1, "管理员"),
	GROUP_ADMIN(2, "集团管理员"),
	GROUP_NORMAL(3, "集团普通用户");

	private int code;
	private String desc;

	UserLevelEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static UserLevelEnum getByCode(Integer code) {
		for (UserLevelEnum enums : UserLevelEnum.values()) {
			if (enums.getCode() == code) {
				return enums;
			}
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
