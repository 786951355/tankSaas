package com.tanker.basemodule.model.home_model;

import java.io.Serializable;
import java.util.List;

/**
 * 调度车辆，车辆列表信息
 */
public class VehicleListModel implements Serializable {


    /**
     * total : 1
     * rows : [{"vehicleId":"80","vehicleType":"1","vehicleNumber":"湘B23233","trailerNumber":"沪A32132","auditStatus":"0","loadingTonnage":"22.000"}]
     */

    private String total;
    private List<RowsBean> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * vehicleId : 80
         * vehicleType : 1
         * vehicleNumber : 湘B23233
         * trailerNumber : 沪A32132
         * auditStatus : 0
         * loadingTonnage : 22.000
         */

        private String vehicleId;
        //车俩类型,1.半挂车、2,.一体车
        private String vehicleType;
        private String vehicleNumber;
        private String trailerNumber;
        private String auditStatus;
        private String loadingTonnage;

        private boolean isSelectedDriver = false;
        private Integer bindedDriverPosition = -1;
        private Integer bindedGuarderPosition = -1;
        private boolean isOpenList = false;
        private boolean isFinished = false;


        public boolean isFinished() {
            return bindedDriverPosition != 1 && bindedGuarderPosition != -1;
        }

        public void setFinished(boolean finished) {
            isFinished = finished;
        }

        public boolean isOpenList() {
            return isOpenList;
        }

        public void setOpenList(boolean openList) {
            isOpenList = openList;
        }

        public int getBindedDriverPosition() {
            return bindedDriverPosition;
        }

        public void setBindedDriverPosition(int bindedDriverPosition) {
            this.bindedDriverPosition = bindedDriverPosition;
        }

        public boolean getIsSelectedDriver() {
            return isSelectedDriver;
        }

        public void setIsSelectedDriver(boolean isSelectedDriver) {
            this.isSelectedDriver = isSelectedDriver;
        }


        public int getBindedGuarderPosition() {
            return bindedGuarderPosition;
        }

        public void setBindedGuarderPosition(int bindedGuarderPosition) {
            this.bindedGuarderPosition = bindedGuarderPosition;
        }

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
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

        public String getVehicleTypeText() {
            return "1".equals(vehicleType) ? "半挂车" : "一体车";
        }

        public boolean contains(int position) {
            return bindedDriverPosition != position && bindedGuarderPosition != position;
        }
    }
}
