package com.tanker.basemodule.common;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.hawk.Hawk;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.BuildConfig;
import com.tanker.basemodule.constants.BuglyConst;
import com.tanker.basemodule.dao.DaoMaster;
import com.tanker.basemodule.dao.DaoSession;
import com.tanker.basemodule.http.api.RetroAPIFactory;
import com.tanker.basemodule.model.User;
import com.tanker.basemodule.utils.SharedPreferenceUtil;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import org.greenrobot.greendao.AbstractDao;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ronny on 2018/3/21.
 */

public class TankerApp extends Application {
    private static TankerApp mApp;
    private DaoSession daoSession;
    private UserManager userManager;
    private ConfigManager configManager;
    private String token;
    private String TAG = "TankerApp";

    public UserManager getUserManager() {
        if (userManager==null){
            initManager();
        }
        return userManager;
    }

    public ConfigManager getConfigManager() {
        if (configManager==null){
            initManager();
        }
        return configManager;
    }

    public static TankerApp getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String packageName = getPackageName();
        Logger.d("baoming :" + packageName);
        mApp = this;
        //LeakCanary.install(this);
        SharedPreferenceUtil.initSharedPreference(this);
        Hawk.init(this).build();
        if (!BuildConfig.isDebug||Hawk.get("isSaveCrash",false)) {
            initCrashHandler();
            Logger.d(TAG,"Turn on the crash handler！");
        }
//        initDatabase("tanker.db");
        RetroAPIFactory.init();
        initManager();
        initJpush();
        initBugly();
        initUmeng();
    }

    /**
     * @author lwj
     * @Description: 友盟分享平台初始化
     * @date 2018/6/5 10:40
     */
    {
        //微信
        //PlatformConfig.setWeixin("wx51b2f44500d354b2", "2d04642e6f159e8546dec29de2b906de");//我的测试账号
        PlatformConfig.setWeixin("wx249c978c39903b95", "467d87ca73fd592a34839f054a16ea9c");//正式
    }

    /**
     * @author lwj
     * @Description: 友盟
     * @date 2018/5/18 11:43
     */
    private void initUmeng() {

        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);

        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(false);

        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret,需要集成Push功能时必须传入Push的secret，否则传空。
         */
        UMConfigure.init(this, BuildConfig.isDebug?AppConstants.UMENG_DEBUG_KEY:AppConstants.UMENG_RELEASE_KEY, getChannel(AppConstants.UMENG_CHANEL), UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    /**
     * @author lwj
     * @Description: 极光推送
     * @date 2018/5/18 11:43
     */
    private void initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initManager() {
        userManager = new UserManager();
        configManager = new ConfigManager();
    }

    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);  //为了防止dex突破65535的限制
    }

    /**
     * 配置数据库
     */
    private void initDatabase(String str) {
        DaoMaster.DevOpenHelper helper;
        helper = new DaoMaster.DevOpenHelper(this, str, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private void initBugly() {
        Log.d("tanker-bugly", "init bugly version name = "
                + BuildConfig.VERSION_NAME + ", version code = " + BuildConfig.VERSION_CODE + ", isDebug = " + BuildConfig.isDebug);
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//        Beta.autoCheckUpgrade = false;
//        Beta.upgradeDialogLayoutId = R.layout.dialog_bugly_upgrade;
        Bugly.init(getApplicationContext(), BuildConfig.isDebug ? BuglyConst.Debug.APP_ID : BuglyConst.Release.APP_ID, BuildConfig.isDebug);
    }

    private DaoSession getDaoInstant() {
        return daoSession;
    }

    public <T> AbstractDao getCommonDao(T t) {
        if (t instanceof User) {
            return getDaoInstant().getUserDao();
        }
        return null;
    }

    public String getVersion() {
        PackageManager packageManager = getPackageManager();
        String mVersionName = "";
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            mVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "V" + mVersionName;
    }

    public void exit() {
        updateToken(null);
        getUserManager().setUser(null);
        getConfigManager().setConfigInfo(null);
        //极光推送-调用此 API 来删除别名（手机号码）。
        JPushInterface.deleteAlias(this, 2);
    }

    public void updateToken(String token) {
        Hawk.put(AppConstants.HAWK_TOKEN,token);
        this.token=token;
    }

    public String getToken() {
        if (TextUtils.isEmpty(token)){
            token=Hawk.get(AppConstants.HAWK_TOKEN);
        }
        return token==null?"0":token;
    }


    /**
     * get App versionCode
     * @return
     */
    public Integer getVersionCode(){
        PackageManager packageManager=getPackageManager();
        PackageInfo packageInfo;
        String versionCode="";
        try {
            packageInfo=packageManager.getPackageInfo(getPackageName(),0);
            versionCode=packageInfo.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Integer.valueOf(versionCode);
    }

    /**
     * get App versionName
     * @return
     */
    public String getVersionName(){
        PackageManager packageManager=getPackageManager();
        PackageInfo packageInfo;
        String versionName="";
        try {
            packageInfo=packageManager.getPackageInfo(getPackageName(),0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    //key为渠道名的key，对应友盟的 UMENG_CHANNEL
    private String getChannel(String key) {
        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return "";
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d("sglei-app", "onlowmemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d("sglei-app", "ontrimmemory");
    }
}
