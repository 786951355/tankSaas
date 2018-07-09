package com.tanker.basemodule.http.api;

import com.tanker.basemodule.model.home_model.AlreadQuoteModel;
import com.tanker.basemodule.model.home_model.BiddingOrderDetModel;
import com.tanker.basemodule.model.home_model.BiddingOrderModel;
import com.tanker.basemodule.model.home_model.BiddingPrefectModel;
import com.tanker.basemodule.model.home_model.GraborderOrderCountModel;
import com.tanker.basemodule.model.home_model.LastVehicleDriverModel;
import com.tanker.basemodule.model.mine_model.CarListModel;
import com.tanker.basemodule.model.mine_model.DriverListModel;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OrdersService {

    /**
     * 获取抢单、已报价、竞价成功、竞价失败列表,传递参数
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/order/getBiddingOrderList")
    Observable<HttpResult<BiddingOrderModel>> biddingList(@Body HashMap<String, String> hashMap);

    /**
     * 待抢单详情
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/order/getBiddingOrderDet")
    Observable<HttpResult<BiddingOrderDetModel>> rersverOrderInfo(@Body HashMap<String, String> hashMap);


    /**
     * 已报价详情
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/order/getAlreadQuote")
    Observable<HttpResult<AlreadQuoteModel>> alreadQuote(@Body HashMap<String, String> hashMap);

    /**
     * 忽略竞价
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/bidding/ignoreBidding")
    Observable<HttpResult<String>> ignoreBidding(@Body HashMap<String, String> hashMap);


    /**
     * 完善调度车辆接口
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/order/completeDispatchInfo")
    Observable<HttpResult<String>> completeDispatchInfo(@Body HashMap<String, Object> hashMap);

    /**
     * 用户报价接口
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/order/getBiddingPrefect")
    Observable<HttpResult<BiddingPrefectModel>> biddingPrefect(@Body HashMap<String, String> hashMap);


    /**
     * 确认报价接口
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/order/confirmQuoter")
    Observable<HttpResult<String>> confirmQuoter(@Body HashMap<String, String> hashMap);

    /**
     * 报价（单价和包车价）接口
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/order/quoteOrder")
    Observable<HttpResult<String>> quotedOrder(@Body HashMap<String, String> hashMap);

    /**
     * 获取最新成交司机和车辆
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/order/getLastVehicleAndDriver")
    Observable<HttpResult<LastVehicleDriverModel>> lastVehicleAndDriver(@Body HashMap<String, String> hashMap);

    /**
     * 车辆信息列表
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/vehicle/getVehicleList")
    Observable<HttpResult<CarListModel>> vehicleList(@Body HashMap<String, String> hashMap);

    /**
     * 司机押车员列表信息
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/driver/getDriverList")
    Observable<HttpResult<DriverListModel>> driverGuard(@Body HashMap<String, String> hashMap);

    /**
     * 获取抢单顶部统计数字
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/order/getOrderCount")
    Observable<HttpResult<GraborderOrderCountModel>> getOrderCount(@Body HashMap<String, String> hashMap);

    /**
     * 获取抢单减少顶部统计数字
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/order/reduceOrderCount")
    Observable<HttpResult<String>> getReduceOrderCount(@Body HashMap<String, String> hashMap);

    /**
     * 重新报价
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/bidding/reQuote")
    Observable<HttpResult<String>> reQuote(@Body HashMap<String, String> hashMap);

    /**
     * 根据询价单ID获取详情
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/order/gerBiddingInfoByEnquiryId")
    Observable<HttpResult<BiddingOrderDetModel>> getBiddingInfoByEnquiryId(@Body HashMap<String, String> hashMap);
}
