package com.tanker.basemodule.model.home_model;

/**
* @author lwj
* @ClassName: BillDetailModel
* @Description:  账单详情实体类
* @date 2018/7/16 14:10
*/
public class BillDetailModel {
    private String billId;
    private String orderNo;
    private String billstatus;
    private String billstatusName;
    private String billstartAreaName;
    private String billendAreaName;
    private String billTime;
    private String billMoney;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBillstatus() {
        return billstatus;
    }

    public void setBillstatus(String billstatus) {
        this.billstatus = billstatus;
    }

    public String getBillstatusName() {
        int type = Integer.valueOf(billstatus);
        switch (type) {
            case 1:
                billstatusName = "已确认";
                break;
            case 2:
                billstatusName = "待确认";
                break;
            default:
                billstatusName = "";
                break;
        }
        return billstatusName;
    }

    public void setBillstatusName(String billstatusName) {
        this.billstatusName = billstatusName;
    }

    public String getBillstartAreaName() {
        return billstartAreaName;
    }

    public void setBillstartAreaName(String billstartAreaName) {
        this.billstartAreaName = billstartAreaName;
    }

    public String getBillendAreaName() {
        return billendAreaName;
    }

    public void setBillendAreaName(String billendAreaName) {
        this.billendAreaName = billendAreaName;
    }

    public String getBillTime() {
        return billTime;
    }

    public void setBillTime(String billTime) {
        this.billTime = billTime;
    }

    public String getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(String billMoney) {
        this.billMoney = billMoney;
    }

    @Override
    public String toString() {
        return "BillDetailModel{" +
                "billId='" + billId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", billstatus='" + billstatus + '\'' +
                ", billstatusName='" + billstatusName + '\'' +
                ", billstartAreaName='" + billstartAreaName + '\'' +
                ", billendAreaName='" + billendAreaName + '\'' +
                ", billTime='" + billTime + '\'' +
                ", billMoney='" + billMoney + '\'' +
                '}';
    }
}