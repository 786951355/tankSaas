package com.tanker.basemodule.model.home_model;

/**
 * 完善信息调度车辆
 */
public class CompleteDispatchRequest {

    //司机
    private String driverId;
    //押车员
    private String followVehiclesId;
    //车辆
    private String vehicleId;
    //吨位
    private String transportTonnage;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getFollowVehiclesId() {
        return followVehiclesId;
    }

    public void setFollowVehiclesId(String followVehiclesId) {
        this.followVehiclesId = followVehiclesId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getTransportTonnage() {
        return transportTonnage;
    }

    public void setTransportTonnage(String transportTonnage) {
        this.transportTonnage = transportTonnage;
    }
}
