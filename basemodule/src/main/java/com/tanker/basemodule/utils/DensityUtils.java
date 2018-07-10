package com.tanker.basemodule.utils;

import android.util.DisplayMetrics;

import com.tanker.basemodule.common.SaasApp;

/**
 * Created by ronny on 2017/8/19.
 */

public class DensityUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = SaasApp.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = SaasApp.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(float pxValue) {
        final float fontScale = SaasApp.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(float spValue) {
        final float fontScale = SaasApp.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getWindowWidth() {
        DisplayMetrics displayMetrics = SaasApp.getInstance().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        return width;
    }

    public static int getWindowHeight() {
        DisplayMetrics displayMetrics = SaasApp.getInstance().getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        return height;
    }
}
