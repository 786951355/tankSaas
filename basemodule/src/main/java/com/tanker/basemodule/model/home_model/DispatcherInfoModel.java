package com.tanker.basemodule.model.home_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.tanker.basemodule.model.mine_model.DriverModel;

/**
 * 车辆调度信息
 */
public class DispatcherInfoModel implements Parcelable {

    //车辆id
    private String carId;
    //车牌号
    private String carPlateNum;
    //承载吨位
    private String transportTonnage;
    private DriverModel driver;
    private DriverModel guarder;


    public DispatcherInfoModel() {
    }


    protected DispatcherInfoModel(Parcel in) {
        carId = in.readString();
        carPlateNum = in.readString();
        transportTonnage = in.readString();
        driver = in.readParcelable(DriverModel.class.getClassLoader());
        guarder = in.readParcelable(DriverModel.class.getClassLoader());
    }

    public static final Creator<DispatcherInfoModel> CREATOR = new Creator<DispatcherInfoModel>() {
        @Override
        public DispatcherInfoModel createFromParcel(Parcel in) {
            return new DispatcherInfoModel(in);
        }

        @Override
        public DispatcherInfoModel[] newArray(int size) {
            return new DispatcherInfoModel[size];
        }
    };

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarPlateNum() {
        return carPlateNum;
    }

    public void setCarPlateNum(String carPlateNum) {
        this.carPlateNum = carPlateNum;
    }

    public String getTransportTonnage() {
        return transportTonnage;
    }

    public void setTransportTonnage(String transportTonnage) {
        this.transportTonnage = transportTonnage;
    }

    public DriverModel getDriver() {
        return driver;
    }

    public void setDriver(DriverModel driver) {
        this.driver = driver;
    }

    public DriverModel getGuarder() {
        return guarder;
    }

    public void setGuarder(DriverModel guarder) {
        this.guarder = guarder;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(carId);
        dest.writeString(carPlateNum);
        dest.writeString(transportTonnage);
        dest.writeParcelable(driver, flags);
        dest.writeParcelable(guarder, flags);
    }
}
