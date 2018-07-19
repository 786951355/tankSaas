package com.tanker.homemodule.contract;

import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.base.baseImpl.BasePresenterImpl;
import com.tanker.basemodule.model.home_model.MileageDetailModel;

/**
 * author zhanglei
 * date 2018/7/16
 * description 故障详情&&故障编辑&&添加
 **/
public interface MileageEditContract {
    interface View extends BaseView {
        void refreshUI(MileageDetailModel mileageDetailModel);

        void refreshImageView(String path);
    }

    abstract class Presenter extends BasePresenterImpl<MileageEditContract.View> {
        public Presenter(MileageEditContract.View view) {
            super(view);
        }

        public abstract void getMileageDetail();

        public abstract void addMileage();

        public abstract void upload(String path);
    }
}
