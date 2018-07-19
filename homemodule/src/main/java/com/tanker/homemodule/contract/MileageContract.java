package com.tanker.homemodule.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;
import com.tanker.basemodule.model.home_model.MileageListModel;

/**
 * author zhanglei
 * date 2018/7/16
 * description 里程列表接口
 **/
public interface MileageContract {

    interface View extends BaseView {
        void refreshUI(int page, MileageListModel mileageListModel);

        void dismissSwipeRefresh();
    }

    abstract class Presenter extends BasePresenterImpl<MileageContract.View> {
        public Presenter(MileageContract.View view) {
            super(view);
        }

        public abstract void getMileages(int page);
    }
}
