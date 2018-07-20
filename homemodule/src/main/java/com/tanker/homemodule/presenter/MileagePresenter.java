package com.tanker.homemodule.presenter;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.http.CommonObserver;
import com.tanker.basemodule.http.ExceptionEngine;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.home_model.MileageListModel;
import com.tanker.basemodule.model.home_model.MileageModel;
import com.tanker.homemodule.api.HomeApi;
import com.tanker.homemodule.contract.MileageContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * author zhanglei
 * date 2018/7/16
 * description 里程列表数据返回
 **/
public class MileagePresenter extends MileageContract.Presenter {


    public MileagePresenter(MileageContract.View view) {
        super(view);
    }

    @Override
    public void getMileages(int page) {
        String cacheKey = AppConstants.CACHE_KEY.MILEAGE_LIST;
        Observable<HttpResult<MileageListModel>> resultObservable = HomeApi.getInstance().getMileageList(page);
        toSubscribe(resultObservable, new CommonObserver<MileageListModel>(page == 1 ? cacheKey : null, mView.getContext(), false) {
            @Override
            public void onNext(MileageListModel mileageListModel) {
                mView.dismissSwipeRefresh();
                mView.refreshUI(page, mileageListModel);
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.dismissSwipeRefresh();
                //TODO
//                mView.showMessage(t.message);
                MileageListModel mileageListModel = new MileageListModel();
                mileageListModel.setTotal(50);
                List<MileageModel> mileageModels = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    mileageModels.add(new MileageModel());
                }
                mileageListModel.setMileageModelList(mileageModels);
                mView.refreshUI(page, mileageListModel);
            }
        }, cacheKey, page == 1);
    }

}
