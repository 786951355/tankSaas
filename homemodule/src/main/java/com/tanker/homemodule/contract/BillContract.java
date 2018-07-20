package com.tanker.homemodule.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;
import com.tanker.basemodule.model.home_model.BillListModel;


/**
* @author lwj
* @ClassName: BillContract
* @Description:  账单列表
* @date 2018/7/16 15:10
*/
public interface BillContract {

    interface View extends BaseView {
        //获取账单列表数据
        void getBillInfoList(int page, BillListModel model);
        void dismissSwipeRefresh();
    }

    abstract class Presenter extends BasePresenterImpl<View> {
        public Presenter(View view) {
            super(view);
        }

        /**
         *  @author lwj
         *  @describe
         *  @params 获取账单信息列表
         *  @time 2018/7/16 14:22
         */
        public abstract void getBillInfoList(int page);

    }
}
