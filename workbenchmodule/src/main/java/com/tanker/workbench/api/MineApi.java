package com.tanker.workbench.api;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.common.SaasApp;
import com.tanker.basemodule.http.HttpParam;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.http.api.MineService;
import com.tanker.basemodule.http.api.RetroAPIFactory;
import com.tanker.basemodule.model.mine_model.UserInfoModel;

import java.util.HashMap;

import io.reactivex.Observable;


/**
 * Created by ronny on 2018/3/23.
 */

public class MineApi {
    static {
        mMineApi = new MineApi();
    }

    private static MineApi mMineApi;
    private final MineService mineService;

    public MineApi() {
        mineService = RetroAPIFactory.create(MineService.class);
    }

    public static MineApi getInstance() {
        return mMineApi;
    }

    public Observable<HttpResult<UserInfoModel>> getMineInfo() {
        HashMap<String, String> paramMap = HttpParam.createParams()
                .end();
        return mineService.getMineInfo(paramMap);
    }


    public Observable<HttpResult<String>> logout() {
        HashMap<String, String> paramMap = HttpParam.createParams().end();
        paramMap.put(AppConstants.PARAM_PHONE, SaasApp.getInstance().getUserManager().getPhone());
        return mineService.logout(paramMap);
    }


    /**
     * @author lwj
     * @Description: 添加车主接口
     * @date 2018/5/25 16:20
     */
    public Observable<HttpResult<String>> addCarrierUser(String carrierCompanyName) {
        HashMap<String, String> paramMap = HttpParam.createParams()
                .putParam("carrierCompanyName", carrierCompanyName)
                .end();
        return mineService.addCarrierUser(paramMap);
    }

}
