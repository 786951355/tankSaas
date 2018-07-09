package com.tanker.basemodule.router;

import com.tanker.basemodule.event.OrdersMsg;

public interface OrdersCall extends BaseModuleCall {
    void refreshOrderUI(OrdersMsg ordersMsg);
}
