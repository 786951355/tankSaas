package com.tanker.basemodule.model.mine_model;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class DriverListModel {

    /**
     * total : 2
     * driverList : [{"driverId":"25","driverName":"大法师","auditStatus":"1","role":"2","enabled":"1","driverPhone":"213123123"},{"driverId":"25","driverName":"大法师","auditStatus":"1","role":"2","enabled":"1","driverPhone":"213123123"}]
     */

    private String total;
    private List<DriverModel> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<DriverModel> getDriverList() {
        return rows;
    }

    public void setDriverList(List<DriverModel> driverList) {
        this.rows = driverList;
    }

//    public static class DriverListBean {
//        /**
//         * driverId : 25
//         * driverName : 大法师
//         * auditStatus : 1
//         * role : 2
//         * enabled : 1
//         * driverPhone : 213123123
//         */
//
//        private String driverId;
//        private String driverName;
//        private String auditStatus;
//        private String role;
//        private String enabled;
//        private String driverPhone;
//
//        public String getCarId() {
//            return driverId;
//        }
//
//        public void setCarId(String driverId) {
//            this.driverId = driverId;
//        }
//
//        public String getDriverName() {
//            return driverName;
//        }
//
//        public void setDriverName(String driverName) {
//            this.driverName = driverName;
//        }
//
//        public String getAuditStatus() {
//            return auditStatus;
//        }
//
//        public void setAuditStatus(String auditStatus) {
//            this.auditStatus = auditStatus;
//        }
//
//        public String getRole() {
//            return role;
//        }
//
//        public void setRole(String role) {
//            this.role = role;
//        }
//
//        public String getEnabled() {
//            return enabled;
//        }
//
//        public void setEnabled(String enabled) {
//            this.enabled = enabled;
//        }
//
//        public String getDriverPhone() {
//            return driverPhone;
//        }
//
//        public void setDriverPhone(String driverPhone) {
//            this.driverPhone = driverPhone;
//        }
//    }
}
