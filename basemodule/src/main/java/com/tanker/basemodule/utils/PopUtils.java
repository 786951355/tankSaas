package com.tanker.basemodule.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.tanker.basemodule.R;


public class PopUtils {
    public static final int IMG_POP = 1;
    public static final int CAR_DRIVER_POP = 2;

    public static PopupWindow showImgPopWindow(final Activity activity, final PopClickListener listener) {
        return showPopWindow(activity, listener, IMG_POP);
    }


    public static PopupWindow showCarPopWindow(final Activity activity, final PopClickListener listener) {
        return showPopWindow(activity, listener, CAR_DRIVER_POP);
    }


    private static PopupWindow showPopWindow(final Activity activity, final PopClickListener listener, int popType) {
        View popView = View.inflate(activity, R.layout.popupwindow_camera_need, null);
        Button bt_camera = popView.findViewById(R.id.btn_pop_camera);
        Button bt_album = popView.findViewById(R.id.btn_pop_album);
        Button bt_cancel = popView.findViewById(R.id.btn_pop_cancel);

        if (popType == CAR_DRIVER_POP) {
            bt_cancel.setVisibility(View.GONE);
            bt_camera.setText("新增车辆");
            bt_album.setText("新增司机/押车员");
        }

        //获取屏幕宽高
        int weight = activity.getResources().getDisplayMetrics().widthPixels;
        int height = activity.getResources().getDisplayMetrics().heightPixels * 1 / 3;
        final PopupWindow popupWindow = new PopupWindow(popView, weight, height);
        popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(true);
        bt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                listener.onItem2Click();

            }
        });
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                listener.onItem1Click();

            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //popupWindow消失屏幕变为不透明
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }
        });
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        activity.getWindow().setAttributes(lp);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 0);
        return popupWindow;
    }

    public interface PopClickListener {
        void onItem1Click();

        //        void onCancleClick();
        void onItem2Click();
    }

}
