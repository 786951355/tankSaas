package com.tanker.basemodule.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tanker.basemodule.R;
import com.tanker.basemodule.utils.DensityUtils;


/**
 * 自定义一个组合View
 * Created by ronny on 2017/8/18.
 */

public class MineItem extends RelativeLayout {

    private String mRightText;
    private Drawable mItemIcon;
    private String mItemText;
    private Drawable mItemRightTextBackground;
    private ImageView leftImageView;
    private TextView textContent;
    private TextView mRightTextView;
    private boolean isPhoneNum;
    private int mMaxLength;
    private boolean mItemTextEditable;
    private float mItemLeftIconMargin;
    private Drawable mLeftDrawable;
    private int mItemRightTextColor;
    private int mItemRightTextSize;


    private OnRightBtnClickListener rightBtnClickListener;

    public MineItem(Context context) {
        this(context, null);
    }

    public MineItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MineItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MineItem);
        /** 自定义属性配置后，报错Caused by: org.xmlpull.v1.XmlPullParserException: Binary XML file line #2: invalid drawable tag vector*/
        //if (typedArray.hasValue(R.styleable.LoginItem_itemIcon)){
        //  mItemIcon = typedArray.getDrawable(R.styleable.LoginItem_itemIcon);
        //}
        mItemText = typedArray.getString(R.styleable.MineItem_itemText);
        mLeftDrawable = typedArray.getDrawable(R.styleable.MineItem_itemLeftSrc);
        mItemRightTextBackground = typedArray.getDrawable(R.styleable.MineItem_itemRightIcon);
        mRightText = typedArray.getString(R.styleable.MineItem_itemRightText);
        isPhoneNum = typedArray.getBoolean(R.styleable.MineItem_phoneNum, false);
        mMaxLength = typedArray.getInt(R.styleable.MineItem_maxLength, Integer.MAX_VALUE);
        mItemTextEditable = typedArray.getBoolean(R.styleable.MineItem_itemTextEditable, true);
        mItemLeftIconMargin = typedArray.getDimension(R.styleable.MineItem_itemIconMargin, 4);
        mItemRightTextColor = typedArray.getColor(R.styleable.MineItem_itemRightTextColor, context.getResources().getColor(android.R.color.black));
        mItemRightTextSize = typedArray.getDimensionPixelSize(R.styleable.MineItem_itemRightTextSize, context.getResources().getDimensionPixelSize(R.dimen.mineitem_right_text_default_size));

        typedArray.recycle();
        initChild(context);
        addChildrenView(context);
    }


    private void initChild(Context context) {
        leftImageView = new ImageView(context);
        textContent = new TextView(context);
        mRightTextView = new TextView(context);

        leftImageView.setImageDrawable(mLeftDrawable);
        leftImageView.setScaleType(ImageView.ScaleType.CENTER);

        textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textContent.setText(mItemText);
        textContent.setGravity(Gravity.LEFT);
        textContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
        textContent.setBackground(null);
        textContent.setFocusable(mItemTextEditable);

        mRightTextView.setBackground(mItemRightTextBackground);
        mRightTextView.setGravity(Gravity.CENTER);
        mRightTextView.setText(mRightText);
        mRightTextView.setTextSize(DensityUtils.px2dip(mItemRightTextSize));
        mRightTextView.setTextColor(mItemRightTextColor);
        if (mItemRightTextBackground != null) {
            mRightTextView.setTextColor(getResources().getColor(R.color.text_yellow));
            mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        }

        if (rightBtnClickListener != null) { //防止textview的空点击事件，覆盖item点击事件
            mRightTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightBtnClickListener.onRightClick(v);
                }
            });
        }
        //电话号码
        if (isPhoneNum) {
            textContent.setInputType(InputType.TYPE_CLASS_PHONE);
            textContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        }
    }

    private void addChildrenView(Context context) {
        LayoutParams itemLeftTextParams =
                new LayoutParams(LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        itemLeftTextParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        itemLeftTextParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        itemLeftTextParams.addRule(RelativeLayout.LEFT_OF, textContent.getId());
        //itemIconParams.setMargins(DensityUtils.dip2px(context, 4), 0, 0, 0);
        itemLeftTextParams.setMargins((int) mItemLeftIconMargin, 0, 0, 0);

        addView(leftImageView, itemLeftTextParams);

        LayoutParams textParams =
                new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textParams.setMargins((int) (DensityUtils.dip2px(20) + 2 * mItemLeftIconMargin), 0, DensityUtils.dip2px(64), 0);
        textParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        addView(textContent, textParams);


        int padding = -mRightTextView.getCompoundPaddingLeft();

        LayoutParams rightParams =
                new LayoutParams(DensityUtils.dip2px(54), DensityUtils.dip2px(16));
        mRightTextView.setPadding(padding, padding, padding, padding);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        rightParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        rightParams.setMargins(0, 0, (int) mItemLeftIconMargin, 0);
        addView(mRightTextView, rightParams);

        LayoutParams lineParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtils.dip2px(0.5f));
        lineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, TRUE);
    }

    //返回输入的字符串
    public String getText() {
        return textContent.getText().toString();
    }

    public void setText(String txt) {
        textContent.setText(txt);
    }

    public void setItemIcon(Drawable drawable) {
        mItemIcon = drawable;
        leftImageView.setBackgroundDrawable(mItemIcon);
    }

    public void setRightBtnClickable(boolean isClickable) {
        mRightTextView.setClickable(isClickable);
    }

    public void setOnRightBtnClickListener(OnRightBtnClickListener rightBtnClickListener) {
        this.rightBtnClickListener = rightBtnClickListener;
    }

    public void setRightBtnBackground(Drawable btn_line_shape_with_4dp_radius) {
        mRightTextView.setBackground(btn_line_shape_with_4dp_radius);
    }

    public void setRightBtnTextColor(int color) {
        mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        mRightTextView.setTextColor(color);
    }

    public void setRightBtnText(String s) {
        mRightTextView.setGravity(Gravity.CENTER);
        mRightTextView.setText(s);
    }

    public interface OnRightBtnClickListener {
        void onRightClick(View view);
    }
}
