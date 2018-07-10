package com.tanker.mainmodule.presenter;

import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.common.SaasApp;
import com.tanker.basemodule.http.CommonObserver;
import com.tanker.basemodule.http.ExceptionEngine;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.app_model.SplashPicModel;
import com.tanker.basemodule.model.login_model.ConfigInfo;
import com.tanker.basemodule.router.ReflectUtils;
import com.tanker.mainmodule.api.AppApi;
import com.tanker.mainmodule.contract.WelcomeContract;
import com.tanker.mainmodule.view.GuideActivity;
import com.tanker.mainmodule.view.SplashActivity;

import io.reactivex.Observable;

public class WelcomePresenter extends WelcomeContract.Presenter {
    public WelcomePresenter(WelcomeContract.View view) {
        super(view);
    }

    @Override
    public void requestConfig() {
        Observable<HttpResult<ConfigInfo>> httpResultObservable = AppApi.getInstance().requestConfig();

        toSubscribe(httpResultObservable, new CommonObserver<ConfigInfo>(mView.getContext(), false) {
            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                requestSplashImg();
            }

            @Override
            public void onNext(ConfigInfo configInfo) {
                ConfigInfo config = Hawk.get(AppConstants.HAWK_CONFIG);
                if (config != null && Integer.valueOf(config.getAppJsonVersion()) < Integer.valueOf(configInfo.getAppJsonVersion())) {
                    configInfo.setNeedUpdate(true);
                } else if (config != null) {
                    config.setNeedUpdate(false);
                }
                Hawk.put(AppConstants.HAWK_CONFIG, configInfo);
                requestSplashImg();
            }
        });
    }

    @Override
    public void requestSplashImg() {
        Observable<HttpResult<SplashPicModel>> httpResultObservable = AppApi.getInstance().requestSplashImg();
        toSubscribe(httpResultObservable, new CommonObserver<SplashPicModel>(mView.getContext(), false) {
            @Override
            public void onNext(SplashPicModel splashPicModel) {
                Hawk.put(AppConstants.HAWK_SPLASH_IMG, splashPicModel);
                boolean isFirstIn = Hawk.get("isFirstIn", true);
                if (isFirstIn) {
                    Hawk.put("isFirstIn", false);
                    mView.navigationTo(GuideActivity.class);
                    mView.getContext().finish();
                } else {
                    if (splashPicModel != null && !TextUtils.isEmpty(splashPicModel.getPictureSrc())) { //防止跳空白界面
                        mView.navigationTo(SplashActivity.class);
                        mView.getContext().finish();
                    } else {
                        gotoLoginOrMain();
                    }
                }
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                boolean isFirstIn = Hawk.get("isFirstIn", true);
                if (isFirstIn) {//防止第一次进入app，配置请求失败导航栏界面被覆盖
                    Hawk.put("isFirstIn", false);
                    mView.navigationTo(GuideActivity.class);
                    mView.getContext().finish();
                } else {
                    gotoLoginOrMain();
                }
            }
        });
    }

    private void gotoLoginOrMain() {
        if (SaasApp.getInstance().getUserManager().getUser() != null
                && SaasApp.getInstance().getUserManager().getUser().getUserId() != null) {
            ReflectUtils.navigationToHome(mView.getContext(), 0);
        } else {
            ReflectUtils.startActivityWithName(mView.getContext(), "com.tanker.loginmodule.view.TankerLoginActivity");
        }
        mView.getContext().finish();
    }
}
