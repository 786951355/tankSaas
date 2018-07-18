package com.tanker.loginmodule.view;

import android.graphics.Color;
import android.os.SystemClock;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.security.AesEncodeUtil;
import com.tanker.basemodule.security.Md5Util;
import com.tanker.basemodule.utils.DensityUtils;
import com.tanker.basemodule.utils.EmptyUtils;
import com.tanker.basemodule.utils.NetUtil;
import com.tanker.basemodule.utils.StringUtils;
import com.tanker.loginmodule.R;
import com.tanker.loginmodule.common.VerifyStrFormatUtils;
import com.tanker.loginmodule.common.ViewUtils;
import com.tanker.loginmodule.constants.LoginConstants;
import com.tanker.loginmodule.contract.RetrieveContract;
import com.tanker.loginmodule.inputFilter.ChineseInputFilter;
import com.tanker.loginmodule.presenter.RetrievePresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/26.
 */

public class RetrieveActivity extends BaseActivity<RetrievePresenter> implements RetrieveContract.View {


    private Disposable time_disposable;
    protected Button mBtnConfirm;
    protected TextView mTvGetCode;
    protected EditText mEtRetrievePhoneNum, mEtRetrieveVerifyCode, mEtRetrievePwd, mEtRetrieveConfirmPwd;
    private View space1;
    private View space2;

    @Override
    public int getContentView() {
        return R.layout.loginmodule_activity_retrieve;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setTitle(getString(R.string.loginmodule_title_retrieve)).setElevation(0);
    }

    @Override
    protected void initView() {
        mEtRetrievePhoneNum = findViewById(R.id.et_retrieve_phone_num);
        mEtRetrieveVerifyCode = findViewById(R.id.et_login_code_or_pwd);
        mBtnConfirm = findViewById(R.id.btn_retrieve_confirm);
        mTvGetCode = findViewById(R.id.tv_get_code);
        mEtRetrievePwd = findViewById(R.id.et_retrieve_pwd);
        mEtRetrieveConfirmPwd = findViewById(R.id.et_retrieve_confirm_pwd);
        space1 = findViewById(R.id.space1);
        space2 = findViewById(R.id.space2);
    }

    /**
     * 事件监听
     */
    @Override
    protected void initEvent() {
        super.initEvent();
        mEtRetrievePwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)
                , new ChineseInputFilter()});
        mEtRetrieveConfirmPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)
                , new ChineseInputFilter()});
        mEtRetrieveVerifyCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)
                , new ChineseInputFilter()});
        //获取验证码的点击时间
        addDisposable(RxView.clicks(mTvGetCode)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(view -> getMsgCode()));
        //找回密码提交按钮
        addDisposable(RxView.clicks(mBtnConfirm)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(view -> RetrievePwd()));
        //账号检查
        Observable<Boolean> userNameObservable = RxTextView.textChanges(mEtRetrievePhoneNum)
                .map(CharSequence::toString)
                .map(StringUtils::checkMobileNum);
        //验证码检查
        Observable<Boolean> msgCodeObservable = RxTextView.textChanges(mEtRetrieveVerifyCode)
                .map(CharSequence::toString)
                .map(StringUtils::checkMsgCode);
        //首次密码检查
        Observable<Boolean> pwdObservable = RxTextView.textChanges(mEtRetrievePwd)
                .map(CharSequence::toString)
                .map(StringUtils::checkPwd);
        //密码确认
        Observable<Boolean> confirmPwdObservable = RxTextView.textChanges(mEtRetrieveConfirmPwd)
                .map(CharSequence::toString)
                .map(confirmPwd -> StringUtils.checkPwd(confirmPwd)
                        && confirmPwd != null
                        && confirmPwd.equals(mEtRetrievePwd.getText().toString()));

        addDisposable(userNameObservable
                .map(b -> b ? Color.BLACK : getmColor(R.color.text_red))
                .subscribe(mEtRetrievePhoneNum::setTextColor));
        addDisposable((msgCodeObservable)
                .map(b -> b ? Color.BLACK : getmColor(R.color.text_red))
                .subscribe(mEtRetrieveVerifyCode::setTextColor));
        addDisposable((pwdObservable)
                .map(b -> b ? Color.BLACK : getmColor(R.color.text_red))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(color -> mEtRetrievePwd.setTextColor(color))
                .map(color -> ((color.equals(Color.BLACK))
                        && (mEtRetrievePwd.getText().toString().equals(mEtRetrieveConfirmPwd.getText().toString())) ?
                        Color.BLACK : getmColor(R.color.text_red)))
                .subscribe(RetrieveColor -> {
                    mEtRetrieveConfirmPwd.setTextColor(RetrieveColor);
                    mBtnConfirm.setEnabled(RetrieveColor.equals(Color.BLACK));
                }));

        addDisposable((confirmPwdObservable)
                .map(b -> b ? Color.BLACK : getmColor(R.color.text_red))
                .subscribe(mEtRetrieveConfirmPwd::setTextColor));
        //当满足上面四个验证后登录按钮才可以点击
        addDisposable(Observable
                .combineLatest(userNameObservable, pwdObservable, msgCodeObservable, confirmPwdObservable
                        , (nameIsAvailable, msgCodeIsAvailable, pwdIsAvailable, confirmPwdIsAvailable)
                                -> nameIsAvailable && msgCodeIsAvailable && confirmPwdIsAvailable)
                .subscribe(mBtnConfirm::setEnabled));

        //监听键盘弹起，避免遮挡登录按钮
        rootView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
            space1.setVisibility(isSoftShowing() ? View.GONE : View.VISIBLE);
            space2.setVisibility(isSoftShowing() ? View.GONE : View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = findViewById(R.id.ll_edit_area).getLayoutParams();
            layoutParams.height = DensityUtils.dip2px(isSoftShowing() ? 212 : 240);
            findViewById(R.id.ll_edit_area).setLayoutParams(layoutParams);
            new Thread(() -> {
                SystemClock.sleep(100);
                runOnUiThread(() -> {
                    findViewById(R.id.ll_edit_area).requestLayout();
                    rootView.requestLayout();
                });
            }).start();

        });
    }

    /**
     * 手机号码找回密码输入验证
     *
     * @return
     */
    private boolean phoneNumRetrieveInputVerity() {
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
    private boolean phoneNumCodeInputCodeVerity() {
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
    private boolean confirmPwdInputVerify() {
        String etPwd = mEtRetrievePwd.getText().toString();
        String etConfirmPwd = mEtRetrieveConfirmPwd.getText().toString();
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
        if (!etPwd.equals(etConfirmPwd)) {
            ViewUtils.showToast(mContext, getString(R.string.toast_comfire_pwd_str));
            return false;
        }
        return true;
    }


    /**
     * 网络请求（提交手机号、验证码、密码、确认密码）
     */

    private void RetrievePwd() {
        //验证手机号码、验证码、密码、确认密码
        if (!phoneNumCodeInputCodeVerity() || !confirmPwdInputVerify())
            return;
        //手机账号注册网络请求
        String firstPwd = mEtRetrievePwd.getText().toString();
        String secondPwd = mEtRetrieveConfirmPwd.getText().toString();
        mPresenter.retrievePwd(LoginConstants.PLATFORM_VALUE,
                mEtRetrievePhoneNum.getText().toString(),
                mEtRetrieveVerifyCode.getText().toString(),
                encodePwd(firstPwd), encodePwd(secondPwd));
    }


    private String encodePwd(String pwd) {
        pwd = Md5Util.encode(pwd);
        pwd = AesEncodeUtil.encrypt(pwd);
        return pwd;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new RetrievePresenter(this);
    }


    /**
     * 登录发送验证码
     */
    private void getMsgCode() {
        if (!NetUtil.isNetworkAvailable(mContext)) {
            showMessage(getString(com.tanker.basemodule.R.string.error_net));
            return;
        }
        if (!phoneNumRetrieveInputVerity()) return;

//        //发送验证码
//        mPresenter.getCode(mEtRetrievePhoneNum.getText().toString(),
//                LoginConstants.PLATFORM_VALUE,
//                LoginConstants.RETRIEVE_REGISTER_SMSTEMPLATETYPE
//        );

        //开始倒计时
        final int count_time = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(count_time + 1)
                .map(aLong -> count_time - aLong)
                .doOnSubscribe(disposable -> mTvGetCode.setClickable(false))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                        time_disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Logger.d("onNext" + aLong);
                        mTvGetCode.setText("已发送" + aLong + "s");
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

    private void resetSmsUI() {
        if (!time_disposable.isDisposed()) {
            time_disposable.dispose();
        }
        mTvGetCode.setClickable(true);
        mTvGetCode.setText("获取验证码");
    }
}
