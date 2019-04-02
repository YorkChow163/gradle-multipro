package com.zhouyu.securitydemo.globalmsg;

/**
 * @Description:
 * @Date:2019/4/2 11:26
 * @Author:zhouyu
 */
public enum ReturnMsgEnum {


    AUTHENTICATIONSUCCESS("验证成功",200);

    private String msg;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ReturnMsgEnum(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

}
