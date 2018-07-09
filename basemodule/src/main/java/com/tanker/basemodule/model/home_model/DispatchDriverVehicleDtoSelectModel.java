package com.tanker.basemodule.model.home_model;

import java.util.List;

/**
 * 本地选择完善信息调度车辆
 */
public class DispatchDriverVehicleDtoSelectModel {
    private int postion;
    private List<DispatchDriverVehicleDto> dispatchDriverVehicleDto;

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public List<DispatchDriverVehicleDto> getDispatchDriverVehicleDto() {
        return dispatchDriverVehicleDto;
    }

    public void setDispatchDriverVehicleDto(List<DispatchDriverVehicleDto> dispatchDriverVehicleDto) {
        this.dispatchDriverVehicleDto = dispatchDriverVehicleDto;
    }

    public static class DispatchDriverVehicleDto {

        private String driverId;
        private String followVehiclesId;
        private String vehicleId;
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
}
