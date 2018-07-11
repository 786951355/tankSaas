package com.tanker.loginmodule.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.constants.DialogConst;
import com.tanker.basemodule.constants.RouterConstants;
import com.tanker.basemodule.dialog.TankerDialog;
import com.tanker.basemodule.dialog.TankerTwoButtonDialog;
import com.tanker.basemodule.router.ReflectUtils;
import com.tanker.loginmodule.R;
import com.umeng.analytics.MobclickAgent;

public class RegisterSuccessActivity extends BaseActivity implements View.OnClickListener {


    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setToolbarVisible(false);
        //友盟禁止默认的页面统计功能
        MobclickAgent.openActivityDurationTrack(false);
    }

    @Override
    protected void initView() {
        mBtnRegisterAuth = findViewById(R.id.btn_register_authen);
        mBtnRegisterWaitAuth = findViewById(R.id.btn_register_wait_authen);
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.GONE);
        mTvRight = findViewById(R.id.tv_right);
    }


    @Override
    protected void initEvent() {
        mBtnRegisterWaitAuth.setOnClickListener(this);
        mBtnRegisterAuth.setOnClickListener(this);
        mTvRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        //去资质认证
        if (viewId == R.id.btn_register_authen) {
            //友盟事件监听
            MobclickAgent.onEvent(mContext, "1119", "立即维护资质按键");
            ReflectUtils.startActivityWithName(this, RouterConstants.BASIC_CERTIFICATION_EDIT);
            //稍后资质认证
        } else if (viewId == R.id.btn_register_wait_authen) {
            //友盟事件监听
            MobclickAgent.onEvent(mContext, "1119", "稍后维护资质按键");
            showTwoButtonDialog(getResources().getString(R.string.if_to_qualification_later),
                    getResources().getString(R.string.to_edit_qualification),
                    getResources().getString(R.string.to_edit_qualification_later), new TankerTwoButtonDialog.OptionListener() {
                        @Override
                        public void onConfirm(TankerTwoButtonDialog obDialog) {
                            ReflectUtils.startActivityWithName(RegisterSuccessActivity.this, RouterConstants.BASIC_CERTIFICATION_EDIT);
                        }

                        @Override
                        public void onCancel(TankerTwoButtonDialog obDialog) {
                            ReflectUtils.navigationToHome(mContext, 0);
                            mContext.finish();
                        }
                    });
            //返回
        } else if (viewId == R.id.iv_back || viewId == R.id.tv_right) {
            showAlertDialog("是否退出当前账号", obDialog -> {
                navigationTo(LoginActivity.class);
                mContext.finish();
            });
        }
    }

    private void showTwoButtonDialog(String msg, String leftText,
                                     String rightText, TankerTwoButtonDialog.OptionListener optionListener) {
        TankerTwoButtonDialog dialog = new TankerTwoButtonDialog(this);
        Bundle bundle = new Bundle();
        bundle.putString(DialogConst.DIALOG_CONFIRM_TEXT, leftText);
        bundle.putString(DialogConst.DIALOG_CANCEL_TEXT, rightText);
        bundle.putInt(DialogConst.DIALOG_MESSAGE_TYPE, TankerDialog.IMAGE_DIALOG_WITH_TWO_CONTENT);
        bundle.putString(DialogConst.DIALOG_CONTENT_TEXT, msg);
        dialog.switchStyleAndShow(bundle, optionListener);
    }


    @Override
    protected void initData() {
        mTvTitle.setText(getString(R.string.register_title));
        mTvRight.setText(getString(R.string.tv_abort_str));
    }


    @Override
    public int getContentView() {
        return R.layout.loginmodule_activity_register_success;
    }

    private Button mBtnRegisterAuth, mBtnRegisterWaitAuth;
    private TextView mTvTitle, mTvRight;
    private ImageView mIvBack;

    @Override
    public void onResume() {
        super.onResume();
        //手动统计页面(页面名称可自定义)
        MobclickAgent.onPageStart("注册成功界面");
    }

    @Override
    public void onPause() {
        super.onPause();
        //手动统计页面(页面名称可自定义)
        MobclickAgent.onPageEnd("注册成功界面");
    }
}
