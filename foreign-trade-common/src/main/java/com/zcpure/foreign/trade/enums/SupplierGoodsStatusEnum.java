package com.zcpure.foreign.trade.enums;

public enum SupplierGoodsStatusEnum {

	NORMAL(1, "正常状态"),
	STOP(2, "停产");

	private int code;
	private String desc;

	SupplierGoodsStatusEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static SupplierGoodsStatusEnum getByCode(Integer code) {
		for (SupplierGoodsStatusEnum enums : SupplierGoodsStatusEnum.values()) {
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
