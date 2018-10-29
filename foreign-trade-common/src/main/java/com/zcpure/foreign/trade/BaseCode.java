package com.zcpure.foreign.trade;

import java.io.Serializable;

public class BaseCode implements Serializable {

    public static BaseCode SUCCESS = getCode(1000, "成功");
    public static BaseCode _ERROR = getCode(1001, "参数异常");
    public static BaseCode SYSTEMERROR = getCode(1002, "系统异常");
    public static BaseCode LOGIN_ERROR = getCode(1003, "登录失败");
    public static BaseCode USER_TOKEN_EXPIRE = getCode(1004, "登录状态已失效，请重新登录");
    public static BaseCode NO_USER = getCode(1005, "用户不存在或密码错误 ");
    public static BaseCode FAIL = getCode(1006, "失败 ");
    public static BaseCode CONTECTION_TIME_OUT = getCode(1007, "哎呀~ 连接出现了点小问题");
    public static BaseCode ACCESE_NO = getCode(1008, "权限不足");
    public static BaseCode API_IS_ERROR = getCode(1009, "接口内部错误");
    
    private static final long serialVersionUID = 1229001345233615995L;
    public int index;
    public String msg;
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public BaseCode(int index, String msg) {
        super();
        this.index = index;
        this.msg = msg;
    }
    public BaseCode() {
        super();
    }
    public static BaseCode getCode(int index, String msg) {
        return new BaseCode(index, msg);
    }

    public boolean equals(Object object){
        if(object instanceof BaseCode){
            if (((BaseCode) object).getIndex() == this.getIndex()) {
                return true;
            }
        }
        return false;
    }
}
