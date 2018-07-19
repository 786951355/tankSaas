package com.tanker.homemodule.presenter;

import com.tanker.basemodule.http.CommonObserver;
import com.tanker.basemodule.http.ExceptionEngine;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.home_model.FaultDetailModel;
import com.tanker.homemodule.api.HomeApi;
import com.tanker.homemodule.contract.FaultDetailContract;

import io.reactivex.Observable;

/**
 * author zhanglei
 * date 2018/7/16
 * description 故障详情数据返回
 **/
public class FaultDetailPresenter extends FaultDetailContract.Presenter {


    public FaultDetailPresenter(FaultDetailContract.View view) {
        super(view);
    }

    @Override
    public void getFaultDetail() {
        Observable<HttpResult<FaultDetailModel>> resultObservable = HomeApi.getInstance().getFaultDetail();
        toSubscribe(resultObservable, new CommonObserver<FaultDetailModel>(
                mView.getContext()) {
            @Override
            public void onNext(FaultDetailModel orderDetailModel) {
                mView.refreshUI(orderDetailModel);
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.showMessage(t.message);
            }
        });
    }
}
