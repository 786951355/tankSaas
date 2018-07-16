package com.tanker.basemodule.model.home_model;

import java.util.List;

/**
* @author lwj
* @ClassName: BillDetailListModel
* @Description:  账单详情列表
* @date 2018/7/16 15:03
*/
public class BillDetailListModel {
    private String total;
    private List<BillDetailModel> billDetailInfoList;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<BillDetailModel> getBillDetailInfoList() {
        return billDetailInfoList;
    }

    public void setBillDetailInfoList(List<BillDetailModel> billDetailInfoList) {
        this.billDetailInfoList = billDetailInfoList;
    }

    @Override
    public String toString() {
        return "BillDetailListModel{" +
                "total='" + total + '\'' +
                ", billDetailInfoList=" + billDetailInfoList +
                '}';
    }
}
