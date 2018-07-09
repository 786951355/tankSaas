package com.tanker.orders.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;

/**
 * 抢单顶部导航数据数量变化
 */
public interface OrdersContract {

    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenterImpl<View> {
        public Presenter(View view) {
            super(view);
        }

        /**
         * 获取抢单顶部统计数字
         */
        public abstract void getOrderCount();

        /**
         * 获取抢单减少顶部统计数字
         */
        public abstract void reduceOrderCount(String type);
    }
}
