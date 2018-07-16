package com.tanker.homemodule.view;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.homemodule.R;
import com.tanker.homemodule.presenter.FaultDetailPresenter;

public class FaultDetailActivity extends BaseActivity<FaultDetailPresenter> {
    @Override
    protected void initView() {

    }

    @Override
    public int getContentView() {
        return R.layout.homemodule_activity_fault_detail;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {

    }
}
