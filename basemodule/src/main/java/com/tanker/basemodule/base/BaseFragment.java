package com.tanker.basemodule.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanker.basemodule.dialog.TankerDialog;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by ronny on 2018/3/21.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {
    private static final String TAG = "BaseFragment";
    public T mPresenter;
    protected BaseActivity mContext;
    protected final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
        initView(view);
        initEvent();
        initData();
        return view;
    }

    protected abstract void initView(View view);

    protected void initEvent() {
    }

    protected void initData() {
    }

    protected abstract int getLayoutId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = (BaseActivity) getActivity();
    }

    @Override
    public void showProgress() {
        mContext.showProgress();
    }

    @Override
    public void showProgress(String msg) {
        mContext.showProgress(msg);
    }

    @Override
    public void showProgress(boolean cancelable) {
        mContext.showProgress(cancelable);
    }

    @Override
    public void dismissProgress() {
        mContext.dismissProgress();
    }

    @Override
    public void showMessage(String error) {
        mContext.showMessage(error);
    }

    @Override
    public void showErrorMessage(int code, String msg) {
        mContext.showErrorMessage(code, msg);
    }

    @Override
    public PublishSubject<ActivityLifeCycleEvent> getLifecycleSubject() {
        return lifecycleSubject;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @Override
    public void showAlertDialog(String msg, TankerDialog.OptionListener optionListener) {
        mContext.showAlertDialog(msg, optionListener);
    }

    @Override
    public void showAlertDialog(String msg, int ImageRes, TankerDialog.OptionListener optionListener) {
        mContext.showAlertDialog(msg, ImageRes, optionListener);
    }

    @Override
    public void showAlertDialogNoCancel(String content, String secondContent, int ImageRes, TankerDialog.OptionListener optionListener) {
        mContext.showAlertDialogNoCancel(content, secondContent, ImageRes, optionListener);
    }

    @Override
    public void showAlertDialogNoCancel(String content, String confirmText,TankerDialog.OptionListener optionListener) {
        mContext.showAlertDialogNoCancel(content, confirmText,optionListener);
    }

    @Override
    public void navigationTo(Intent intent) {
        mContext.navigationTo(intent);
    }

    @Override
    public void navigationTo(Class clz) {
        mContext.navigationTo(clz);
    }

    @Override
    public int getContentView() {
        return 0;
    }

    @Override
    public void setCustomContentView(@LayoutRes int contentViewID) {
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
    }

    @Override
    public BaseActivity getContext() {
        return mContext;
    }

    @Override
    public Drawable getmDrawable(@DrawableRes int drawableRes) {
        return getResources().getDrawable(drawableRes);
    }

    @Override
    public int getmColor(int resColor) {
        return getResources().getColor(resColor);
    }

    @Override
    public void showNoDataImgTip() {
        mContext.showNoDataImgTip();
    }

    @Override
    public void showNoSearchDataImgTip() {
        mContext.showNoSearchDataImgTip();
    }

    @Override
    public void showNoLinesTip() {
        mContext.showNoLinesTip();
    }


    @Override
    public void hideImgTip() {
        mContext.hideImgTip();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("lwj", "fragment_onResumeSimpleName: "+mContext.getClass().getSimpleName());
       // MobclickAgent.onResume(mContext); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("lwj", "fragment_onPauseSimpleName: "+mContext.getClass().getSimpleName());
        //MobclickAgent.onPause(mContext); //统计时长
    }

}
