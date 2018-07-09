package com.tanker.basemodule.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.R;
import com.tanker.basemodule.model.ImageBean;
import com.tanker.basemodule.utils.AndroidLifecycleUtils;
import com.tanker.basemodule.utils.ImageUtils;

import java.io.File;
import java.util.List;

public class PhotoPagerAdapter extends PagerAdapter {

    private List<ImageBean> paths;
    private String waterMark;
    private RequestManager mGlide;

    public PhotoPagerAdapter(RequestManager glide, List<ImageBean> paths, String waterMark) {
        this.paths = paths;
        this.mGlide = glide;
        this.waterMark = waterMark;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Context context = container.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.picker_picker_item_pager, container, false);

        final ImageView imageView = itemView.findViewById(R.id.iv_pager);

        final String path = AppConstants.IMAGE_SEVER+ paths.get(position).getNetPath();
        final String localPath = paths.get(position).getLocalPath();
        final Uri uri;
        if (!TextUtils.isEmpty(localPath)) {
            uri = Uri.fromFile(new File(localPath));
        } else if (!TextUtils.isEmpty(path)) {
            uri = Uri.parse(path);
        } else {
            return null;
        }
        boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(context);

        if (canLoadImage) {
            final RequestOptions options = new RequestOptions();
            options.dontAnimate()
                    .dontTransform()
                    .override(800, 800)
                    .placeholder(R.drawable.picker_ic_photo_black_48dp)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .error(R.drawable.picker_ic_broken_image_black_48dp);
            mGlide.setDefaultRequestOptions(options).load(uri)
                    .thumbnail(0.1f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(TextUtils.isEmpty(waterMark)){
                                return false;
                            }else {
                                Bitmap bitmap= ((BitmapDrawable) resource).getBitmap();
                                if(bitmap!=null){
                                    Bitmap bitmap_new= ImageUtils.drawTextsToBitmap(context,bitmap,waterMark,context.getResources().getDimension(R.dimen.watermark_big_size),context.getResources().getColor(R.color.text_watermark),-30,60);
                                    imageView.setImageBitmap(bitmap_new);
                                }
                                return true;
                            }
                        }
                    })
                    .into(imageView);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof Activity) {
                    if (!((Activity) context).isFinishing()) {
                        ((Activity) context).onBackPressed();
                    }
                }
            }
        });

        container.addView(itemView);

        return itemView;
    }


    @Override
    public int getCount() {
        return paths.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mGlide.clear((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public String getWaterMark() {
        return waterMark;
    }

    public void setWaterMark(String waterMark) {
        this.waterMark = waterMark;
    }
}
