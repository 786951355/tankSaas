package com.tanker.homemodule.view;

import android.text.Html;
import android.widget.TextView;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.homemodule.R;
import com.tanker.homemodule.presenter.FaultDetailPresenter;

public class FaultDetailActivity extends BaseActivity<FaultDetailPresenter> {
    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_label_address)).setText(Html.fromHtml(getString(R.string.homemodule_label_address)));
        ((TextView) findViewById(R.id.tv_label_fault)).setText(Html.fromHtml(getString(R.string.homemodule_label_fault)));
        ((TextView) findViewById(R.id.tv_label_time)).setText(Html.fromHtml(getString(R.string.homemodule_label_fault_time)));
    }

    @Override
    public int getContentView() {
        return R.layout.homemodule_activity_fault_detail;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setToolbarVisible(true).setTitle(getString(R.string.homemodule_title_fault_detail));
    }
}
