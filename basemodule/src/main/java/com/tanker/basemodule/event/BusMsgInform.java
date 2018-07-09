package com.tanker.basemodule.event;

/**
 * 通知的消息类型
 */
public class BusMsgInform {
    private String msgType;
    private String msg;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
