package com.zcpure.foreign.trade.enums;

public enum SupplierStatusEnum {

	NORMAL(1, "正常状态"),
	STOP(2, "停产");

	private int code;
	private String desc;

	SupplierStatusEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static SupplierStatusEnum getByCode(Integer code) {
		for (SupplierStatusEnum enums : SupplierStatusEnum.values()) {
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
