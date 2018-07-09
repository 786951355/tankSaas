package com.tanker.basemodule.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_MDHM = "MM-dd HH:mm";
    public static final String DATE_FORMAT_YMDH = "yyyy-MM-dd HH";
    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YM = "yyyy-MM";
    public static final String DATE_FORMAT_MD = "MM-dd";
    public static final String CHINA_DATE_FORMAT_YMD = "yyyy年MM月dd日";
    public static final String CHINA_DATE_FORMAT_MD = "MM月dd日";

    public static String dateToStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMDHMS, Locale.CHINA);
        String s = sdf.format(date);
        return s;
    }

    public static String dateToStr(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = DATE_FORMAT_YMDHMS;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        String s = sdf.format(date);
        return s;
    }

    /**
     * 获取指定时间与当前时间的时间差
     *
     * @param date
     * @return
     */
    public static String getTimeToCur(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMDHMS, Locale.CHINA);
        long ms = 0, day = 0, hour = 0, minute = 0, second = 0;
        String result;
        try {
            ms = sdf.parse(sdf.format(new Date())).getTime() - sdf.parse(sdf.format(date)).getTime();
            if (ms <= 0) {
                result = "已到时间";
                return result;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        day = ms / (1000 * 60 * 60 * 24);
        hour = (ms = ms - day * 1000 * 60 * 60 * 24) / (1000 * 60 * 60);
        minute = (ms = ms - hour * 1000 * 60 * 60) / (1000 * 60);
        second = (ms = ms - minute * 1000 * 60) / 1000;
        result = String.valueOf(day) + "天" + String.valueOf(hour) + "时" + String.valueOf(minute) + "分" + String.valueOf(second) + "秒";
        return result;
    }

    /**
     * @return
     */
    public static int getDayToCur(long time) {
        Date date = new Date();
        int days = (int) ((time - date.getTime()) / (1000 * 3600 * 24)) + 1;
        return days;
    }

    public static String getForeFiveYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return sdf.format(calendar.getTime());
    }

    public static String getBackFiveYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return sdf.format(calendar.getTime());
    }

    /**
     * 时间戳转日期格式
     *
     * @param millis
     * @return
     */
    public static String millisToDate(String millis) {
        if (TextUtils.isEmpty(millis)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMD, Locale.CHINA);
        long time = Long.valueOf(millis);
        return sdf.format(new Date(time));
    }

    /**
     * 时间戳转日期格式
     *
     * @param millis
     * @param format
     * @return
     */
    public static String millisToDate(String millis, String format) {
        if (TextUtils.isEmpty(millis)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        long time = Long.valueOf(millis);
        return sdf.format(new Date(time));
    }

    /**
     * 时间戳转日期格式
     *
     * @param millis
     * @param format
     * @return
     */
    public static String millisToDate(long millis, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(new Date(millis));
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMD, Locale.CHINA);
        return sdf.format(new Date());
    }

    public static String getCurrentDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(new Date());
    }

    public static String getTomorrowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMD, Locale.CHINA);
        return sdf.format(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
    }

    public static boolean isToday(String date) {
        Calendar cur = Calendar.getInstance();
        cur.setTime(new Date(System.currentTimeMillis()));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(Long.valueOf(date)));
        if (cal.get(Calendar.YEAR) == cur.get(Calendar.YEAR)) {
            return cal.get(Calendar.DAY_OF_YEAR) - cur.get(Calendar.DAY_OF_YEAR) == 0;
        }
        return false;
    }

    public static boolean isToday(long millis) {
        Calendar cur = Calendar.getInstance();
        cur.setTime(new Date(System.currentTimeMillis()));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(millis));
        if (cal.get(Calendar.YEAR) == cur.get(Calendar.YEAR)) {
            return cal.get(Calendar.DAY_OF_YEAR) - cur.get(Calendar.DAY_OF_YEAR) == 0;
        }
        return false;
    }

    /**
     * 判断是否是同一天
     *
     * @param millis0
     * @param millis1
     * @return
     */
    public static boolean isSameDay(long millis0, long millis1) {
        Calendar cur = Calendar.getInstance();
        cur.setTime(new Date(millis0));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(millis1));
        if (cal.get(Calendar.YEAR) == cur.get(Calendar.YEAR)) {
            return cal.get(Calendar.DAY_OF_YEAR) - cur.get(Calendar.DAY_OF_YEAR) == 0;
        }
        return false;
    }

    public static String format(int year, int month, int day, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        Date date = new Date();
        try {
            date = sdf.parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    public static String format(String str, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        String d = str;
        try {
            Date date = sdf.parse(str);
            d = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 前者比后者大，则返回值大于0
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(long date1, long date2) {
        return new Date(date1).compareTo(new Date(date2));
    }

    /**
     * 前者比后者大，则返回值大于0
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(String date1, String date2) {

        return new Date(getTime(date1)).compareTo(new Date(getTime(date2)));
    }

    // 将字符串转为时间戳
    public static long getTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_FORMAT_YMD, Locale.CHINA);
        Date d;
        try {
            d = sdf.parse(date);
            return d.getTime();
        } catch (ParseException ignored) {
            return 0;
        }
    }

    /**
     * 截取日期字符串
     *
     * @param date 2018-05-01 00:00:00 长度19
     * @return 2018-05-01 00:00
     */
    public static String getYmdhsDate(String date) {
        return TextUtils.isEmpty(date) ? "" : (date.length() < 19 ? date : date.substring(0, 16));
    }

    /**
     * 获取指定时间与当前时间的时间差
     *
     * @param date
     * @return
     */
    public static int getDateToCur(String date) {
        if (date == null) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMDHMS, Locale.CHINA);
        long ms = 0;
        try {
            ms = sdf.parse(sdf.format(new Date())).getTime() - sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) (ms / (1000 * 60 * 60 * 24));
    }


    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */

    public static String getWeek(String pTime) {
        String week = "星期";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            week += "六";
        }
        return week;
    }
}
