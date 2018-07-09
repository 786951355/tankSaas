package com.tanker.basemodule.model.order_model;

import android.text.TextUtils;

public class BillInfo {
    private String id;
    private String billPath;

    public BillInfo(String id, String billPath) {
        this.id = id;
        this.billPath = billPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillPath() {
        return billPath;
    }

    public void setBillPath(String billPath) {
        this.billPath = billPath;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"billPath\":\"" + billPath + '\"' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillInfo)) return false;
        BillInfo billInfo = (BillInfo) o;
        boolean hasId = !TextUtils.isEmpty(id);
        boolean otherHasId = !TextUtils.isEmpty(billInfo.id);
        return hasId && otherHasId && billInfo.id.equals(id);
    }

}
