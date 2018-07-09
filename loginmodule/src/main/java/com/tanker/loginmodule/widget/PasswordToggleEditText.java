package com.tanker.loginmodule.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.tanker.loginmodule.R;


public class PasswordToggleEditText extends AppCompatEditText implements
        OnFocusChangeListener, TextWatcher {
    /**
     * 眼睛按钮
     */
    private Drawable mToggleDrawable;
    /**
     * 是否密文
     */
    private boolean isCiphertext = false;

    public PasswordToggleEditText(Context context) {
        this(context, null);
    }

    public PasswordToggleEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PasswordToggleEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化右边小眼睛的控件
     */
    private void init() {
        //获取EditText的DrawableRight,主要是通过xml或者外部设置右边的按钮，如果没有设置就采用默认的
        mToggleDrawable = ContextCompat.getDrawable(getContext(), R.drawable.icon_login_pwd_hide);
        mToggleDrawable.setBounds(0, 0, mToggleDrawable.getIntrinsicWidth(), mToggleDrawable.getIntrinsicHeight());
        setToggleIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

            } else if (event.getAction() == MotionEvent.ACTION_UP
                    && event.getX() > (getWidth() - getPaddingRight() - mToggleDrawable.getIntrinsicWidth())
                    && (event.getX() < ((getWidth() - getPaddingRight())))) { // bug1029: 禁止点击眼睛之外的edittext明文变密文
                if (isCiphertext) {
                    hideReturnsTransformationMethod();
                } else {
                    passwordTransformationMethod(event);
                }
                event.setAction(MotionEvent.ACTION_CANCEL);
                onTouchEvent(event);
                return true; //bug1030: 禁止点击眼睛图标弹出键盘
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 显示成密文
     */
    private void hideReturnsTransformationMethod() {
        //显示成密文
        isCiphertext = false;
        mToggleDrawable = ContextCompat.getDrawable(getContext(), R.drawable.icon_login_pwd_hide);
        mToggleDrawable.setBounds(0, 0, mToggleDrawable.getIntrinsicWidth(), mToggleDrawable.getIntrinsicHeight());
        //隐藏密码明文
        setTransformationMethod(PasswordTransformationMethod.getInstance());
        postInvalidate();
        setSelection(getText().length());
    }

    /**
     * 显示明文
     *
     * @param event
     */
    private void passwordTransformationMethod(MotionEvent event) {
        //显示成明文
        isCiphertext = true;
        boolean touchable = event.getX() > (getWidth()
                - getPaddingRight() - mToggleDrawable.getIntrinsicWidth())
                && (event.getX() < ((getWidth() - getPaddingRight())));
        if (touchable) {
            mToggleDrawable = ContextCompat.getDrawable(getContext(), R.drawable.icon_login_pwd_show);
            mToggleDrawable.setBounds(0, 0, mToggleDrawable.getIntrinsicWidth(), mToggleDrawable.getIntrinsicHeight());
            //显示密码明文
            setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            postInvalidate();
            CharSequence charSequence = getText();
            //为了保证体验效果，需要保持输入焦点在文本最后一位
            if (charSequence != null) {
                Spannable spanText = (Spannable) charSequence;
                Selection.setSelection(spanText, charSequence.length());
            }
        }
    }

    /**
     * 当EditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setToggleIconVisible(getText().length() > 0);
        } else {
            setToggleIconVisible(false);
            setShakeAnimation();
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    public void setToggleIconVisible(boolean visible) {
//        Drawable right = visible ? mToggleDrawable : null;
        if (mToggleDrawable != null)
            setCompoundDrawables(getCompoundDrawables()[0],
                    getCompoundDrawables()[1], mToggleDrawable, getCompoundDrawables()[3]);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        setToggleIconVisible(s.length() > 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        //this.setAnimation(shakeAnimation(3));
    }


    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }
}
