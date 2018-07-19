package com.tanker.basemodule.model.home_model;

import java.util.List;

public class MileageListModel {
    private int total;
    private List<MileageModel> mileageModelList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MileageModel> getMileageModelList() {
        return mileageModelList;
    }

    public void setMileageModelList(List<MileageModel> mileageModelList) {
        this.mileageModelList = mileageModelList;
    }
}
