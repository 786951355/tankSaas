package com.tanker.basemodule.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.R;
import com.tanker.basemodule.model.ImageBean;
import com.tanker.basemodule.model.ImageTitleBean;
import com.tanker.basemodule.utils.AndroidLifecycleUtils;
import com.tanker.basemodule.utils.DensityUtils;

import java.io.File;
import java.util.List;

public class PhotoPickerAdapter extends ExpandableGridAdapter {
    private List<ImageTitleBean> images;
    private Context context;
    private float groupTextSize;
    private int groupTextColor;
    private int groupHeight;
    private int childBackground;
    private int column;
    private TypedArray typedArray;

    public final static int TYPE_TITLE = 0;
    public final static int TYPE_ADD = 1;
    public final static int TYPE_PHOTO = 2;

    private OnItemDeleteListener onItemDeleteListener;

    public PhotoPickerAdapter(Context context, List<ImageTitleBean> images, TypedArray typedArray, OnItemDeleteListener onItemDeleteListener) {
        this.context = context;
        this.images = images;
        this.typedArray = typedArray;
        this.onItemDeleteListener = onItemDeleteListener;
        init();
    }

    private void init() {
        groupTextSize = typedArray.getDimension(R.styleable.ExpandableGridView_group_text_size, context.getResources().getDimension(R.dimen.picker_grid_group_text_size));
        groupTextColor = typedArray.getColor(R.styleable.ExpandableGridView_group_text_color, ContextCompat.getColor(context, R.color.picker_grid_group_text_color));
        groupHeight = typedArray.getDimensionPixelSize(R.styleable.ExpandableGridView_group_height, context.getResources().getDimensionPixelSize(R.dimen.picker_grid_group_height));
        childBackground = typedArray.getResourceId(R.styleable.ExpandableGridView_child_background, R.drawable.picker_bg_add);
        column = typedArray.getResourceId(R.styleable.ExpandableGridView_column, 3);

        typedArray.recycle();
    }

    @Override
    public int getGridGroupCount() {
        return images.size();
    }

    @Override
    public int getGridChildCount(int gridGroupPosition) {
        return images.get(gridGroupPosition).getImages().size();
    }

    @Override
    public View getGridGroupView(int gridGroupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_title, parent, false);
        TextView tvTitle = convertView.findViewById(R.id.tv_title);
        tvTitle.setTextColor(groupTextColor);
        tvTitle.setTextSize(DensityUtils.px2dip(groupTextSize));
        tvTitle.setHeight(groupHeight);
        tvTitle.setGravity(Gravity.CENTER_VERTICAL);
        View vStar = convertView.findViewById(R.id.tv_star);
        tvTitle.setText(images.get(gridGroupPosition).getLabel());
        if (images.get(gridGroupPosition).isNotNull()) {
            vStar.setVisibility(View.VISIBLE);
        } else {
            vStar.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public View getGridChildView(final int gridGroupPosition, final int gridChildPosition, View convertView, ViewGroup parent) {
        ImageBean imageBean = images.get(gridGroupPosition).getImages().get(gridChildPosition);
//        if (convertView == null)
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        ImageView ivPhoto = convertView.findViewById(R.id.iv_photo);
        TextView tvName = convertView.findViewById(R.id.tv_name);
        View vDelete = convertView.findViewById(R.id.iv_delete);

        if (imageBean.getType() == TYPE_ADD) {
            ivPhoto.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            ivPhoto.setImageResource(R.drawable.icon_add);
            vDelete.setVisibility(View.GONE);
            vDelete.setOnClickListener(null);
        } else {
            Uri uri;
            if (!TextUtils.isEmpty(imageBean.getLocalPath())) {
                uri = Uri.fromFile(new File(imageBean.getLocalPath()));
            } else {
                uri = Uri.parse(AppConstants.IMAGE_SEVER + imageBean.getNetPath());
            }
            boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(ivPhoto.getContext());
            if (canLoadImage) {
                final RequestOptions options = new RequestOptions();
                options.centerCrop()
                        .placeholder(R.drawable.picker_ic_photo_black_48dp)
                        .error(R.drawable.picker_ic_broken_image_black_48dp);
                Glide.with(context)
                        .load(uri)
                        .apply(options)
                        .thumbnail(0.1f)
                        .into(ivPhoto);
            }
            vDelete.setVisibility(View.VISIBLE);
            vDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemDeleteListener.onItemDelete(gridGroupPosition, gridChildPosition);
                }
            });
        }
        tvName.setText(imageBean.getLabel());
        return convertView;
    }

    @Override
    public int getNumColumns(int gridGroupPosition) {
        return column;
    }

    public interface OnItemDeleteListener {
        void onItemDelete(int gridGroupPosition, int gridChildPosition);
    }
}