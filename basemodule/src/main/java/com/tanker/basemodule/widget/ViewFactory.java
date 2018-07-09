package com.tanker.basemodule.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tanker.basemodule.R;

/**
 * ImageView创建工厂
 */
public class ViewFactory {
    /**
     * 获取ImageView视图的同时加载显示url
     */
    public static View getView(Context context, int lineResId, int iconResId, String text, boolean showButton, final ClickListener listener) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_banner, null);
        ImageView ivLine = view.findViewById(R.id.iv_line);
        ImageView ivIcon = view.findViewById(R.id.iv_icon);
        TextView tvBanner = view.findViewById(R.id.tv_text);
        TextView tvSkip = view.findViewById(R.id.tv_skip);
        Button btSkip = view.findViewById(R.id.bt_skip);
        ivLine.setImageResource(lineResId);
        ivIcon.setImageResource(iconResId);
        tvBanner.setText(text);
        if (showButton) {
            tvSkip.setVisibility(View.VISIBLE);
            tvSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.click();
                }
            });
        }
        btSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click();
            }
        });
        return view;
    }

    public interface ClickListener {
        void click();
    }
}
