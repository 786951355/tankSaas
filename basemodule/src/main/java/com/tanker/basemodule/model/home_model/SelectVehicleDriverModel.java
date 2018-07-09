package com.tanker.basemodule.model.home_model;

import java.io.Serializable;
import java.util.List;

/**
 * 选择的车辆司机信息
 */
public class SelectVehicleDriverModel implements Serializable {
    /**
     * 车辆名称
     */
    private String vehicleName;
    /**
     * 吨位
     */
    private String tonnage;
    /**
     * 司机、押车员信息列表
     */
    private List<VehicleDriverModel> vehicleDriverInfos;

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getTonnage() {
        return tonnage;
    }

    public void setTonnage(String tonnage) {
        this.tonnage = tonnage;
    }

    public List<VehicleDriverModel> getVehicleDriverInfos() {
        return vehicleDriverInfos;
    }

    public void setVehicleDriverInfos(List<VehicleDriverModel> vehicleDriverInfos) {
        this.vehicleDriverInfos = vehicleDriverInfos;
    }
}
