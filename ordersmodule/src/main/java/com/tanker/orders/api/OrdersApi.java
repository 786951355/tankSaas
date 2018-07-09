package com.tanker.orders.api;

import com.tanker.basemodule.http.api.OrdersService;
import com.tanker.basemodule.http.api.RetroAPIFactory;

public class OrdersApi {

    static {
        mGrabOrderApi = new OrdersApi();
    }

    private static OrdersApi mGrabOrderApi;
    private final OrdersService mGrabOrderService;

    public OrdersApi() {
        mGrabOrderService = RetroAPIFactory.create(OrdersService.class);
    }

    public static OrdersApi getInstance() {
        return mGrabOrderApi;
    }


}
