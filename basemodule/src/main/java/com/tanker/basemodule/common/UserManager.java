package com.tanker.basemodule.common;

import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.hawk.Hawk;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.model.login_model.UserInfo;
import com.tanker.basemodule.model.mine_model.UserInfoModel;

public class UserManager {

    private UserInfo user;

    public UserManager() {
        checkUser();
    }

    private void checkUser() {
        if (user == null) {
            user = Hawk.get(AppConstants.HAWK_USER);
        }
    }

    public UserInfo getUser() {
        checkUser();
        return user;
    }

    public void setUser(UserInfo user) {
        Hawk.delete(AppConstants.HAWK_USER);
        Log.d("sglei-user", "setUser user = " + (user == null ? null : user.toString()));
        Hawk.put(AppConstants.HAWK_USER, user);
        this.user = user;
    }

    public String getUserId() {
        return getUser().getUserId();
    }

    public String getUserName() {
        return user.getUserName();
    }

    public String getHeadPath() {
        String headImgUrl = getUser().getAvatorsrc();
        if (headImgUrl != null && !headImgUrl.contains("http://")) {
            headImgUrl = AppConstants.IMAGE_SEVER + headImgUrl;
        }
        return headImgUrl;
    }

    public String getPhone() {
        return getUser().getMobilePhone();
    }

    public String getUUID() {
        return user.getUserUUID();
    }

    public void updateNewPhone(String newPhone) {
        user.setMobilePhone(newPhone);
        Hawk.put(AppConstants.HAWK_USER, user);
        Hawk.put(AppConstants.HAWK_RECENT_ACCOUNT, newPhone);
    }

    public void updateLines(String lineCount) {
        if (!TextUtils.isEmpty(lineCount)) {
            user.setLinesCount(lineCount);
            Hawk.put(AppConstants.HAWK_USER, user);
        }
    }

    public void updateUserInfo(UserInfoModel userInfoModel) {
        if (userInfoModel.getUserName() != null
                && !userInfoModel.getUserName().equals(user.getUserName())) {
            user.setUserName(userInfoModel.getUserName());
        }

        if (userInfoModel.getMobilePhone() != null
                && !userInfoModel.getMobilePhone().equals(user.getMobilePhone())) {
            user.setMobilePhone(userInfoModel.getMobilePhone());
        }

        if (userInfoModel.getQualification() != null
                && !userInfoModel.getQualification().equals(user.getAuditStatus())) {
            user.setAuditStatus(userInfoModel.getQualification());
        }

        if (userInfoModel.getLineTotal() != null
                && !userInfoModel.getLineTotal().equals(user.getLinesCount())) {
            user.setLinesCount(userInfoModel.getLineTotal());
        }

        if (userInfoModel.getAvatorsrc() != null
                && !userInfoModel.getAvatorsrc().equals(user.getAvatorsrc())) {
            user.setAvatorsrc(userInfoModel.getAvatorsrc());
        }

        if (userInfoModel.getRole() != null && !userInfoModel.getRole().equals(user.getRole())) {
            user.setRole(userInfoModel.getRole());
        }

        if (userInfoModel.getIfDriverAgreeInvitation() != null
                && !userInfoModel.getIfDriverAgreeInvitation().equals(user.getIfDriverAgreeInvitation())) {
            user.setIfDriverAgreeInvitation(userInfoModel.getIfDriverAgreeInvitation());
        }

        if (userInfoModel.getIfEscortAgreeInvitation() != null
                && !userInfoModel.getIfEscortAgreeInvitation().equals(user.getIfEscortAgreeInvitation())) {
            user.setIfEscortAgreeInvitation(userInfoModel.getIfEscortAgreeInvitation());
        }

        if (userInfoModel.getIsCarrier() != null
                && !userInfoModel.getIsCarrier().equals(user.getIsCarrier())) {
            user.setIsCarrier(userInfoModel.getIsCarrier());
        }
        Hawk.put(AppConstants.HAWK_USER, user);

    }

    public void updateCompanyName(String carrierCompanyName) {
        user.setCompany(carrierCompanyName);
        Hawk.put(AppConstants.HAWK_USER, user);
    }

    public void updateUserName(String userName) {
        user.setUserName(userName);
        Hawk.put(AppConstants.HAWK_USER, user);
    }
}
