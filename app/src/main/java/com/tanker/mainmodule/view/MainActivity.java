package com.tanker.mainmodule.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.event.InformMsg;
import com.tanker.basemodule.event.RxBus;
import com.tanker.basemodule.model.app_model.StatisticalHeadModel;
import com.tanker.basemodule.router.ReflectUtils;
import com.tanker.basemodule.utils.CommonUtils;
import com.tanker.basemodule.utils.StatusBarUtil;
import com.tanker.mainmodule.R;
import com.tanker.mainmodule.contract.MainContract;
import com.tanker.mainmodule.entity.TabEntity;
import com.tanker.mainmodule.presenter.MainPresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private CommonTabLayout commonTab;
    private double mLastClickTime;
    private ArrayList<CustomTabEntity> mTabEntities;
    private ArrayList<Fragment> mFragments;
    private List<String> mTitles;
    private List<Integer> mIconUnselectedIds;
    private List<Integer> mIconSelectIds;

    private int index = 0;
    private boolean isRead = true;
    private Disposable disposable;

    @Override
    protected void initView() {
        List<String> titles = Arrays.asList(
                "订单",
                "我的");
        List<Integer> iconUnselectedIds = Arrays.asList(
                R.drawable.order_normal,
                R.drawable.mine_normal);
        List<Integer> iconSelectIds = Arrays.asList(
                R.drawable.order_selected,
                R.drawable.mine_selected);
        mTitles = new ArrayList<>(titles);
        mIconSelectIds = new ArrayList<>(iconSelectIds);
        mIconUnselectedIds = new ArrayList<>(iconUnselectedIds);
        mTitles.add(0, "抢单");
        mIconUnselectedIds.add(0, R.drawable.grab_order_normal);
        mIconSelectIds.add(0, R.drawable.grab_order_selected);
        index = index >= mTitles.size() ? mTitles.size() - 1 : index; //防止数组溢出
        mTabEntities = new ArrayList<>();
        mFragments = new ArrayList<>();
        commonTab = findViewById(R.id.ctl_bottom);
        for (int i = 0; i < mTitles.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), mIconSelectIds.get(i), mIconUnselectedIds.get(i)));
        }

        Fragment fragmentOrders = ReflectUtils.getFragment("com.tanker.ordersmodule.view.OrdersFragment");
        Fragment fragmentHome = ReflectUtils.getFragment("com.tanker.graborder.view.NewHomeFragment");
        Fragment fragmentMine = ReflectUtils.getFragment("com.tanker.minemodule.view.MineFragment");

        if (fragmentHome == null || fragmentMine == null || fragmentOrders == null) {
            CommonUtils.showToast(mContext, "业务组件单独调试不应该跟其他业务Module产生交互，如果你依然想要在运行期依赖其它组件，那么请参考MainModule");
        } else {
            mFragments.add(fragmentHome);
            mFragments.add(fragmentOrders);
            mFragments.add(fragmentMine);
        }
        commonTab.setTabData(mTabEntities, this, R.id.main, mFragments);
        commonTab.setCurrentTab(index);
        if (index == mTitles.size() - 1) {
            mCustomToolbar.setToolbarVisible(false);
        }
        commonTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showTitle(position);
                if (position == mTitles.size() - 1) {
                    updateMine();
                }
            }

            @Override
            public void onTabReselect(int position) {
                if (position == mTitles.size() - 1) {
                    updateMine();
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new MainPresenter(this);
        disposable = RxBus.getInstanceBus().doSubscribe(InformMsg.class, informMsg -> {
            if ("clear".equals(informMsg.getType())) { // 同步清空消息数量
                mPresenter.clearIsRead();
                mCustomToolbar.setMessageNum(0);
            } else if ("114".equals(informMsg.getType()) || "115".equals(informMsg.getType()) || "116".equals(informMsg.getType())) { // 当接收到通知的极光推送时，刷新通知数量
                mPresenter.getStatisticalHead();
            }
        }, throwable -> Logger.e(throwable.toString()));
    }

    @Override
    public int getContentView() {
        return R.layout.activty_new_main;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        initToolBar("首页");

    }

    private void initToolBar(String title) {
        mCustomToolbar.setTitle(title).setRightIcon(R.drawable.setting)
                .setElevation(0).setToolbarVisible(true)
                .setOnRightIconClickListener(v -> {
                    // 点击消息图标，同步更新我的界面消息数量
                    RxBus.getInstanceBus().post(new InformMsg<StatisticalHeadModel>("refresh"));
                    mPresenter.clearIsRead();
                    mCustomToolbar.setMessageNum(0);
                    ReflectUtils.startActivityWithName(mContext, "com.zhaoguanche.inform.view.SettingActivity");
                });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
        index = intent.getIntExtra(AppConstants.HOME_PAGE_INDEX, 0);
        setContentView(R.layout.activity_base);
        viewContainer = findViewById(R.id.view_container);
        rootView = findViewById(R.id.root_view);
        setCustomContentView(getContentView());
        leftAction = findViewById(R.id.left_action);
        tvTitle = findViewById(R.id.tv_title);
        rightAction = findViewById(R.id.right_action);
        rightIcon = findViewById(R.id.right_icon);
        toolbar = findViewById(R.id.toolbar);
        statusLine = findViewById(R.id.status_line);
        errorImg = findViewById(R.id.iv_error_data);
        mContext = this;
        // 友盟-设置为U-APP场景为普通
        MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_UM_NORMAL);
        StatusBarUtil.StatusBarLightMode(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags =
                    (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        initDialog();
        initToolbar();
        initView();
        initData();
        initEvent();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastClickTime > 1000) {
            Toast.makeText(this, "再次点击退出应用", Toast.LENGTH_SHORT).show();
            mLastClickTime = System.currentTimeMillis();
        } else {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            navigationTo(home);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
        outState.putInt("saveCurrentIndex", commonTab.getCurrentTab());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            int index = savedInstanceState.getInt("saveCurrentIndex", 0);
            commonTab.setCurrentTab(index);
            showTitle(index);
        }
    }

    private void showTitle(int position) {
        switch (position) {
            case 0:
                initToolBar("首页");
                break;
            case 1:
                initToolBar("订单");
                break;
            case 2:
                initToolBar("工作台");
                break;

        }
    }

    private void updateMine() {
        RxBus.getInstanceBus().post(new InformMsg("refreshMine"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRead) {
            mPresenter.getStatisticalHead();
            isRead = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void refreshStatistical(StatisticalHeadModel model) {
        if (model == null) { // 清空消息接口
            isRead = true;
        } else if (!TextUtils.isEmpty(model.getAllTotal())) { // 获取消息数量接口
            mCustomToolbar.setMessageNum(Integer.valueOf(model.getAllTotal()));
            // 同步更新我的界面消息数量
            InformMsg<StatisticalHeadModel> informMsg = new InformMsg<>("refresh");
            informMsg.setData(model);
            RxBus.getInstanceBus().post(informMsg);
        }
    }
}
