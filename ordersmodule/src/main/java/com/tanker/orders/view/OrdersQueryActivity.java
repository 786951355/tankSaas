package com.tanker.orders.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tanker.basemodule.adapter.TabPagerAdapter;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.orders.R;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * author zhanglei
 * date 2018/4/26 16:23
 * description 快速搜索
 */
public class OrdersQueryActivity extends BaseActivity implements OnTabSelectListener {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int position = 0;

    @Override
    protected void initView() {
        String[] mTitles = getResources().getStringArray(R.array.ordersmodule_query_type_tabs);
//        mFragments.add(PlateOrOrderFragment.getInstance(CommonConstants.QUERY_TYPE.TYPE_PLATE));
//        mFragments.add(PlateOrOrderFragment.getInstance(CommonConstants.QUERY_TYPE.TYPE_ORDER));
        mFragments.add(DateQueryFragment.getInstance());

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabPagerAdapter(mContext.getSupportFragmentManager(), mFragments, mTitles));
        SlidingTabLayout tabLayout = findViewById(R.id.tab_types);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnTabSelectListener(this);

//        if (RolesConst.isDriver()) {
            tabLayout.setCurrentTab(0);
//        } else {
//            showSoftKeybordAsyn();
//        }
    }

    @Override
    public void onTabSelect(int position) {
        this.position = position;
        switch (position) {
            case 0:
//                showSoftKeybordAsyn();
//                break;
//            case 1:
//                EditText editText = ((PlateOrOrderFragment) mFragments.get(1)).etQuery;
//                if (editText != null && !TextUtils.isEmpty(editText.getText().toString()) && editText.getText().toString().length() > 1) {
//                    showSoftKeybordAsyn();
//                } else {
//                    hideSoftKeybord();
//                }
//                break;
//            case 2:
                hideSoftKeybord();
                break;
        }
    }

    @Override
    public void onTabReselect(int position) {
    }

    @Override
    public int getContentView() {
        return R.layout.ordersmodule_activity_orders_query;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setTitle(getResources().getString(R.string.ordersmodule_title_quickly_query));
    }

    // 延迟弹出为了解决fragment界面完成初始化之前etQuery为空的情况
    public void showSoftKeybordAsyn() {
//        if (position == 2) {
//            return;
//        }
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//                if (!(mFragments.get(position) instanceof PlateOrOrderFragment)) {
//                    return;
//                }
//                EditText editText = ((PlateOrOrderFragment) mFragments.get(position)).etQuery;
//                if ((position == 1 && RolesConst.isDriver() && TextUtils.isEmpty(editText.getText().toString()))
//                        || editText == null || editText.getContext() == null) {
//                    return;
//                }
//                InputMethodManager inputManager =
//                        (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (inputManager != null)
//                    inputManager.showSoftInput(editText, 0);
//            }
//        }, 500);
    }

}
