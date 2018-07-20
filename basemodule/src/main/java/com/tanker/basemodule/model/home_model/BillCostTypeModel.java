package com.tanker.basemodule.model.home_model;

/**
* @author lwj
* @ClassName: BillCostTypeModel
* @Description:  账本添加界面-费用类别实体类
* @date 2018/7/18 15:44
*/
public class BillCostTypeModel {
    private String key;
    private String value;
    private boolean selected =false;

    public BillCostTypeModel() {
    }

    public BillCostTypeModel(String key, String value, boolean selected) {
        this.key = key;
        this.value = value;
        this.selected = selected;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "BillCostTypeModel{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", selected=" + selected +
                '}';
    }
}
