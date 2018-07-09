package com.tanker.loginmodule.presenter;

import android.text.TextUtils;

import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.common.TankerApp;
import com.tanker.basemodule.http.CommonObserver;
import com.tanker.basemodule.http.ExceptionEngine;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.login_model.ConfigInfo;
import com.tanker.basemodule.model.login_model.LoginModel;
import com.tanker.basemodule.model.login_model.UserInfo;
import com.tanker.basemodule.utils.FileUtils;
import com.tanker.loginmodule.R;
import com.tanker.loginmodule.api.LoginApi;
import com.tanker.loginmodule.contract.RegisterContract;
import com.tanker.loginmodule.view.TankerRegisterSuccessActivity;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ronny on 2018/3/27.
 */

public class RegisterPresenter extends RegisterContract.Presenter {

    public interface IregisterResponseCallBack {
        void registerResponse(UserInfo userInfo);
    }

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String platform, String userPhone, String userPwd, String vertificationCode) {
        doRegister(platform, userPhone, userPwd, vertificationCode);
        processDownLoad();
    }

    private void doRegister(String platform, String userPhone, String userPwd, String vertificationCode) {
        Observable<HttpResult<LoginModel>> register = LoginApi.getInstance().register(platform, userPhone, userPwd, vertificationCode);
        toSubscribe(register, new CommonObserver<LoginModel>(mView.getContext()) {
            @Override
            public void onNext(LoginModel loginModel) {
                UserInfo user = loginModel.getUserInfo();
                if (user!=null){
                    //AuthBookActiivty的退出字段是通过AuditStatus是否为空判断的
                    //所以如果是注册过去的设置盖字段
                    user.setAuditStatus("");
                    TankerApp.getInstance().getUserManager().setUser(user);
                    TankerApp.getInstance().updateToken(loginModel.getToken());
                    //为极光设置别名（登陆手机号码）
                    JPushInterface.setAlias(mView.getContext(), 1, user.getMobilePhone());
                    mView.dismissProgress();
                    mView.navigationTo(TankerRegisterSuccessActivity.class);
                    mView.getContext().finish();
                }else{
                    mView.showMessage("用户信息获取失败，请重新尝试");
                }
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.showMessage(t.message);
            }
        });
    }

    private void processDownLoad() {
        ConfigInfo configInfo = TankerApp.getInstance().getConfigManager().getConfigInfo();

        if (!configInfo.isNeedUpdateJson()) {//是否需要更新json
            return;
        }
        Disposable province = LoginApi.getInstance().downloadFile(configInfo.getAppJsonSrc())
                .map(responseBody -> {
                    String tempFilePath =
                            FileUtils.writeResponseBodyToDisk("province", responseBody,
                                    FileUtils.TYPE_JPG);
                    return TextUtils.isEmpty(tempFilePath);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    Logger.d("下载Json成功：" + aBoolean);
                    if (aBoolean) {
                        TankerApp.getInstance().getConfigManager().setNeedUpdate(false);
                    }
                });
        addDisposable(province);
    }

    @Override
    public void getCode(String mobilePhone, String platform, String smsTemplateType) {
        Observable<HttpResult<String>> getCodeObservable = LoginApi.getInstance().
                getCode(mobilePhone, platform, smsTemplateType);
        toSubscribe(getCodeObservable, new CommonObserver<String>(mView.getContext(), false) {
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

    @Override
    public void requestConfig(final String platform, final String userPhone, final String userPwd, final String vertificationCode) {
        Observable<HttpResult<ConfigInfo>> httpResultObservable = LoginApi.getInstance().requestConfig();
        toSubscribe(httpResultObservable, new CommonObserver<ConfigInfo>(mView.getContext()) {
            @Override
            public void onNext(ConfigInfo configInfo) {
                configInfo.setNeedUpdate(true);
                TankerApp.getInstance().getConfigManager().setConfigInfo(configInfo);
                register(platform, userPhone,userPwd,vertificationCode);
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.showMessage(t.message);
            }
        });
    }

}
