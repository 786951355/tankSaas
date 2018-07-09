package com.tanker.mainmodule.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;

public interface WelcomeContract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenterImpl<View> {
        public Presenter(View view) {
            super(view);
        }

        public abstract void requestConfig();

        public abstract void requestSplashImg();
    }
}
