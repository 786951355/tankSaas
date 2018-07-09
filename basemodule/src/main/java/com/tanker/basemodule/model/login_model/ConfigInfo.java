package com.tanker.basemodule.model.login_model;

import android.text.TextUtils;

public class ConfigInfo {
    /**
     * 地址html地址
     */
    private String appAddressSrc;

    /**
     * 开票配置html地址
     */
    private String appInvoiceSrc;

    /**
     * 全国json地址
     */
    private String appJsonSrc;

    /**
     * 全国json版本号
     */
    private String appJsonVersion;

    /**
     * 客服电话
     */
    private String appServicePhone;

    /**
     * 订单超时时间
     */
    private String confirmQuoteTime;

    /**
     * 上传图片最大数
     */
    private String pictureCount;

    /**
     * 客服工作时间
     */
    private String workTime;

    /**
     * 运能工作时间
     */
    private String transportWorkTime;

    /**
     * 资质审核时间
     */
    private String auditTime;

    private boolean isNeedUpdate = false;

    public void setNeedUpdate(boolean needUpdate) {
        isNeedUpdate = needUpdate;
    }

    public boolean isNeedUpdateJson() {
        return isNeedUpdate;
    }


    public String getAppAddressSrc() {
        return appAddressSrc;
    }

    public void setAppAddressSrc(String appAddressSrc) {
        this.appAddressSrc = appAddressSrc;
    }

    public String getAppInvoiceSrc() {
        return appInvoiceSrc;
    }

    public void setAppInvoiceSrc(String appInvoiceSrc) {
        this.appInvoiceSrc = appInvoiceSrc;
    }

    public String getAppJsonSrc() {
        return appJsonSrc;
    }

    public void setAppJsonSrc(String appJsonSrc) {
        this.appJsonSrc = appJsonSrc;
    }

    public String getAppJsonVersion() {
        if (!TextUtils.isEmpty(appJsonVersion) && (appJsonVersion.startsWith("v") || appJsonVersion.startsWith("V"))) {
            appJsonVersion = appJsonVersion.substring(1).replaceAll("\\.", "");
        } else {
            appJsonVersion = "0";
        }
        return appJsonVersion;
    }

    public void setAppJsonVersion(String appJsonVersion) {
        this.appJsonVersion = appJsonVersion;
    }

    public String getAppServicePhone() {
        return appServicePhone;
    }

    public void setAppServicePhone(String appServicePhone) {
        this.appServicePhone = appServicePhone;
    }

    public String getConfirmQuoteTime() {
        return confirmQuoteTime;
    }

    public void setConfirmQuoteTime(String confirmQuoteTime) {
        this.confirmQuoteTime = confirmQuoteTime;
    }

    public String getPictureCount() {
        return pictureCount;
    }

    public void setPictureCount(String pictureCount) {
        this.pictureCount = pictureCount;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getTransportWorkTime() {
        return transportWorkTime;
    }

    public void setTransportWorkTime(String transportWorkTime) {
        this.transportWorkTime = transportWorkTime;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }
}
