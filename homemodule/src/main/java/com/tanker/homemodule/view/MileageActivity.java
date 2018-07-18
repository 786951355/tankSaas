package com.tanker.homemodule.view;

import android.view.View;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.homemodule.R;

public class MileageActivity extends BaseActivity {
    @Override
    protected void initView() {

    }

    @Override
    public int getContentView() {
        return R.layout.homemodule_activity_mileage;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setToolbarVisible(true).setTitle(getString(R.string.homemodule_title_mileage));
    }

    public void report(View view) {
        navigationTo(MileageAddActivity.class);
    }
}
