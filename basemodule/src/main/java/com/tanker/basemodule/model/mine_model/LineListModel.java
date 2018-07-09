package com.tanker.basemodule.model.mine_model;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class LineListModel {

    /**
     * count : 2
     * lineDtoList : [{"lineId":"20","startProvinceId":"123456","startProvinceName":"上海","startCityId":" 10000","startCityName":"上海","endProvinceId":"123213","endProvinceName":"浙江","endCityId":"10002","endCityName":"杭州"},{"lineId":"21","startProvinceId":"123456","startProvinceName":"上海","startCityId":" 10002","startCityName":"杭州","endProvinceId":"123213","endProvinceName":"浙江","endCityId":"1000","endCityName":"上海"}]
     */

    private String total;
    private List<LineModel> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String count) {
        this.total = count;
    }

    public List<LineModel> getLineListModel() {
        return rows;
    }

    public void setLineListModel(List<LineModel> lineDtoList) {
        this.rows = lineDtoList;
    }

//    public static class LineModel {
//        /**
//         * lineId : 20
//         * startProvinceId : 123456
//         * startProvinceName : 上海
//         * startCityId :  10000
//         * startCityName : 上海
//         * endProvinceId : 123213
//         * endProvinceName : 浙江
//         * endCityId : 10002
//         * endCityName : 杭州
//         */
//
//        private String lineId;
//        private String startProvinceId;
//        private String startProvinceName;
//        private String startCityId;
//        private String startCityName;
//        private String endProvinceId;
//        private String endProvinceName;
//        private String endCityId;
//        private String endCityName;
//
//        public String getLineId() {
//            return lineId;
//        }
//
//        public void setLineId(String lineId) {
//            this.lineId = lineId;
//        }
//
//        public String getStartProvinceId() {
//            return startProvinceId;
//        }
//
//        public void setStartProvinceId(String startProvinceId) {
//            this.startProvinceId = startProvinceId;
//        }
//
//        public String getStartProvinceName() {
//            return startProvinceName;
//        }
//
//        public void setStartProvinceName(String startProvinceName) {
//            this.startProvinceName = startProvinceName;
//        }
//
//        public String getStartCityId() {
//            return startCityId;
//        }
//
//        public void setStartCityId(String startCityId) {
//            this.startCityId = startCityId;
//        }
//
//        public String getStartCityName() {
//            return startCityName;
//        }
//
//        public void setStartCityName(String startCityName) {
//            this.startCityName = startCityName;
//        }
//
//        public String getEndProvinceId() {
//            return endProvinceId;
//        }
//
//        public void setEndProvinceId(String endProvinceId) {
//            this.endProvinceId = endProvinceId;
//        }
//
//        public String getEndProvinceName() {
//            return endProvinceName;
//        }
//
//        public void setEndProvinceName(String endProvinceName) {
//            this.endProvinceName = endProvinceName;
//        }
//
//        public String getEndCityId() {
//            return endCityId;
//        }
//
//        public void setEndCityId(String endCityId) {
//            this.endCityId = endCityId;
//        }
//
//        public String getEndCityName() {
//            return endCityName;
//        }
//
//        public void setEndCityName(String endCityName) {
//            this.endCityName = endCityName;
//        }
//    }
}
