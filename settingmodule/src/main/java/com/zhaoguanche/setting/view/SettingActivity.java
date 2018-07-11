package com.zhaoguanche.setting.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.common.SaasApp;
import com.tanker.basemodule.dialog.TankerDialog;
import com.tanker.basemodule.router.ReflectUtils;
import com.tanker.basemodule.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhaoguanche.setting.R;
import com.zhaoguanche.setting.contract.SettingContract;
import com.zhaoguanche.setting.presenter.SettingPresenter;

/**
 * Created by ronny on 2018/3/23.
 */

public class SettingActivity extends BaseActivity<SettingPresenter> implements View.OnClickListener, SettingContract.View {

    private TextView mTvVersionNum;
    private TextView mTvPhoneNum;
    private ImageView mIvSwitch;
    private TextView mTvSoundTip;
    private Boolean toggle;
    private RelativeLayout mRlBindPhone;
    private LinearLayout ll_content;
    private TextView mBtnExit;


    @Override
    public int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setTitle("设置");
    }

    @Override
    protected void initView() {
        //友盟禁止默认的页面统计功能
        MobclickAgent.openActivityDurationTrack(false);
        mTvVersionNum = findViewById(R.id.tv_version_num);
        mTvPhoneNum = findViewById(R.id.tv_phone_num);
        mBtnExit = findViewById(R.id.btn_exit);
        mBtnExit.setOnClickListener(this);
        findViewById(R.id.rl_check_version).setOnClickListener(this);

//        ll_content.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        toggle = Hawk.<Boolean>get(AppConstants.SOUND_TOGGLE, Boolean.TRUE);
        mTvVersionNum.setText(SaasApp.getInstance().getVersion());
//        String phone = SaasApp.getInstance().getUserManager().getPhone();
//        phone = StringUtils.getHidePhoneNum(phone);
//        mTvPhoneNum.setText(phone);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i==R.id.btn_exit){
            TankerDialog.OptionListener optionListener = obDialog -> {
                ReflectUtils.navigationToLogin(mContext);
                SaasApp.getInstance().exit();
                mContext.finish();
            };
            showAlertDialog("确认退出当前账号吗？", R.drawable.warning, optionListener);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //手动统计页面(页面名称可自定义)
        MobclickAgent.onPageStart("设置界面");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //手动统计页面(页面名称可自定义)
        MobclickAgent.onPageEnd("设置界面");
    }
}
