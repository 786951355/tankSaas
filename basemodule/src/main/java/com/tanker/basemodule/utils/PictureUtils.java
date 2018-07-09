package com.tanker.basemodule.utils;

import android.media.ExifInterface;
import android.text.TextUtils;

import com.tanker.basemodule.model.PictureInfo;

import java.io.IOException;

public class PictureUtils {

    public static PictureInfo getPicInfo(String filePath) {
        PictureInfo pictureInfo = new PictureInfo();
        String address = GPSUtils.parseAddress(filePath);
        pictureInfo.setPicAddress(address);
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String newDate = "";
        String time = "";
        String week = "";
        if (exifInterface != null) {
            String picDate = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            if (!TextUtils.isEmpty(picDate)) {
                newDate = picDate.split(" ")[0].replace(":", "-");
                week = DateUtils.getWeek(newDate);
                time = picDate.split(" ")[1];
                time = TextUtils.isEmpty(time) ? "" : time.substring(0, time.lastIndexOf(":"));
            }
        }
        pictureInfo.setPicTime(time);
        pictureInfo.setPicDate(newDate);
        pictureInfo.setPicWeek(week);
        return pictureInfo;
    }
}
