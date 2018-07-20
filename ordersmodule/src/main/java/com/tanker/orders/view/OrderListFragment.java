package com.tanker.orders.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.tanker.basemodule.adapter.CommonAdapter;
import com.tanker.basemodule.adapter.ViewHolder;
import com.tanker.basemodule.base.BaseFragment;
import com.tanker.orders.R;
import com.tanker.orders.contract.OrderListContract;
import com.tanker.orders.presenter.OrderListPresenter;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class OrderListFragment extends BaseFragment<OrderListPresenter> implements OrderListContract.View {
    private static final String TAG = OrderListFragment.class.getName();

    public static OrderListFragment newInstance(String type) {
        OrderListFragment f = new OrderListFragment();
        Bundle b = new Bundle();
        b.putString("orderStateType", type);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.orders_fragment_order_list;
    }

    @Override
    protected void initView(View view) {
        RecyclerView mRcOrderList=view.findViewById(R.id.rc_order_list);
        List mData = null;
        mRcOrderList.setAdapter(new CommonAdapter(mContext,R.layout.ordersmodule_item_order_list,mData) {
            @Override
            public void convert(ViewHolder holder, Object o, int position) {

            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new OrderListPresenter(this);

    }


}
