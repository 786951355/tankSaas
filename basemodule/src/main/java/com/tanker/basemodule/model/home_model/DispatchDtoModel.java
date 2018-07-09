package com.tanker.basemodule.model.home_model;

/**
 * 司机押车员信息
 */
public class DispatchDtoModel {

    private String dirverId;
    private String followId;
    private String vehicleId;
    private String vehicleNumber;

    public String getDirverId() {
        return dirverId;
    }

    public void setDirverId(String dirverId) {
        this.dirverId = dirverId;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

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
}
