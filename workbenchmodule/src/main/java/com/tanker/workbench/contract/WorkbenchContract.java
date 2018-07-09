package com.tanker.workbench.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;
import com.tanker.basemodule.model.mine_model.UserInfoModel;

/**
 * Created by ronny on 2018/3/24.
 */

public interface WorkbenchContract {
    interface View extends BaseView {
        void refreshUI(UserInfoModel userInfoModel);

        void addCarrierUserSuccess();
    }

    abstract class Presenter extends BasePresenterImpl<View> {
        public Presenter(View view) {
            super(view);
        }

        public abstract void getMineInfo();

        public abstract void LoginOut();

        /**
         * @author lwj
         * @Description: 添加车主
         * @date 2018/5/25 16:25
         */
        public abstract void addCarrierUser(String carrierCompanyName);
    }

}
