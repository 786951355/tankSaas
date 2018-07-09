package com.tanker.basemodule.model.home_model;

import java.io.Serializable;
import java.util.List;

/**
 * 已报价详情
 */
public class AlreadQuoteModel implements Serializable {

    private String code;
    private String message;
    private String requestUUID;
    private String responseTime;
    private List<QuoteResponseDto> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestUUID() {
        return requestUUID;
    }

    public void setRequestUUID(String requestUUID) {
        this.requestUUID = requestUUID;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public List<QuoteResponseDto> getData() {
        return data;
    }

    public void setData(List<QuoteResponseDto> data) {
        this.data = data;
    }

    public class QuoteResponseDto {
        private int vehiclesCount;
        private String price;
        private int startCityId;
        private int endCityId;
        private String startCityName;
        private String endCityName;
        private String loadingTime;
        private String unloadingTime;
        private String goodsName;
        private String transportTonnage;
        private String remark;
        private String status;
        private String quoteTime;
        private String tax;
        private int ifFollow;

        public int getVehiclesCount() {
            return vehiclesCount;
        }

        public void setVehiclesCount(int vehiclesCount) {
            this.vehiclesCount = vehiclesCount;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStartCityId() {
            return startCityId;
        }

        public void setStartCityId(int startCityId) {
            this.startCityId = startCityId;
        }

        public int getEndCityId() {
            return endCityId;
        }

        public void setEndCityId(int endCityId) {
            this.endCityId = endCityId;
        }

        public String getStartCityName() {
            return startCityName;
        }

        public void setStartCityName(String startCityName) {
            this.startCityName = startCityName;
        }

        public String getEndCityName() {
            return endCityName;
        }

        public void setEndCityName(String endCityName) {
            this.endCityName = endCityName;
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

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getTransportTonnage() {
            return transportTonnage;
        }

        public void setTransportTonnage(String transportTonnage) {
            this.transportTonnage = transportTonnage;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getQuoteTime() {
            return quoteTime;
        }

        public void setQuoteTime(String quoteTime) {
            this.quoteTime = quoteTime;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public int getIfFollow() {
            return ifFollow;
        }

        public void setIfFollow(int ifFollow) {
            this.ifFollow = ifFollow;
        }
    }
}
