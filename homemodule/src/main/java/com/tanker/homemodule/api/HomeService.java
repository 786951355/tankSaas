package com.tanker.homemodule.api;

import com.tanker.basemodule.http.api.HttpResult;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author lwj
 * @ClassName: HomeService
 * @Description: 订单管理接口
 * @date 2018/4/26
 */
public interface HomeService {


    @Headers("Content-Type: application/json")
    @POST("/common/uploadFile")
    Observable<HttpResult<String>> uploadFile(@Body HashMap<String, String> hashMap);
}
