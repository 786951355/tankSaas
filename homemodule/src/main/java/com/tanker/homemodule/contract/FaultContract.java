package com.tanker.homemodule.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;

/**
 * author zhanglei
 * date 2018/7/16
 * description 故障列表接口
 **/
public interface FaultContract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenterImpl<FaultContract.View> {
        public Presenter(FaultContract.View view) {
            super(view);
        }
    }
}
