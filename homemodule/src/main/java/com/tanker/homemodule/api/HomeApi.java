package com.tanker.homemodule.api;

import android.util.Base64;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.http.HttpParam;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.http.api.RetroAPIFactory;
import com.tanker.basemodule.model.home_model.BillDetailListModel;
import com.tanker.basemodule.model.home_model.BillListModel;
import com.tanker.basemodule.model.home_model.FaultDetailModel;
import com.tanker.basemodule.model.home_model.FaultListModel;
import com.tanker.basemodule.model.home_model.MileageDetailModel;
import com.tanker.basemodule.model.home_model.MileageListModel;

import java.io.File;
import java.io.FileInputStream;
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

    public Observable<HttpResult<String>> uploadFile(String path) {

        String base64File = "";
        File file = new File(path);

        String[] strings = file.getName().split("\\.");
        try {
            base64File = encodeBase64File(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, String> hashMap = HttpParam.createParams()
                .putParam("userCode", "123")
                .putParam("file", base64File)
                .putParam("fileType", strings[strings.length - 1])
                .endWithoutSign();
        return homeService.uploadFile(hashMap);

    }

    public static String encodeBase64File(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }


    /**
     * @author lwj
     * @describe 获取账单列表数据
     * @params
     * @time 2018/7/16 17:04
     */
    public Observable<HttpResult<BillListModel>> getBillInfoList(String page) {
        HashMap<String, String> paramMap = HttpParam.createParams()
                .putParam(AppConstants.PARAM_PAGE, page)
                .putParam(AppConstants.PARAM_PAGE_SIZE, String.valueOf(AppConstants.ROWS))
                .end();
        return homeService.getBillInfoList(paramMap);
    }

    /**
     * @author lwj
     * @describe 获取账单详情列表数据
     * @params
     * @time 2018/7/16 17:04
     */
    public Observable<HttpResult<BillDetailListModel>> getBillDetailInfoList(String page) {
        HashMap<String, String> paramMap = HttpParam.createParams()
                .putParam(AppConstants.PARAM_PAGE, page)
                .putParam(AppConstants.PARAM_PAGE_SIZE, String.valueOf(AppConstants.ROWS))
                .end();
        return homeService.getBillDetailInfoList(paramMap);
    }

    /**
     * @param page
     * @return 故障列表
     */
    public Observable<HttpResult<FaultListModel>> getFaultList(int page) {
        HashMap<String, String> params = HttpParam.createParams()
                .putParam(AppConstants.PARAM_PAGE, String.valueOf(page))
                .putParam(AppConstants.PARAM_PAGE_SIZE, String.valueOf(AppConstants.ROWS))
                .end();
        return homeService.getFaultList(params);
    }

    /**
     * @return 故障详情
     */
    public Observable<HttpResult<FaultDetailModel>> getFaultDetail() {
        HashMap<String, String> params = HttpParam.createParams()
                .end();
        return homeService.getFaultDetail(params);
    }

    /**
     * @return 故障上报返回详情
     */
    public Observable<HttpResult<FaultDetailModel>> addFault() {
        HashMap<String, String> params = HttpParam.createParams()
                .end();
        return homeService.addFault(params);
    }

    /**
     * @param page
     * @return 里程列表
     */
    public Observable<HttpResult<MileageListModel>> getMileageList(int page) {
        HashMap<String, String> params = HttpParam.createParams()
                .putParam(AppConstants.PARAM_PAGE, String.valueOf(page))
                .putParam(AppConstants.PARAM_PAGE_SIZE, String.valueOf(AppConstants.ROWS))
                .end();
        return homeService.getMileageList(params);
    }

    /**
     * @return 里程上报或更新返回详情
     */
    public Observable<HttpResult<MileageDetailModel>> addOrUpdateMileage() {
        HashMap<String, String> params = HttpParam.createParams()
                .end();
        return homeService.addOrUpdateMileage(params);
    }

    /**
     * @return 里程详情
     */
    public Observable<HttpResult<MileageDetailModel>> getMileageDetail() {
        HashMap<String, String> params = HttpParam.createParams()
                .end();
        return homeService.getMileageDetail(params);
    }

}
