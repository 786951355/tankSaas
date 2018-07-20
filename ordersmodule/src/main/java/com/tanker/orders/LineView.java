package com.tanker.orders;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tanker.basemodule.utils.DensityUtils;

public class LineView extends RelativeLayout {

    private final Context mContext;
    private String text;
    private String tips;
    private boolean showLine;
    private TextView mainText;
    private TextView tipText;
    private View dashLine;

    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, com.tanker.basemodule.R.styleable.LineView);
        text = typedArray.getString(R.styleable.LineView_text);
        tips = typedArray.getString(R.styleable.LineView_tipText);
        showLine = typedArray.getBoolean(R.styleable.LineView_showLeftLine, false);
        typedArray.recycle();
        initChild();
        addChild();
    }

    private void initChild() {
        mainText = new TextView(mContext);
        tipText = new TextView(mContext);
        dashLine = new View(mContext);
        dashLine.setId(R.id.id_dash_line);

        mainText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        tipText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        tipText.setTextColor(mContext.getResources().getColor(R.color.white));
        tipText.setBackground(mContext.getResources().getDrawable(R.drawable.btn_radius_bg));

        dashLine.setVisibility(showLine ? VISIBLE : GONE);
        dashLine.setBackground(mContext.getResources().getDrawable(R.drawable.common_line_draw_dash_vertical));
        tipText.setText(tips);
        tipText.setGravity(Gravity.CENTER);
        mainText.setText(text);
    }

    private void addChild() {

        LayoutParams dashLineParams =
                new LayoutParams(DensityUtils.dip2px(2), DensityUtils.dip2px(30));
        dashLineParams.leftMargin=DensityUtils.dip2px(25);

        LayoutParams tipTextParams =
                new LayoutParams(DensityUtils.dip2px(18), DensityUtils.dip2px(18));
        tipTextParams.addRule(RelativeLayout.BELOW, dashLine.getId());
        tipTextParams.leftMargin=DensityUtils.dip2px(16);


        LayoutParams mainTextParams =
                new LayoutParams(LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mainTextParams.addRule(RelativeLayout.BELOW, dashLine.getId());
        mainTextParams.addRule(RelativeLayout.LEFT_OF, tipText.getId());
        mainTextParams.leftMargin = DensityUtils.dip2px(44);




        addView(dashLine, dashLineParams);
        addView(tipText, tipTextParams);
        addView(mainText, mainTextParams);
    }

    public void setTipsBackground(Drawable tipsBackground) {
        tipText.setBackground(tipsBackground);
    }

    public void showDashLine(boolean showLine) {
        dashLine.setVisibility(showLine ? VISIBLE : GONE);
    }

    public void setTips(String tips){
        tipText.setText(tips);
    }

    public void setText(String text){
        mainText.setText(text);
    }


}
