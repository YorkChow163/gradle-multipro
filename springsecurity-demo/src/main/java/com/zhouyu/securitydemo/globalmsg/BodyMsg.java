package com.zhouyu.securitydemo.globalmsg;

import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @Date:2019/4/2 11:33
 * @Author:zhouyu
 */
@Data
@ToString
public class BodyMsg<T> {
    private int code;
    private String msg;
    private T data;

    public BodyMsg(ReturnMsgEnum returnMsgEnum) {
        this.code=returnMsgEnum.getCode();
        this.msg=returnMsgEnum.getMsg();
    }

    public BodyMsg(ReturnMsgEnum returnMsgEnum, T data) {
        this.code=returnMsgEnum.getCode();
        this.msg=returnMsgEnum.getMsg();
        this.data = data;
    }

    public BodyMsg(Exceptionenum exceptionenum) {
        this.code = exceptionenum.getCode();
        this.msg = exceptionenum.getMsg();
    }

    public BodyMsg(String msg,int code) {
        this.msg = msg;
        this.code=code;
    }
}
