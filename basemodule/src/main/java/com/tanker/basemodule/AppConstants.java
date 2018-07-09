package com.tanker.basemodule;

import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;
import com.tanker.basemodule.common.TankerApp;
import com.tanker.basemodule.model.login_model.ConfigInfo;

/**
 * Created by ronny on 2018/3/21.
 */
public class AppConstants {
    public static final String MIT_IMAGE_URL = "http://mit.image.guanguan.com/images";
    public static final String IMAGE_SEVER = Hawk.get("IS_MIT_ENV", false) ? MIT_IMAGE_URL : BuildConfig.ImageUrl;

    public static final String PROTOCOL_URL = "file:///android_asset/agreement.html";
    public static final String SOUND_TOGGLE = "SOUND_TOGGLE_KEY";
    public static final String WEB_ACTION = "Action";
    public static final String AUTHENTICATION_TYPE = "AUTHENTICATION_TYPE";
    public static final String PARAM_APP_ID = "appId";
    public static final String APP_ID = "zgc.carrier.app";
    public static final String PARAM_EVENT_TIME = "eventTime";
    public static final String HAWK_CONFIG = "HAWK_CONFIG_KEY";
    public static final String HOME_PAGE_INDEX = "homePageIndex";
    public static final String WEB_URL = "webUrl";
    public static final String PARAM_VERIFY_TYPE = "verifyType";
    public static final String PARAM_VERIFY_CODE = "verifyCode";
    public static final int ROWS = 15;
    public static final String JPUSH_COME = "jpushcome";
    public static final String DRIVER_ID = "driverId";
    public static final String VEHICLE_ID = "VEHICLE_ID";
    public static final String CARRIERORDER_ID = "carrierOrderId";
    public static final String DRIVER_ROLE = "driverRole";

    /*---------------------------通知-开始---------------------------**/
    //通知公告通知关系id
    public static final String NOTICE_ID = "noticeId";
    //公告标题
    public static final String NOTICE_TITLE = "noticeTitle";
    //公告描述
    public static final String NOTICE_DESCRIPTION = "noticeDescription";
    //公告网页链接
    public static final String NOTICE_WEBURL = "noticeWebUrl";
    //通知是否已读
    public static final String ISREAD = "isRead";
    /*---------------------------通知-结束---------------------------**/

    public static final String QUALIFICATION_KEY = "qualificationKey";

    public static final int FROM_DISPATCHER_CAR = 102;
    public static final int ADD_CAR_RESULT_CODE = 202;
    public static final int ADD_DRIVER_RESULT_CODE = 203;
    public static final String INTENT_START = "START";
    public static final String INTENT_DATA = "data";

    public static final String HAWK_TOKEN = "HAWK_TOKEN";
    public static final String HAWK_RECENT_ACCOUNT = "HAWK_RECENT_ACCOUNT";
    public static final String HAWK_USER = BuildConfig.isDebug ? "HAWK_DEBUG_USER_KEY" : "HAWK_USER_KEY";

    public static final String ANDROID_VERISON = "android_version";
    public static final String HAWK_APP_HOST = "TestHost";
    public static final String HAWK_SPLASH_IMG = "SplashImage";


    /**************************网络请求参数的key--Start**************************/
    public static final String PARAM_KEY_WORD = "keyword";
    public static final String PARAM_DATA = "data";
    public static final String PARAM_TOKEN = "token";
    public static final String PARAM_LOGIN_TYPE = "loginType";
    public static final String PARAM_COMPANY_NAME = "company";
    public static final String PARAM_PLATFORM = "platform";
    //短信验证码
    public static final String PARAM_VERFICATION_CODE = "verficationCode";
    public static final String PARAM_SMS_TEMPLATE_TYPE = "smsTemplateType";

    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_SIGN = "sign";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_PAGE_SIZE = "rows";
    public static final String PARAM_UID = "userId";
    public static final String PARAM_PHONE = "mobilePhone";
    public static final String PARAM_NAME = "userName";

    public static final String PARAM_NEW_PHONE = "newPhone";
    public static final String PARAM_PAGE_COUNT = "pageCount";

    /**************************网络请求参数的key--End**************************/


    /**************************第三方sdkKey--start**************************/
    public static final String UMENG_CHANEL = "UMENG_CHANNEL";
    public static final String UMENG_DEBUG_KEY = "5b04f863a40fa34ab9000253";
    public static final String UMENG_RELEASE_KEY = "5b023668a40fa3536000005c";


    /**************************第三方sdkKey--End**************************/

    public static String getServicePhone() {
        ConfigInfo configInfo = TankerApp.getInstance().getConfigManager().getConfigInfo();
        return configInfo != null ? configInfo.getAppServicePhone() : "17701655392";
    }

    public static String getTransportWorkTime() {
        ConfigInfo configInfo = TankerApp.getInstance().getConfigManager().getConfigInfo();
        return configInfo != null ? configInfo.getTransportWorkTime() : "早9:00-晚8:00";
    }

    public static int getMaxPictureCount() {
        String count = TankerApp.getInstance().getConfigManager().getConfigInfo() != null ?
                TankerApp.getInstance().getConfigManager().getConfigInfo().getPictureCount() : "";
        return (TextUtils.isEmpty(count) || Integer.valueOf(count) <= 0) ? 6 : Integer.valueOf(count);
    }

    /**
     * 司机、押车员邀请状态
     */
    public interface INVITATION_STATE {
        //0 待邀请 1 已邀请 2已拒绝 3 已开通
        String TO_BE_INVITED = "0";
        String INVITED = "1";
        String REFUSED = "2";
        String OPENED = "3";
    }

    /**
     * 缓存 健
     */
    public interface CACHE_KEY {
        String ORDER_LIST = "order_list";
        String INFORM_LIST = "inform_list";
    }

    /**
     * 用于图片框架返回真实的绝对路径，以获取图片的Exif信息
     */
    public final static String THE_REAL_PATH = "THE_REAL_PATH";


}
