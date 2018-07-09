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

public class HttpParamObj {
    private HashMap<String, Object> paramsMap;
    private String appSecrete = "egz0MhBqb0";
    List<String> ignoreParamNames;


    public HttpParamObj(boolean isLogin) {
        paramsMap = new HashMap<>();
        ignoreParamNames = new ArrayList<>();
        paramsMap.put(AppConstants.PARAM_APP_ID, AppConstants.APP_ID);
        paramsMap.put(AppConstants.PARAM_EVENT_TIME, System.currentTimeMillis() + "");
        paramsMap.put(AppConstants.PARAM_TOKEN, TankerApp.getInstance().getToken());
        if (!isLogin) {
            paramsMap.put(AppConstants.PARAM_UID, TankerApp.getInstance().getUserManager().getUserId());
        }
    }

    public static HttpParamObj createParams() {
        return new HttpParamObj(false);
    }

    /**
     * 非登录的接口，默认传token及uId
     *
     * @param isLogin 是否是登录
     * @return
     */
    public static HttpParamObj createParams(boolean isLogin) {
        return new HttpParamObj(isLogin);
    }

    public HttpParamObj putParam(String key, Object value) {
        putParam(key, value, true);
        return this;
    }

    public HttpParamObj putParam(String key, Object value, boolean needSign) {
        if (!needSign) {
            ignoreParamNames.add(key);
        }
//    if (!TextUtils.isEmpty(value)) {
        paramsMap.put(key, value);
//    }
        return this;
    }

    public HttpParamObj putAll(Map<String, Object> stringStringMap) {
        paramsMap.putAll(stringStringMap);
        return this;
    }

    public HashMap<String, Object> end() {
        String sign = sign();
        paramsMap.put(AppConstants.PARAM_SIGN, sign);
        return paramsMap;
    }
//  public static String getMd5Sign(Map<String, Object> map, String secretKey) {
//    Set<String> sets = new TreeSet();
//    Set<String> mapSets = map.keySet();
//    Iterator i$ = mapSets.iterator();
//
//    String md5;
//    while(i$.hasNext()) {
//      md5 = (String)i$.next();
//      sets.add(md5);
//    }
//
//    StringBuilder parames = new StringBuilder();
//    Iterator iterator = sets.iterator();
//
//    while(iterator.hasNext()) {
//      String item = (String)iterator.next();
//      parames.append(item).append(map.get(item));
//    }
//
//    parames.append(secretKey);
//    logger.debug("MD5 params : " + parames.toString());
//    md5 = SecurityUtils.toMd5(parames.toString());
//    return md5.toUpperCase();
//  }


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


//    public static void main(String[] args) {
//        String s = "罗人//";
//        String s1="{\"warehousingPoundsOrderList\":[],\"sign\":\"0D2D7D77DA1F88786A25D19B6EE125A3\",\"leadPhotoOrderList\":[],\"carrierOrderId\":\"505\",\"samplingPhotoOrderList\":[],\"carOilSampleOrderList\":[],\"userId\":\"293\",\"token\":\"a01ae3b27afa422b9993cf5ac00494e4_18200983996\",\"eventTime\":\"1526449239177\",\"appId\":\"zgc.carrier.app\",\"outboundOrderList\":[{\"billPath\":\"/CarrierOrder/Y180516001001/pickUpGoodsBill/20180516/879f27a1bb0241169ced8ae5c536fdd4.jpg\",\"id\":\"1192\"}],\"signReceiptOrderList\":[{\"billPath\":\"/tempImages/78b6969718be4de6a7aa149eb6470da6.jpg\"}],\"unloadingTonnage\":\"22.500\"}";
//        HashMap<String ,Object> hashMap=new HashMap<>();
//        ArrayList<Object> list=new ArrayList();
//        list.add("1");
//        list.add(new BillInfo("55","http://com.tanker/fjdk.png"));
//
//        hashMap.put("m1",list);
//        StringBuilder stringBuilder = new StringBuilder();
//        Set<String> strings = hashMap.keySet();
//        for (String string:strings){
//            stringBuilder.append(string)
//                    .append(hashMap.get(string));
//        }
//        String replace = stringBuilder.toString()
//                .replace("\"", "")
//                .replace(",", "")
//                .replace("=", "")
//                .replace(" ", "")
//                .replace(":", "");
//        System.out.print(replace);
//    }
}
