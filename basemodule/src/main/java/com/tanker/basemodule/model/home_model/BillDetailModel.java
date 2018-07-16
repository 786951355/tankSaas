package com.tanker.basemodule.model.home_model;

/**
* @author lwj
* @ClassName: BillDetailModel
* @Description:  账单详情实体类
* @date 2018/7/16 14:10
*/
public class BillDetailModel {
    private String billId;
    private String billno;
    private String billstatus;
    private String billloadingAreaName;
    private String billunloadingAreaName;
    private String billTime;
    private String billMoney;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getBillstatus() {
        return billstatus;
    }

    public void setBillstatus(String billstatus) {
        this.billstatus = billstatus;
    }

    public String getBillloadingAreaName() {
        return billloadingAreaName;
    }

    public void setBillloadingAreaName(String billloadingAreaName) {
        this.billloadingAreaName = billloadingAreaName;
    }

    public String getBillunloadingAreaName() {
        return billunloadingAreaName;
    }

    public void setBillunloadingAreaName(String billunloadingAreaName) {
        this.billunloadingAreaName = billunloadingAreaName;
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
                ", billno='" + billno + '\'' +
                ", billstatus='" + billstatus + '\'' +
                ", billloadingAreaName='" + billloadingAreaName + '\'' +
                ", billunloadingAreaName='" + billunloadingAreaName + '\'' +
                ", billTime='" + billTime + '\'' +
                ", billMoney='" + billMoney + '\'' +
                '}';
    }
}