package com.tanker.basemodule.model.mine_model;

/**
 * Created by Administrator on 2018/3/29.
 */

public class UserInfoModel {


    /**
     * userId : 20
     * userName : 吴中武
     * userUniqueKey : 444
     * mobilePhone : 17521505246
     * qualification : -1  //车主与公司的资质认证情况,-1未维护,0待审核;1审核通过;2审核驳回；
     * orderTotal : 123
     * lineTotal : 233
     * vehicleTotal : 234
     * driverTotal : 25
     */

    private String userId;
    private String userName;
    private String userUniqueKey;
    private String mobilePhone;
    //基本资质认证维护状态  -2待完善，-1未维护,0待审核;1审核通过;2审核驳回；
    private String qualification;
    //道路运输许可证维护状态  -2待完善，-1未维护,0待审核;1审核通过;2审核驳回；
    private String roadLicenseStatus;
    private String orderTotal;
    private String lineTotal;
    private String vehicleTotal;
    private String driverTotal;
    private String avatorsrc;
    private String carrierOrderCount; // 运单数（司机）
    private String carrierUserCount; // 车主数（司机）
    private String role; //角色 0：司机/押车员/(司机和押车员) 1：委托方 2：承运商
    private String ifDriverAgreeInvitation; //司机是否接受邀请 0：否 1：是
    private String ifEscortAgreeInvitation; //押车员是否接受邀请 0：否 1：是
    private String isCarrier; //1:有车主角色 0:没有车主角色

    public String getRoadLicenseStatus() {
        return roadLicenseStatus;
    }

    public void setRoadLicenseStatus(String roadLicenseStatus) {
        this.roadLicenseStatus = roadLicenseStatus;
    }

    public void setAvatorsrc(String avatorsrc) {
        this.avatorsrc = avatorsrc;
    }

    public String getAvatorsrc() {
        return avatorsrc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUniqueKey() {
        return userUniqueKey;
    }

    public void setUserUniqueKey(String userUniqueKey) {
        this.userUniqueKey = userUniqueKey;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(String lineTotal) {
        this.lineTotal = lineTotal;
    }

    public String getVehicleTotal() {
        return vehicleTotal;
    }

    public void setVehicleTotal(String vehicleTotal) {
        this.vehicleTotal = vehicleTotal;
    }

    public String getDriverTotal() {
        return driverTotal;
    }

    public void setDriverTotal(String driverTotal) {
        this.driverTotal = driverTotal;
    }

    public String getCarrierOrderCount() {
        return carrierOrderCount;
    }

    public void setCarrierOrderCount(String carrierOrderCount) {
        this.carrierOrderCount = carrierOrderCount;
    }

    public String getCarrierUserCount() {
        return carrierUserCount;
    }

    public void setCarrierUserCount(String carrierUserCount) {
        this.carrierUserCount = carrierUserCount;
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
}
