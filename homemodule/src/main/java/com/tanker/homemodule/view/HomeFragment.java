package com.tanker.homemodule.view;

import android.view.View;

import com.tanker.basemodule.base.BaseFragment;
import com.tanker.homemodule.R;
import com.tanker.homemodule.contract.HomeContract;
import com.tanker.homemodule.presenter.HomePresenter;


public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    @Override
    protected void initView(View view) {

    }


    @Override
    protected void initData() {
        super.initData();
        mPresenter = new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }


}
