package com.tanker.homemodule.api;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.http.HttpParam;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.http.api.RetroAPIFactory;
import com.tanker.basemodule.model.home_model.BillDetailListModel;
import com.tanker.basemodule.model.home_model.BillDetailModel;
import com.tanker.basemodule.model.home_model.BillListModel;
import com.tanker.basemodule.model.mine_model.CarrierListModel;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * @author lwj
 * @ClassName: HomeApi
 * @Description: 接口请求封装
 * @date 2018/4/26
 */
public class HomeApi {

    static {
        homeApi = new HomeApi();
    }

    private static HomeApi homeApi;
    private final HomeService homeService;

    public HomeApi() {
        homeService = RetroAPIFactory.create(HomeService.class);
    }

    public static HomeApi getInstance() {
        return homeApi;
    }

    /**
     *  @author lwj
     *  @describe 获取账单列表数据
     *  @params
     *  @time 2018/7/16 17:04
     */
    public Observable<HttpResult<BillListModel>> getBillInfoList(String page) {
        HashMap<String, String> paramMap = HttpParam.createParams()
                .putParam(AppConstants.PARAM_PAGE, page)
                .putParam(AppConstants.PARAM_PAGE_SIZE, String.valueOf(AppConstants.ROWS))
                .end();
        return homeService.getBillInfoList(paramMap);
    }

    /**
     *  @author lwj
     *  @describe 获取账单详情列表数据
     *  @params
     *  @time 2018/7/16 17:04
     */
    public Observable<HttpResult<BillDetailListModel>> getBillDetailInfoList(String page) {
        HashMap<String, String> paramMap = HttpParam.createParams()
                .putParam(AppConstants.PARAM_PAGE, page)
                .putParam(AppConstants.PARAM_PAGE_SIZE, String.valueOf(AppConstants.ROWS))
                .end();
        return homeService.getBillDetailInfoList(paramMap);
    }

}
