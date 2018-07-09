package com.tanker.basemodule.http.api;

import com.tanker.basemodule.model.mine_model.CarInfoModel;
import com.tanker.basemodule.model.mine_model.CarListModel;
import com.tanker.basemodule.model.mine_model.CarrierListModel;
import com.tanker.basemodule.model.mine_model.DriverInfoModel;
import com.tanker.basemodule.model.mine_model.DriverListModel;
import com.tanker.basemodule.model.mine_model.FollowedCities;
import com.tanker.basemodule.model.mine_model.LineListModel;
import com.tanker.basemodule.model.mine_model.QualificationInfoModel;
import com.tanker.basemodule.model.mine_model.UserInfoModel;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by ronny on 2018/3/23.
 */

public interface MineService {

    /*******************************车主信息相关api************************************/

    /**
     * 获取我的相关信息
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/carrier/getCarrierInfo")
    Observable<HttpResult<UserInfoModel>> getMineInfo(@Body HashMap<String, String> hashMap);

    /**
     * 获取用户资质信息
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/carrier/getQualificationInfo")
    Observable<HttpResult<QualificationInfoModel>> getQualificationInfo(@Body HashMap<String, String> hashMap);

    /**
     * 修改／提交用户资质信息
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/carrier/updateQualification")
    Observable<HttpResult<String>> updateQualificationInfo(@Body HashMap<String, String> hashMap);

    /**
     * 查看授权书图片
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/carrier/getAuthorization")
    Observable<HttpResult<String>> getAuthImg(@Body HashMap<String, String> hashMap);

    /**
     * 确认授权书
     *
     * @param paramMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/carrier/confirmAuthorization")
    Observable<HttpResult<String>> confirmAuthBook(@Body HashMap<String, String> paramMap);


    /*******************************用户相关api************************************/

    /**
     * 获取短信验证码
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/user/getAuthCode")
    Observable<HttpResult<String>> getMsgCode(@Body HashMap<String, String> hashMap);

    /**
     * 获取短信验证码
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/user/verifyVerifyCode")
    Observable<HttpResult<String>> checkMsgCode(@Body HashMap<String, String> hashMap);

    /**
     * 验证短信／身份证号
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/user/verifyIdentity")
    Observable<HttpResult<String>> verifyIDOrMsgCode(@Body HashMap<String, String> hashMap);

    /**
     * 绑定新手机号码
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/user/bindPhoneNumber")
    Observable<HttpResult<String>> bindNewPhone(@Body HashMap<String, String> hashMap);

    /**
     * 绑定新手机号码
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/user/userLogout")
    Observable<HttpResult<String>> logout(@Body HashMap<String, String> hashMap);


    /*******************************线路相关api************************************/

    /**
     * 获取已关注的线路信息
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/line/getLineList")
    Observable<HttpResult<LineListModel>> getLineList(@Body HashMap<String, String> hashMap);

    /**
     * 添加关注的线路信息
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/line/addLine")
    Observable<HttpResult<String>> addLine(@Body HashMap<String, Object> hashMap);

    /**
     * 删除已关注的线路信息
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/line/deleteLineById")
    Observable<HttpResult<String>> deleteLineById(@Body HashMap<String, Object> hashMap);

    @Headers("Content-Type: application/json")
    @POST("/line/getCityIdByProvinceId")
    Observable<HttpResult<FollowedCities>> getFollowedCities(@Body HashMap<String, String> paramMap);


    /************************************车辆相关api************************************/

    /**
     * 获取车辆详情信息
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/vehicle/getVehicleInfoById")
    Observable<HttpResult<CarInfoModel>> getCarInfoById(@Body HashMap<String, String> hashMap);

    /**
     * 获取车辆详情信息
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/vehicle/getVehicleList")
    Observable<HttpResult<CarListModel>> getCarList(@Body HashMap<String, String> hashMap);

    /**
     * 获取车辆详情信息
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/vehicle/addOrUpdateVehicle")
    Observable<HttpResult<CarInfoModel>> addCar(@Body HashMap<String, String> hashMap);

    /**
     * 获取车辆详情信息
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/vehicle/deleteVehicleById")
    Observable<HttpResult<String>> deleteCar(@Body HashMap<String, Object> hashMap);


    /************************************司机／押车员相关api************************************/

    @Headers("Content-Type: application/json")
    @POST("/driver/getDriverList")
    Observable<HttpResult<DriverListModel>> getDriverList(@Body HashMap<String, String> hashMap);

    @Headers("Content-Type: application/json")
    @POST("/carrier/searchDriverOrEscort")
    Observable<HttpResult<DriverListModel>> searchDriverOrEscort(@Body HashMap<String, String> hashMap);

    @Headers("Content-Type: application/json")
    @POST("/carrier/inviteDriver")
    Observable<HttpResult<String>> inviteDriver(@Body HashMap<String, Object> hashMap);

    @Headers("Content-Type: application/json")
    @POST("/driver/deleteOrDisableDriverById")
    Observable<HttpResult<String>> deleteOrForbiddenDriver(@Body HashMap<String, Object> hashMap);

    @Headers("Content-Type: application/json")
    @POST("/driver/updateDriverOrEscortStatus")
    Observable<HttpResult<String>> updateDriverOrEscortStatus(@Body HashMap<String, Object> hashMap);

    @Headers("Content-Type: application/json")
    @POST("/driver/getDriverOrEscortInfoById")
    Observable<HttpResult<DriverInfoModel>> getDriverInfo(@Body HashMap<String, String> paramMap);


    @Headers("Content-Type: application/json")
    @POST("/driver/addOrUpdateDriverOrEscort")
    Observable<HttpResult<DriverInfoModel>> addOrUpdate(@Body HashMap<String, String> paramMap);


    /**
     * 上传图片api
     *
     * @param uid
     * @param fileType 图片上传的类型，1为身份证正面,2为身份证背面,3公司营业执照,4为授权书,5为行驶证,6为车辆道路运输许可证,7为驾驶证,8为司机从业资格证,8为押车员从业资格证
     * @param pic
     * @return
     */
    @Multipart
    @POST("/common/uploadFile")
    Observable<HttpResult<String>> uploadFile(
            @Part("uid") RequestBody uid,
            @Part("userCode") RequestBody userCode,
            @Part("token") RequestBody token,
            @Part("fileType") RequestBody fileType,
            @Part MultipartBody.Part pic);


    @Headers("Content-Type: application/json")
    @POST("/common/uploadFile")
    Observable<HttpResult<String>> uploadFile(@Body HashMap<String, String> hashMap);


    /**
     * 下载文件
     *
     * @param authBookPath
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String authBookPath);

    /**
     * @author lwj
     * @Description: 获取关联车主列表
     * @date 2018/5/23 11:40
     */
    @Headers("Content-Type: application/json")
    @POST("/driver/getCarrierInfoList")
    Observable<HttpResult<CarrierListModel>> getCarrierInfoList(@Body HashMap<String, String> hashMap);

    /**
     * @author lwj
     * @Description: 添加车主接口
     * @date 2018/5/25 16:20
     */
    @Headers("Content-Type: application/json")
    @POST("/driver/addCarrierUser")
    Observable<HttpResult<String>> addCarrierUser(@Body HashMap<String, String> hashMap);


    /**
     * @author luorenyu
     * @Description: 认证道路资质
     */
    @Headers("Content-Type: application/json")
    @POST("/carrier/updateRoadLicense")
    Observable<HttpResult<String>> commitRoadTrans(@Body HashMap<String, String> paramMap);
}
