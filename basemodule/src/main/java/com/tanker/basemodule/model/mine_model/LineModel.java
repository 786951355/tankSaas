package com.tanker.basemodule.model.mine_model;

import com.google.gson.Gson;

/**
 * Created by ronny on 2018/3/25.
 */

public class LineModel {

    /**
     * lineId : 20
     * startProvinceId : 123456
     * startProvinceName : 上海
     * startCityId :  10000
     * startCityName : 上海
     * endProvinceId : 123213
     * endProvinceName : 浙江
     * endCityId : 10002
     * endCityName : 杭州
     */

    private String lineId;
    private String startProvinceId;
    private String startProvinceName;
    private String startCityId;
    private String startCityName;
    private String endProvinceId;
    private String endProvinceName;
    private String endCityId;
    private String endCityName;

    public LineModel() {
    }

    public LineModel(String s, String e) {
        startCityName = s;
        endCityName = e;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getStartProvinceId() {
        return startProvinceId;
    }

    public void setStartProvinceId(String startProvinceId) {
        this.startProvinceId = startProvinceId;
    }

    public String getStartProvinceName() {
        return startProvinceName;
    }

    public void setStartProvinceName(String startProvinceName) {
        this.startProvinceName = startProvinceName;
    }

    public String getStartCityId() {
        return startCityId;
    }

    public void setStartCityId(String startCityId) {
        this.startCityId = startCityId;
    }

    public String getStartCityName() {
        return startCityName;
    }

    public void setStartCityName(String startCityName) {
        this.startCityName = startCityName;
    }

    public String getEndProvinceId() {
        return endProvinceId;
    }

    public void setEndProvinceId(String endProvinceId) {
        this.endProvinceId = endProvinceId;
    }

    public String getEndProvinceName() {
        return endProvinceName;
    }

    public void setEndProvinceName(String endProvinceName) {
        this.endProvinceName = endProvinceName;
    }

    public String getEndCityId() {
        return endCityId;
    }

    public void setEndCityId(String endCityId) {
        this.endCityId = endCityId;
    }

    public String getEndCityName() {
        return endCityName;
    }

    public void setEndCityName(String endCityName) {
        this.endCityName = endCityName;
    }


    public String toGson() {
        return new Gson().toJson(this);
    }
}
