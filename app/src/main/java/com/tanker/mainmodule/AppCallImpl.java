package com.tanker.mainmodule;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.login_model.ConfigInfo;
import com.tanker.mainmodule.api.AppApi;
import com.tanker.mainmodule.view.MainActivity;
import com.tanker.basemodule.router.AppCall;

import io.reactivex.Observable;

public class AppCallImpl implements AppCall {
    private BaseActivity context;

    @Override
    public void initContext(BaseActivity context) {
        this.context = context;
    }

    @Override
    public void goHome() {
        context.navigationTo(MainActivity.class);
    }

    @Override
    public Observable<HttpResult<ConfigInfo>> requestConfig() {
        return AppApi.getInstance().requestConfig();
    }

    @Override
    public void updateIsRead(String notificationOwnerRelationId) {

    }

    @Override
    public void receiveNotification(String noticeId) {

    }

    @Override
    public void notificationReadBack(String noticeId) {

    }

}
