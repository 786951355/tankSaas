package com.tanker.basemodule.utils;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.tanker.basemodule.R;
import com.tanker.basemodule.common.TankerApp;
import com.tanker.basemodule.constants.DialogConst;
import com.tanker.basemodule.dialog.TankerDialog;
import com.tanker.basemodule.dialog.TankerDialogHelper;

import java.util.Locale;

/**
 * Created by Ronny on 2018/3/23.
 */

//错误提示显示工具
public class ShowMassageUtil {
    private static Toast toast;

    /**
     * 显示错误弹窗
     *
     * @param errorCode 错误码 - 不为空显示 [XXXXX] 为空则不显示
     * @param errorMsg  错误信息
     */
    public static TankerDialog showErrorDialog(Activity context, String errorCode, String errorMsg) {
        return showErrorDialog(context, errorCode, errorMsg, false);
    }


    public static TankerDialog showErrorDialog(Activity context, String errorCode, String errorMsg,
                                               boolean finishActivity) {
        String message = getErrorMessage(errorCode, errorMsg);
        Bundle bundle = new Bundle();
        bundle.putString(DialogConst.DIALOG_TITLE_TEXT, context.getString(R.string.dialog_title));
        bundle.putString(DialogConst.DIALOG_CONTENT_TEXT, message);
        bundle.putString(DialogConst.DIALOG_CONFIRM_TEXT,
                context.getString(R.string.dialog_show_ensure));
        bundle.putBoolean(DialogConst.DIALOG_CLOSE_ACTIVITY, finishActivity);
        return TankerDialogHelper.showOBDialog(context, bundle);
    }

    /**
     * 显示不带错误码的toast
     *
     * @param errorMsg 错误信息
     */
    public static void showErrorToast(String errorMsg) {
        showErrorToast(null, errorMsg);
    }

    /**
     * 显示带错误码的toast
     *
     * @param errorCode 错误码 - 为空则不显示[]
     * @param errorMsg  错误信息
     */
    public static void showErrorToast(String errorCode, String errorMsg) {
        toast = Toast.makeText(TankerApp.getInstance().getApplicationContext(), getErrorMessage(errorCode, errorMsg), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, DensityUtils.dip2px(148));
        toast.show();
//        showToast(getErrorMessage(errorCode,errorMsg));
    }


    /**
     * Toast提示（解决Toast重复弹出 长时间不消失的问题 ）
     *
     * @param message
     */
    public static void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(TankerApp.getInstance().getApplicationContext(), message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();//设置新的消息提示
    }

    public static String getErrorMessage(String errorCode, String errorMsg) {
        String msg, code;
        msg = TextUtils.isEmpty(errorMsg) ? "" : errorMsg;
        code =
                TextUtils.isEmpty(errorCode) ? "" : String.format(Locale.getDefault(), "『 %s 』：", errorCode);
        String message = msg + code;
        return message;
    }

//  public static void showCertExpiredMsgToast(Context context) {
//    String msg = UserManager.getInstance().getCertExpiredMsg();
//    if(!StringUtils.isEmpty(msg)) {
//      Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//    }
//  }
}
