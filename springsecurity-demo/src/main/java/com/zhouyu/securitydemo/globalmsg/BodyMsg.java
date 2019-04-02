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
    private int status;
    private String message;
    private T data;

    public BodyMsg(ReturnMsgEnum returnMsgEnum) {
        this.status=returnMsgEnum.getCode();
        this.message=returnMsgEnum.getMsg();
    }

    public BodyMsg(ReturnMsgEnum returnMsgEnum, T data) {
        this.status=returnMsgEnum.getCode();
        this.message=returnMsgEnum.getMsg();
        this.data = data;
    }

    public BodyMsg(Exceptionenum exceptionenum) {
        this.status = exceptionenum.getCode();
        this.message = exceptionenum.getMsg();
    }

    public BodyMsg(String message,int status) {
        this.status = status;
        this.message=message;
    }
}
