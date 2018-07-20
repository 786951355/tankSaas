package com.tanker.homemodule.view;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.homemodule.R;

public class MileageAddActivity extends BaseActivity {
    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_label_select_order)).setText(Html.fromHtml(getString(R.string.homemodule_label_select_order)));
    }

    @Override
    public int getContentView() {
        return R.layout.homemodule_activity_mileage_add;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setToolbarVisible(true).setTitle(getString(R.string.homemodule_title_mileage_add));
    }

    public void save(View view) {

    }
}
