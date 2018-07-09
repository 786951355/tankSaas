package com.tanker.basemodule.model.home_model;

/**
 * 报包车价和单价
 */
public class QuotedModel {

    private String biddingId;
    private String userId;
    private String type;
    private String price;
    private String planUploadingTime;


    public String getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(String biddingId) {
        this.biddingId = biddingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPlanUploadingTime() {
        return planUploadingTime;
    }

    public void setPlanUploadingTime(String planUploadingTime) {
        this.planUploadingTime = planUploadingTime;
    }
}
