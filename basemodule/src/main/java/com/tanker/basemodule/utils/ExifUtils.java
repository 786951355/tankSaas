package com.tanker.basemodule.utils;

import android.location.Location;
import android.media.ExifInterface;

import java.io.IOException;

public class ExifUtils {
    /**
     * 更新path对应图片的位置信息
     *
     * @param path
     * @param location
     */
    public static void updateLocationInfoToPic(String path, Location location) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, convertFloatToRationalLocation(location.getLatitude()));
        exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "E");//纬度
        exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, convertFloatToRationalLocation(location.getLongitude()));
        exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "N");//经度
        try {
            exif.saveAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param floatLocation
     * @return
     * @description 该方法用于将获取到的经纬度换算成相机中存储的经纬度
     * 相机中存储的经度：31/1,17/1,359016/10000
     * 获取到的经纬度：31.293306
     */
    private static String convertFloatToRationalLocation(double floatLocation) {
        if (floatLocation == 0.0d) {
            return null;
        }
        int degrees = (int) (floatLocation + 0);
        floatLocation = floatLocation - (double) degrees;
        int minutes = (int) (floatLocation * 60);
        floatLocation = floatLocation * 60 - (double) minutes;
        int seconds = (int) (floatLocation * 60 * 10000);
        String result = degrees + "/1," + minutes + "/1," + seconds + "/10000";
        return result;
    }

}
