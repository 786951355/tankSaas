package com.tanker.mainmodule.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;
import com.tanker.basemodule.model.app_model.StatisticalHeadModel;

public interface MainContract {
    interface View extends BaseView {
        void refreshStatistical(StatisticalHeadModel model);
    }

    abstract class Presenter extends BasePresenterImpl<MainContract.View> {
        public Presenter(MainContract.View view) {
            super(view);
        }

        public abstract void getStatisticalHead();

        public abstract void clearIsRead();
    }
}
