package com.tanker.basemodule.model.home_model;

import java.io.Serializable;
import java.util.List;

/**
 * 抢单列表信息
 */
public class BiddingOrderModel implements Serializable {


    /**
     * data : {"total":"5","rows":[{"id":"69","quoteExpireTime":"2018-04-02 11:33:55","loadingCityName":"石家庄市","loadingAreaName":"桥西区","unloadingCityName":"秦皇岛市","unloadingAreaName":"北戴河区","loadingTime":"2018-04-02 00:00:00","unloadingTime":"2018-04-21 00:00:00","vehiclesCount":"2","goodsName":"甲醇","ifFollowVehicles":"","expectedTonnage":"","goodsUnitPrice":"1000.00","goodsUnitPriceType":"1","ifInvoice":"2","remark":"这个是备注","status":"2"}]}
     * code : 0
     * message : 操作成功
     * responseTime : 2018-04-08 12:01:32
     */


    /**
     * total : 5
     * rows : [{"id":"69","quoteExpireTime":"2018-04-02 11:33:55","loadingCityName":"石家庄市","loadingAreaName":"桥西区","unloadingCityName":"秦皇岛市","unloadingAreaName":"北戴河区","loadingTime":"2018-04-02 00:00:00","unloadingTime":"2018-04-21 00:00:00","vehiclesCount":"2","goodsName":"甲醇","ifFollowVehicles":"","expectedTonnage":"","goodsUnitPrice":"1000.00","goodsUnitPriceType":"1","ifInvoice":"2","remark":"这个是备注","status":"2"}]
     */

    private String total;
    private List<RowsBean> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * biddingId : 2036
         * loadingCityName : 北京市
         * loadingAreaName : 朝阳区
         * unloadingCityName : 天津市
         * unloadingAreaName : 河西区
         * loadingTime : 1523781200
         * unloadingTime : 1523867614
         * vehiclesCount : 23
         * goodsName : 请问额
         * expectationTonnage : 23.000
         * ifFollowVehicles : 0
         * ifInvoice : 2
         * restrictTime : 1523879742
         * remark : 无损耗
         * actualLoadingTime : 1523879700
         * status : 2
         * expectationPrice :
         * currentTime : 1523879806
         * endTime : 1523885052
         * biddingTime :
         * quotedCount : 2
         * quotedPriceType : 1
         * quotedPrice : 22.00
         * loadingWareHouse : 未全额
         * unloadingWareHouse :
         * enquiryId : 1074
         */

        private String biddingId;
        private String loadingCityName;
        private String loadingAreaName;
        private String unloadingCityName;
        private String unloadingAreaName;
        private String loadingTime;
        private String unloadingTime;
        private String vehiclesCount;
        private String goodsName;
        private String expectationTonnage;
        private String ifFollowVehicles;
        private String ifInvoice;
        private String restrictTime;
        private String remark;
        private String actualLoadingTime;
        private String status;
        private String expectationPrice;
        //期望价类型1:报单价2:包车价
        private String expectationPriceType;
        private String currentTime;
        private String endTime;
        private String biddingTime;
        private String quotedCount;
        private String quotedPriceType;
        private String quotedPrice;
        private String loadingWareHouse;
        private String unloadingWareHouse;
        private String enquiryId;
        //      是否确认报价,0为未确认报价,1为已确认报价,当status为2的时候,需要判断这个字段,如果为0则显示确认报价按钮,1则已确认,不显示按钮
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

        public String getBiddingId() {
            return biddingId;
        }

        public void setBiddingId(String biddingId) {
            this.biddingId = biddingId;
        }

        public String getLoadingCityName() {
            return loadingCityName;
        }

        public void setLoadingCityName(String loadingCityName) {
            this.loadingCityName = loadingCityName;
        }

        public String getLoadingAreaName() {
            return loadingAreaName;
        }

        public void setLoadingAreaName(String loadingAreaName) {
            this.loadingAreaName = loadingAreaName;
        }

        public String getUnloadingCityName() {
            return unloadingCityName;
        }

        public void setUnloadingCityName(String unloadingCityName) {
            this.unloadingCityName = unloadingCityName;
        }

        public String getUnloadingAreaName() {
            return unloadingAreaName;
        }

        public void setUnloadingAreaName(String unloadingAreaName) {
            this.unloadingAreaName = unloadingAreaName;
        }

        public String getLoadingTime() {
            return loadingTime;
        }

        public void setLoadingTime(String loadingTime) {
            this.loadingTime = loadingTime;
        }

        public String getUnloadingTime() {
            return unloadingTime;
        }

        public void setUnloadingTime(String unloadingTime) {
            this.unloadingTime = unloadingTime;
        }

        public String getVehiclesCount() {
            return vehiclesCount;
        }

        public void setVehiclesCount(String vehiclesCount) {
            this.vehiclesCount = vehiclesCount;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getExpectationTonnage() {
            return expectationTonnage;
        }

        public void setExpectationTonnage(String expectationTonnage) {
            this.expectationTonnage = expectationTonnage;
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

        public String getRestrictTime() {
            return restrictTime;
        }

        public void setRestrictTime(String restrictTime) {
            this.restrictTime = restrictTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getActualLoadingTime() {
            return actualLoadingTime;
        }

        public void setActualLoadingTime(String actualLoadingTime) {
            this.actualLoadingTime = actualLoadingTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getExpectationPrice() {
            return expectationPrice;
        }

        public void setExpectationPrice(String expectationPrice) {
            this.expectationPrice = expectationPrice;
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

        public String getBiddingTime() {
            return biddingTime;
        }

        public void setBiddingTime(String biddingTime) {
            this.biddingTime = biddingTime;
        }

        public String getQuotedCount() {
            return quotedCount;
        }

        public void setQuotedCount(String quotedCount) {
            this.quotedCount = quotedCount;
        }

        public String getQuotedPriceType() {
            return quotedPriceType;
        }

        public void setQuotedPriceType(String quotedPriceType) {
            this.quotedPriceType = quotedPriceType;
        }

        public String getQuotedPrice() {
            return quotedPrice;
        }

        public void setQuotedPrice(String quotedPrice) {
            this.quotedPrice = quotedPrice;
        }

        public String getLoadingWareHouse() {
            return loadingWareHouse;
        }

        public void setLoadingWareHouse(String loadingWareHouse) {
            this.loadingWareHouse = loadingWareHouse;
        }

        public String getUnloadingWareHouse() {
            return unloadingWareHouse;
        }

        public void setUnloadingWareHouse(String unloadingWareHouse) {
            this.unloadingWareHouse = unloadingWareHouse;
        }

        public String getEnquiryId() {
            return enquiryId;
        }

        public void setEnquiryId(String enquiryId) {
            this.enquiryId = enquiryId;
        }

        @Override
        public String toString() {
            return "RowsBean{" +
                    "biddingId='" + biddingId + '\'' +
                    ", loadingCityName='" + loadingCityName + '\'' +
                    ", loadingAreaName='" + loadingAreaName + '\'' +
                    ", unloadingCityName='" + unloadingCityName + '\'' +
                    ", unloadingAreaName='" + unloadingAreaName + '\'' +
                    ", loadingTime='" + loadingTime + '\'' +
                    ", unloadingTime='" + unloadingTime + '\'' +
                    ", vehiclesCount='" + vehiclesCount + '\'' +
                    ", goodsName='" + goodsName + '\'' +
                    ", expectationTonnage='" + expectationTonnage + '\'' +
                    ", ifFollowVehicles='" + ifFollowVehicles + '\'' +
                    ", ifInvoice='" + ifInvoice + '\'' +
                    ", restrictTime='" + restrictTime + '\'' +
                    ", remark='" + remark + '\'' +
                    ", actualLoadingTime='" + actualLoadingTime + '\'' +
                    ", status='" + status + '\'' +
                    ", expectationPrice='" + expectationPrice + '\'' +
                    ", currentTime='" + currentTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", biddingTime='" + biddingTime + '\'' +
                    ", quotedCount='" + quotedCount + '\'' +
                    ", quotedPriceType='" + quotedPriceType + '\'' +
                    ", quotedPrice='" + quotedPrice + '\'' +
                    ", loadingWareHouse='" + loadingWareHouse + '\'' +
                    ", unloadingWareHouse='" + unloadingWareHouse + '\'' +
                    ", enquiryId='" + enquiryId + '\'' +
                    '}';
        }
    }

}

