package com.tanker.homemodule.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;

/**
 * author zhanglei
 * date 2018/7/16
 * description 故障上报
**/
public interface FaultAddContract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenterImpl<FaultAddContract.View> {
        public Presenter(FaultAddContract.View view) {
            super(view);
        }
    }
}
