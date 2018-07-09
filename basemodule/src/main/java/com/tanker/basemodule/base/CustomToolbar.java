package com.tanker.basemodule.base;

import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tanker.basemodule.utils.DensityUtils;

/**
 * Created by ronny on 2018/3/21.
 */

public class CustomToolbar {

    private final RelativeLayout toolbar;
    private final ImageView leftAction;
    private final AppCompatTextView rightText;
    private final TextView tvTitle;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private final View statusLine;
    private final TextView tvMessageNum;
    private ImageView rightImage;
    private View.OnClickListener onRightIconClickListener;

    private View.OnClickListener onLeftClickListener;
    private View.OnClickListener onRightClickListener;

    public void setOnLeftClickListener(View.OnClickListener onLeftClickListener) {
        this.onLeftClickListener = onLeftClickListener;
    }

    public void setOnRightTextClickListener(View.OnClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
    }

    public void setOnRightIconClickListener(View.OnClickListener onRightIconClickListener) {
        this.onRightIconClickListener = onRightIconClickListener;
    }

    private CustomToolbar(RelativeLayout toolbar, ImageView leftAction, AppCompatTextView rightText,
                          ImageView righticon, TextView tvTitle, View statusLine, TextView tvMessageNum) {
        this.toolbar = toolbar;
        this.leftAction = leftAction;
        this.rightText = rightText;
        this.rightImage = righticon;
        this.tvTitle = tvTitle;
        this.statusLine = statusLine;
        this.tvMessageNum = tvMessageNum;
    }

    public static CustomToolbar newInstance(RelativeLayout toolbar, ImageView leftAction,
                                            AppCompatTextView rightAction, ImageView rightIcon, TextView tvTitle, View statusLine, TextView tvMessageNum) {
        return new CustomToolbar(toolbar, leftAction, rightAction, rightIcon, tvTitle, statusLine, tvMessageNum);
    }

    public CustomToolbar setBackground(@ColorRes int bgColor) {
        toolbar.setBackgroundResource(bgColor);
        return this;
    }

    public CustomToolbar setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    public TextView getTitle() {
        return tvTitle;
    }

    public CustomToolbar setTitleColor(@ColorInt int titleColor) {
        tvTitle.setTextColor(titleColor);
        return this;
    }

    public CustomToolbar setLeftIcon(@DrawableRes int leftIcon) {
        leftAction.setImageResource(leftIcon);
//    leftAction.setImageDrawable(ImageUtils.svg4TextDrawable(leftIcon));
        return this;
    }

    public CustomToolbar setLeftIconVisible(boolean isVisible) {
        leftAction.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        return this;
    }

    public CustomToolbar setRightTextVisible(boolean isVisible) {
        rightText.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        return this;
    }

    public AppCompatTextView getRightText() {
        return rightText;
    }

    public CustomToolbar setRightIcon(@DrawableRes int rightIcon) {
        rightImage.setImageResource(rightIcon);
        return this;
    }

    public CustomToolbar setRightText(String string) {
        rightText.setText(string);
        return this;
    }

    public CustomToolbar setLeftText(String string) {
        rightText.setText(string);
        return this;
    }

    public CustomToolbar setMessageNum(int num) {
        if (num > 0) {
            tvMessageNum.setText(num + "");
            tvMessageNum.setVisibility(View.VISIBLE);
        } else {
            tvMessageNum.setVisibility(View.GONE);
        }

        return this;
    }

    public RelativeLayout getToolbar() {
        return toolbar;
    }

    public CustomToolbar setToolbarVisible(boolean visible) {
        toolbar.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public CustomToolbar hideStatusLine(boolean isHide) {
        statusLine.setVisibility(isHide ? View.GONE : View.VISIBLE);
        return this;
    }


    public CustomToolbar setElevation(float elevation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(DensityUtils.dip2px(elevation));
        }
        return this;
    }

    public View.OnClickListener getOnLeftClickListener() {
        return onLeftClickListener;
    }

    public View.OnClickListener getOnRightClickListener() {
        return onRightClickListener;
    }

    public View.OnClickListener getOnRightIconClickListener() {
        return onRightIconClickListener;
    }

    public CustomToolbar setRightTextColor(int rightTextColor) {
        rightText.setTextColor(rightTextColor);
        return this;
    }
}
