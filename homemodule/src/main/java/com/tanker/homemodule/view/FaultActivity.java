package com.tanker.homemodule.view;

import android.view.View;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.homemodule.R;

/**
 * author zhanglei
 * date 2018/7/16
 * description 故障列表界面
 **/
public class FaultActivity extends BaseActivity {
    @Override
    protected void initView() {

    }

    @Override
    public int getContentView() {
        return R.layout.homemodule_activity_fault;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setToolbarVisible(true).setTitle(getString(R.string.homemodule_title_fault));
    }

    public void report(View view) {
        navigationTo(FaultDetailActivity.class);
    }
}
