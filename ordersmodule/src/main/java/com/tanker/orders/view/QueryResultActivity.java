package com.tanker.orders.view;

import android.os.Bundle;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.orders.R;

/**
 * author zhanglei
 * date 2018/4/26 16:22
 * description 查询结果
 */
public class QueryResultActivity extends BaseActivity {
    OrderListFragment ordersListFragment;

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getBundleExtra("data");
        if (bundle != null && ordersListFragment == null) {
            ordersListFragment = OrderListFragment.newInstance("");
            getSupportFragmentManager().beginTransaction().add(R.id.fl_container, ordersListFragment).commit();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.ordersmodule_activity_query_result;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setTitle(getResources().getString(R.string.ordersmodule_title_query_result));
    }
}
