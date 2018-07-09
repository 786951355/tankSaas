package com.tanker.loginmodule.presenter;

import com.tanker.basemodule.http.CommonObserver;
import com.tanker.basemodule.http.ExceptionEngine;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.RetrieveModel;
import com.tanker.loginmodule.R;
import com.tanker.loginmodule.api.LoginApi;
import com.tanker.loginmodule.contract.RetrieveContract;

import io.reactivex.Observable;

public class RetrievePresenter extends RetrieveContract.Presenter {

    public RetrievePresenter(RetrieveContract.View view, IretrieveCallBack callBack) {
        super(view);
        this.iretrieveCallBack = callBack;
    }

    private IretrieveCallBack iretrieveCallBack;

    public interface IretrieveCallBack {
        void retrieve();
    }

    @Override
    public void retrievePwd(String platform, String userPhone, String verficationCode, String oldPwd, String newPwd) {
        Observable<HttpResult<RetrieveModel>> register = LoginApi.getInstance().retrievePwd
                (platform, userPhone, verficationCode, oldPwd, newPwd);
        toSubscribe(register, new CommonObserver<String>(mView.getContext()) {
            @Override
            public void onNext(String s) {
                if (iretrieveCallBack != null)
                    iretrieveCallBack.retrieve();
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.showMessage(t.message);
            }
        });
    }

    @Override
    public void getCode(String mobilePhone, String platform, String smsTemplateType) {
        Observable<HttpResult<String>> getCodeObservable = LoginApi.getInstance().
                getCode(mobilePhone, platform, smsTemplateType);
        toSubscribe(getCodeObservable, new CommonObserver<String>(mView.getContext()) {
            @Override
            public void onNext(String string) {
                mView.showMessage(mView.getContext().getString(R.string.tips_get_msg_success));
                mView.startCountDown();
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.showMessage(t.message);
            }
        });
    }
}
