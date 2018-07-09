package com.tanker.loginmodule.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;

/**
 * Created by ronny on 2018/3/27.
 */

public interface LoginContract {
    interface View extends BaseView {
    }

    abstract class Presenter extends BasePresenterImpl<View> {
        public Presenter(View view) {
            super(view);
        }

        public abstract void login(String userName, String passwordOrCode, String loginType);

        public abstract void requestConfig(String userPhone, String passwordOrCode, String loginType);

        public abstract void getCode(String mobilePhone, String platform, String smsTemplateType);
    }
}
