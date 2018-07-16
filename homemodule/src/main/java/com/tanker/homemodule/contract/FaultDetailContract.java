package com.tanker.homemodule.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;

/**
 * author zhanglei
 * date 2018/7/16
 * description 故障详情
 **/
public interface FaultDetailContract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenterImpl<FaultDetailContract.View> {
        public Presenter(FaultDetailContract.View view) {
            super(view);
        }
    }
}
