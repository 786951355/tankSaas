package com.tanker.basemodule.utils;

import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.R;
import com.tanker.basemodule.common.TankerApp;
import com.tanker.basemodule.event.TextSpanClickable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String getHidePhoneNum(String pNumber) {

        if (!TextUtils.isEmpty(pNumber) && pNumber.length() > 6) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pNumber.length(); i++) {
                char c = pNumber.charAt(i);
                if (i >= 3 && i < pNumber.length() - 4) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return String.valueOf(sb);
        }
        return "";
    }

    public static boolean checkMsgCode(String msgCode) {
        return msgCode.length() >= 6;
    }

    public static String formatErrorMsg(int errorCode, String errorMsg) {
        return "『" + errorCode + "』" + errorMsg;
    }


    public static SpannableString showProtocalLink(Activity context, String tagString) {
        SpannableString spannableInfo = new SpannableString(tagString);
        int firsStar = spannableInfo.toString().indexOf("《");
        int firstEnd = spannableInfo.toString().indexOf("》");
        spannableInfo.setSpan(new TextSpanClickable(context, AppConstants.PROTOCOL_URL), firsStar, firstEnd + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableInfo.setSpan(new ForegroundColorSpan(TankerApp.getInstance().getResources().getColor(R.color.colorAccent)), firsStar, firstEnd + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableInfo;
    }

    // 正则表达式，提取我们所有匹配的内容；
    public static SpannableString showPhoneLink(Activity context, String note) {
        SpannableString spannableInfo = new SpannableString(note);

        Pattern pattern = Pattern
                .compile("\\d{3}-\\d{8}|\\d{4}-\\d{7}|\\d{11}");
        Matcher matcher = pattern.matcher(note);
        int start = 0;
        //遍历取出字符串中所有的符合条件的；
        while (matcher.find(start)) {
            start = matcher.end();
            String phoneNum = spannableInfo.subSequence(matcher.start(), matcher.end()).toString();

            spannableInfo.setSpan(new TextSpanClickable(context, TextSpanClickable.TO_CALL, phoneNum), matcher.start(),
                    matcher.end(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            spannableInfo.setSpan(new ForegroundColorSpan(TankerApp.getInstance().getResources().getColor(R.color.text_green)), matcher.start(), matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (start >= note.length()) {
                break;
            }
        }
        return spannableInfo;
    }

    // 正则表达式，提取我们所有匹配的内容；
    public static SpannableString showMoneyLink(String note) {
        SpannableString spannableInfo = new SpannableString(note);
        Pattern pattern = Pattern
                .compile("[0-9]+([.]?[0-9]{0,2})(元(/吨){0,1}){1}");
        Matcher matcher = pattern.matcher(note);
        int start = 0;
        //遍历取出字符串中所有的符合条件的；
        while (matcher.find(start)) {
            start = matcher.end();
            spannableInfo.setSpan(new ForegroundColorSpan(TankerApp.getInstance().getResources().getColor(R.color.text_money)), matcher.start(), matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (start >= note.length()) {
                break;
            }
        }
        return spannableInfo;
    }

    /**
     * @Description: 正则表达式，提取我们所有匹配的内容,颜色可传递自定义颜色
     * @date 2018/5/2
     * @author lwj
     */
    public static SpannableString showPhoneLink(Activity context, String note, int colorid) {
        SpannableString spannableInfo = new SpannableString(note);

        Pattern pattern = Pattern
                .compile("\\d{3}-\\d{8}|\\d{4}-\\d{7}|\\d{11}");
        Matcher matcher = pattern.matcher(note);
        int start = 0;
        //遍历取出字符串中所有的符合条件的；
        while (matcher.find(start)) {
            start = matcher.end();
            String phoneNum = spannableInfo.subSequence(matcher.start(), matcher.end()).toString();

            spannableInfo.setSpan(new TextSpanClickable(context, TextSpanClickable.TO_CALL, phoneNum), matcher.start(),
                    matcher.end(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            spannableInfo.setSpan(new ForegroundColorSpan(TankerApp.getInstance().getResources().getColor(colorid)), matcher.start(), matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (start >= note.length()) {
                break;
            }
        }
        return spannableInfo;
    }

    // 正则表达式，提取我们所有匹配的内容；
    public static SpannableString showUrlLink(Activity context, SpannableString sp, String text) {
        Pattern pattern = Pattern
                .compile("[a-zA-z]+://[^\\s]*");
        Matcher matcher = pattern.matcher(text);
        int start = 0;
        //遍历取出字符串中所有的符合条件的；
        while (matcher.find(start)) {
            start = matcher.end();
            sp.setSpan(new TextSpanClickable(context, TextSpanClickable.TO_CALL), matcher.start(),
                    matcher.end(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            sp.setSpan(new ForegroundColorSpan(TankerApp.getInstance().getResources().getColor(R.color.colorAccent)), matcher.start(), matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (start >= text.length()) {
                break;
            }
        }
        return sp;
    }

    public static boolean isUrl(String str) {
        Pattern pattern = Pattern
                .compile("[a-zA-z]+://[^\\s]*");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean checkId(String str) {
        Pattern pattern = Pattern
                .compile("(^[0-9]{15}$)|([0-9]{17}([0-9]|X)$)");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 注册输入姓名格式
     * 匹配中文、英文、"."、","2-20个字符，
     *
     * @param userNameStr
     * @return
     */
    public static boolean checkName(String userNameStr) {
        String userNameRegex = "[a-zA-Z\\u4e00-\\u9fa5.,]{2,20}$";
        return !TextUtils.isEmpty(userNameStr) && userNameStr.matches(userNameRegex);

    }

    public static boolean checkMobileNum(String driverPhone) {
        return (!TextUtils.isEmpty(driverPhone)) && driverPhone.length() == 11;
    }

    public static boolean checkTonnageNum(String tonnage) {
        try {
            Float aFloat = Float.valueOf(tonnage);
            String[] split = aFloat.toString().split("\\.");
            if (aFloat > 200f || (split.length >= 2 && split[1].length() > 3)) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * 公司名验证
     */
    public static String checkCompanyName(String etCompanyName) {
        if (EmptyUtils.isEmpty(etCompanyName)) {
            return TankerApp.getInstance().getResources().getString(R.string.login_toast_company_name_empty_str);
        }
        if (!companyNameFormat(etCompanyName)) {
            return TankerApp.getInstance().getResources().getString(R.string.login_toast_company_name_format_str);
        }
        return "";
    }

    /**
     * 匹配注册时公司名称
     *
     * @param companyNameStr
     * @return
     */
    public static boolean companyNameFormat(String companyNameStr) {
        // String pwdRegex = "@[0-9a-zA-Z\\u4e00-\\u9fa5()_\\-]+\\){4,30}$";
        String pwdRegex = "[a-zA-Z-\\u4e00-\\u9fa5()]{4,30}$";
        return companyNameStr.matches(pwdRegex);
    }

    /**
     * 整数格式化 + 截取两位小数（不四舍五入）
     *
     * @param value
     * @return
     */
    public static String valueFormatWithTwo(String value) {
        if (TextUtils.isEmpty(value)) {
            return "0.000";
        }

        BigDecimal bd = new BigDecimal(value);
        DecimalFormat df = new DecimalFormat("##0.000");//小数点点不够两位补0，例如："0" --> 0.00（个位数补成0因为传入的是0则会显示成：.00，所以各位也补0；）
        String xs = df.format(bd.setScale(3, BigDecimal.ROUND_DOWN));//直接截取小数点后两位（不四舍五入）
        return xs;
    }


}
