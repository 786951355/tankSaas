package com.tanker.basemodule.event;

public class GrabOrderMsg {
    private String msgType;
    private boolean isRefreshDot;

    public GrabOrderMsg(String extraMsgType) {
        this.msgType=extraMsgType;
    }

    public GrabOrderMsg(boolean isRefreshDot) {
        msgType="";
        this.isRefreshDot=isRefreshDot;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public boolean isRefreshDot() {
        return isRefreshDot;
    }

    public void setRefreshDot(boolean refreshDot) {
        isRefreshDot = refreshDot;
    }
}
