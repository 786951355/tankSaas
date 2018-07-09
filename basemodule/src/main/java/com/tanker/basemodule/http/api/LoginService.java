package com.tanker.basemodule.http.api;

import com.tanker.basemodule.model.RetrieveModel;
import com.tanker.basemodule.model.login_model.LoginModel;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by ronny on 2018/3/
 */

public interface LoginService {


    /**
     * 注册
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/user/userRegister")
    Observable<HttpResult<LoginModel>> register(@Body HashMap<String, String> hashMap);

    /**
     * 忘记/修改 登陆密码
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/editpassword")
    Observable<HttpResult<String>> resetPwd(@Body HashMap<String, String> hashMap);

    /**
     * 密码/验证码 登陆
     *
     * @param paramMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/user/userLogin")
    Observable<HttpResult<LoginModel>> login(@Body HashMap<String, String> paramMap);

    /**
     * 获取验证码
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/user/getAuthCode")
    Observable<HttpResult<String>> getCode(@Body HashMap<String, String> hashMap);

    /**
     * 找回密码
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/user/findPassword")
    Observable<HttpResult<RetrieveModel>> retrievePwd(@Body HashMap<String, String> hashMap);


    /**
     * 下载文件
     *
     * @param Path
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String Path);

}
