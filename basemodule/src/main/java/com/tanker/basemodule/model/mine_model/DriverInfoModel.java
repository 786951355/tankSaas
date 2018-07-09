package com.tanker.basemodule.model.mine_model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by Administrator on 2018/3/29.
 */

public class DriverInfoModel implements Parcelable {

    /**
     * driverId : 25
     * driverName : 大法师
     * auditStatus : 1
     * role : 2
     * enabled : 1
     * driverPhone : 213123123
     * drivingIdentityBackFile : sdfsafsadf.jpg
     * drivingIdentityFrontFile  : 324234234.jpg
     * drivingLicenseImage  : 12342rwsfd.jpg
     * qualificationCertificateImage  : 1231234wrfsdfsadf.jpg
     * followVehiclesImage  : asdfasdfsafsa.jpg
     */

    private String driverId = "";
    private String driverName = "";
    //审核状态, 0待审核;1审核通过;2审核驳回
    private String auditStatus;
    //司机/押车员角色:1为司机,2为押车员,3为司机和押车员
    private String role = "";
    private String enabled;
    private String driverPhone = "";
    private String drivingIdentityBackFile = "";
    private String drivingIdentityFrontFile = "";
    private String drivingLicenseImage = "";
    private String qualificationCertificateImage = "";
    private String followVehiclesImage = "";
    private String drivingIdentityNumber = "";
    //是否允许删除 0为允许,1为不允许
    private String ifAllowUpdate;
    private String invitationState; //0 待邀请 1 已邀请 2已拒绝 3 已开通

    public String getDrivingIdentityNumber() {
        return drivingIdentityNumber;
    }

    public void setDrivingIdentityNumber(String drivingIdentityNumber) {
        this.drivingIdentityNumber = drivingIdentityNumber;
    }

    public DriverInfoModel() {
    }

    public DriverInfoModel(DriverInfoModel driverInfoModel){
        this.driverId=driverInfoModel.driverId;
        this.driverName=driverInfoModel.driverName;
        this.auditStatus=driverInfoModel.auditStatus;
        this.role=driverInfoModel.role;
        this.enabled=driverInfoModel.enabled;
        this.driverPhone=driverInfoModel.driverPhone;
        this.drivingIdentityBackFile=driverInfoModel.drivingIdentityBackFile;
        this.drivingIdentityFrontFile=driverInfoModel.drivingIdentityFrontFile;
        this.drivingLicenseImage=driverInfoModel.drivingLicenseImage;
        this.qualificationCertificateImage=driverInfoModel.qualificationCertificateImage;
        this.followVehiclesImage=driverInfoModel.followVehiclesImage;
        this.drivingIdentityNumber=driverInfoModel.drivingIdentityNumber;
        this.ifAllowUpdate = driverInfoModel.ifAllowUpdate;
        this.invitationState = driverInfoModel.invitationState;
    }

    protected DriverInfoModel(Parcel in) {
        driverId = in.readString();
        driverName = in.readString();
        auditStatus = in.readString();
        role = in.readString();
        enabled = in.readString();
        driverPhone = in.readString();
        drivingIdentityBackFile = in.readString();
        drivingIdentityFrontFile = in.readString();
        drivingLicenseImage = in.readString();
        qualificationCertificateImage = in.readString();
        followVehiclesImage = in.readString();
        drivingIdentityNumber = in.readString();
        ifAllowUpdate = in.readString();
        invitationState = in.readString();
    }

    public static final Creator<DriverInfoModel> CREATOR = new Creator<DriverInfoModel>() {
        @Override
        public DriverInfoModel createFromParcel(Parcel in) {
            return new DriverInfoModel(in);
        }

        @Override
        public DriverInfoModel[] newArray(int size) {
            return new DriverInfoModel[size];
        }
    };


    public String getIfAllowUpdate() {
        return ifAllowUpdate;
    }

    public boolean isAllowUpdate(){
        return "0".equals(ifAllowUpdate);
    }

    public void setIfAllowUpdate(String ifAllowUpdate) {
        this.ifAllowUpdate = ifAllowUpdate;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDrivingIdentityBackFile() {
        return drivingIdentityBackFile;
    }

    public void setDrivingIdentityBackFile(String drivingIdentityBackFile) {
        this.drivingIdentityBackFile = drivingIdentityBackFile;
    }

    public String getDrivingIdentityFrontFile() {
        return drivingIdentityFrontFile;
    }

    public void setDrivingIdentityFrontFile(String drivingIdentityFrontFile) {
        this.drivingIdentityFrontFile = drivingIdentityFrontFile;
    }

    public String getDrivingLicenseImage() {
        return drivingLicenseImage;
    }

    public void setDrivingLicenseImage(String drivingLicenseImage) {
        this.drivingLicenseImage = drivingLicenseImage;
    }

    public String getQualificationCertificateImage() {
        return qualificationCertificateImage;
    }

    public void setQualificationCertificateImage(String qualificationCertificateImage) {
        this.qualificationCertificateImage = qualificationCertificateImage;
    }

    public String getFollowVehiclesImage() {
        return followVehiclesImage;
    }

    public void setFollowVehiclesImage(String followVehiclesImage) {
        this.followVehiclesImage = followVehiclesImage;
    }

    public String getInvitationState() {
        return invitationState;
    }

    public void setInvitationState(String invitationState) {
        this.invitationState = invitationState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(driverId);
        parcel.writeString(driverName);
        parcel.writeString(auditStatus);
        parcel.writeString(role);
        parcel.writeString(enabled);
        parcel.writeString(driverPhone);
        parcel.writeString(drivingIdentityBackFile);
        parcel.writeString(drivingIdentityFrontFile);
        parcel.writeString(drivingLicenseImage);
        parcel.writeString(qualificationCertificateImage);
        parcel.writeString(followVehiclesImage);
        parcel.writeString(drivingIdentityNumber);
        parcel.writeString(ifAllowUpdate);
        parcel.writeString(invitationState);
    }

    public boolean hasIdFrontImg() {
        return !TextUtils.isEmpty(drivingIdentityFrontFile);
    }

    public boolean hasIDBackImg(){
        return !TextUtils.isEmpty(drivingIdentityBackFile);
    }

    public boolean hasDrivingLicenseImg(){
        return !TextUtils.isEmpty(drivingLicenseImage);
    }

    public boolean hasFollowVehicleImg(){
        return !TextUtils.isEmpty(followVehiclesImage);
    }

    public boolean hasQualiCertificateImg(){
        return !TextUtils.isEmpty(qualificationCertificateImage);
    }
}
