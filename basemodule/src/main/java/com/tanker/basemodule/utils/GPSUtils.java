package com.tanker.basemodule.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.tanker.basemodule.common.SaasApp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSUtils {

    public static String parseAddress(String imagePath) {
        double[] locations = getLocation(imagePath);
        Geocoder gc = new Geocoder(SaasApp.getInstance().getApplicationContext(), Locale.getDefault());
        List<android.location.Address> locationList = null;
        if (!Geocoder.isPresent()) {
            Toast.makeText(SaasApp.getInstance().getApplicationContext(), "地理位置服务不存在", Toast.LENGTH_LONG).show();
            return "";
        }
        try {
            locationList = gc.getFromLocation(locations[0], locations[1], 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (locationList.size() <= 0) {
            return "";
        }
        Address address = locationList.get(0);//得到Address实例
        String cityName = address.getLocality();//  得到城市名称，比如：上海市
        String area = address.getSubLocality();   //  区名字：虹口区
        String road = address.getThoroughfare();  //  街道  ：汶水东路
        String roadNum = address.getSubThoroughfare();  // 门牌号 ：351号b座

        String featureName = address.getFeatureName();   //部分手机该字段为空，该字段的名称比较全面 优先获取 "汶水东路351号b座1876老站创意园"

        String addressLine = " ";
        if (TextUtils.isEmpty(featureName)) { //如果获取不到featureName那么可以就用其他字段拼凑出完整的地址
            addressLine = cityName + area + road + roadNum;
        } else {
            addressLine = cityName + area + featureName;
        }

        return addressLine.trim()
                .replace("null", "")
                .replace("NULL", "")
                .replace("Null", "");
    }


    /**
     * path 为照片的路径
     */
    public static double[] getLocation(String path) {
        float output1 = 0;
        float output2 = 0;
        @SuppressWarnings("unused")
        String context;
        Location location;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String lngRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
            if (latValue != null && latRef != null && lngValue != null && lngRef != null) {
                try {
                    output1 = convertRationalLatLonToFloat(latValue, latRef);
                    output2 = convertRationalLatLonToFloat(lngValue, lngRef);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        context = Context.LOCATION_SERVICE;
        location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(output1);
        location.setLongitude(output2);
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        double[] f = {lat, lng};
        return f;
    }

    private static float convertRationalLatLonToFloat(
            String rationalString, String ref) {
        try {
            String[] parts = rationalString.split(",");

            String[] pair;
            pair = parts[0].split("/");
            double degrees = Double.parseDouble(pair[0].trim())
                    / Double.parseDouble(pair[1].trim());

            pair = parts[1].split("/");
            double minutes = Double.parseDouble(pair[0].trim())
                    / Double.parseDouble(pair[1].trim());

            pair = parts[2].split("/");
            double seconds = Double.parseDouble(pair[0].trim())
                    / Double.parseDouble(pair[1].trim());

            double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
            if ((ref.equals("S") || ref.equals("W"))) {
                return (float) -result;
            }
            return (float) result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps || network;

    }

    /**
     * 强制帮用户打开GPS
     *
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

}
