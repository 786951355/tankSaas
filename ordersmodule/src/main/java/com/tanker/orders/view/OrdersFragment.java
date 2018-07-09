package com.tanker.orders.view;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tanker.basemodule.base.BaseFragment;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.event.GrabOrderMsg;
import com.tanker.basemodule.event.RxBus;
import com.tanker.orders.OrderListFragment;
import com.tanker.orders.OrderStateType;
import com.tanker.orders.R;
import com.tanker.orders.adapter.OrdersPagerAdapter;
import com.tanker.orders.contract.OrdersContract;
import com.tanker.orders.presenter.OrdersPresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/3/27.
 */

public class OrdersFragment extends BaseFragment<OrdersPresenter> implements OrdersContract.View {
    private static final String TAG = OrdersFragment.class.getName();
    private ArrayList<OrderListFragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {"待装货", "待卸货", "已签收", "已取消","账本"};
    protected OrderListFragment waitGrabOrderFragment = new OrderListFragment().newInstance(OrderStateType.WAITTING);
    protected OrderListFragment quotedGrabOrderFragment = new OrderListFragment().newInstance(OrderStateType.QUOTED);
    protected OrderListFragment biddingSuccessGrabOrderFragment = new OrderListFragment().newInstance(OrderStateType.SUCCESS);
    protected OrderListFragment biddingFailGrabOrderFragment = new OrderListFragment().newInstance(OrderStateType.FAILED);
    private ViewPager viewPager;
    private SlidingTabLayout stb;
    private Disposable disposable;

    @Override
    protected int getLayoutId() {
        return R.layout.orders_fragment;
    }

    @Override
    protected void initView(View parent) {
        mFragments.add(waitGrabOrderFragment);
        mFragments.add(quotedGrabOrderFragment);
        mFragments.add(biddingSuccessGrabOrderFragment);
        mFragments.add(biddingFailGrabOrderFragment);
        stb = parent.findViewById(R.id.stb);
        viewPager = parent.findViewById(R.id.vp_grab_order);
        OrdersPagerAdapter grabViewPagerAdapter = new OrdersPagerAdapter(getChildFragmentManager(), mTitles, mFragments);
        viewPager.setAdapter(grabViewPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(4);
        stb.setViewPager(viewPager);
        stb.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
            }

            @Override
            public void onTabReselect(int position) {
                reduceDot();
//                mFragments.get(position).refreshDataOnly();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                reduceDot();
//                mFragments.get(position).refreshDataOnly();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new OrdersPresenter(this);
        //刷新小红点
        disposable = RxBus.getInstanceBus().doSubscribe(GrabOrderMsg.class, grabOrderMsg -> {
            if (grabOrderMsg.isRefreshDot()) {
                reduceDot();
            } else {
                //刷新小红点
                mPresenter.getOrderCount();
            }
        }, throwable -> Logger.e(throwable.getMessage()));
    }

    @Override
    public void onResume() {
        super.onResume();
        reduceDot();
        MobclickAgent.onResume(getActivity()); //统计时长
        Log.d(TAG, "onResume: "+"来了onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity()); //统计时长
        Log.d(TAG, "onPause: "+"来了onPause");
    }

    private void reduceDot() {
        switch (viewPager.getCurrentItem()) {
            case 0:
                mPresenter.reduceOrderCount("1");
                break;
            case 1:
                mPresenter.reduceOrderCount("2");
                break;
            case 2:
                mPresenter.reduceOrderCount("3");
                break;
        }
    }


    private void showTabMsgCount(int position, int msgCount) {
        stb.showMsg(position, msgCount);
        if (msgCount <= 0) {
            stb.hideMsg(position);
        } else if (msgCount>9&&msgCount<=99){
            stb.getMsgView(position).setTextSize(12);
        }else if (msgCount>99){
            stb.getMsgView(position).setTextSize(8);
        }

        stb.setMsgMargin(position, position == 2 ? 8 : 16, 9);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

}

