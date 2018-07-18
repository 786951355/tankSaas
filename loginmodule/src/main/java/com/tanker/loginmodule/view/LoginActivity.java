package com.tanker.loginmodule.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
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
import com.tanker.basemodule.utils.StringUtils;
import com.tanker.basemodule.utils.ViewUtils;
import com.tanker.loginmodule.BuildConfig;
import com.tanker.loginmodule.R;
import com.tanker.loginmodule.common.VerifyStrFormatUtils;
import com.tanker.loginmodule.constants.LoginConstants;
import com.tanker.loginmodule.contract.LoginContract;
import com.tanker.loginmodule.presenter.LoginPresenter;
import com.tanker.loginmodule.widget.PasswordToggleEditText;
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

/**
 * @author ronnyluo
 * @date 2018/7/17 下午5:57
 * @desc 登录页面
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener, LoginContract.View {

    protected EditText mEtLoginUserName, mEtLoginPwd, mEtLoginVerifyCode;
    protected TextView mTvSwitchLoginWay;
    protected TextView mTvLoginGetCodeOrRetrieve, mBtnLogin, mTvLoginPhonePwd;
    private boolean isPwdLogin;
    private InputFilter[] pwdInputFilters;
    private String TAG = "LoginActivity";
    private int screenHeight;
    private int keyHeight;
    private ImageView mIvLogo;
    private View space1;
    private View space2;
    private View space3;
    private View mRlLogin;


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
        checkNotificationPermission();
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
        //检查更新
        Beta.checkUpgrade(false, false);
        //点击空白处隐藏软键盘
        mEtLoginUserName = findViewById(R.id.et_login_username);
        mRlLogin = findViewById(R.id.rl_login);
        mEtLoginVerifyCode = findViewById(R.id.et_login_code_or_pwd);
        mTvLoginGetCodeOrRetrieve = findViewById(R.id.tv_login_get_code_or_retrieve);
        mBtnLogin = findViewById(R.id.btn_login);
        mTvSwitchLoginWay = findViewById(R.id.tv_switch_login_way);
        mIvLogo = findViewById(R.id.iv_logo);
        space1 = findViewById(R.id.space1);
        space2 = findViewById(R.id.space2);
        space3 = findViewById(R.id.space3);
        switchLoginUI();
        pwdInputFilters = new InputFilter[]{
                (charSequence, i, i1, spanned, i2, i3) -> {
                    boolean isChinese = Pattern.compile(RegexConstants.REGEX_CHINESE_ONLY)
                            .matcher(charSequence.toString()).find();
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

    /**
     * 设置事件监听
     */
    @SuppressLint("ClickableViewAccessibility")
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
        //监听键盘弹起，避免遮挡登录按钮
        rootView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
            space1.setVisibility(isSoftShowing() ? View.GONE : View.VISIBLE);
            space2.setVisibility(isSoftShowing() ? View.GONE : View.VISIBLE);
            space3.setVisibility(isSoftShowing() ? View.GONE : View.VISIBLE);
            new Thread(() -> {
                SystemClock.sleep(200);
                runOnUiThread(() -> rootView.requestLayout());
            }).start();

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
                navigationTo(RetrieveActivity.class);
            } else {
                tvLoginSendCode();
            }
        } else if (i == R.id.btn_login) {
//            登录
//            tvLogin();
            ReflectUtils.navigationToHome(this, 0);
        } else if (i == R.id.tv_switch_login_way) {
            //用手机号密码或者手机号验证码登录
            switchLoginUI();
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
//        mPresenter.getCode(mEtLoginUserName.getText().toString(),
//                LoginConstants.PLATFORM_VALUE,
//                LoginConstants.LOGIN_SMSTEMPLATETYPE);

        //开始倒计时
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
                        addDisposable(d);
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
     * 切换密码登录和短信登录的登录方式
     */
    protected void switchLoginUI() {
        isPwdLogin = !isPwdLogin;
        mEtLoginVerifyCode.setText("");
        if (isPwdLogin && time_disposable != null && !time_disposable.isDisposed()) {
            time_disposable.dispose();
        }
        mTvLoginGetCodeOrRetrieve.setClickable(true);
        mEtLoginVerifyCode.setInputType(isPwdLogin ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_NUMBER);
        mEtLoginVerifyCode.setTransformationMethod(isPwdLogin ? PasswordTransformationMethod.getInstance() : null); //设置为密码输入框
        mEtLoginVerifyCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(isPwdLogin ? 20 : 6)});

        ((PasswordToggleEditText) mEtLoginVerifyCode).setToggleIconShow(isPwdLogin);

        mTvLoginGetCodeOrRetrieve.setText(getString(isPwdLogin ?
                R.string.loginmodule_title_retrieve : R.string.loginmodule_send_code));
        mEtLoginVerifyCode.setHint(getString(isPwdLogin ?
                R.string.loginmodule_hint_pwd : R.string.loginmodule_hint_msg_code));
        mTvSwitchLoginWay.setText(getString(isPwdLogin ?
                R.string.loginmodule_switch_msg_code_login : R.string.loginmodule_switch_pwd_login));

        Observable<Boolean> userNameObservable = RxTextView.textChanges(mEtLoginUserName)
                .map(CharSequence::toString)
                .map(StringUtils::checkMobileNum);

        Observable<Boolean> msgCodeObservable = RxTextView.textChanges(mEtLoginVerifyCode)
                .map(CharSequence::toString)
                .map(StringUtils::checkMsgCode);

        Observable<Boolean> pwdObservable = RxTextView.textChanges(mEtLoginVerifyCode)
                .map(CharSequence::toString)
                .map(StringUtils::checkPwd);

        addDisposable(userNameObservable
                .map(b -> b ? Color.BLACK : getmColor(R.color.text_red))
                .subscribe(mEtLoginUserName::setTextColor));
        addDisposable((isPwdLogin ? pwdObservable : msgCodeObservable)
                .map(b -> b ? Color.BLACK : getmColor(R.color.text_red))
                .subscribe(mEtLoginVerifyCode::setTextColor));
        addDisposable(Observable
                .combineLatest(userNameObservable, isPwdLogin ? pwdObservable : msgCodeObservable
                        , (nameIsAvailable, msgCodeIsAvailable) -> nameIsAvailable && msgCodeIsAvailable)
                .subscribe(mBtnLogin::setEnabled));

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

    private void checkNotificationPermission() {
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            showMessage(getString(R.string.open_notification_tip));
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }
    }


}
