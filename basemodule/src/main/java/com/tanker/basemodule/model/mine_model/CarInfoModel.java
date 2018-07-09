package com.tanker.basemodule.model.mine_model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.tanker.basemodule.adapter.PhotoPickerAdapter;
import com.tanker.basemodule.model.ImageBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/29.
 */

public class CarInfoModel implements Parcelable {

    /**
     * vehicleId : 20
     * vehicleNumber  :  赣A12345
     * trailerNumber : 赣B23456
     * auditStatus  : 1
     * loadingTonnage : 22
     * vehicle_type  : 1
     * driveCertificateImage  : 2DASDASDAS.jpg
     * roadTransportCertificateImage : asdasdasdasda.jpg
     */

    private String vehicleId = "";
    private String vehicleNumber = "";
    private String trailerNumber = "";
    //    审核状态, 0待审核;1审核通过;2审核驳回
    private String auditStatus;
    private String loadingTonnage = "";
    //车俩类型,1.半挂车、2,.一体车
    private String vehicleType = "1";
    private String driveCertificateImage = "";
    private String roadTransportCertificateImage = "";
    //是否允许删除 0为允许,1为不允许
    private String ifAllowUpdate;

    public CarInfoModel() {
    }

    protected CarInfoModel(Parcel in) {
        vehicleId = in.readString();
        vehicleNumber = in.readString();
        trailerNumber = in.readString();
        auditStatus = in.readString();
        loadingTonnage = in.readString();
        vehicleType = in.readString();
        driveCertificateImage = in.readString();
        roadTransportCertificateImage = in.readString();
        ifAllowUpdate = in.readString();
    }

    public String getIfAllowUpdate() {
        return ifAllowUpdate;
    }

    public boolean isAllowUpdate() {
        return "0".equals(ifAllowUpdate);
    }

    public void setIfAllowUpdate(String ifAllowUpdate) {
        this.ifAllowUpdate = ifAllowUpdate;
    }

    public static final Creator<CarInfoModel> CREATOR = new Creator<CarInfoModel>() {
        @Override
        public CarInfoModel createFromParcel(Parcel in) {
            return new CarInfoModel(in);
        }

        @Override
        public CarInfoModel[] newArray(int size) {
            return new CarInfoModel[size];
        }
    };

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getTrailerNumber() {
        return trailerNumber;
    }

    public void setTrailerNumber(String trailerNumber) {
        this.trailerNumber = trailerNumber;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getLoadingTonnage() {
        return loadingTonnage;
    }

    public void setLoadingTonnage(String loadingTonnage) {
        this.loadingTonnage = loadingTonnage;
    }

    public String getVehicle_type() {
        return vehicleType;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicleType = vehicle_type;
    }

    public String getDriveCertificateImage() {
        return driveCertificateImage;
    }

    public void setDriveCertificateImage(String driveCertificateImage) {
        this.driveCertificateImage = driveCertificateImage;
    }

    public String getRoadTransportCertificateImage() {
        return roadTransportCertificateImage;
    }

    public void setRoadTransportCertificateImage(String roadTransportCertificateImage) {
        this.roadTransportCertificateImage = roadTransportCertificateImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(vehicleId);
        parcel.writeString(vehicleNumber);
        parcel.writeString(trailerNumber);
        parcel.writeString(auditStatus);
        parcel.writeString(loadingTonnage);
        parcel.writeString(vehicleType);
        parcel.writeString(driveCertificateImage);
        parcel.writeString(roadTransportCertificateImage);
        parcel.writeString(ifAllowUpdate);
    }

    public boolean hasDrivingImg() {
        return !TextUtils.isEmpty(driveCertificateImage);
    }

    public boolean hasTransImage() {
        return !TextUtils.isEmpty(roadTransportCertificateImage);
    }

    public ArrayList<ImageBean> getImages() {
        ArrayList<ImageBean> images = new ArrayList<>();
        if (hasDrivingImg()) {
            images.add(new ImageBean(PhotoPickerAdapter.TYPE_PHOTO, null, null, driveCertificateImage));
        }
        if (hasTransImage()) {
            images.add(new ImageBean(PhotoPickerAdapter.TYPE_PHOTO, null, null, roadTransportCertificateImage));
        }
        return images;
    }
}
