package com.tanker.basemodule.model.home_model;

import java.util.List;

public class FaultListModel {
    private int total;
    private List<FaultModel> faultModelList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<FaultModel> getFaultModelList() {
        return faultModelList;
    }

    public void setFaultModelList(List<FaultModel> faultModelList) {
        this.faultModelList = faultModelList;
    }
}
