package com.tanker.basemodule.model.mine_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.R;

/**
 * Created by ronny on 2018/3/29.
 */

public class DriverModel implements Parcelable {
    /**
     * driverId : 25
     * driverName : 大法师
     * auditStatus : 1
     * role : 2
     * enabled : 1
     * driverPhone : 213123123
     */

    private String driverId;
    private String driverName;

    //    审核状态, 0待审核;1审核通过;2审核驳回
    private String auditStatus;

    //司机/押车员角色:1为司机,2为押车员,3为司机和押车员
    private String role;
    //休业,开工状态:1为开工,0为休业
    private String enabled;
    private String driverPhone;
    private String remark;
    private boolean isOpenTools = false;
    //是否允许删除 0为允许,1为不允许
    private String ifAllowUpdate;
    //0 待邀请 1 已邀请 2已拒绝 3 已开通
    private String invitationState;
    private String invitationText;
    //该字段为押车员的休业和开业状态：1为开工,0为休业（只在车辆调度中使用）
    private String escortEnabled;
    private int invitationBg;
    private int invitationTextColor;

    protected DriverModel(Parcel in) {
        driverId = in.readString();
        driverName = in.readString();
        auditStatus = in.readString();
        role = in.readString();
        enabled = in.readString();
        driverPhone = in.readString();
        remark = in.readString();
        isOpenTools = in.readByte() != 0;
        ifAllowUpdate = in.readString();
        invitationState = in.readString();
        invitationText = in.readString();
        escortEnabled = in.readString();
        invitationBg = in.readInt();
        invitationTextColor = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(driverId);
        dest.writeString(driverName);
        dest.writeString(auditStatus);
        dest.writeString(role);
        dest.writeString(enabled);
        dest.writeString(driverPhone);
        dest.writeString(remark);
        dest.writeByte((byte) (isOpenTools ? 1 : 0));
        dest.writeString(ifAllowUpdate);
        dest.writeString(invitationState);
        dest.writeString(invitationText);
        dest.writeString(escortEnabled);
        dest.writeInt(invitationBg);
        dest.writeInt(invitationTextColor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DriverModel> CREATOR = new Creator<DriverModel>() {
        @Override
        public DriverModel createFromParcel(Parcel in) {
            return new DriverModel(in);
        }

        @Override
        public DriverModel[] newArray(int size) {
            return new DriverModel[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverModel that = (DriverModel) o;

        if (driverId != null ? !driverId.equals(that.driverId) : that.driverId != null)
            return false;
        if (driverName != null ? !driverName.equals(that.driverName) : that.driverName != null)
            return false;
        if (auditStatus != null ? !auditStatus.equals(that.auditStatus) : that.auditStatus != null)
            return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (enabled != null ? !enabled.equals(that.enabled) : that.enabled != null) return false;
        if (driverPhone != null ? !driverPhone.equals(that.driverPhone) : that.driverPhone != null)
            return false;
        return remark != null ? remark.equals(that.remark) : that.remark == null;
    }


    @Override
    public int hashCode() {
        int result = driverId != null ? driverId.hashCode() : 0;
        result = 31 * result + (driverName != null ? driverName.hashCode() : 0);
        result = 31 * result + (auditStatus != null ? auditStatus.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (driverPhone != null ? driverPhone.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }

    public DriverModel() {
    }

    public String getIfAllowUpdate() {
        return ifAllowUpdate;
    }

    public void setIfAllowUpdate(String ifAllowUpdate) {
        this.ifAllowUpdate = ifAllowUpdate;
    }

    public DriverModel(DriverInfoModel driverInfoModel) {
        driverId = driverInfoModel.getDriverId();
        driverName = driverInfoModel.getDriverName();
        driverPhone = driverInfoModel.getDriverPhone();
        auditStatus = driverInfoModel.getAuditStatus();
        enabled = driverInfoModel.getEnabled();
        role = driverInfoModel.getRole();
        ifAllowUpdate = driverInfoModel.getIfAllowUpdate();
        invitationState = driverInfoModel.getInvitationState();
    }

    public String getEscortEnabled() {
        return escortEnabled;
    }

    public void setEscortEnabled(String escortEnabled) {
        this.escortEnabled = escortEnabled;
    }

    public boolean isOpenTools() {
        return isOpenTools;
    }

    public void setOpenTools(boolean openTools) {
        isOpenTools = openTools;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRole() {
        return role;
    }

    public String getRoleText() {
        String roles = "";
        switch (role) {
            case "1":
                roles = "司机";
                break;
            case "2":
                roles = "押车员";
                break;
            case "3":
                roles = "司机/押车员";
                break;
        }
        return roles;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEnabled() {
        return enabled;
    }

    public String getEnabledText() {
        return enabled.equals("1") ? "开工" : "休业";
    }

    public void initInvitation() {
        if (invitationState == null) {
            invitationText = "";
            invitationBg = 0;
            invitationTextColor = 0;
            return;
        }
        switch (invitationState) {
            case AppConstants.INVITATION_STATE.TO_BE_INVITED:
                invitationText = "待邀请";
                invitationBg = R.drawable.btn_bg_4rd_line_normal_orange;
                invitationTextColor = R.color.text_orange;
                return;
            case AppConstants.INVITATION_STATE.INVITED:
                invitationText = "已邀请";
                invitationBg = R.drawable.btn_bg_4rd_line_normal_green;
                invitationTextColor = R.color.text_green;
                return;
            case AppConstants.INVITATION_STATE.REFUSED:
                invitationText = "已拒绝";
                invitationBg = R.drawable.btn_bg_4rd_line_normal_red;
                invitationTextColor = R.color.text_red;
                return;
            case AppConstants.INVITATION_STATE.OPENED:
                invitationText = "已开通";
                invitationBg = R.drawable.btn_bg_4rd_line_normal_green;
                invitationTextColor = R.color.text_green;
                return;
            default:
                invitationText = "";
                invitationBg = 0;
                invitationTextColor = 0;
        }
    }

    public String getInvitationText() {
        return invitationText;
    }

    public int getInvitationBg() {
        return invitationBg;
    }

    public int getInvitationTextColor() {
        return invitationTextColor;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getInvitationState() {
        return invitationState;
    }

    public void setInvitationState(String invitationState) {
        this.invitationState = invitationState;
    }

    public String getAuditStatusText() {
        String states = "";
        switch (auditStatus) {
            case "1":
                states = "已认证";
                break;
            case "2":
                states = "已驳回";
                break;
            case "0":
                states = "待认证";
                break;
        }
        return states;
    }

    public boolean getEnabledBoolean() {
        return "1".equals(enabled);
    }

    public boolean getFollowerEnabled() {
        return "1".equals(escortEnabled);
    }

    public void switchEnabled() {
        if ("1".equals(enabled)) {
            enabled = "0";
        } else {
            enabled = "1";
        }
    }


    public boolean isAllowDelete() {
        return "0".equals(ifAllowUpdate);
    }
}
