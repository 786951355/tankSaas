package com.tanker.basemodule.utils;


import com.tanker.basemodule.common.Logger;

public class AvoidClickAgainUtils {

  private static long lastClickTime;
  private static long lastRequestTime;

  public static boolean isFastDoubleClick() {//普通点击0.8秒
    long time = System.currentTimeMillis();
    long timeD = time - lastClickTime;
    if (0 < timeD && timeD < 800) {
      return true;
    } else {
      Logger.e("TAG", timeD + "");
    }
    lastClickTime = time;
    return false;
  }

  public static boolean isMainDoubleClick() {//主页0.3秒
    long time = System.currentTimeMillis();
    long timeD = time - lastClickTime;
    if (0 < timeD && timeD < 300) {
      return true;
    } else {
      Logger.e("TAG", timeD + "");
    }
    lastClickTime = time;
    return false;
  }

  public static boolean isH5DoubleClick() {//H5请求5秒
    long time = System.currentTimeMillis();
    long timeD = time - lastClickTime;
    if (0 < timeD && timeD < 5000) {
      return true;
    } else {
      Logger.e("TAG", timeD + "");
    }
    lastClickTime = time;
    return false;
  }
  public static boolean isDoubleClick() {//防止重复点击请求5s
    long time = System.currentTimeMillis();
    long timeD = time - lastClickTime;
    if (0 < timeD && timeD < 5000) {
      return true;
    } else {
      Logger.e("TAG", timeD + "");
    }
    lastClickTime = time;
    return false;
  }
  public static boolean isDoubleRequest() {//防止重复Ajax请求5s
    long time = System.currentTimeMillis();
    long timeD = time - lastRequestTime;
    if (0 < timeD && timeD < 5000) {
      return true;
    } else {
      Logger.e("TAG", timeD + "");
    }
    lastRequestTime = time;
    return false;
  }
}
