package com.tanker.mainmodule.view;

import android.util.Log;
import android.view.KeyEvent;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.mainmodule.R;
import com.tanker.mainmodule.contract.WelcomeContract;
import com.tanker.mainmodule.presenter.WelcomePresenter;
import com.umeng.analytics.MobclickAgent;

public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContract.View {
    private static final String TAG = WelcomeActivity.class.getName();

    @Override
    protected void initView() {
        //友盟禁止默认的页面统计功能
        MobclickAgent.openActivityDurationTrack(false);
        mPresenter = new WelcomePresenter(this);
        mPresenter.requestConfig();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_welcome;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setToolbarVisible(false);
    }

    /**
     * 返回键不可用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //手动统计页面(页面名称可自定义)
        MobclickAgent.onPageStart("WelcomeActivity");
        Log.d(TAG, "onResume: " + "来了");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //手动统计页面(页面名称可自定义)
        MobclickAgent.onPageEnd("WelcomeActivity");
        Log.d(TAG, "onPause: " + "来了");
    }
}
