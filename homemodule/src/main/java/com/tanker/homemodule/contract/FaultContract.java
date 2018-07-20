package com.tanker.homemodule.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;
import com.tanker.basemodule.model.home_model.FaultListModel;

/**
 * author zhanglei
 * date 2018/7/16
 * description 故障列表接口
 **/
public interface FaultContract {

    interface View extends BaseView {
        void refreshUI(int page, FaultListModel faultListModel);

        void dismissSwipeRefresh();
    }

    abstract class Presenter extends BasePresenterImpl<FaultContract.View> {
        public Presenter(FaultContract.View view) {
            super(view);
        }

        public abstract void getFaults(int page);
    }
}
