package com.tanker.basemodule.model.home_model;

import java.io.Serializable;

/**
 * 忽略抢单
 */
public class IgnoreBiddingModel implements Serializable {

    private String code;
    private String message;
    private String requestUUID;
    private String responseTime;


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
}
