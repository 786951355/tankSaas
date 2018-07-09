//package com.tanker.basemodule.constants;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.orhanobut.hawk.Hawk;
//import com.tanker.basemodule.AppConstants;
//import com.tanker.basemodule.model.login_model.UserInfo;
//
//public class RolesConst {
//    private static UserInfo userInfo;
//
//    public static void initRoles() {
//        userInfo = Hawk.get(AppConstants.HAWK_USER);
//        Log.d("sglei-user", "initRoles userinfo = " + userInfo);
//    }
//
//    public static String getCurrentRole() {
//        Log.d("sglei-user", "getCurrentRole userinfo = " + userInfo + ", userInfo.getRole() = " + (userInfo == null ? "" : userInfo.getRole()));
//        if (userInfo == null || TextUtils.isEmpty(userInfo.getRole())) {
//            return "";
//        }
//        return userInfo.getRole();
//    }
//
//    /**
//     * 切换角色后设置用户当前角色
//     *
//     * @param role 角色类型
//     * @return 角色参数是否合法
//     */
//    public static boolean setCurrentRole(String role) {
//        if (role == null || userInfo == null) {
//            return false;
//        }
//        switch (role) {
//            case ROLES.DRIVER:
//            case ROLES.CLIENT:
//            case ROLES.CARRIER:
//                userInfo.setRole(role);
//                Hawk.put(AppConstants.HAWK_USER, userInfo);
//                return true;
//            default:
//                return false;
//        }
//    }
//
//    public static void switchRole() {
//        if (RolesConst.isCarrier()) {
//            setCurrentRole(ROLES.DRIVER);
//        } else if (RolesConst.isDriver()) {
//            setCurrentRole(ROLES.CARRIER);
//        }
//    }
//
//    /**
//     * @return 是否是司机/押车员角色
//     */
//    public static boolean isDriver() {
//        return getCurrentRole().equals(ROLES.DRIVER);
//    }
//
//    /**
//     * @return 是否委托方角色
//     */
//    public static boolean isClient() {
//        return getCurrentRole().equals(ROLES.CLIENT);
//    }
//
//    /**
//     * @return 是否是车主（承运商）角色
//     */
//    public static boolean isCarrier() {
//        return getCurrentRole().equals(ROLES.CARRIER);
//    }
//
//    /**
//     * @return 是否可以切换到司机角色 (同时拥有 司机角色和车主角色)
//     */
//    public static boolean canSwitchToDriver() { // 当前角色是车主并且有司机或者押车员身份
//        return isCarrier() && (!TextUtils.isEmpty(userInfo.getIfDriverAgreeInvitation()) && userInfo.getIfDriverAgreeInvitation().equals("1")
//                || !TextUtils.isEmpty(userInfo.getIfEscortAgreeInvitation()) && userInfo.getIfEscortAgreeInvitation().equals("1"));
//
//    }
//
//    /**
//     * @return 是否可以切换到车主角色 (同时拥有 司机角色和车主角色)、
//     * 第一次登录如果登录角色是司机，便没有车主角色
//     */
//    public static boolean canSwitchToCarrier() { //当前角色是司机并且有车主身份
//        return isDriver() && userInfo.hasCarrierRole();
//    }
//
//    /**
//     * 更新车主角色
//     *
//     * @param isCarrier 是否有车主角色
//     */
//    public static void updateCarrierRole(String isCarrier) {
//        userInfo.setIsCarrier(isCarrier);
//        Hawk.put(AppConstants.HAWK_USER, userInfo);
//    }
//
//    public interface ROLES {
//        /**
//         * 司机/押车员
//         */
//        String DRIVER = "0";
//        /**
//         * 委托方
//         */
//        String CLIENT = "1";
//        /**
//         * 承运商
//         */
//        String CARRIER = "2";
//
//        /**
//         * 司机
//         */
//        String TYPE_DRIVER = "1";
//        /**
//         * 押车员
//         */
//        String TYPE_ESCORT = "2";
//        /**
//         * 司机&押车员
//         */
//        String TYPE_DRIVER_ESCORT = "3";
//    }
//
//}
