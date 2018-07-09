package com.tanker.basemodule.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tanker.basemodule.R;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.event.RxBus;
import com.tanker.basemodule.model.ImageModel;
import com.tanker.basemodule.utils.ShowImageUtils;
import com.tanker.basemodule.utils.TAimationUtils;
import com.tanker.basemodule.view.PhotoPagerActivity;
import com.tanker.basemodule.view.PhotoPickerActivity;

import io.reactivex.functions.Consumer;

public class CustomImageView extends LinearLayout {
    private final Context mContext;
    private TextView tV_lable;
    private ImageView imageView;
    private OnImageListener onImageListern;
    public ImageModel imageModel;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initChild();
        addChildrenView();
        imageModel = new ImageModel();
        RxBus.getInstanceBus().doSubscribe(ImageModel.class, new Consumer<ImageModel>() {
            @Override
            public void accept(ImageModel image) {
                imageModel.setLocalUrl(image.getLocalUrl());
                onImageListern.onProcess(imageModel.getLocalUrl(), CustomImageView.this);

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.d(throwable.toString());
            }
        });
    }

    private void addChildrenView() {
        imageView = new ImageView(mContext);
        tV_lable = new TextView(mContext);
        imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_add));
        imageView.setBackground(mContext.getResources().getDrawable(R.drawable.dash_line_shape));
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageModel.hasImage()) {//没有图片去添加图片
                    TAimationUtils.startLoadingAnimation((ImageView) v);
                    Intent intent = new Intent(mContext, PhotoPickerActivity.class);
                    mContext.startActivity(intent);
                } else {//有图片预览图片
                    Intent intent = new Intent(mContext, PhotoPagerActivity.class);
//                    intent.putParcelableArrayListExtra(PhotoPreview.EXTRA_PHOTOS, imageModel.getLocalUrl());
//                    intent.putExtra(PhotoPreview.EXTRA_IS_MOVE, false);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private void initChild() {
        LayoutParams imageViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
        addView(imageView, imageViewParams);
        LayoutParams textParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(tV_lable, textParams);
    }

    public void showImage(String url) {
        TAimationUtils.cancleAnimation(imageView);
        ShowImageUtils.showImageView(mContext, url, imageView);
    }

    public void setLable(String lable) {
        tV_lable.setText(lable);
    }

    public void setOnImageListener(OnImageListener onImageListener) {
        this.onImageListern = onImageListener;
    }

    interface OnImageListener {
        void onProcess(String localUrl, CustomImageView cImageView);
    }

}
