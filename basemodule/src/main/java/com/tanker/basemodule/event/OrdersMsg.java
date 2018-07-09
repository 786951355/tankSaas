package com.tanker.basemodule.event;

public class OrdersMsg<T> {

    private String type;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public OrdersMsg(String s) {
        this.type=s;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
