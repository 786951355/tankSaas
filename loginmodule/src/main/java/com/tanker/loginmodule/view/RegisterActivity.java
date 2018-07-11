package com.tanker.loginmodule.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.common.SaasApp;
import com.tanker.basemodule.security.AesEncodeUtil;
import com.tanker.basemodule.security.Md5Util;
import com.tanker.basemodule.utils.EmptyUtils;
import com.tanker.basemodule.utils.NetUtil;
import com.tanker.basemodule.utils.StringUtils;
import com.tanker.loginmodule.R;
import com.tanker.loginmodule.common.TimeUtils;
import com.tanker.loginmodule.common.VerifyStrFormatUtils;
import com.tanker.loginmodule.common.ViewUtils;
import com.tanker.loginmodule.constants.LoginConstants;
import com.tanker.loginmodule.contract.RegisterContract;
import com.tanker.loginmodule.presenter.RegisterPresenter;

import java.util.Locale;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements View.OnClickListener, RegisterContract.View, TimeUtils.ItimeTaskListener {
    protected EditText mEtRegisterPhoneNum, mEtRegisterVerifyCode, mEtRegisterPwd, mEtRegisterConfirmPwd;
    private TextView tv_agreement;
    protected ImageView mIvBack;
    protected ImageView mIvLoginAgreementCheck, mIvLoginAgreementUncheck;
    protected RelativeLayout rl_login_agreement_uncheck;
    protected TextView mTvRegister, mTvRegisterSendCode;
    private int mGetCodeTime = 60;


    @Override
    protected void initView() {
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setImageResource(R.drawable.icon_white_back);
        mIvLoginAgreementUncheck = findViewById(R.id.iv_login_agreement_uncheck);
        mIvLoginAgreementCheck = findViewById(R.id.iv_login_agreement_check);
        rl_login_agreement_uncheck = findViewById(R.id.rl_login_agreement_uncheck);
        mTvRegister = findViewById(R.id.tv_register);
        mEtRegisterPwd = findViewById(R.id.et_register_pwd);
        mEtRegisterPhoneNum = findViewById(R.id.et_register_phone_num);
        mEtRegisterVerifyCode = findViewById(R.id.et_login_verifi_code);
        mEtRegisterConfirmPwd = findViewById(R.id.et_register_comfire_pwd);
        mTvRegisterSendCode = findViewById(R.id.tv_login_send_code);
        tv_agreement = findViewById(R.id.tv_agreement);
        //设置标题
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        tvTitle.setText(getString(R.string.register_title));
    }


    @Override
    protected void initEvent() {
        mIvBack.setOnClickListener(this);
        rl_login_agreement_uncheck.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mTvRegisterSendCode.setOnClickListener(this);
        SpannableString spannableString = StringUtils.showProtocalLink(mContext, getString(R.string.tv_read_agreement));
        tv_agreement.setText(spannableString);
        tv_agreement.setMovementMethod(LinkMovementMethod.getInstance());
        hideSoftInputFromWindow();
    }


    /**
     * 点击空白处隐藏软键盘
     */
    private void hideSoftInputFromWindow() {
        rootView.setOnTouchListener((view1, motionEvent) -> {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                    manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
            return false;
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_back) {
            onBackPressed();
            //点击切换协议
        } else if (i == R.id.rl_login_agreement_uncheck) {
            llLoginAgreeMent();
            //注册
        } else if (i == R.id.tv_register) {
            register();
            //发送验证码
        } else if (i == R.id.tv_login_send_code) {
            registerSendCode();
            //跳转到登录
        }
    }

    protected boolean isRegisterAgreementCheckShowHide() {
        return mIvLoginAgreementCheck.getVisibility() == View.VISIBLE;
    }

    /**
     * 注册是否勾选协议
     *
     * @return
     */
    protected boolean registerAgreementCheck() {
        if (!isRegisterAgreementCheckShowHide()) {
            ViewUtils.showToast(mContext, getString(R.string.toast_please_read_check_useragreement));
            return false;
        }
        return true;
    }

    /**
     * 手机号码输入验证
     *
     * @return
     */
    protected boolean phoneNumRegisterInputVerity() {
        String etRegisterPhoneNum = mEtRegisterPhoneNum.getText().toString();
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
     * 密码、确认密码输入验证
     */
    protected boolean comfirePwdInputVerify() {
        String etPwd = mEtRegisterPwd.getText().toString();
        String etComfirePwd = mEtRegisterConfirmPwd.getText().toString();
        //密码为空
        if (EmptyUtils.isEmpty(etPwd)) {
            ViewUtils.showToast(mContext, getString(R.string.toast_input_pwd_empty_str));
            return false;
        }
        //判断密码格式
        if (!VerifyStrFormatUtils.pwdFormat(etPwd)) {
            ViewUtils.showToast(mContext, getString(R.string.input_error_pwd));
            return false;
        }
        if (!etPwd.equals(etComfirePwd)) {
            ViewUtils.showToast(mContext, getString(R.string.toast_comfire_pwd_str));
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
        if (!phoneNumRegisterInputVerity())
            return false;
        //判断验证码格式
        String etRegisterCode = mEtRegisterVerifyCode.getText().toString();
        if (EmptyUtils.isEmpty(etRegisterCode)) {
            ViewUtils.showToast(mContext, getString(R.string.code_empty));
            return false;
        } else if (etRegisterCode.length() < 6) {
            ViewUtils.showToast(mContext, getString(R.string.toast_input_code_str));
            return false;
        }
        return true;
    }


    protected void ivBack() {
        navigationTo(LoginActivity.class);
        finish();
    }

    /**
     * 勾选获取取消勾选协议
     */
    protected void llLoginAgreeMent() {
        boolean ivLoginAgreementCheckShowHide = mIvLoginAgreementCheck.getVisibility() == View.VISIBLE;
        mIvLoginAgreementCheck.setVisibility(ivLoginAgreementCheckShowHide ? View.GONE : View.VISIBLE);
        mIvLoginAgreementUncheck.setVisibility(ivLoginAgreementCheckShowHide ? View.VISIBLE : View.GONE);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mPresenter = new RegisterPresenter(this);
    }

    /**
     * 注册
     */
    protected void register() {
        if (verify())
            return;
        //注册密码
        String registerPwd = mEtRegisterPwd.getText().toString();
        //注册手机号码
        String registerPhoneNum = mEtRegisterPhoneNum.getText().toString();
        //注册验证码
        String verifyCode = mEtRegisterVerifyCode.getText().toString();
        String encode = Md5Util.encode(registerPwd);
        String pwd = AesEncodeUtil.encrypt(encode);
        if (SaasApp.getInstance().getConfigManager().getConfigInfo() == null) {
            mPresenter.requestConfig(LoginConstants.PLATFORM_VALUE, registerPhoneNum, pwd, verifyCode);
        } else {
            //注册（发送网络请求）
            mPresenter.register(LoginConstants.PLATFORM_VALUE, registerPhoneNum, pwd, verifyCode);
        }

    }

    /**
     * 验证公司名称、用户名、手机号码、密码、确认密码、是否勾选用户协议
     *
     * @return
     */
    private boolean verify() {
        return !phoneNumCodeInputCodeVerity() || !comfirePwdInputVerify() ||
                !registerAgreementCheck();
    }

    protected void registerSendCode() {
        if (!NetUtil.isNetworkAvailable(mContext)) {
            showMessage(getString(com.tanker.basemodule.R.string.error_net));
            return;
        }
        //发送验证码校验手机号码
        if (!phoneNumRegisterInputVerity())
            return;

        //发送验证码
        mPresenter.getCode(mEtRegisterPhoneNum.getText().toString(),
                LoginConstants.PLATFORM_VALUE,
                LoginConstants.REGISTER_SMSTEMPLATETYPE
        );
        mEtRegisterVerifyCode.setFocusable(true);
        mEtRegisterVerifyCode.setFocusableInTouchMode(true);
        mEtRegisterVerifyCode.requestFocus();
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
        mTvRegisterSendCode.setEnabled(false);
        new Handler().post(() -> {
            mTvRegisterSendCode.setText(String.format(Locale.CHINA, "已发送%ds", mGetCodeTime));
            if (mGetCodeTime <= 0) {
                //启用点击发送验证码功能
                mTvRegisterSendCode.setEnabled(true);
                //倒计时最大妙
                mGetCodeTime = 60;
                TimeUtils.timeCancel();
                mTvRegisterSendCode.setText(getString(R.string.tv_login_resend_code));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁定时器
        TimeUtils.timeCancel();
    }

    @Override
    public void startCountDown() {
        //60秒倒计时提示
        TimeUtils.timeTask(mContext, this);
    }


    @Override
    public int getContentView() {
        return R.layout.loginmodule_activity_register;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setToolbarVisible(false);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
