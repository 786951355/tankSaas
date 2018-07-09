package com.tanker.basemodule.model.mine_model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ronny on 2018/3/27.
 */

public class CarModel implements Parcelable {


    /**
     * vehicleId : 20
     * vehicleNumber  :  赣A12345
     * trailerNumber : 赣B23456
     * auditStatus  : 1
     * loadingTonnage : 22
     */

    private String vehicleId;
    private String vehicleNumber;
    private String trailerNumber;
    //    审核状态, 0待审核;1审核通过;2审核驳回
    private String auditStatus;
    private String loadingTonnage;
    //是否允许删除 0为允许,1为不允许
    private String ifAllowUpdate;

    private boolean isOpenList = false;

    public CarModel(CarInfoModel carInfo) {
        if (carInfo != null) {
            vehicleId = carInfo.getVehicleId();
            vehicleNumber = carInfo.getVehicleNumber();
            trailerNumber = carInfo.getTrailerNumber();
            auditStatus = carInfo.getAuditStatus();
            loadingTonnage = carInfo.getLoadingTonnage();
        }
    }

    public String getIfAllowUpdate() {
        return ifAllowUpdate;
    }

    public void setIfAllowUpdate(String ifAllowUpdate) {
        this.ifAllowUpdate = ifAllowUpdate;
    }

    public boolean isAllowDelete(){
        return "0".equals(ifAllowUpdate);
    }

    public boolean isOpenList() {
        return isOpenList;
    }

    public void setOpenList(boolean openList) {
        isOpenList = openList;
    }

    public CarModel() {
    }

    public CarModel(String vehicleNumber, String trailerNumber, String tonnage, String state) {
        this.vehicleNumber = vehicleNumber;
        this.trailerNumber = trailerNumber;
        this.loadingTonnage = tonnage;
        this.auditStatus = state;
    }

    public CarModel(Parcel in) {
        vehicleId = in.readString();
        vehicleNumber = in.readString();
        trailerNumber = in.readString();
        auditStatus = in.readString();
        loadingTonnage = in.readString();
    }

    public static final Creator<CarModel> CREATOR = new Creator<CarModel>() {
        @Override
        public CarModel createFromParcel(Parcel in) {
            return new CarModel(in);
        }

        @Override
        public CarModel[] newArray(int size) {
            return new CarModel[size];
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

    public String getAuditText() {
        switch (auditStatus) {
            case "0":
                return "待认证";
            case "1":
                return "已认证";
            case "2":
                return "已驳回";
        }
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
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        CarModel carModel = (CarModel) o;

        if (vehicleId == null) return null != carModel.getVehicleId();
        if (vehicleNumber == null) return null != carModel.getVehicleNumber();
        if (trailerNumber == null) return null != carModel.getTrailerNumber();
        if (auditStatus == null) return null != carModel.getAuditStatus();
        if (loadingTonnage == null) return null != carModel.getLoadingTonnage();

        if (!vehicleId.equals(carModel.vehicleId)) return false;
        if (!vehicleNumber.equals(carModel.vehicleNumber)) return false;
        if (!trailerNumber.equals(carModel.trailerNumber)) return false;
        if (!auditStatus.equals(carModel.auditStatus)) return false;
        return loadingTonnage.equals(carModel.loadingTonnage);
    }

    @Override
    public int hashCode() {
        int result = vehicleId.hashCode();
        result = 31 * result + vehicleNumber.hashCode();
        result = 31 * result + trailerNumber.hashCode();
        result = 31 * result + auditStatus.hashCode();
        result = 31 * result + loadingTonnage.hashCode();
        return result;
    }


//    public static void  main(String[] args ){
//        CarModel carModel = new CarModel();
//        carModel.setAuditStatus("2");
//        carModel.setLoadingTonnage("34");
//        carModel.setVehicleId("3");
//        carModel.setTrailerNumber("fsdfsd");
//        carModel.setVehicleNumber("fdsfds");
//
//
//        CarModel carModel2 = new CarModel();
//        carModel2.setAuditStatus(null);
//        carModel2.setLoadingTonnage("34");
//        carModel2.setVehicleId("3");
//        carModel2.setTrailerNumber("fsdfsd");
//        carModel2.setVehicleNumber("fdsfds");
//
//        System.out.println(carModel.equals(carModel2));
//        System.out.println(null==null);
//
//    }

    @Override
    public String toString() {
        return "CarModel{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", trailerNumber='" + trailerNumber + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                ", loadingTonnage='" + loadingTonnage + '\'' +
                '}';
    }
}
