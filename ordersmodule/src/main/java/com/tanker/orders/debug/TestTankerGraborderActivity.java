package com.tanker.orders.debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.orders.R;
import com.tanker.orders.view.OrdersFragment;

/**
 * Created by Administrator on 2018/3/26.
 */

public class TestTankerGraborderActivity extends BaseActivity {

    private final String TANKER_GRABORDER = "tanker_graborder";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test_tanker_graborder);
        //addGraborderFragment();
    }


    @Override
    public int getContentView() {
        return R.layout.activity_test_tanker_graborder;
    }

    /**
     * 添加（添加抢单）Fragment to activity
     */
    private void addGraborderFragment() {
        OrdersFragment tankerLoginFragment = new OrdersFragment();
        if (!tankerLoginFragment.isAdded()) {
            addFragment(tankerLoginFragment, TANKER_GRABORDER);
        }
    }

    /**
     * 添加Fragment（support.v4.库）
     *
     * @param fragment
     */
    private void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.test_tanker_graborder, fragment, tag)
                //加入返回栈，这样你点击返回键的时候就会回退到fragment1了
                .addToBackStack(tag)
                // 提交事务
                .commit();
    }


    @Override
    public void configToolbar(CustomToolbar rToolbar) {

    }

    @Override
    protected void initView() {
        addGraborderFragment();
    }
}
