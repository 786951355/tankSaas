package com.tanker.basemodule.utils;

import android.text.TextUtils;


public class EmptyUtils {
    public static boolean isEmpty(String target) {
        return target == null || TextUtils.isEmpty(target);
    }

    public static boolean isNotEmpty(String target) {
        return target != null && !TextUtils.isEmpty(target);
    }

    public static boolean isNull(Object obj) {
        return null == obj;
    }

    public static String NullToEmpty(String obj) {
        if (isNull(obj))
            return "";
        else
            return obj;
    }


    public static String getNullString() {
        return "暂无";
    }
}
