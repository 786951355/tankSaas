package com.tanker.loginmodule.api;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.http.HttpParam;
import com.tanker.basemodule.http.api.AppService;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.http.api.LoginService;
import com.tanker.basemodule.http.api.RetroAPIFactory;
import com.tanker.basemodule.model.RetrieveModel;
import com.tanker.basemodule.model.login_model.ConfigInfo;
import com.tanker.basemodule.model.login_model.LoginModel;
import com.tanker.loginmodule.constants.LoginConstants;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by ronny on 2018/3/27.
 */

public class LoginApi {
    static {
        mLoginApi = new LoginApi();
    }

    private static LoginApi mLoginApi;
    private final LoginService mineService;
    private final AppService apiService;

    public LoginApi() {
        apiService = RetroAPIFactory.create(AppService.class);
        mineService = RetroAPIFactory.create(LoginService.class);
    }

    public static LoginApi getInstance() {
        return mLoginApi;
    }

    public Observable<HttpResult<LoginModel>> register(String platform, String userPhone, String UserPassword, String verficationCode) {
        HashMap<String, String> paramMap = HttpParam.createParams(true)
                .putParam(LoginConstants.PLATFORM, platform)
                .putParam(LoginConstants.PARAM_VERFICATION_CODE, verficationCode)
                .putParam(AppConstants.PARAM_PLATFORM, "2")
                .putParam(AppConstants.PARAM_PHONE, userPhone)
                .putParam(AppConstants.PARAM_PASSWORD, UserPassword)
                .end();
        Logger.i(getClass().getName(), paramMap + "====");
        return mineService.register(paramMap);
    }


    public Observable<HttpResult<LoginModel>> login(String userPhone, String userPwdOrCode, String loginType) {
        HashMap<String, String> paramMap = HttpParam.createParams(true)
                .putParam(AppConstants.PARAM_PLATFORM, "2")
                .putParam(AppConstants.PARAM_PHONE, userPhone)
                .putParam(AppConstants.PARAM_PASSWORD, userPwdOrCode)
                .putParam(AppConstants.PARAM_LOGIN_TYPE, loginType)
                .end();
        Logger.i(getClass().getName(), paramMap + "===");
        return mineService.login(paramMap);
    }

    /**
     * 获取验证码
     *
     * @param mobilePhone
     * @param platform
     * @param smsTemplateType
     * @return
     */
    public Observable<HttpResult<String>> getCode(String mobilePhone, String platform, String smsTemplateType) {
        HashMap<String, String> paramMap = HttpParam.createParams(true)
                .putParam(AppConstants.PARAM_PHONE, mobilePhone)
                .putParam(LoginConstants.PLATFORM, platform)
                .putParam(LoginConstants.SMSTEMPLATETYPE, smsTemplateType)
                .end();
        return mineService.getCode(paramMap);
    }


    /**
     * 找回密码
     *
     * @param platform
     * @param userPhone
     * @param verficationCode
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public Observable<HttpResult<RetrieveModel>> retrievePwd(String platform, String userPhone, String verficationCode,
                                                             String oldPwd, String newPwd) {
        HashMap<String, String> paramMap = HttpParam.createParams(true)
                .putParam(AppConstants.PARAM_PLATFORM, platform)
                .putParam(AppConstants.PARAM_PHONE, userPhone)
                .putParam(LoginConstants.RETRIEVE_VERIFY_CODE, verficationCode)
                .putParam(LoginConstants.OLD_PWD, oldPwd)
                .putParam(LoginConstants.NEW_PWD, newPwd)
                .end();
        Logger.i(getClass().getName(), paramMap + "====");
        return mineService.retrievePwd(paramMap);
    }


    public Observable<HttpResult<ConfigInfo>> requestConfig() {
        HashMap<String, String> hashMap = HttpParam.createParams(true)
                .putParam(AppConstants.PARAM_PLATFORM, "2")
                .end();
        return apiService.requestConfig(hashMap);
    }

    public Observable<ResponseBody> downloadFile(String authBookPath) {
        return mineService.downloadFile(authBookPath);
    }
}
