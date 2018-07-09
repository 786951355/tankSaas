package com.zhaoguanche.setting.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;

public interface SettingContract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenterImpl<View> {

        public Presenter(View view) {
            super(view);
        }


    }
}
