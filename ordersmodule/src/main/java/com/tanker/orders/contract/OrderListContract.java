package com.tanker.orders.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;

public interface OrderListContract {

    interface View<T> extends BaseView {

    }

    abstract class Presenter extends BasePresenterImpl<View> {

        public Presenter(View view) {
            super(view);
        }
    }
}
