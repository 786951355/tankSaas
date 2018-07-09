package com.tanker.basemodule.model.home_model;

import java.io.Serializable;
import java.util.List;

/**
 * 用户报价信息
 */
public class BiddingPrefectModel implements Serializable {
    private String code;
    private String message;
    private String requestUUID;
    private String responseTime;
    private List<BiddingPrefect> data;

    public class BiddingPrefect {
        private int vehiclesCount;
        private int price;
        private int startCityId;
        private int endCityId;
        private String loadingCityName;
        private String unloadingCityName;
        private String loadingTime;
        private String unloadingTime;
        private String goodsName;
        private String transportTonnage;
        private String remark;
        private String status;
        private String searchVehicleId;
        private String tax;
        private String IfFollow;

        public int getVehiclesCount() {
            return vehiclesCount;
        }

        public void setVehiclesCount(int vehiclesCount) {
            this.vehiclesCount = vehiclesCount;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
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

        public String getLoadingCityName() {
            return loadingCityName;
        }

        public void setLoadingCityName(String loadingCityName) {
            this.loadingCityName = loadingCityName;
        }

        public String getUnloadingCityName() {
            return unloadingCityName;
        }

        public void setUnloadingCityName(String unloadingCityName) {
            this.unloadingCityName = unloadingCityName;
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

        public String getSearchVehicleId() {
            return searchVehicleId;
        }

        public void setSearchVehicleId(String searchVehicleId) {
            this.searchVehicleId = searchVehicleId;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getIfFollow() {
            return IfFollow;
        }

        public void setIfFollow(String ifFollow) {
            IfFollow = ifFollow;
        }
    }

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

    public List<BiddingPrefect> getData() {
        return data;
    }

    public void setData(List<BiddingPrefect> data) {
        this.data = data;
    }
}
