package com.tanker.loginmodule.common;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/3/29.
 */

public class ViewUtils {

    private static Toast toast;

    /**
     * Toast提示（解决Toast重复弹出 长时间不消失的问题 ）
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();//设置新的消息提示
    }

}
