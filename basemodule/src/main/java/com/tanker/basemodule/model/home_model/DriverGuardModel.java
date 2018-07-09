package com.tanker.basemodule.model.home_model;

import java.io.Serializable;
import java.util.List;

/**
 * 车辆司机和押车员信息
 */
public class DriverGuardModel implements Serializable {

    /**
     * total : 2
     * rows : [{"driverId":"130","driverName":"WOJIU","driverPhone":"13453434344","auditStatus":"0","role":"2","enabled":"1","remark":null}]
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
         * driverId : 130
         * driverName : WOJIU
         * driverPhone : 13453434344
         * auditStatus : 0
         * role : 2
         * enabled : 1
         * remark : null
         */

        private String driverId;
        private String driverName;
        private String driverPhone;
        private String auditStatus;
        private String role;
        private String enabled;
        private String remark;
        private boolean driverSelected;
        private boolean guarderSelected;

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getDriverPhone() {
            return driverPhone;
        }

        public void setDriverPhone(String driverPhone) {
            this.driverPhone = driverPhone;
        }

        public String getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(String auditStatus) {
            this.auditStatus = auditStatus;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getEnabled() {
            return enabled;
        }

        public void setEnabled(String enabled) {
            this.enabled = enabled;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public void setDriverSelected(boolean driverSelected) {
            this.driverSelected = driverSelected;
        }

        public boolean isDriverSelected() {
            return driverSelected;
        }

        public void setGuarderSelected(boolean guarderSelected) {
            this.guarderSelected = guarderSelected;
        }

        public boolean isGuarderSelected() {
            return guarderSelected;
        }
    }
}
