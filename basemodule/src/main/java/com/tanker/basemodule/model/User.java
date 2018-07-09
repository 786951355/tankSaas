package com.tanker.basemodule.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by ronny on 2018/3/23.
 */
@Entity
public class User {
    @Id
    @NotNull
    @Unique
    private String uId;
    private String phone;
    private String headImg;

    @Generated(hash = 1180651183)
    public User(@NotNull String uId, String phone, String headImg) {
        this.uId = uId;
        this.phone = phone;
        this.headImg = headImg;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getUId() {
        return this.uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }
}
