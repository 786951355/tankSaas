package com.tanker.basemodule.model.login_model;

public class LoginModel {

    private String token;
    private UserInfo userInfo;
//    private ConfigInfo configInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
//
//    public ConfigInfo getConfigInfo() {
//        return configInfo;
//    }
//
//    public void setConfigInfo(ConfigInfo configInfo) {
//        this.configInfo = configInfo;
//    }


}
