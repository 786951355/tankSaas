package com.tanker.loginmodule.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.router.ReflectUtils;
import com.tanker.basemodule.security.AesEncodeUtil;
import com.tanker.basemodule.security.Md5Util;
import com.tanker.basemodule.utils.EmptyUtils;
import com.tanker.basemodule.utils.NetUtil;
import com.tanker.basemodule.utils.ViewUtils;
import com.tanker.loginmodule.BuildConfig;
import com.tanker.loginmodule.R;
import com.tanker.loginmodule.common.VerifyStrFormatUtils;
import com.tanker.loginmodule.constants.LoginConstants;
import com.tanker.loginmodule.contract.LoginContract;
import com.tanker.loginmodule.presenter.LoginPresenter;
import com.tanker.resmodule.constants.RegexConstants;
import com.tencent.bugly.beta.Beta;
import com.umeng.analytics.MobclickAgent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener, LoginContract.View {

    protected EditText mEtLoginUserName, mEtLoginPwd, mEtLoginVerifyCode;
    protected TextView mTvSwitchLoginWay;
    protected TextView mTvLoginGetCodeOrRetrieve, mBtnLogin, mTvLoginPhonePwd;
    private boolean isPwdLogin = true;
    private InputFilter[] pwdInputFilters;


    @Override
    public int getContentView() {
        return R.layout.loginmodule_activity_login;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setTitle(getString(R.string.loginmodule_title_login)).setLeftIconVisible(false).setElevation(0);
        //友盟禁止默认的页面统计功能
        MobclickAgent.openActivityDurationTrack(false);
    }

    private Disposable time_disposable;

    @Override
    protected void initView() {
        // 通知权限没有打开，打开设置通知界面
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            showMessage(getString(R.string.open_notification_tip));
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }
        //检查更新
        Beta.checkUpgrade(false, false);
        //点击空白处隐藏软键盘
        mEtLoginUserName = findViewById(R.id.et_login_username);
        mEtLoginVerifyCode = findViewById(R.id.et_login_code_or_pwd);

        mTvLoginGetCodeOrRetrieve = findViewById(R.id.tv_login_get_code_or_retrieve);
        mBtnLogin = findViewById(R.id.btn_login);
        mTvSwitchLoginWay = findViewById(R.id.tv_switch_login_way);
        pwdInputFilters = new InputFilter[]{
                (charSequence, i, i1, spanned, i2, i3) -> {
                    boolean isChinese = Pattern.matches(RegexConstants.REGEX_CHINESE_ONLY, charSequence.toString());
                    if (isChinese) {
                        return "";
                    }
                    return null;
                }
        };
        mEtLoginVerifyCode.setFilters(pwdInputFilters);

        //测试时候的配置信息
        if (BuildConfig.DEBUG) {
            findViewById(R.id.iv_logo).setOnLongClickListener(v -> {
                navigationTo(DebugActivity.class);
                return true;
            });
        }

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new LoginPresenter(this);
        //回显最近一次的手机号
        if (BuildConfig.DEBUG) {
            String phone = Hawk.get(AppConstants.HAWK_RECENT_ACCOUNT);
            mEtLoginUserName.setText(phone != null ? phone : "");
            ViewUtils.moveToEndSelection(mEtLoginUserName);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        //手动统计页面(页面名称可自定义)
        MobclickAgent.onPageStart("登录界面");
    }

    @Override
    public void onPause() {
        super.onPause();
        //手动统计页面(页面名称可自定义)
        MobclickAgent.onPageEnd("登录界面");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (time_disposable != null) {
            time_disposable.dispose();
        }
    }

    /**
     * 设置事件监听
     */
    @Override
    protected void initEvent() {
        //登录发送验证码
        mTvLoginGetCodeOrRetrieve.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);//登录
        mTvSwitchLoginWay.setOnClickListener(this);//用手机号密码或者手机号验证码登录
        rootView.setOnTouchListener((view1, motionEvent) -> {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (manager != null && getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                    manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
            return false;
        });
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_login_get_code_or_retrieve) {
            if (isPwdLogin) {
                //跳转找回密码
                tvRetrievePwd();
            } else {
                tvLoginSendCode();
            }
        } else if (i == R.id.btn_login) {
//            登录
//            tvLogin();
            ReflectUtils.navigationToHome(this, 0);
        } else if (i == R.id.tv_switch_login_way) {
            //用手机号密码或者手机号验证码登录
//            switchLoginUI();
            isPwdLogin = !isPwdLogin;
            if (isPwdLogin&&!time_disposable.isDisposed()) {
                time_disposable.dispose();
            }
            mTvLoginGetCodeOrRetrieve.setText(getString(isPwdLogin ?
                    R.string.loginmodule_title_retrieve : R.string.loginmodule_send_code));
            mEtLoginVerifyCode.setHint(getString(isPwdLogin ?
                    R.string.loginmodule_hint_pwd : R.string.loginmodule_hint_msg_code));
            mTvSwitchLoginWay.setText(getString(isPwdLogin ?
                    R.string.loginmodule_switch_msg_code_login : R.string.loginmodule_switch_pwd_login));
            mEtLoginVerifyCode.setFilters(isPwdLogin ? pwdInputFilters : new InputFilter[]{});
        }
    }


    /**
     * 手机号码、密码登录，验证输入
     * 手机号码11位数字
     * 密码6-20字符，非中文
     *
     * @return
     */
    protected boolean phoneNumPwdLoginInputVerity(boolean phonePwdShowHide) {
        if (!phoneNumLoginInputVerity(phonePwdShowHide))
            return false;
        String etLoginPwd = mEtLoginPwd.getText().toString();
        //密码为空
        if (EmptyUtils.isEmpty(etLoginPwd)) {
            showMessage(getString(R.string.toast_input_pwd_empty_str));
            return false;
        }
        //判断密码格式
        if (!VerifyStrFormatUtils.pwdFormat(etLoginPwd)) {
            showMessage(getString(R.string.toast_input_pwd_str));
            return false;
        }
        return true;
    }

    /**
     * 手机号码输入验证
     *
     * @return
     */
    protected boolean phoneNumLoginInputVerity(boolean phonePwdShowHide) {
        String etLoginUserName = mEtLoginUserName.getText().toString();
        //手机号码为空
        if (EmptyUtils.isEmpty(etLoginUserName)) {
            showMessage(getString(R.string.toast_input_phonenum_empty_str));
            return false;
        }
        //手机格式判断
        if (!VerifyStrFormatUtils.phoneNumFormat(etLoginUserName)) {
            String inputCorrectPhoneNum = mContext.getString(R.string.input_correct_phonenum);
            showMessage(inputCorrectPhoneNum);
            return false;
        }
        return true;
    }

    /**
     * 手机号码、验证码输入验证
     *
     * @return
     */
    protected boolean phoneNumCodeInputCodeVerity(boolean phonePwdShowHide) {
        if (!phoneNumLoginInputVerity(phonePwdShowHide))
            return false;
        //判断验证码格式
        String etLoginCode = mEtLoginVerifyCode.getText().toString();
        if (EmptyUtils.isEmpty(etLoginCode)) {
            showMessage(getString(R.string.code_empty));
            return false;
        }
        if (etLoginCode.length() < 6) {
            showMessage(getString(R.string.toast_input_code_str));
            return false;
        }
        return true;
    }


    /**
     * 登录发送验证码
     */
    protected void tvLoginSendCode() {
        if (!NetUtil.isNetworkAvailable(mContext)) {
            showMessage(getString(com.tanker.basemodule.R.string.error_net));
            return;
        }
        if (!phoneNumLoginInputVerity(true)) return;

        //发送验证码
        mPresenter.getCode(mEtLoginUserName.getText().toString(),
                LoginConstants.PLATFORM_VALUE,
                LoginConstants.LOGIN_SMSTEMPLATETYPE);

        final int count_time = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(count_time + 1)
                .map(aLong -> count_time - aLong)
                .doOnSubscribe(disposable -> mTvLoginGetCodeOrRetrieve.setClickable(false))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        time_disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Logger.d("onNext" + aLong);
                        mTvLoginGetCodeOrRetrieve.setText("已发送" + aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        resetSmsUI();
                    }
                });
    }

    public void resetSmsUI() {
        if (!time_disposable.isDisposed()) {
            time_disposable.dispose();
        }
        mTvLoginGetCodeOrRetrieve.setClickable(true);
        mTvLoginGetCodeOrRetrieve.setText("获取验证码");
    }

    /**
     * 点击登录
     */
    protected void tvLogin() {
        boolean tvLoginPhonePwdShowHide = tvLoginPhonePwdShowHide();
        //输入手机号密码或者验证码是否正确、是否勾选协议
        if (!loginInputVerify())
            return;
        //登录用户名（手机号码）密码、验证码
        String userPhone = mEtLoginUserName.getText().toString();
        String userLoginVerifiCode = mEtLoginVerifyCode.getText().toString();
        String passWord = mEtLoginPwd.getText().toString();
        String passwordOrCode = tvLoginPhonePwdShowHide ? userLoginVerifiCode : passWord;
        if (!tvLoginPhonePwdShowHide) {
            passwordOrCode = Md5Util.encode(passwordOrCode);
            passwordOrCode = AesEncodeUtil.encrypt(passwordOrCode);
        }
        String loginType = tvLoginPhonePwdShowHide ? LoginConstants.TYPE_VERIFY_CODE_LOGIN : LoginConstants.TYPE_PHONE_NUM_PWD_LOGIN;

//        if (SaasApp.getInstance().getConfigManager().getConfigInfo() == null || BuildConfig.DEBUG) {
//            mPresenter.requestConfig(userPhone, passwordOrCode, loginType);
//        } else {
//            //网络请求登录
//            mPresenter.login(userPhone, passwordOrCode, loginType);
//        }
    }

    /**
     * 登录输入验证（手机号码、验证码、密码的格式验证）
     */
    private boolean loginInputVerify() {
        boolean phonePwdShowHide = tvLoginPhonePwdShowHide();
        //用手机号码、验证码登录
        if (!phonePwdShowHide) {
            return phoneNumPwdLoginInputVerity(phonePwdShowHide);
        }
        return phoneNumCodeInputCodeVerity(phonePwdShowHide);
    }


    private boolean tvLoginPhonePwdShowHide() {
        return mTvLoginPhonePwd.getVisibility() == View.VISIBLE;
    }

    /**
     * 切换密码登录和短信登录
     */
    protected void switchLoginUI() {

        //清空文本框内容
        mEtLoginVerifyCode.setText("");
        mEtLoginPwd.setText("");
        boolean mEtLoginPwdShowHide = mEtLoginPwd.getVisibility() == View.VISIBLE;
        mEtLoginPwd.setVisibility(mEtLoginPwdShowHide ? View.GONE : View.VISIBLE);
    }


    /**
     * 跳转找回密码
     */
    protected void tvRetrievePwd() {
        //友盟事件监听
        MobclickAgent.onEvent(mContext, "1118", "找回密码按键");
        navigationTo(RetrieveActivity.class);
    }

}
