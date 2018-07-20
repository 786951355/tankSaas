package com.tanker.basemodule.http.api;

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
     * 绑定新手机号码
     *
     * @param hashMap
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("/user/userLogout")
    Observable<HttpResult<String>> logout(@Body HashMap<String, String> hashMap);

}
