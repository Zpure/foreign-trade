package com.zcpure.foreign.trade.enums;

/**
 * Created by lenovo on 2017/9/15.
 */
public enum CustomerMsgStatusEnum {

	SUBMIT(10, "提交"),
	DEAL(20, "处理");

	private int code;
	private String desc;

	CustomerMsgStatusEnum(int code, String desc) {
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
