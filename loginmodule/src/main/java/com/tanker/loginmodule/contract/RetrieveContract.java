package com.tanker.loginmodule.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;

/**
 * Created by ronny on 2018/3/27.
 */

public interface RetrieveContract {
    interface View extends BaseView {
    }

    abstract class Presenter extends BasePresenterImpl<View> {
        public Presenter(View view) {
            super(view);
        }

        //找回密码
        public abstract void retrievePwd(String platform, String userPhone, String verficationCode, String oldPwd, String newPwd);

        public abstract void getCode(String mobilePhone, String platform, String smsTemplateType);
    }
}
