package com.tanker.basemodule.model.mine_model;

import android.os.Parcelable;

import java.util.List;

public interface CityInterface extends Parcelable {
    boolean isSelected();

    void setSelected(boolean selected);

    void setEnabled(boolean enable);

    boolean isEnabled();

    String getName();

    void setName(String name);

    String getId();

    String getParentName();

    void setId(String id);

    List<CityBean> getChildren();

    void setChildren(List<CityBean> cities);
}
