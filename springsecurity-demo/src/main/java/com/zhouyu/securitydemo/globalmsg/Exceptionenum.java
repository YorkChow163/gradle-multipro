package com.zhouyu.securitydemo.globalmsg;

import lombok.ToString;

/**
 * @Description:
 * @Date:2019/4/2 11:13
 * @Author:zhouyu
 */
@ToString
public enum  Exceptionenum {
    AUTHENTICATIONFAILED("验证失败",401);

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private String msg;
    private int code;

    Exceptionenum(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }


}
