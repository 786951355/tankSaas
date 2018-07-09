package com.tanker.basemodule.model.home_model;

import java.io.Serializable;

/**
 * 调度车辆（选择车辆司机信息）
 */
public class DispatchVehicleDriverModel implements Serializable {
    /**
     * 车辆名称
     */
    private String vehicleName;
    /**
     * 吨位
     */
    private String tonnage;

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
}
