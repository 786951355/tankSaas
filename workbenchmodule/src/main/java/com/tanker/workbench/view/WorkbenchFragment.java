package com.tanker.workbench.view;


import android.support.v4.app.Fragment;
import android.view.View;

import com.tanker.basemodule.base.BaseFragment;
import com.tanker.basemodule.model.mine_model.UserInfoModel;
import com.tanker.workbench.R;
import com.tanker.workbench.contract.WorkbenchContract;
import com.tanker.workbench.presenter.WorkbenchPresenter;
import com.umeng.analytics.MobclickAgent;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkbenchFragment extends BaseFragment<WorkbenchPresenter> implements WorkbenchContract.View, View.OnClickListener {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View parent) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        mPresenter = new WorkbenchPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getMineInfo();
        //手动统计页面(页面名称可自定义)
        MobclickAgent.onPageStart("我的界面");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        //手动统计页面(页面名称可自定义)
        MobclickAgent.onPageEnd("我的界面");
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

    }


    @Override
    public void refreshUI(UserInfoModel userInfoModel) {

    }

    @Override
    public void addCarrierUserSuccess() {

    }


}
