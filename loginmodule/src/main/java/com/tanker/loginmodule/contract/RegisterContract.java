package com.tanker.loginmodule.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;

/**
 * Created by ronny on 2018/3/27.
 */

public interface RegisterContract {
    interface View extends BaseView {
        void startCountDown();
    }

    abstract class Presenter extends BasePresenterImpl<View> {
        public Presenter(View view) {
            super(view);
        }

        //注册方法
        public abstract void register(String platform, String userPhone, String userPwd, String verficationCode);

        public abstract void getCode(String mobilePhone, String platform, String smsTemplateType);

        public abstract void requestConfig(String platform, String userPhone, String userPwd, String vertificationCode);
    }
}
