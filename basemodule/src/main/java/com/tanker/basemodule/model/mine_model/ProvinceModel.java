package com.tanker.basemodule.model.mine_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProvinceModel implements CityInterface, Parcelable {

    private String name;
    private String id;
    private List<CityBean> cities;
    private boolean isSelected;
    private boolean isEnabled = true;


    public ProvinceModel() {
    }

    protected ProvinceModel(Parcel in) {
        name = in.readString();
        id = in.readString();
        cities = in.createTypedArrayList(CityBean.CREATOR);
        isSelected = in.readByte() != 0;
        isEnabled = in.readByte() != 0;
    }

    public static final Creator<ProvinceModel> CREATOR = new Creator<ProvinceModel>() {
        @Override
        public ProvinceModel createFromParcel(Parcel in) {
            return new ProvinceModel(in);
        }

        @Override
        public ProvinceModel[] newArray(int size) {
            return new ProvinceModel[size];
        }
    };

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
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
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getParentName() {
        return "中国";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public List<CityBean> getChildren() {
        return cities;
    }

    @Override
    public void setChildren(List<CityBean> cities) {
        this.cities = cities;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeTypedList(cities);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
        parcel.writeByte((byte) (isEnabled ? 1 : 0));
    }


}
