package com.tanker.basemodule.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.R;
import com.tanker.basemodule.model.ImageBean;
import com.tanker.basemodule.utils.AndroidLifecycleUtils;

import java.io.File;
import java.util.ArrayList;

public class PhotoWithDeleteAdapter extends RecyclerView.Adapter<PhotoWithDeleteAdapter.PhotoViewHolder> {

    private ArrayList<ImageBean> images;
    private LayoutInflater inflater;

    private Context mContext;

    public final static int TYPE_ADD = 1;
    public final static int TYPE_PHOTO = 2;

    public final static int MAX = 20;

    private OnDeleteItemListener listener;

    public PhotoWithDeleteAdapter(Context mContext, ArrayList<ImageBean> images, OnDeleteItemListener listener) {
        this.images = images;
        this.mContext = mContext;
        this.listener = listener;
        inflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =  inflater.inflate(R.layout.item_image, parent, false);
        return new PhotoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final PhotoViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_PHOTO) {
            ImageBean imageBean = images.get(position);
            Uri uri;
            if (!TextUtils.isEmpty(imageBean.getLocalPath())) {
                uri = Uri.fromFile(new File(imageBean.getLocalPath()));
            } else {
                uri = Uri.parse(AppConstants.IMAGE_SEVER + imageBean.getNetPath());
            }
            boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(holder.ivPhoto.getContext());
            if (canLoadImage) {
                final RequestOptions options = new RequestOptions();
                options.centerCrop()
                        .placeholder(R.drawable.picker_ic_photo_black_48dp)
                        .error(R.drawable.picker_ic_broken_image_black_48dp);
                Glide.with(mContext)
                        .load(uri)
                        .apply(options)
                        .thumbnail(0.1f)
                        .into(holder.ivPhoto);
            }
            holder.vDelete.setVisibility(View.VISIBLE);
            holder.vDelete.setOnClickListener(view -> listener.delete(position));
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public int getItemViewType(int position) {
        return images.get(position).getType();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private View vDelete;

        PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            vDelete = itemView.findViewById(R.id.iv_delete);
        }
    }

    public interface OnDeleteItemListener {
        void delete(int position);
    }
}
