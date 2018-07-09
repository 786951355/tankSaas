package com.tanker.basemodule.model.home_model;

import java.io.Serializable;

/**
 * 司机、押车员信息
 */
public class VehicleDriverModel implements Serializable {
    /**
     * 角色名称（司机或者押车员）
     */
    private String role;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色名称的电话号码
     */
    private String roleNamePhone;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleNamePhone() {
        return roleNamePhone;
    }

    public void setRoleNamePhone(String roleNamePhone) {
        this.roleNamePhone = roleNamePhone;
    }
}
