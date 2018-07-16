package com.tanker.basemodule.model.home_model;

/**
* @author lwj
* @ClassName: BillModel
* @Description:  账单列表实体类
* @date 2018/7/16 14:10
*/
public class BillModel {
    /**
     * billId ：1
     * billTime : 2018-07
     * billConfirmMoney : 2000.90
     * billNoConfirmMoney : 3000.50
     */
    private String billId;
    private String billTime;
    private String billConfirmMoney;
    private String billNoConfirmMoney;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillTime() {
        return billTime;
    }

    public void setBillTime(String billTime) {
        this.billTime = billTime;
    }

    public String getBillConfirmMoney() {
        return billConfirmMoney;
    }

    public void setBillConfirmMoney(String billConfirmMoney) {
        this.billConfirmMoney = billConfirmMoney;
    }

    public String getBillNoConfirmMoney() {
        return billNoConfirmMoney;
    }

    public void setBillNoConfirmMoney(String billNoConfirmMoney) {
        this.billNoConfirmMoney = billNoConfirmMoney;
    }

    @Override
    public String toString() {
        return "BillModel{" +
                "billId='" + billId + '\'' +
                ", billTime='" + billTime + '\'' +
                ", billConfirmMoney='" + billConfirmMoney + '\'' +
                ", billNoConfirmMoney='" + billNoConfirmMoney + '\'' +
                '}';
    }
}