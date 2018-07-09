package com.tanker.orders.presenter;

import com.tanker.orders.contract.OrdersContract;


public class OrdersPresenter extends OrdersContract.Presenter {

    public OrdersPresenter(OrdersContract.View view) {
        super(view);
    }

    /**
     * 获取抢单顶部统计数字
     */
    @Override
    public void getOrderCount() {

    }

    /**
     * 获取抢单减少顶部统计数字
     */
    @Override
    public void reduceOrderCount(String type) {

    }
}
