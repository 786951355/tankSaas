package com.tanker.homemodule.presenter;


import com.tanker.homemodule.contract.HomeContract;

/**
 * @author lwj
 * @ClassName: HomePresenter
 * @Description: 订单管理-详情接口数据返回
 * @date 2018/4/26
 */
public class HomePresenter extends HomeContract.Presenter {

    private static final String TAG = "HomePresenter";

    public HomePresenter(HomeContract.View view) {
        super(view);
    }


}
