package com.tanker.basemodule.http.api;

import com.tanker.basemodule.model.app_model.SplashPicModel;
import com.tanker.basemodule.model.app_model.StatisticalHeadModel;
import com.tanker.basemodule.model.login_model.ConfigInfo;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AppService {

    @Headers("Content-Type: application/json")
    @POST("/appBaseConfig/getAppStartPicture")
    Observable<HttpResult<SplashPicModel>> requestSplashImg(@Body HashMap<String, String> hashMap);

    @Headers("Content-Type: application/json")
    @POST("/appBaseConfig/getBusinessInfo")
    Observable<HttpResult<ConfigInfo>> requestConfig(@Body HashMap<String, String> hashMap);

    /**
     * @author lwj
     * @describe 通知已读接口(根据通知id ， 更改通知为已读通知)
     * @params
     * @time 2018/6/11 15:05
     */
    @Headers("Content-Type: application/json")
    @POST("/notificationCenter/updateIsRead")
    Observable<HttpResult<String>> updateIsRead(@Body HashMap<String, Object> hashMap);

    @Headers("Content-Type: application/json")
    @POST("/notificationCenter/getStatisticalHead")
    Observable<HttpResult<StatisticalHeadModel>> getStatisticalHead(@Body HashMap<String, String> hashMap);

    @Headers("Content-Type: application/json")
    @POST("/notificationCenter/clearIsRead")
    Observable<HttpResult<String>> clearIsRead(@Body HashMap<String, String> hashMap);

    /**
     * @author lwj
     * @describe 用户接收到通知以后回调接口统计数量
     * @params
     * @time 2018/6/25 12:01
     */
    @Headers("Content-Type: application/json")
    @POST("/notificationCenter/receiveNotification")
    Observable<HttpResult<String>> receiveNotification(@Body HashMap<String, Object> hashMap);

    /**
     * @author lwj
     * @describe 用户在app内阅读公告, 返回时统计阅读时长
     * @params
     * @time 2018/6/25 12:01
     */
    @Headers("Content-Type: application/json")
    @POST(" /notificationCenter/notificationReadBack")
    Observable<HttpResult<String>> notificationReadBack(@Body HashMap<String, Object> hashMap);

}
