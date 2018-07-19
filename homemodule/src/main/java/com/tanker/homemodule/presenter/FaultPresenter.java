package com.tanker.homemodule.presenter;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.http.CommonObserver;
import com.tanker.basemodule.http.ExceptionEngine;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.home_model.FaultListModel;
import com.tanker.basemodule.model.home_model.FaultModel;
import com.tanker.homemodule.api.HomeApi;
import com.tanker.homemodule.contract.FaultContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * author zhanglei
 * date 2018/7/16
 * description 故障列表数据返回
 **/
public class FaultPresenter extends FaultContract.Presenter {


    public FaultPresenter(FaultContract.View view) {
        super(view);
    }

    @Override
    public void getFaults(int page) {
        String cacheKey = AppConstants.CACHE_KEY.FAULT_LIST;
        Observable<HttpResult<FaultListModel>> resultObservable = HomeApi.getInstance().getFaultList(page);
        toSubscribe(resultObservable, new CommonObserver<FaultListModel>(page == 1 ? cacheKey : null, mView.getContext(), false) {
            @Override
            public void onNext(FaultListModel faultListModel) {
                mView.dismissSwipeRefresh();
                mView.refreshUI(page, faultListModel);
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.dismissSwipeRefresh();
                //TODO
//                mView.showMessage(t.message);
                FaultListModel faultListModel = new FaultListModel();
                faultListModel.setTotal(50);
                List<FaultModel> faultModels = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    faultModels.add(new FaultModel());
                }
                faultListModel.setFaultModelList(faultModels);
                mView.refreshUI(page, faultListModel);
            }
        }, cacheKey, page == 1);
    }
}
