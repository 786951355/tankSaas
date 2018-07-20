package com.tanker.homemodule.view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.tanker.basemodule.base.BaseFragment;
import com.tanker.basemodule.router.ReflectUtils;
import com.tanker.homemodule.GlideImageLoader;
import com.tanker.homemodule.R;
import com.tanker.homemodule.contract.HomeContract;
import com.tanker.homemodule.presenter.HomePresenter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {


    private Banner mBanner;

    /**
     * homemodule_item_order_unfinished_count
     */
    private TextView mTvUnfinished;
    /**
     * homemodule_item_order_cancel_count
     */
    private TextView mTvCancel;
    /**
     * homemodule_item_order_finished_count
     */
    private TextView mTvFinished;

    /**
     * homemodule_item_order_bill_costs
     */
    private TextView mTvBillCosts;
    /**
     * homemodule_item_order_cancel_count
     */
    private TextView mTvLastBillCosts;

    /**
     * homemodule_item_sys_fault_count
     */
    private TextView mTvFaultCounts;
    /**
     * homemodule_item_sys_last_fault_state_off
     */
    private TextView mTvLastFault;

    /**
     * homemodule_item_mileage_count
     */
    private TextView mTvMileageCounts;
    private List<String> images;
    private Button mBtnRecordBill;
    private Button mBtnRecordFault;
    private Button mBtnRecordMileage;
    private View mVBill;
    private View mVOrders;
    private View mVSysFailure;
    private View mVMileage;


    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }

    @Override
    protected void initView(View view) {
        images = new ArrayList<>();
        images.add("http://img.mp.itc.cn/upload/20170216/5d7b93b860d642e291b9158f4e3e0ff2_th.jpeg");
        images.add("http://f.hiphotos.baidu.com/zhidao/pic/item/1b4c510fd9f9d72ad5d1b6a1d32a2834359bbbd1.jpg");
        images.add("http://img.mp.itc.cn/upload/20170216/6d0f80a6bfa1437fbd126a9c4848c8f9_th.jpeg");
        images.add("http://f.hiphotos.baidu.com/zhidao/pic/item/18d8bc3eb13533faff688d88afd3fd1f40345bbd.jpg");

        mBanner = view.findViewById(R.id.banner);
        mTvUnfinished = view.findViewById(R.id.tv_unfinished);
        mTvCancel = view.findViewById(R.id.tv_cancel);
        mTvFinished = view.findViewById(R.id.tv_finished);
        mTvBillCosts = view.findViewById(R.id.tv_bill_costs);
        mTvLastBillCosts = view.findViewById(R.id.tv_last_bill_costs);
        mTvFaultCounts = view.findViewById(R.id.tv_fault_counts);
        mTvLastFault = view.findViewById(R.id.tv_last_fault);
        mTvMileageCounts = view.findViewById(R.id.tv_mileage_counts);
        mBtnRecordBill = view.findViewById(R.id.btn_record_bill);
        mBtnRecordFault = view.findViewById(R.id.btn_record_fault);
        mBtnRecordMileage = view.findViewById(R.id.btn_record_mileage);
        mVBill = view.findViewById(R.id.v_bills);
        mVOrders = view.findViewById(R.id.v_orders);
        mVSysFailure = view.findViewById(R.id.v_sys_failure);
        mVMileage = view.findViewById(R.id.v_mileage);
        mBanner.setImages(images).setImageLoader(new GlideImageLoader()).start();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        addDisposable(RxView.clicks(mBtnRecordBill)
                .subscribe(view -> navigationTo(AddBillActivity.class)));

        addDisposable(RxView.clicks(mBtnRecordFault)
                .subscribe(view -> navigationTo(FaultAddActivity.class)));

        addDisposable(RxView.clicks(mBtnRecordMileage)
                .subscribe(view->navigationTo(MileageAddActivity.class)));

        addDisposable(RxView.clicks(mVBill)
                .subscribe(view -> navigationTo(BillActivity.class)));

        addDisposable(RxView.clicks(mVOrders)
                .subscribe(view -> ReflectUtils.navigationToHome(mContext, 1)));

        addDisposable(RxView.clicks(mVSysFailure)
                .subscribe(view -> navigationTo(FaultActivity.class)));

        addDisposable(RxView.clicks(mVBill)
                .subscribe(view->navigationTo(BillActivity.class)));
    }


}


