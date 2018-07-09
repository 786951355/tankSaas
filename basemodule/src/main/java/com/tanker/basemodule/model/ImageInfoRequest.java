package com.tanker.basemodule.model;

class ImageInfoRequest {

    private String pictureTime ;
    private String billPath ;
    private String picturePlace;

    public String getPictureTime() {
        return pictureTime;
    }

    public void setPictureTime(String pictureTime) {
        this.pictureTime = pictureTime;
    }

    public String getBillPath() {
        return billPath;
    }

    public void setBillPath(String billPath) {
        this.billPath = billPath;
    }

    public String getPicturePlace() {
        return picturePlace;
    }

    public void setPicturePlace(String picturePlace) {
        this.picturePlace = picturePlace;
    }

    @Override
    public String toString() {
        return "{" +
                "pictureTime=" + pictureTime  +
                ", billPath=" + billPath +
                ", picturePlace=" + picturePlace  +
                '}';
    }
}
