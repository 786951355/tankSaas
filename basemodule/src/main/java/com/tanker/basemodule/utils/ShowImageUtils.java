package com.tanker.basemodule.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.R;
import com.tanker.basemodule.common.Logger;

public class ShowImageUtils {
    /**
     * (1)
     * 显示图片Imageview
     *
     * @param context   上下文
     * @param errorImg  错误的资源图片
     * @param url       图片链接
     * @param imageView 组件
     */
    public static void showImageView(Context context, int errorImg, String url,
                                     ImageView imageView) {
        if (!StringUtils.isUrl(url)) {
            url = AppConstants.IMAGE_SEVER + url;
        }
        RequestOptions requestOption = new RequestOptions();
        requestOption.centerCrop();
        requestOption.error(errorImg);
        Glide.with(context).load(url)// 加载图片
                .apply(requestOption)// 设置错误图片
                .into(imageView);
    }

    public static void showImageView(Context context, String url,
                                     ImageView imageView) {
        if (!StringUtils.isUrl(url)) {
            url = AppConstants.IMAGE_SEVER + url;
        }
        Logger.d("显示图片地址：" + url);
        Glide.with(context).load(url)// 加载图片
                .apply(new RequestOptions()
                        .centerCrop()
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true)
                )
                .into(imageView);
    }

    /**
     * 加载失败展示
     *
     * @param context
     * @param url
     * @param imageView
     * @param loadImageListener
     */
    public static void showLocalImage(Context context, String url,
                                      ImageView imageView, RequestListener loadImageListener) {
        RequestOptions requestOption = new RequestOptions();
        requestOption.error(R.drawable.wutu);
        Glide.with(context).load(url)// 加载图片
                .apply(requestOption)// 设置错误图片
                .listener(loadImageListener)
                .into(imageView);
    }

    /**
     * 加载失败展示
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void showLocalImage(Context context, String url,
                                      ImageView imageView) {
        RequestOptions requestOption = new RequestOptions();
        requestOption.error(R.drawable.wutu).centerCrop();
        Glide.with(context).load(url)// 加载图片
                .apply(requestOption)// 设置错误图片
                .into(imageView);
    }


    public static void showImageView(Context context, int errorImg, String url,
                                     ImageView imageView, RequestListener loadImageListener) {
        url = AppConstants.IMAGE_SEVER + url;
        RequestOptions requestOption = new RequestOptions();
        requestOption.error(errorImg).centerCrop();
        Glide.with(context)
                .load(url)
                .listener(loadImageListener)
                .apply(requestOption)
                .into(imageView);
    }

    public static void showImageView(Context context, String url,
                                     ImageView imageView, RequestListener loadImageListener) {
        url = AppConstants.IMAGE_SEVER + url;
        RequestOptions requestOption = new RequestOptions();
        requestOption.centerCrop();
//        requestOption.error(R.drawable.wutu);
        Glide.with(context)
                .load(url)
                .apply(requestOption)
                .listener(loadImageListener)
                .into(imageView);
    }

    public static void showImageWithMemoryCache(Context context, String url,
                                     ImageView imageView) {
        if (!StringUtils.isUrl(url)) {
            url = AppConstants.IMAGE_SEVER + url;
        }
        Logger.d("显示图片地址：" + url);
        Glide.with(context).load(url)// 加载图片
                .apply(new RequestOptions()
                                .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(false)
                )
                .into(imageView);
    }


    public static void showImageViewWithoutCache(Context context, String url,
                                                 ImageView imageView, RequestListener loadImageListener) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .override(1080,1920);

        url = AppConstants.IMAGE_SEVER + url;

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .listener(loadImageListener)
                .into(imageView);
    }

}
