package com.tanker.loginmodule.presenter;

import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.common.TankerApp;
import com.tanker.basemodule.http.CommonObserver;
import com.tanker.basemodule.http.ExceptionEngine;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.login_model.ConfigInfo;
import com.tanker.basemodule.model.login_model.LoginModel;
import com.tanker.basemodule.model.login_model.UserInfo;
import com.tanker.basemodule.router.ReflectUtils;
import com.tanker.basemodule.utils.FileUtils;
import com.tanker.loginmodule.R;
import com.tanker.loginmodule.api.LoginApi;
import com.tanker.loginmodule.common.ViewUtils;
import com.tanker.loginmodule.contract.LoginContract;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ronny on 2018/3/28.
 */

public class LoginPresenter extends LoginContract.Presenter {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }


    @Override
    public void login(final String userPhone, String passwordOrCode, final String loginType) {
        processDownLoad();
        doLogin(userPhone, passwordOrCode, loginType);
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

    private void doLogin(final String userPhone, String passwordOrCode, String loginType) {
        Observable<HttpResult<LoginModel>> loginObservable = LoginApi.getInstance().login(userPhone, passwordOrCode, loginType);
        toSubscribe(loginObservable, new CommonObserver<LoginModel>(mView.getContext()) {
            @Override
            public void onNext(LoginModel loginModel) {
                UserInfo tankerUser = loginModel.getUserInfo();
                if (tankerUser != null) {
                    TankerApp.getInstance().getUserManager().setUser(tankerUser);
                    TankerApp.getInstance().updateToken(loginModel.getToken());
                    //存储最近一次登录的手机号
                    Hawk.put(AppConstants.HAWK_RECENT_ACCOUNT, userPhone);
                    if (tankerUser.isHistoryUser()) {
                        mView.showMessage(mView.getContext().getString(R.string.tips_old_user));
                    }
                    //为极光设置别名（登陆手机号码）
                    JPushInterface.setAlias(mView.getContext(), 1, tankerUser.getMobilePhone());
                    ReflectUtils.navigationToHome(mView.getContext(), 0);
                    mView.getContext().finish();
                } else {
                    mView.showMessage("用户信息获取失败，请重新尝试");
                }
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                ViewUtils.showToast(mView.getContext(), t.message);
            }
        });
    }

    @Override
    public void requestConfig(final String userPhone, final String passwordOrCode, final String loginType) {
        Observable<HttpResult<ConfigInfo>> httpResultObservable = LoginApi.getInstance().requestConfig();
        toSubscribe(httpResultObservable, new CommonObserver<ConfigInfo>(mView.getContext()) {
            @Override
            public void onNext(ConfigInfo configInfo) {
                configInfo.setNeedUpdate(true);
                TankerApp.getInstance().getConfigManager().setConfigInfo(configInfo);
                login(userPhone, passwordOrCode, loginType);
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
//                mView.showMessage(mView.getContext().getString(R.string.tips_get_msg_success));
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.showMessage(t.message);
            }
        });
    }
}
