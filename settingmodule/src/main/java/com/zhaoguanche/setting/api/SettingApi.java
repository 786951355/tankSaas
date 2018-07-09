package com.zhaoguanche.setting.api;

import com.tanker.basemodule.http.api.RetroAPIFactory;

/**
 * author zhanglei
 * date 2018/6/11
 * description 接口请求
 **/
public class SettingApi {

    static {
        informApi = new SettingApi();
    }

    private static SettingApi informApi;
    private final SettingService informService;

    public SettingApi() {
        informService = RetroAPIFactory.create(SettingService.class);
    }

    public static SettingApi getInstance() {
        return informApi;
    }

}
