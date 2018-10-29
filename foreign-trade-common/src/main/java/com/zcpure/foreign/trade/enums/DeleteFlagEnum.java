package com.zcpure.foreign.trade.enums;

/**
 * Created by lenovo on 2017/9/15.
 */
public enum DeleteFlagEnum {

    NORMAL(0, "正常"), DELETE(1, "删除"), ALL(3, "全部");

    private int code;
    private String desc;

    DeleteFlagEnum(int code, String desc) {
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
