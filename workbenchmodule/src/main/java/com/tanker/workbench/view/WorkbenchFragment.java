package com.tanker.workbench.view;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tanker.basemodule.base.BaseFragment;
import com.tanker.basemodule.model.mine_model.UserInfoModel;
import com.tanker.basemodule.widget.MineItem;
import com.tanker.workbench.R;
import com.tanker.workbench.contract.WorkbenchContract;
import com.tanker.workbench.presenter.WorkbenchPresenter;
import com.umeng.analytics.MobclickAgent;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkbenchFragment extends BaseFragment<WorkbenchPresenter> implements WorkbenchContract.View, View.OnClickListener {

    private ImageView head, iv_inform;
    private TextView phoneNum, oder, name, exit, tvRole, tvSwitch, tvMessageNum;
    private View vArrow;
    private MineItem address, invoice, setting, line, qualification, carCount, driver, addCarrier, relateCarrier;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View parent) {
        head = parent.findViewById(R.id.iv_head);
        name = parent.findViewById(R.id.name);
        phoneNum = parent.findViewById(R.id.tv_phone_num);
        oder = parent.findViewById(R.id.tv_order_num);
        exit = parent.findViewById(R.id.tv_exit);
        line = parent.findViewById(R.id.mineitem_line);
        carCount = parent.findViewById(R.id.mineitem_carnum);
        driver = parent.findViewById(R.id.mineitem_driver);
        setting = parent.findViewById(R.id.mineitem_setting);
        address = parent.findViewById(R.id.mineitem_address_info);
        invoice = parent.findViewById(R.id.mineitem_invoice_info);
        qualification = parent.findViewById(R.id.mineitem_qualification);
        addCarrier = parent.findViewById(R.id.mineitem_add_carrier_role);
        relateCarrier = parent.findViewById(R.id.mineitem_relate_carrier);
        tvRole = parent.findViewById(R.id.tv_role);
        tvSwitch = parent.findViewById(R.id.tv_switch);
        vArrow = parent.findViewById(R.id.tv_right_arrow);
        tvMessageNum = parent.findViewById(R.id.tv_message_num);
        iv_inform = parent.findViewById(R.id.iv_inform);
    }

    @Override
    protected void initEvent() {
        exit.setOnClickListener(this);
        address.setOnClickListener(this);
        invoice.setOnClickListener(this);
        setting.setOnClickListener(this);
        carCount.setOnClickListener(this);
        line.setOnClickListener(this);
        driver.setOnClickListener(this);
        invoice.setOnClickListener(this);
        setting.setOnClickListener(this);
        qualification.setOnClickListener(this);
        addCarrier.setOnClickListener(this);
        relateCarrier.setOnClickListener(this);
        tvSwitch.setOnClickListener(this);
        iv_inform.setOnClickListener(this);
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
