package com.tanker.basemodule.model.mine_model;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class CarListModel {

    /**
     * count : 2
     * VehicleDtoList : [{"vehicle":"20"," veghicleNumber ":" 赣A12345"," trailerNumber":"赣B23456"," auditStatus ":"1"," loadingTonnage":"22"},{"vehicle":"21"," veghicleNumber ":" 赣A12345"," trailerNumber":"赣B23456"," auditStatus ":"1"," loadingTonnage":"21"}]
     */

    private String total;
    private List<CarModel> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String count) {
        this.total = count;
    }

    public List<CarModel> getVehicleDtoList() {
        return rows;
    }

    public void setVehicleDtoList(List<CarModel> vehicleDtoList) {
        this.rows = vehicleDtoList;
    }


//    public static class CarModel {
//        /**
//         * vehicle : 20
//         *  veghicleNumber  :  赣A12345
//         *  trailerNumber : 赣B23456
//         *  auditStatus  : 1
//         *  loadingTonnage : 22
//         */
//
//        private String vehicle;
//        private String veghicleNumber;
//        private String trailerNumber;
//        private String auditStatus;
//        private String loadingTonnage;
//
//        public String getCarPlateNum() {
//            return vehicle;
//        }
//
//        public void setCarPlateNum(String vehicle) {
//            this.vehicle = vehicle;
//        }
//    }
}
