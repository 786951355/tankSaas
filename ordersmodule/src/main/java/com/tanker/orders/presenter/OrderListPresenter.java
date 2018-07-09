package com.tanker.orders.presenter;

import com.tanker.orders.contract.OrderListContract;

public class OrderListPresenter extends OrderListContract.Presenter {

    public OrderListPresenter(OrderListContract.View view) {
        super(view);
    }

}
