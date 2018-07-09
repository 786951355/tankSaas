package com.tanker.basemodule.model.home_model;

import java.io.Serializable;

/**
 * 抢单详情
 */
public class BiddingOrderDetModel implements Serializable {


    /**
     * actualLoadingTime :
     * biddingId : 377
     * biddingTime :
     * consignerMobilePhone : 17621912511
     * consignerName : mac
     * currentTime : 1523771662
     * endTime : 1523773547
     * enturstOrderCode : H1804150012
     * expectationPrice : 555.00
     * expectationTonnage : 123.000
     * goodsName : MMMMM
     * ifFollowVehicles : 1
     * ifInvoice : 1
     * loadingAreaName : 东城区
     * loadingCityName : 北京市
     * loadingDetailAddress : 起点起点起点
     * loadingTime : 1523705804
     * loadingWareHouse : 起点起点起点起点
     * otherFees :
     * quotedCount :
     * quotedPrice :
     * quotedPriceType :
     * receiverMobilePhone : 17621912511
     * receiverName : mac
     * remark : 铁罐（单层）,铝合金罐（保温）,无损耗,1234rtytre2wqerhgjhg
     * restrictTime :
     * servicePhone :
     * status : 1
     * transportPhone : 15618752930
     * unloadingAreaName : 和平区
     * unloadingCityName : 天津市
     * unloadingDetailAddress : 看点
     * unloadingTime : 1523705819
     * unloadingWareHouse : 看点看点看点看点
     * vehiclesCount : 123
     */

    private String actualLoadingTime;
    private String biddingId;
    private String biddingTime;
    private String consignerMobilePhone;
    private String consignerName;
    private String currentTime;
    private String endTime;
    private String enturstOrderCode;
    private String expectationPrice;
    //期望价类型1:报单价2:包车价
    private String expectationPriceType;
    private String expectationTonnage;
    private String goodsName;
    private String ifFollowVehicles;
    private String ifInvoice;
    private String loadingAreaName;
    private String loadingCityName;
    private String loadingDetailAddress;
    private String loadingTime;
    private String loadingWareHouse;
    private String otherFees;
    private String quotedCount;
    private String quotedPrice;
    //    报价类型  0：未报价 1 报单价  2包车价
    private String quotedPriceType;
    private String receiverMobilePhone;
    private String receiverName;
    private String remark;
    private String restrictTime;
    private String servicePhone;
    private String status;
    private String transportPhone;
    private String unloadingAreaName;
    private String unloadingCityName;
    private String unloadingDetailAddress;
    private String unloadingTime;
    private String unloadingWareHouse;
    private String vehiclesCount;
    private String failedReason;
    //是否确认报价,0为未确认报价,1为已确认报价,当status为2的时候,需要判断这个字段,如果为0则显示确认报价按钮,1则已确认,不显示按钮
    private String ifConfirmQuote;


    public boolean isExpectUnitPrice() {
        return "1".equals(expectationPriceType);
    }

    public String getExpectationPriceType() {
        return expectationPriceType;
    }

    public void setExpectationPriceType(String expectationPriceType) {
        this.expectationPriceType = expectationPriceType;
    }

    public String getIfConfirmQuote() {
        return ifConfirmQuote;
    }

    public void setIfConfirmQuote(String ifConfirmQuote) {
        this.ifConfirmQuote = ifConfirmQuote;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public String getActualLoadingTime() {
        return actualLoadingTime;
    }

    public void setActualLoadingTime(String actualLoadingTime) {
        this.actualLoadingTime = actualLoadingTime;
    }

    public String getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(String biddingId) {
        this.biddingId = biddingId;
    }

    public String getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(String biddingTime) {
        this.biddingTime = biddingTime;
    }

    public String getConsignerMobilePhone() {
        return consignerMobilePhone;
    }

    public void setConsignerMobilePhone(String consignerMobilePhone) {
        this.consignerMobilePhone = consignerMobilePhone;
    }

    public String getConsignerName() {
        return consignerName;
    }

    public void setConsignerName(String consignerName) {
        this.consignerName = consignerName;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEnturstOrderCode() {
        return enturstOrderCode;
    }

    public void setEnturstOrderCode(String enturstOrderCode) {
        this.enturstOrderCode = enturstOrderCode;
    }

    public String getExpectationPrice() {
        return expectationPrice;
    }

    public void setExpectationPrice(String expectationPrice) {
        this.expectationPrice = expectationPrice;
    }

    public String getExpectationTonnage() {
        return expectationTonnage;
    }

    public void setExpectationTonnage(String expectationTonnage) {
        this.expectationTonnage = expectationTonnage;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getIfFollowVehicles() {
        return ifFollowVehicles;
    }

    public void setIfFollowVehicles(String ifFollowVehicles) {
        this.ifFollowVehicles = ifFollowVehicles;
    }

    public String getIfInvoice() {
        return ifInvoice;
    }

    public void setIfInvoice(String ifInvoice) {
        this.ifInvoice = ifInvoice;
    }

    public String getLoadingAreaName() {
        return loadingAreaName;
    }

    public void setLoadingAreaName(String loadingAreaName) {
        this.loadingAreaName = loadingAreaName;
    }

    public String getLoadingCityName() {
        return loadingCityName;
    }

    public void setLoadingCityName(String loadingCityName) {
        this.loadingCityName = loadingCityName;
    }

    public String getLoadingDetailAddress() {
        return loadingDetailAddress;
    }

    public void setLoadingDetailAddress(String loadingDetailAddress) {
        this.loadingDetailAddress = loadingDetailAddress;
    }

    public String getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(String loadingTime) {
        this.loadingTime = loadingTime;
    }

    public String getLoadingWareHouse() {
        return loadingWareHouse;
    }

    public void setLoadingWareHouse(String loadingWareHouse) {
        this.loadingWareHouse = loadingWareHouse;
    }

    public String getOtherFees() {
        return otherFees;
    }

    public void setOtherFees(String otherFees) {
        this.otherFees = otherFees;
    }

    public String getQuotedCount() {
        return quotedCount;
    }

    public void setQuotedCount(String quotedCount) {
        this.quotedCount = quotedCount;
    }

    public String getQuotedPrice() {
        return quotedPrice;
    }

    public void setQuotedPrice(String quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    public String getQuotedPriceType() {
        return quotedPriceType;
    }

    public void setQuotedPriceType(String quotedPriceType) {
        this.quotedPriceType = quotedPriceType;
    }

    public String getReceiverMobilePhone() {
        return receiverMobilePhone;
    }

    public void setReceiverMobilePhone(String receiverMobilePhone) {
        this.receiverMobilePhone = receiverMobilePhone;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRestrictTime() {
        return restrictTime;
    }

    public void setRestrictTime(String restrictTime) {
        this.restrictTime = restrictTime;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransportPhone() {
        return transportPhone;
    }

    public void setTransportPhone(String transportPhone) {
        this.transportPhone = transportPhone;
    }

    public String getUnloadingAreaName() {
        return unloadingAreaName;
    }

    public void setUnloadingAreaName(String unloadingAreaName) {
        this.unloadingAreaName = unloadingAreaName;
    }

    public String getUnloadingCityName() {
        return unloadingCityName;
    }

    public void setUnloadingCityName(String unloadingCityName) {
        this.unloadingCityName = unloadingCityName;
    }

    public String getUnloadingDetailAddress() {
        return unloadingDetailAddress;
    }

    public void setUnloadingDetailAddress(String unloadingDetailAddress) {
        this.unloadingDetailAddress = unloadingDetailAddress;
    }

    public String getUnloadingTime() {
        return unloadingTime;
    }

    public void setUnloadingTime(String unloadingTime) {
        this.unloadingTime = unloadingTime;
    }

    public String getUnloadingWareHouse() {
        return unloadingWareHouse;
    }

    public void setUnloadingWareHouse(String unloadingWareHouse) {
        this.unloadingWareHouse = unloadingWareHouse;
    }

    public String getVehiclesCount() {
        return vehiclesCount;
    }

    public void setVehiclesCount(String vehiclesCount) {
        this.vehiclesCount = vehiclesCount;
    }

    public boolean isConfirmPrice() {
        return "1".equals(ifConfirmQuote);
    }
}
