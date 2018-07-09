package com.tanker.basemodule.http;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.common.TankerApp;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ronny on 2017/9/21.
 */

public class HttpParam {
    private HashMap<String, String> paramsMap;
    private String appSecrete = "egz0MhBqb0";
    List<String> ignoreParamNames;


    public HttpParam(boolean isLogin) {
        paramsMap = new HashMap<>();
        ignoreParamNames = new ArrayList<>();
        paramsMap.put(AppConstants.PARAM_APP_ID, AppConstants.APP_ID);
        paramsMap.put(AppConstants.PARAM_EVENT_TIME, System.currentTimeMillis() + "");
        paramsMap.put(AppConstants.PARAM_TOKEN, TankerApp.getInstance().getToken());
        if (!isLogin) {
            paramsMap.put(AppConstants.PARAM_UID, TankerApp.getInstance().getUserManager().getUserId());
        }
    }

    public static HttpParam createParams() {
        return new HttpParam(false);
    }

    /**
     * 非登录的接口，默认传token及uId
     *
     * @param isLogin 是否是登录
     * @return
     */
    public static HttpParam createParams(boolean isLogin) {
        return new HttpParam(isLogin);
    }

    public HttpParam putParam(String key, String value) {
        putParam(key, value, true);
        return this;
    }

    public HttpParam putParam(String key, String value, boolean needSign) {
        if (!needSign) {
            ignoreParamNames.add(key);
        }
        paramsMap.put(key, value);
        return this;
    }

    public HttpParam putAll(Map<String, String> stringStringMap) {
        paramsMap.putAll(stringStringMap);
        return this;
    }

    public HashMap<String, String> endWithoutSign() {
//        String sign = sign();
//        paramsMap.put(AppConstants.PARAM_SIGN, sign);
        return paramsMap;
    }

    public HashMap<String, String> end() {
        String sign = sign();
        paramsMap.put(AppConstants.PARAM_SIGN, sign);
        return paramsMap;
    }

    /**
     * 对请求参数进行签名处理
     *
     * @return
     */
    public String sign() {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> paramNames = new ArrayList<>(paramsMap.size());
            paramNames.addAll(paramsMap.keySet());
            if (ignoreParamNames != null && ignoreParamNames.size() > 0) {
                for (String ignoreParamName : ignoreParamNames) {
                    paramNames.remove(ignoreParamName);
                }
            }
            Collections.sort(paramNames);
            for (String paramName : paramNames) {
                sb.append(paramName)
                        .append(paramsMap.get(paramName));
            }
            sb.append(appSecrete);
            String replace = sb.toString()
                    .replace("\"", "")
                    .replace(",", "")
                    .replace("=", "")
                    .replace(" ", "")
                    .replace(":", "");

            sb.setLength(0);
            sb.append(replace);

            Logger.d("排序前：" + sb.toString());
            sb = sortChar(sb);
            Logger.d("排序后：" + sb.toString());
            byte[] md5Digest = getMD5Digest(sb.toString());
            String sign = byte2hex(md5Digest);
            return sign;
        } catch (IOException e) {
            throw new RuntimeException("加密签名计算错误", e);
        }
    }


    public StringBuilder sortChar(StringBuilder sb) {
        List<String> chars = new ArrayList<>();
        int length = sb.length();
        for (int i = 0; i < length; i++) {
            chars.add(sb.charAt(i) + "");
        }
        Collections.sort(chars);
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : chars) {
            stringBuilder.append(string);
        }
        return stringBuilder;
    }


    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    private static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

}
