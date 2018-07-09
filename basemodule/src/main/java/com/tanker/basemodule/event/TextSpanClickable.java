package com.tanker.basemodule.event;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.R;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.common.TankerApp;
import com.tanker.basemodule.common.WebViewActivity;

import java.lang.ref.WeakReference;

/**
 * Created by ronny on 2017/12/21.
 */

public class TextSpanClickable extends ClickableSpan {
    public static int TO_CALL = 1;
    public static int TO_WEB = 2;
    private String phoneNum = AppConstants.getServicePhone();
    private WeakReference<Activity> wrContext;
    private int type;
    private String url;

    public TextSpanClickable(Activity context, int type) {
        super();
        this.wrContext = new WeakReference<>(context);
        this.type = type;
    }

    public TextSpanClickable(Activity context, int type, String phoneNum) {
        super();
        this.wrContext = new WeakReference<>(context);
        this.type = type;
        this.phoneNum = phoneNum;
    }


    public TextSpanClickable(Activity context, String url) {
        super();
        this.wrContext = new WeakReference<>(context);
        this.url = url;
        this.type = TO_WEB;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(
                TankerApp.getInstance().getResources().getColor(R.color.text_gray));
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View v) {
        switch (type) {
            case 1:
                Intent intent =
                        new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                onCallPermission(intent);
                break;
            case 2:
                Intent intent2 = new Intent(wrContext.get(), WebViewActivity.class);
                intent2.putExtra(AppConstants.WEB_ACTION, WebViewActivity.TO_PROTOCOL);
                intent2.putExtra(AppConstants.WEB_URL, url);
                wrContext.get().startActivity(intent2);
                break;
        }
    }

    public void onCallPermission(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断当前系统的SDK版本是否大于23
            //如果当前申请的权限没有授权
            if (!(wrContext.get().checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED)) {
                //第一次请求权限的时候返回false,第二次shouldShowRequestPermissionRationale返回true
                //如果用户选择了“不再提醒”永远返回false。
                if (wrContext.get()
                        .shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(wrContext.get(), "您已禁止该权限，需要重新开启。", Toast.LENGTH_LONG).show();
                }
                //请求权限
                wrContext.get().requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {//已经授权了就走这条分支
                Logger.i("onClick granted");
                wrContext.get().startActivity(intent);
            }
        } else {
            wrContext.get().startActivity(intent);
        }
    }
}
