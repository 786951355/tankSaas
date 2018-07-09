package com.tanker.basemodule.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.tanker.basemodule.BuildConfig;

import java.io.File;

public class ImageBean implements Parcelable {
    private int type;
    private String label;
    private String localPath;
    private String netPath;
    private String notNull;
    private String id;
    private PictureInfo pictureInfo;
    private String waterMarkPath;

    public ImageBean(int type, String label, String localPath, String netPath) {
        this.type = type;
        this.label = label;
        this.localPath = localPath;
        this.netPath = netPath;
    }

    public ImageBean(int type, String label, String localPath) {
        this.type = type;
        this.label = label;
        this.localPath = localPath;
    }


    public ImageBean(int type, String label) {
        this.type = type;
        this.label = label;
    }

    public ImageBean(int type) {
        this.type = type;
    }

    public ImageBean(String localPath) {
        this.localPath = localPath;
    }


    protected ImageBean(Parcel in) {
        type = in.readInt();
        label = in.readString();
        localPath = in.readString();
        netPath = in.readString();
        notNull = in.readString();
        id = in.readString();
        pictureInfo = in.readParcelable(PictureInfo.class.getClassLoader());
        waterMarkPath = in.readString();
    }

    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel in) {
            return new ImageBean(in);
        }

        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
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

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getNetPath() {
        return netPath;
    }

    public void setNetPath(String netPath) {
        this.netPath = netPath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNotNull() {
        return notNull;
    }

    public void setNotNull(String notNull) {
        this.notNull = notNull;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageBean)) return false;

        ImageBean imageBean = (ImageBean) o;

        if (localPath == null || imageBean.localPath == null) {//如果本地路径都为空，判断网络路径
            return (netPath != null || imageBean.netPath != null) && netPath != null && imageBean.netPath != null && netPath.equals(imageBean.netPath);
        } else {
            return localPath.equals(imageBean.localPath);
        }
    }

    @Override
    public int hashCode() {
        return localPath.hashCode();
    }

    public void setPictureInfo(PictureInfo pictureInfo) {
        this.pictureInfo = pictureInfo;
    }

    public PictureInfo getPictureInfo() {
        return pictureInfo;
    }


    public ImageInfoRequest getImageInfoRequest() {
        ImageInfoRequest imageInfoRequest = new ImageInfoRequest();
        imageInfoRequest.setBillPath(getNetPath());
        imageInfoRequest.setPicturePlace(pictureInfo.getPicAddress());
        imageInfoRequest.setPictureTime(pictureInfo.getPicDate() + " " + pictureInfo.getPicTime());
        return imageInfoRequest;
    }

    public void setWaterMarkPath(String waterMarkPath) {
        this.waterMarkPath = waterMarkPath;
    }

    public Uri getImageUri() {
        Uri uri = null;
        if (!TextUtils.isEmpty(waterMarkPath)) {
            uri = Uri.fromFile(new File(waterMarkPath));
        } else if (!TextUtils.isEmpty(localPath)) {
            uri = Uri.fromFile(new File(localPath));
        } else if (!TextUtils.isEmpty(netPath)) {
            uri = Uri.parse(BuildConfig.ImageUrl + netPath);
        }
        return uri;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(label);
        dest.writeString(localPath);
        dest.writeString(netPath);
        dest.writeString(notNull);
        dest.writeString(id);
        dest.writeParcelable(pictureInfo, flags);
        dest.writeString(waterMarkPath);
    }
}
