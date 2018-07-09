package com.tanker.basemodule.router;


import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.login_model.ConfigInfo;

import io.reactivex.Observable;

public interface AppCall extends BaseModuleCall {
    void goHome();

    Observable<HttpResult<ConfigInfo>> requestConfig();

    void updateIsRead(String notificationOwnerRelationId);

    /**
     * @author lwj
     * @describe 用户接收到通知以后回调接口统计数量
     * @params
     * @time 2018/6/26 10:20
     */
    void receiveNotification(String noticeId);

    /**
     * @author lwj
     * @describe 用户在app内阅读公告, 返回时统计阅读时长
     * @params
     * @time 2018/6/26 10:20
     */
    void notificationReadBack(String noticeId);
}
