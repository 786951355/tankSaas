package com.tanker.loginmodule.constants;

/**
 * Created by ronny on 2018/3/28.
 */

public class LoginConstants {
    /**
     * 用户登录类型
     */
    public static final String TYPE_PHONE_NUM_PWD_LOGIN = "2", TYPE_VERIFY_CODE_LOGIN = "1";

    public static final String REGISTER_KEY = "RegisterKey";
    public static final String LOGIN_KEY = "loginKey";
    public static final String RETRIEVE_KEY = "retrieveKey";
    public static final String PLATFORM = "platform";
    public static final String GET_CODE_KEY = "getCodeKey";
    public static final String PLATFORM_VALUE = "2";
    /**
     * 注册、登录、找回密码
     */
    public static final String REGISTER_SMSTEMPLATETYPE = "1",
            LOGIN_SMSTEMPLATETYPE = "2", RETRIEVE_REGISTER_SMSTEMPLATETYPE = "3";
    //验证码
    public static final String PARAM_VERFICATION_CODE = "verifyCode";
    //找回密码验证码
    public static final String RETRIEVE_VERIFY_CODE = "verificationCode";
    public static final String OLD_PWD = "oldPassWord", NEW_PWD = "newPassWord";
    //发送验证码模板类型
    public static final String SMSTEMPLATETYPE = "smsTemplateType";
}
