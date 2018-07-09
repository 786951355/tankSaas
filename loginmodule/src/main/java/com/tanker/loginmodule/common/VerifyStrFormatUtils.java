package com.tanker.loginmodule.common;

/**
 * Created by Administrator on 2018/3/29.
 */

public class VerifyStrFormatUtils {
    /**
     * 大陆手机号码格式判断
     *
     * @param phoneNumStr
     * @return
     */
    public static boolean phoneNumFormat(String phoneNumStr) {
        // 大陆手机格式
        String telRegex = "[1][34578]\\d{9}";
        return phoneNumStr.matches(telRegex);
    }

    /**
     * 密码格式（非中文，6-20位）
     *
     * @param pwdStr
     * @return
     */
    public static boolean pwdFormat(String pwdStr) {
        //密码格式
        String pwdRegex = "[^\\u4e00-\\u9fa5]{6,20}$";
        return pwdStr.matches(pwdRegex);
    }


}
