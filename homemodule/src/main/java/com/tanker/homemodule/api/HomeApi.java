package com.tanker.homemodule.api;

import com.tanker.basemodule.http.api.RetroAPIFactory;

/**
 * @author lwj
 * @ClassName: HomeApi
 * @Description: 接口请求封装
 * @date 2018/4/26
 */
public class HomeApi {

    static {
        ordersApi = new HomeApi();
    }

    private static HomeApi ordersApi;
    private final HomeService ordersService;

    public HomeApi() {
        ordersService = RetroAPIFactory.create(HomeService.class);
    }

    public static HomeApi getInstance() {
        return ordersApi;
    }

}
