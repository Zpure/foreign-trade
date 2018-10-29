package com.zcpure.foreign.trade;

import lombok.Data;

import java.util.UUID;

@Data
public class WebJsonBean<T> {

	/**
	 * 返回码
	 */
	private int Code;
	/**
	 * 说明
	 */
	private String desc;
	/**
	 * 请求随机UUID
	 */
	private String tid = UUID.randomUUID().toString();
	/**
	 * 返回数据
	 */
	private T data;

	public WebJsonBean() {
	}

	public WebJsonBean(BaseCode baseCode) {
		this.Code = baseCode.getIndex();
		this.desc = baseCode.getMsg();
	}

	public WebJsonBean(String msg) {
		this.Code = BaseCode.FAIL.index;
		this.desc = msg;
	}

	public WebJsonBean(int Code, String desc) {
		this.Code = Code;
		this.desc = desc;
	}

	public WebJsonBean(int Code, String desc, T data) {
		this.Code = Code;
		this.desc = desc;
		this.data = data;
	}

	public WebJsonBean(BaseCode baseCode, T data) {
		this.Code = baseCode.getIndex();
		this.desc = baseCode.getMsg();
		this.data = data;
	}

	public static WebJsonBean<Void> SUCCESS() {
		return new WebJsonBean(BaseCode.SUCCESS);
	}

	public static <T> WebJsonBean SUCCESS(T data) {
		return new WebJsonBean(BaseCode.SUCCESS, data);
	}

	public static WebJsonBean<Void> FAIL() {
		return new WebJsonBean(BaseCode.FAIL);
	}

	public static WebJsonBean<Void> FAIL(String msg) {
		return new WebJsonBean(BaseCode.FAIL, msg);
	}
}
