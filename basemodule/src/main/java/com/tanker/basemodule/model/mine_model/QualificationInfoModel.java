package com.tanker.basemodule.model.mine_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class QualificationInfoModel implements Parcelable {
    //车主id
    private String userId;
    //车主姓名
    private String userName;
    //车主公司名称
    private String carrierCompanyName;
    //车主与公司的资质认证情况,-2待完善，-1未维护,0待审核;1审核通过;2审核驳回；
    private String auditStatus;
    //身份证号码
    private String identityNumber;
    //身份证前面
    private String identityFrontFile;
    //身份证背面
    private String identityBackFile;
    //营业执照
    private String tradingCertificateImage;
    //道路运输许可证
    private String roadTransportCertificateImage;
    private String companyAuthorizationFile;
    //是否开票,1开票，2不开票
    private String ifInvoice;
    //道路许可证的认证状态  -1未维护,0认证中;1审核通过;2审核驳回；
    private String roadLicenseStatus;
    //用于授权书页面是否展示退出按钮
    private boolean isShowExit=true;


    protected QualificationInfoModel(Parcel in) {
        userId = in.readString();
        userName = in.readString();
        carrierCompanyName = in.readString();
        auditStatus = in.readString();
        identityNumber = in.readString();
        identityFrontFile = in.readString();
        identityBackFile = in.readString();
        tradingCertificateImage = in.readString();
        roadTransportCertificateImage = in.readString();
        companyAuthorizationFile = in.readString();
        ifInvoice = in.readString();
        roadLicenseStatus = in.readString();
        isShowExit = in.readByte() != 0;
    }

    public static final Creator<QualificationInfoModel> CREATOR = new Creator<QualificationInfoModel>() {
        @Override
        public QualificationInfoModel createFromParcel(Parcel in) {
            return new QualificationInfoModel(in);
        }

        @Override
        public QualificationInfoModel[] newArray(int size) {
            return new QualificationInfoModel[size];
        }
    };

    public boolean isShowExit() {
        return isShowExit;
    }

    public void setShowExit(boolean showExit) {
        isShowExit = showExit;
    }


    public QualificationInfoModel() {
    }


    public String getId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCarrierCompanyName() {
        return carrierCompanyName;
    }

    public void setCarrierCompanyName(String carrierCompanyName) {
        this.carrierCompanyName = carrierCompanyName;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getIdentityFrontFile() {
        return identityFrontFile;
    }

    public void setIdentityFrontFile(String identityFrontFile) {
        this.identityFrontFile = identityFrontFile;
    }

    public String getIdentityBackFile() {
        return identityBackFile;
    }

    public void setIdentityBackFile(String identityBackFile) {
        this.identityBackFile = identityBackFile;
    }

    public String getTradingCertificateImage() {
        return tradingCertificateImage;
    }

    public void setTradingCertificateImage(String tradingCertificateImage) {
        this.tradingCertificateImage = tradingCertificateImage;
    }

    public String getRoadTransportCertificateImage() {
        return roadTransportCertificateImage;
    }

    public void setRoadTransportCertificateImage(String roadTransportCertificateImage) {
        this.roadTransportCertificateImage = roadTransportCertificateImage;
    }

    public String getCompanyAuthorizationFile() {
        return companyAuthorizationFile;
    }

    public void setCompanyAuthorizationFile(String companyAuthorizationFile) {
        this.companyAuthorizationFile = companyAuthorizationFile;
    }

    public String getIfInvoice() {
        return ifInvoice;
    }

    public void setIfInvoice(String ifInvoice) {
        this.ifInvoice = ifInvoice;
    }

    public String getRoadLicenseStatus() {
        return roadLicenseStatus;
    }

    public void setRoadLicenseStatus(String roadLicenseStatus) {
        this.roadLicenseStatus = roadLicenseStatus;
    }



    public List<String> getImageList() {
        ArrayList<String> strings = new ArrayList<>();
        if (!tradingCertificateImage.isEmpty()) {
            strings.add(tradingCertificateImage);
        }
//        if (!roadTransportCertificateImage.isEmpty()) {
//            strings.add(roadTransportCertificateImage);
//        }
        if (!identityFrontFile.isEmpty()) {
            strings.add(identityFrontFile);
        }
        if (!identityBackFile.isEmpty()) {
            strings.add(identityBackFile);
        }
        if (!companyAuthorizationFile.isEmpty()) {
            strings.add(companyAuthorizationFile);
        }
        return strings;
    }

    public boolean isInvoice() {
        return "1".equals(ifInvoice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(carrierCompanyName);
        dest.writeString(auditStatus);
        dest.writeString(identityNumber);
        dest.writeString(identityFrontFile);
        dest.writeString(identityBackFile);
        dest.writeString(tradingCertificateImage);
        dest.writeString(roadTransportCertificateImage);
        dest.writeString(companyAuthorizationFile);
        dest.writeString(ifInvoice);
        dest.writeString(roadLicenseStatus);
        dest.writeByte((byte) (isShowExit ? 1 : 0));
    }
}
