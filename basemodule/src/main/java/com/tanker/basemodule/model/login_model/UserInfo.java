package com.tanker.basemodule.model.login_model;

import android.text.TextUtils;

public class UserInfo {
    private String userId = "";
    private String userUUID = "";
    private String userName = "";
    private String mobilePhone = "";
    private String avatorsrc = "";
    private String carrierPhone = "";
    private String auditStatus = "";
    private String company = "";
    private String historyUser = "";
    private String linesCount = "0";
    private String role; //角色 0：司机/押车员/(司机和押车员) 1：委托方 2：承运商
    private String ifDriverAgreeInvitation; //司机是否接受邀请 0：否 1：是
    private String ifEscortAgreeInvitation; //押车员是否接受邀请 0：否 1：是
    private String isCarrier; //1:有车主角色 0:没有车主角色


    public String getLinesCount() {
        return linesCount;
    }

    public void setLinesCount(String linesCount) {
        this.linesCount = linesCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getAvatorsrc() {
        return avatorsrc;
    }

    public void setAvatorsrc(String avatorsrc) {
        this.avatorsrc = avatorsrc;
    }

    public String getCarrierPhone() {
        return carrierPhone;
    }

    public void setCarrierPhone(String carrierPhone) {
        this.carrierPhone = carrierPhone;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getHistoryUser() {
        return historyUser;
    }

    public void setHistoryUser(String historyUser) {
        this.historyUser = historyUser;
    }

    public boolean isHistoryUser() {
        return "1".equals(historyUser);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIfDriverAgreeInvitation() {
        return ifDriverAgreeInvitation;
    }

    public void setIfDriverAgreeInvitation(String ifDriverAgreeInvitation) {
        this.ifDriverAgreeInvitation = ifDriverAgreeInvitation;
    }

    public String getIfEscortAgreeInvitation() {
        return ifEscortAgreeInvitation;
    }

    public void setIfEscortAgreeInvitation(String ifEscortAgreeInvitation) {
        this.ifEscortAgreeInvitation = ifEscortAgreeInvitation;
    }

    public String getIsCarrier() {
        return isCarrier;
    }

    public void setIsCarrier(String isCarrier) {
        this.isCarrier = isCarrier;
    }

    public boolean hasCarrierRole() {
        return !TextUtils.isEmpty(isCarrier) && isCarrier.equals("1");
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", userUUID='" + userUUID + '\'' +
                ", userName='" + userName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", avatorsrc='" + avatorsrc + '\'' +
                ", carrierPhone='" + carrierPhone + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                ", company='" + company + '\'' +
                ", historyUser='" + historyUser + '\'' +
                ", linesCount='" + linesCount + '\'' +
                ", role='" + role + '\'' +
                ", ifDriverAgreeInvitation='" + ifDriverAgreeInvitation + '\'' +
                ", ifEscortAgreeInvitation='" + ifEscortAgreeInvitation + '\'' +
                ", isCarrier='" + isCarrier + '\'' +
                '}';
    }
}
