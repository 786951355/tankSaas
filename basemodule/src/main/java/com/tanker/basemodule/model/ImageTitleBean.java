package com.tanker.basemodule.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.tanker.basemodule.adapter.PhotoPickerAdapter;
import com.tanker.basemodule.model.order_model.BillInfo;

import java.util.ArrayList;
import java.util.List;

public class ImageTitleBean implements Parcelable {
    private String id;
    private String label;
    private boolean notNull;
    private ArrayList<ImageBean> images;

    public ImageTitleBean(String label) {
        this.label = label;
    }

    public ImageTitleBean(String label, boolean notNull) {
        this.label = label;
        this.notNull = notNull;
    }

    protected ImageTitleBean(Parcel in) {
        id = in.readString();
        label = in.readString();
        notNull = in.readByte() != 0;
    }

    public static final Creator<ImageTitleBean> CREATOR = new Creator<ImageTitleBean>() {
        @Override
        public ImageTitleBean createFromParcel(Parcel in) {
            return new ImageTitleBean(in);
        }

        @Override
        public ImageTitleBean[] newArray(int size) {
            return new ImageTitleBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public ArrayList<ImageBean> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageBean> images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(label);
        parcel.writeByte((byte) (notNull ? 1 : 0));
    }

    public List<String> getNetPaths() {
        if (images == null) {
            return null;
        }
        List<String> paths = new ArrayList<>();
        for (ImageBean imageBean : images) {
            if (!TextUtils.isEmpty(imageBean.getNetPath())) {
                paths.add(imageBean.getNetPath());
            }
        }
        return paths;
    }

    public List<ImageInfoRequest> getImageInfo() {
        if (images == null) {
            return null;
        }
        List<ImageInfoRequest> imageInfoList = new ArrayList<>();
        for (ImageBean imageBean : images) {
            if (!TextUtils.isEmpty(imageBean.getNetPath())) {
                imageInfoList.add(imageBean.getImageInfoRequest());
            }
        }
        return imageInfoList;
    }

    public List<BillInfo> getUploadBeans(List<BillInfo> billInfos) {
        if (images == null) {
            return null;
        }
        List<BillInfo> paths = new ArrayList<>();
        for (ImageBean imageBean : images) {
            if (!TextUtils.isEmpty(imageBean.getNetPath())) {
                paths.add(new BillInfo(imageBean.getId(), imageBean.getNetPath()));
            }
        }
        if (billInfos != null) {
            for (BillInfo billInfo : billInfos) {
                if (!paths.contains(billInfo)) {
                    billInfo.setBillPath("");
                    paths.add(billInfo);
                }
            }
        }
        return paths;
    }

    public void removeInvalidImage(List<ImageBean> photos) {
        if (images == null) {
            return;
        }
        ArrayList<ImageBean> imageBeans = new ArrayList<>(images);
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).getType() == PhotoPickerAdapter.TYPE_ADD) {
                continue;
            }
            if (TextUtils.isEmpty(images.get(i).getNetPath())) {//移除列表删除本地路径
                imageBeans.remove(images.get(i));
            } else if (!photos.contains(images.get(i)) && !TextUtils.isEmpty(images.get(i).getLocalPath())) { //如果选中的图片不包含列表图片 删除列表图片
                imageBeans.remove(images.get(i));
            } else { //如果选中的图片包含列表图片 删除选中的图片
                photos.remove(images.get(i));
            }
        }
        images = imageBeans;
    }

}
