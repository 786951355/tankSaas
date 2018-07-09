package com.tanker.loginmodule.view;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.security.AesEncodeUtil;
import com.tanker.basemodule.security.Md5Util;
import com.tanker.basemodule.utils.EmptyUtils;
import com.tanker.loginmodule.R;
import com.tanker.loginmodule.common.TimeUtils;
import com.tanker.loginmodule.common.VerifyStrFormatUtils;
import com.tanker.loginmodule.common.ViewUtils;
import com.tanker.loginmodule.constants.LoginConstants;
import com.tanker.loginmodule.contract.RetrieveContract;
import com.tanker.loginmodule.presenter.RetrievePresenter;

import java.util.Locale;

/**
 * Created by Administrator on 2018/3/26.
 */

public class TankerRetrieveActivity extends BaseActivity<RetrievePresenter> implements
        TimeUtils.ItimeTaskListener, RetrieveContract.View, RetrievePresenter.IretrieveCallBack, View.OnClickListener {


    @Override
    public int getContentView() {
        return R.layout.fragment_tanker_retrieve;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setToolbarVisible(false);
    }

    @Override
    protected void initView() {
        mTvTitle = findViewById(R.id.i_title).findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setImageResource(R.drawable.icon_white_back);
        mEtRetrievePhoneNum = findViewById(R.id.et_retrieve_phone_num);
        mEtRetrieveVerifyCode = findViewById(R.id.et_login_verifi_code);
        mTvRetrieveComfire = findViewById(R.id.tv_retrieve_pwd_comfire);
        mTvRetrieveSendCode = findViewById(R.id.tv_login_send_code);
        mEtRetrievePwd = findViewById(R.id.et_retrieve_pwd);
        mEtRetrieveComfirePwd = findViewById(R.id.et_retrieve_comfire_pwd);
        mTvTitle.setText(getString(R.string.et_retrieve_pwd));
        mTvTitle.setTextColor(getResources().getColor(R.color.white));
        initEvent();
    }

    /**
     * 事件监听
     */
    protected void initEvent() {
        super.initEvent();
        mTvRetrieveSendCode.setOnClickListener(this);
        mTvRetrieveComfire.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        //确定
        if (id == R.id.tv_retrieve_pwd_comfire)
            tvRetrievePwdConfirm();
            //发送验证码
        else if (id == R.id.tv_login_send_code)
            tvRetrieveSendCode();
        else if (id == R.id.iv_back) {
            finish();
        }
    }

    /**
     * 手机号码找回密码输入验证
     *
     * @return
     */
    protected boolean phoneNumRetrieveInputVerity() {
        String etRegisterPhoneNum = mEtRetrievePhoneNum.getText().toString();
        //手机号码为空
        if (EmptyUtils.isEmpty(etRegisterPhoneNum)) {
            ViewUtils.showToast(mContext, getString(R.string.toast_input_phonenum_empty_str));
            return false;
        }
        //手机格式判断
        if (!VerifyStrFormatUtils.phoneNumFormat(etRegisterPhoneNum)) {
            ViewUtils.showToast(mContext, getString(R.string.input_correct_phonenum));
            return false;
        }
        return true;
    }

    /**
     * 手机号码、验证码输入验证
     *
     * @return
     */
    protected boolean phoneNumCodeInputCodeVerity() {
        if (!phoneNumRetrieveInputVerity())
            return false;
        //判断验证码格式
        String etRegisterCode = mEtRetrieveVerifyCode.getText().toString();
        if (EmptyUtils.isEmpty(etRegisterCode)) {
            ViewUtils.showToast(mContext, getString(R.string.code_empty));
            return false;
        }
        if (etRegisterCode.length() < 6) {
            ViewUtils.showToast(mContext, getString(R.string.toast_input_code_str));
            return false;
        }
        return true;
    }

    /**
     * 密码、确认密码输入验证
     */
    protected boolean comfirePwdInputVerify() {
        String etPwd = mEtRetrievePwd.getText().toString();
        String etComfirePwd = mEtRetrieveComfirePwd.getText().toString();
        //密码为空
        if (EmptyUtils.isEmpty(etPwd)) {
            ViewUtils.showToast(mContext, getString(R.string.toast_input_pwd_empty_str));
            return false;
        }
        //判断密码格式
        if (!VerifyStrFormatUtils.pwdFormat(etPwd)) {
            ViewUtils.showToast(mContext, getString(R.string.toast_input_pwd_str));
            return false;
        }
        if (!etPwd.equals(etComfirePwd)) {
            ViewUtils.showToast(mContext, getString(R.string.toast_comfire_pwd_str));
            return false;
        }
        return true;
    }

    protected int mGetCodeTime = 60;
    protected TextView mTvTitle, mTvRetrieveComfire, mTvRetrieveSendCode;
    protected ImageView mIvBack;
    protected EditText mEtRetrievePhoneNum, mEtRetrieveVerifyCode, mEtRetrievePwd, mEtRetrieveComfirePwd;


    /**
     * 网络请求（提交手机号、验证码、密码、确认密码）
     */

    protected void tvRetrievePwdConfirm() {
        //验证手机号码、验证码、密码、确认密码
        if (!phoneNumCodeInputCodeVerity() || !comfirePwdInputVerify())
            return;
        //手机账号注册网络请求
        String firstPwd = mEtRetrievePwd.getText().toString();
        String secondPwd = mEtRetrieveComfirePwd.getText().toString();
        mPresenter.retrievePwd(LoginConstants.PLATFORM_VALUE,
                mEtRetrievePhoneNum.getText().toString(),
                mEtRetrieveVerifyCode.getText().toString(),
                encodePwd(firstPwd), encodePwd(secondPwd));
    }


    public String encodePwd(String pwd) {
        pwd = Md5Util.encode(pwd);
        pwd = AesEncodeUtil.encrypt(pwd);
        return pwd;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new RetrievePresenter(this, this);
    }

    @Override
    public void retrieve() {
        ViewUtils.showToast(mContext, getString(R.string.toast_retrieve_pwd_success));
        finish();
    }

    /**
     * 找回密码发送验证码
     */
    protected void tvRetrieveSendCode() {

        //验证手机号码
        if (!phoneNumRetrieveInputVerity())
            return;

        //发送验证码
        mPresenter.getCode(mEtRetrievePhoneNum.getText().toString(),
                LoginConstants.PLATFORM_VALUE,
                LoginConstants.RETRIEVE_REGISTER_SMSTEMPLATETYPE
        );
        mEtRetrieveVerifyCode.setFocusable(true);
        mEtRetrieveVerifyCode.setFocusableInTouchMode(true);
        mEtRetrieveVerifyCode.requestFocus();
    }

    @Override
    public void timeTask() {
        try {
            sendCodeCountDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendCodeCountDown() {
        mGetCodeTime--;
        //点击发送验证码后，暂时屏蔽点击发送验证码
        mTvRetrieveSendCode.setEnabled(false);
        new Handler().post(() -> {
            mTvRetrieveSendCode.setText(String.format(Locale.CHINA, "已发送%ds", mGetCodeTime));
            if (mGetCodeTime <= 0) {
                //启用点击发送验证码功能
                mTvRetrieveSendCode.setEnabled(true);
                //倒计时最大妙
                mGetCodeTime = 60;
                TimeUtils.timeCancel();
                mTvRetrieveSendCode.setText(getString(R.string.tv_login_resend_code));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TimeUtils.timeCancel();
    }

    @Override
    public void startCountDown() {
        //60秒倒计时提示
        TimeUtils.timeTask(mContext, TankerRetrieveActivity.this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
