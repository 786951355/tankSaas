package com.tanker.basemodule.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewDebug;

/**
 * @author lwj
 * @ClassName: ScrollForeverTextView
 * @Description: 自定义控件，事件一个界面多个跑马灯
 * @date 2018/5/15 17:32
 */
public class ScrollForeverTextView extends AppCompatTextView {
    public ScrollForeverTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ScrollForeverTextView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public ScrollForeverTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    @ViewDebug.ExportedProperty(category = "focus")
    public boolean isFocused() {
        // TODO Auto-generated method stub
        return true;// 重点
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        // TODO Auto-generated method stub
        super.onFocusChanged(true, direction, previouslyFocusedRect);// 重点
    }
}