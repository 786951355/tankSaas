package com.tanker.basemodule.model.mine_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CityBean implements CityInterface, Parcelable {
    private String province;
    private String name;
    private String id;
    private boolean isSelected;
    private boolean isEnabled = true;

    public CityBean() {
    }

    protected CityBean(Parcel in) {
        this.province = in.readString();
        this.name = in.readString();
        this.id = in.readString();
        this.isSelected = in.readByte() != 0;
        this.isEnabled = in.readByte() != 0;
    }

    public static final Creator<CityBean> CREATOR = new Creator<CityBean>() {
        @Override
        public CityBean createFromParcel(Parcel in) {
            return new CityBean(in);
        }

        @Override
        public CityBean[] newArray(int size) {
            return new CityBean[size];
        }
    };

    @Override
    public boolean isSelected() {
        return this.isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    @Override
    public void setEnabled(boolean enable) {
        this.isEnabled = enable;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getParentName() {
        return province;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public List<CityBean> getChildren() {
        return null;
    }

    @Override
    public void setChildren(List<CityBean> cities) {

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.province);
        parcel.writeString(this.name);
        parcel.writeString(this.id);
        parcel.writeByte((byte) (this.isSelected ? 1 : 0));
        parcel.writeByte((byte) (this.isEnabled ? 1 : 0));
    }
}
