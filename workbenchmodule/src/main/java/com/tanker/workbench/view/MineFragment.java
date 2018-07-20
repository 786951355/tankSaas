package com.tanker.workbench.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tanker.basemodule.base.BaseFragment;
import com.tanker.basemodule.common.SaasApp;
import com.tanker.basemodule.dialog.TankerDialog;
import com.tanker.basemodule.model.mine_model.UserInfoModel;
import com.tanker.basemodule.router.ReflectUtils;
import com.tanker.workbench.R;
import com.tanker.workbench.contract.MineContract;
import com.tanker.workbench.presenter.MinePresenter;
import com.tencent.bugly.beta.Beta;
import com.umeng.analytics.MobclickAgent;


/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View, View.OnClickListener {
    //车辆备用金
    private RelativeLayout rl_reserve_money;
    //退出
    private RelativeLayout rl_exit;
    //检查更新
    private RelativeLayout rl_check_version;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View parent) {
        rl_reserve_money = parent.findViewById(R.id.rl_reserve_money);
        rl_reserve_money.setOnClickListener(this);
        rl_exit = parent.findViewById(R.id.rl_exit);
        rl_exit.setOnClickListener(this);
        rl_check_version = parent.findViewById(R.id.rl_check_version);
        rl_check_version.setOnClickListener(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        mPresenter = new MinePresenter(this);
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
        if (id == R.id.rl_exit) {
            TankerDialog.OptionListener optionListener = obDialog -> {
                ReflectUtils.navigationToLogin(mContext);
                SaasApp.getInstance().exit();
                mContext.finish();
            };
            showAlertDialog("确认退出当前账号吗？", R.drawable.warning, optionListener);
        } else if (id == R.id.rl_check_version) {
            //手动加测一次更新
            Beta.checkUpgrade();
        } else if (id == R.id.rl_reserve_money) {
            showMessage("车辆备用金");
        }
    }


    @Override
    public void refreshUI(UserInfoModel userInfoModel) {

    }

    @Override
    public void addCarrierUserSuccess() {

    }


}
