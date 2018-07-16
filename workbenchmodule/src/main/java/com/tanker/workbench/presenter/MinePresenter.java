package com.tanker.workbench.presenter;

import com.tanker.basemodule.common.SaasApp;
import com.tanker.basemodule.http.CommonObserver;
import com.tanker.basemodule.http.ExceptionEngine;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.workbench.api.MineApi;
import com.tanker.workbench.contract.MineContract;

import io.reactivex.Observable;

/**
 * Created by ronny on 2018/3/24.
 */

public class MinePresenter extends MineContract.Presenter {

    public MinePresenter(MineContract.View view) {
        super(view);
    }

    @Override
    public void getMineInfo() {
//        Observable<HttpResult<UserInfoModel>> resultObservable = MineApi.getInstance().getMineInfo();
//        toSubscribe(resultObservable, new CommonObserver<UserInfoModel>(WorkbenchConstants.MINE_INFO_KEY, mView.getContext(), false) {
//            @Override
//            public void onNext(UserInfoModel userInfoModel) {
//                SaasApp.getInstance().getUserManager().updateUserInfo(userInfoModel);
//                mView.refreshUI(userInfoModel);
//            }
//
//            @Override
//            public void onError(ExceptionEngine.ResponseThrowable t) {
//                mView.showMessage(t.message);
//            }
//        }, WorkbenchConstants.MINE_INFO_KEY, true);
    }

    @Override
    public void LoginOut() {
        Observable<HttpResult<String>> observable = MineApi.getInstance().logout();
        toSubscribe(observable, new CommonObserver<String>(mView.getContext()) {
            @Override
            public void onNext(String s) {
                SaasApp.getInstance().exit();
                mView.getContext().finish();
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.showMessage(t.message);
                SaasApp.getInstance().exit();
                mView.getContext().finish();
            }
        });
    }

    /**
     * @author lwj
     * @Description: 添加车主
     * @date 2018/5/25 16:26
     */
    @Override
    public void addCarrierUser(String carrierCompanyName) {
        Observable<HttpResult<String>> resultObservable = MineApi.getInstance().addCarrierUser(carrierCompanyName);
        toSubscribe(resultObservable, new CommonObserver<String>(mView.getContext()) {
            @Override
            public void onNext(String s) {
                mView.addCarrierUserSuccess();
                mView.showMessage(s);
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.showMessage(t.message);
            }
        });

    }

}
