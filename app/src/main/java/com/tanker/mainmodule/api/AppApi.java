package com.tanker.mainmodule.api;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.http.HttpParam;
import com.tanker.basemodule.http.api.AppService;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.http.api.RetroAPIFactory;
import com.tanker.basemodule.model.app_model.SplashPicModel;
import com.tanker.basemodule.model.login_model.ConfigInfo;

import java.util.HashMap;

import io.reactivex.Observable;


public class AppApi {
    static {
        instance = new AppApi();
    }

    private static AppApi instance;
    private final AppService appService;

    public AppApi() {
        appService = RetroAPIFactory.create(AppService.class);
    }

    public static AppApi getInstance() {
        return instance;
    }

    public Observable<HttpResult<ConfigInfo>> requestConfig() {
        HashMap<String, String> hashMap = HttpParam.createParams(true)
                .putParam(AppConstants.PARAM_PLATFORM, "2")
                .end();
        return appService.requestConfig(hashMap);
    }

    public Observable<HttpResult<SplashPicModel>> requestSplashImg() {
        HashMap<String, String> hashMap = HttpParam.createParams(true)
                .putParam(AppConstants.PARAM_PLATFORM, "2")
                .end();
        return appService.requestSplashImg(hashMap);
    }

}
