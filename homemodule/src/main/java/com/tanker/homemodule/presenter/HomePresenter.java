package com.tanker.homemodule.presenter;


import com.tanker.homemodule.contract.HomeContract;

/**
* @author lwj
* @ClassName: HomePresenter
* @Description:  首页
* @date 2018/7/16 14:22
*/
public class HomePresenter extends HomeContract.Presenter {

    private static final String TAG = "HomePresenter";

    public HomePresenter(HomeContract.View view) {
        super(view);
    }


}
