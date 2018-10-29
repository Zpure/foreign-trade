package com.zcpure.foreign.trade.enums;

/**
 * Created by lenovo on 2017/9/15.
 */
public enum GoodsStatusEnum {

	ON_SALE(1, "上架"),
	OFF_SALE(2, "下架");

	private int code;
	private String desc;

	GoodsStatusEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
