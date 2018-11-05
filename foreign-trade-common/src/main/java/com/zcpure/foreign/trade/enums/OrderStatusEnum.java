package com.zcpure.foreign.trade.enums;

public enum OrderStatusEnum {

	INIT(1, "初始生成"),
	CONFIRM(2, "确认"),
	DISTRIBUTION(3, "配货成功"),
	DELIVERY(4, "已发货"),
	RECEIPT(5, "已收货"),
	SUCCESS(10, "完结");

	private int code;
	private String desc;

	OrderStatusEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static OrderStatusEnum getByCode(Integer code) {
		for(OrderStatusEnum enums: OrderStatusEnum.values()) {
			if(enums.getCode() == code) {
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
