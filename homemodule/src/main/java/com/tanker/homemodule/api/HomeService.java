package com.tanker.homemodule.api;

import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.home_model.BillDetailListModel;
import com.tanker.basemodule.model.home_model.BillListModel;
import com.tanker.basemodule.model.mine_model.CarrierListModel;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author lwj
 * @ClassName: HomeService
 * @Description: 订单管理接口
 * @date 2018/4/26
 */
public interface HomeService {


    @Headers("Content-Type: application/json")
    @POST("/common/uploadFile")
    Observable<HttpResult<String>> uploadFile(@Body HashMap<String, String> hashMap);

    /**
     *  @author lwj
     *  @describe 获取账单列表数据
     *  @params
     *  @time 2018/7/16 17:04
     */
    @Headers("Content-Type: application/json")
    @POST("/driver/getCarrierInfoList")
    Observable<HttpResult<BillListModel>> getBillInfoList(@Body HashMap<String, String> hashMap);

    /**
     *  @author lwj
     *  @describe 获取账单详情列表数据
     *  @params
     *  @time 2018/7/16 17:04
     */
    @Headers("Content-Type: application/json")
    @POST("/driver/getCarrierInfoList")
    Observable<HttpResult<BillDetailListModel>> getBillDetailInfoList(@Body HashMap<String, String> hashMap);
}
