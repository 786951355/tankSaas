package com.tanker.homemodule.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;


/**
 * @author lwj
 * @ClassName: HomeContract
 * @Description: 订单详情
 * @date 2018/4/26
 */
public interface HomeContract {

    interface View extends BaseView {
    }

    abstract class Presenter extends BasePresenterImpl<View> {
        public Presenter(View view) {
            super(view);
        }

    }

}
