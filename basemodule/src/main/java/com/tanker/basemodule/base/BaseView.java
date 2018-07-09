package com.tanker.basemodule.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;

import com.tanker.basemodule.dialog.TankerDialog;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by ronny on 2018/3/21.
 */

public interface BaseView {
    void setCustomContentView(@LayoutRes int contentViewID);

    //获取内容View
    @LayoutRes
    int getContentView();

    void configToolbar(CustomToolbar rToolbar);

    void showAlertDialogNoCancel(String content, String secondContent, int ImageRes, TankerDialog.OptionListener optionListener);

    void showAlertDialogNoCancel(String content, String confirmText,TankerDialog.OptionListener optionListener);

    void showMessage(String error);

    void showProgress();

    void showProgress(boolean cancelable);

    void dismissProgress();

    void showProgress(String msg);

    void showAlertDialog(String msg, TankerDialog.OptionListener optionListener);

    void showAlertDialog(String msg, int ImageRes, TankerDialog.OptionListener optionListener);

    void showErrorMessage(int code, String msg);

    void navigationTo(Class cls);

    void navigationTo(Intent intent);

    PublishSubject<ActivityLifeCycleEvent> getLifecycleSubject();

    BaseActivity getContext();

    Drawable getmDrawable(@DrawableRes int drawableRes);

    int getmColor(int resColor);

    void showNoDataImgTip();

    void showNoSearchDataImgTip();

    void showNoLinesTip();

    void hideImgTip();
}
