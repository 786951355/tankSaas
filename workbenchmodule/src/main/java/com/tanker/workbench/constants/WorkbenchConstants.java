package com.tanker.workbench.constants;

/**
 * Created by ronny on 2018/3/29.
 */

public class WorkbenchConstants {

    //分页搜索的条数
    public static final int ROWS = 15;


    public static final String VEHICLE_ID = "VEHICLE_ID";

    public static final String CAE_INFO_KEY = "carInfoKey";
    public static final String MINE_INFO_KEY = "mineInfoKey";

    public static final String QUALIFICATION_STATE = "qualificationState";
    public static final String CAR_TYPE_SEMITRAILER = "1", CAR_TYPE_VEHICLE = "2";
    public static final String CAR_AUDIT_STATUS_PASS = "1", CAR_AUDIT_STATUS_REJECT = "2", CAR_AUDIT_STATUS_ING = "0";

    public static final String PARAM_LINE_REQUEST_DTO = "lineRequestDto";
    public static final String PARAM_LINE_ID = "lineId";
    public static final String PARAM_TYPE = "lineType";
    public static final String PARAM_NEW_PHONE = "newMobilePhone";
    public static final String PARAM_OLD_PHONE = "oldMobilePhone";
    public static final String PARAM_VEHICLE_ID = "vehicleId";
    public static final String PARAM_VEHICLE_NUM = "vehicleNumber";
    public static final String PARAM_VEHICLE_REQUEST_DTO = "vehicleRequestDto";
    public static final String PARAM_IDENTITY_CARD = "identityCard";
    public static final String PARAM_USABLE_PHONE = "usablePhone";
    public static final String PARAM_COMPANY_AUTH_FILE = "companyAuthorizationFile";

    public static final String QUALIFICATION_KEY = "qualificationKey";

    //用于从资质认证页面进入资质详情
    public static final String INTENT_QUALIFICATION_DETAIL = "toQualificationDetail";
    //用于从从公司认证到个人认证界面
    public static final String INTENT_TO_PERSONAL_QUALIFICATION = "toPersonalQualification";
    //去往授权书界面
    public static final String INTENT_TO_AUTH_BOOK = "INTENT_TO_AUTH_BOOK";
    public static final String INTENT_TO_DETAIL_DRIVER = "INTENT_TO_DETAIL_DRIVER";
    public static final String INTENT_TO_ADD_CAR = "INTENT_TO_ADD_CAR";
    public static final String INTENT_TO_CAR_DETAIL = "INTENT_TO_CAR_DETAIL";

    public static final String AUTH_BOOK_KEY = "AuthBookKey";

    public static final String DRIVER_ID = "driverId";
    public static final String DRIVER_MODEL = "driverModel";
    public static final String PARAM_DRIVER_ID = "driverId";
    public static final String PARAM_VALID = "valid";
    public static final String PARAM_ENABLED = "enabled";
    public static final String DRIVER_AUDIT_STATE = "driverAuditState";
    public static final String DRIVER_CAN_MODIFY_PHONE = "canModifyPhone";
    public static final String VEHICLE_AUDIT_STATE = "vehicleAuditState";
    public static final String INTENT_LINE_ID = "INTENT_LINE_ID";
    public static final String IS_ADD_SINGLE_LINE = "IS_ADD_SINGLE_LINE";
    public static final String TYPE = "type";
    public static final String PARAM_PROVINCE_ID = "provinceId";
    public static final String PARAM_START_ID = "startCityId";
    //    public static final String NEED_REFRESH_LINES = "NEED_REFRESH_LINES";
    public static final String INTENT_TO_LINE_MANAGE = "INTENT_TO_LINE_MANAGE";
    public static final String INTENT_TO_CAR_MANAGE = "INTENT_TO_CAR_MANAGE";
    public static final String INTENT_TO_DRIVER_MANAGE = "INTENT_TO_DRIVER_MANAGE";
    public static final String IS_FROM_MINE = "IS_FROM_MINE";
    public static final String PARAM_LINES = "lines";

    /**
     * 司机、押车员状态
     */
    public interface STATUS_DRIVER {
        //1 休业，2开工，3删除
        String CLOSE = "1";
        String OPEN = "2";
        String DELETE = "3";
    }
}
