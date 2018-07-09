package com.tanker.basemodule.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PictureInfo implements Parcelable {
    private String picAddress;
    private String picTime;
    private String picDate;
    private String week;

    public PictureInfo() {
    }

    protected PictureInfo(Parcel in) {
        picAddress = in.readString();
        picTime = in.readString();
        picDate = in.readString();
        week = in.readString();
    }

    public static final Creator<PictureInfo> CREATOR = new Creator<PictureInfo>() {
        @Override
        public PictureInfo createFromParcel(Parcel in) {
            return new PictureInfo(in);
        }

        @Override
        public PictureInfo[] newArray(int size) {
            return new PictureInfo[size];
        }
    };

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }

    public String getPicAddress() {
        return picAddress;
    }

    public String getPicTime() {
        return picTime;
    }

    public void setPicTime(String picTime) {
        this.picTime = picTime;
    }

    public String getPicDate() {
        return picDate;
    }

    public void setPicDate(String picDate) {
        this.picDate = picDate;
    }

    public void setPicWeek(String week) {
        this.week = week;
    }

    public String getPicWeek() {
        return week;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(picAddress);
        dest.writeString(picTime);
        dest.writeString(picDate);
        dest.writeString(week);
    }
}
