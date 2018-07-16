package com.tanker.basemodule.model.home_model;

import java.util.List;

/**
* @author lwj
* @ClassName: BillListModel
* @Description:  账单列表
* @date 2018/7/16 15:03
*/
public class BillListModel {

    /**
     * total : 2
     * billInfoList : [{"carrierUserName":"上海测试企业1","mobilePhone":"18200983996","carrierOrderCount":"3","ifDriver":"1","carrierUserId":"25"}]
     */
    private String total;
    private List<BillModel> billInfoList;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<BillModel> getBillInfoList() {
        return billInfoList;
    }

    public void setBillInfoList(List<BillModel> billInfoList) {
        this.billInfoList = billInfoList;
    }

    @Override
    public String toString() {
        return "BillListModel{" +
                "total='" + total + '\'' +
                ", billInfoList=" + billInfoList +
                '}';
    }
}
