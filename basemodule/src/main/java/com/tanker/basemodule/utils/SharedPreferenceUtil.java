package com.tanker.basemodule.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.common.TankerApp;

public class SharedPreferenceUtil {

    // 用户名key
    public final static String KEY_NAME = "KEY_NAME";
    public final static String KEY_AUTO = "KEY_AUTO";
    public final static String KEY_LOGIN = "KEY_LOGIN";
    public final static String KEY_TOKEN = "KEY_TOKEN";
    public final static String KEY_LEVEL = "KEY_LEVEL";
    public final static String KEY_DELIVERY = "KEY_DELIVERY";
    private static final String KEY_REFRESH_TOKEN = "KEY_REFRESH_TOKEN";
    private static final String KEY_COMPLETE_PICS_DOWNLOAD = "KEY_COMPLETE_PICS_DOWNLOAD";
    private static SharedPreferenceUtil spUtils;
    private SharedPreferences sp;

    //

    /**
     * 初始化，一般在应用启动之后就要初始化
     *
     * @param context 此处的context要用application的全局上下文, 避免static强类型一直持有activity的引用,造成内存泄露
     */
    public static synchronized void initSharedPreference(Context context) {
        if (spUtils == null) {
            spUtils = new SharedPreferenceUtil(context);
        }
    }

    /**
     * 获取唯一的instance
     */

    public static synchronized SharedPreferenceUtil getInstance() {
        if (spUtils == null) {
            spUtils = new SharedPreferenceUtil(TankerApp.getInstance());
        }

        return spUtils;
    }

    public SharedPreferenceUtil(Context context) {
        sp = context.getSharedPreferences("SharedPreferenceUtil", Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPref() {
        return sp;
    }

    public synchronized void putAutoLogin(Boolean AutoLogin) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY_AUTO, AutoLogin);
        editor.commit();
    }

    public synchronized Boolean getAutoLogin() {
        Boolean flag = sp.getBoolean(KEY_AUTO, false);
        Logger.i(flag + "flag");
        return flag;
    }

    public synchronized void setCompletePicsDownload(Boolean finished) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY_COMPLETE_PICS_DOWNLOAD, finished);
        editor.commit();
    }

    public synchronized Boolean getCompletePicsDownload() {
        Boolean flag = sp.getBoolean(KEY_COMPLETE_PICS_DOWNLOAD, false);
        Logger.i(flag + "flag");
        return flag;
    }

    public synchronized String getToken() {
        String token = sp.getString(KEY_TOKEN, "");
        Logger.i("token:" + token);
        return token;
    }

    public synchronized void setToken(String token) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public synchronized String getRefreshToken() {
        String token = sp.getString(KEY_REFRESH_TOKEN, "");
        Logger.i("token:" + token);
        return token;
    }

    public synchronized void setRefreshToken(String token) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_REFRESH_TOKEN, token);
        editor.commit();
    }

    public synchronized void setIsLogin(Boolean AutoLogin) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY_LOGIN, AutoLogin);
        editor.commit();
    }

    public synchronized Boolean getIsLogin() {
        Boolean flag = sp.getBoolean(KEY_LOGIN, false);
        return flag;
    }

    //记录用户名
    public void setUsername(String username) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("pre_username", username);
        editor.apply();
    }

    //读取用户名
    public String getUsername() {
        return sp.getString("pre_username", "");
    }

//    public synchronized User getUser() {
//        String str = sp.getString(SharedPreferenceUtil.KEY_NAME, "");
//        if (StringUtils.isEmpty(str)) {
//            return null;
//        }
//        if (spUser == null) {
//            spUser = new User();
//            //获取序列化的数据
//            try {
//                Object obj = SerializableUtil.strToObj(str);
//                if (obj != null) {
//                    spUser = (User) obj;
//                    Logger.d("USER:getuser" + spUser.toString());
//                }
//            } catch (StreamCorruptedException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return spUser;
//    }
//
//    public synchronized void DeleteUser() {
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(KEY_NAME, "");
//        editor.commit();
//        spUser = null;
//    }
}
