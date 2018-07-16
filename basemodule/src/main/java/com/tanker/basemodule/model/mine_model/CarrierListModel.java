package com.tanker.basemodule.model.mine_model;

import java.util.List;

/**
 * @author lwj
 * @ClassName: BillListModel
 * @Description: 关联车主列表
 * @date 2018/5/23 11:01
 */
public class CarrierListModel {

    /**
     * total : 2
     * driverList : [{"carrierUserName":"上海测试企业1","mobilePhone":"18200983996","carrierOrderCount":"3","ifDriver":"1","carrierUserId":"25"}]
     */
    private String total;
    private List<CarrierInfoBean> carrierInfoList;

    public List<CarrierInfoBean> getCarrierInfoList() {
        return carrierInfoList;
    }

    public void setCarrierInfoList(List<CarrierInfoBean> carrierInfoList) {
        this.carrierInfoList = carrierInfoList;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
