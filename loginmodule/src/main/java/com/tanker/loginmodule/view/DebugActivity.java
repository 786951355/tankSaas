package com.tanker.loginmodule.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.common.SaasApp;
import com.tanker.basemodule.router.ReflectUtils;
import com.tanker.basemodule.utils.StringUtils;
import com.tanker.loginmodule.R;


public class DebugActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup mRgUrl;
    public static final String IS_MIT_ENV = "IS_MIT_ENV";
    /**
     * 保存
     */
    private Button mConfirm;

    private String uat = "http://uat.carrierapi.tankchaoren.com";
    private String test = "https://test.carrierapi.tankchaoren.com";
    private String mit = "http://mit.carrierapi.guanguan.com";
    private RadioButton rbTest;
    private RadioButton rbUat;
    private boolean isTestHost;
    private String tempUrl;
    private EditText etDev;
    private RadioButton rbDev;
    private RadioGroup rgCrash;
    private RadioButton rbSave;
    private RadioButton rbNotSave;
    private Boolean isSaveCrash;
    private RadioButton rbMit;
    private boolean isMit;


    @Override
    protected void initView() {
        tempUrl = Hawk.get(AppConstants.HAWK_APP_HOST, test);
        isSaveCrash = Hawk.get("isSaveCrash", false);
        mRgUrl = findViewById(R.id.rg_url);
        mConfirm = findViewById(R.id.confirm);
        rbTest = findViewById(R.id.rb_test);
        rbUat = findViewById(R.id.rb_uat);
        rbDev = findViewById(R.id.rb_dev);
        rbMit = findViewById(R.id.rb_mit);
        TextView versionName = findViewById(R.id.version_name);
        TextView versionCode = findViewById(R.id.version_code);
        etDev = findViewById(R.id.et_dev);

        rgCrash = findViewById(R.id.rg_crash);
        rbSave = findViewById(R.id.rb_save);
        rbNotSave = findViewById(R.id.rb_not_save);
        if (isSaveCrash) {
            rbSave.setChecked(true);
            rbNotSave.setChecked(false);
        } else {
            rbSave.setChecked(false);
            rbNotSave.setChecked(true);
        }

        mConfirm.setOnClickListener(this);
        if (test.equals(tempUrl)) {
            rbTest.setChecked(true);
            rbUat.setChecked(false);
            rbDev.setChecked(false);
            rbMit.setChecked(false);
            etDev.setVisibility(View.GONE);
        } else if (uat.equals(tempUrl)) {
            rbTest.setChecked(false);
            rbUat.setChecked(true);
            rbDev.setChecked(false);
            rbMit.setChecked(false);
            etDev.setVisibility(View.GONE);
        } else if (mit.equals(tempUrl)) {
            rbTest.setChecked(false);
            rbUat.setChecked(false);
            rbDev.setChecked(false);
            rbMit.setChecked(true);
            etDev.setVisibility(View.GONE);
        } else {
            rbDev.setChecked(true);
            rbUat.setChecked(false);
            rbMit.setChecked(false);
            rbTest.setChecked(false);
            etDev.setText(tempUrl);
            etDev.setVisibility(View.VISIBLE);
        }

        versionName.setText("VersionName: " + SaasApp.getInstance().getVersionName());
        versionCode.setText("VersionCode: " + SaasApp.getInstance().getVersionCode());
        mRgUrl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isMit = false;
                if (checkedId == R.id.rb_test) {
                    tempUrl = test;
                    etDev.setVisibility(View.GONE);
                    isMit = false;
                } else if (checkedId == R.id.rb_uat) {
                    tempUrl = uat;
                    etDev.setVisibility(View.GONE);
                    isMit = false;
                } else if (checkedId == R.id.rb_mit) {
                    isMit = true;
                    tempUrl = mit;
                    etDev.setVisibility(View.GONE);
                } else if (checkedId == R.id.rb_dev) {
                    etDev.setVisibility(View.VISIBLE);
                    isMit = false;
                }
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activty_debug;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setTitle("测试配置");
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.confirm) {
            if (mRgUrl.getCheckedRadioButtonId() == R.id.rb_dev) {
                tempUrl = etDev.getText().toString();
                if (!StringUtils.isUrl(tempUrl)) {
                    showMessage("请输入正确的url地址");
                    return;
                }
            }
            Hawk.put(IS_MIT_ENV, isMit);
            if (rgCrash.getCheckedRadioButtonId() == R.id.rb_save) {
                Hawk.put("isSaveCrash", true);
            } else {
                Hawk.put("isSaveCrash", false);
            }
            if (tempUrl != null && !tempUrl.equals(Hawk.get(AppConstants.HAWK_APP_HOST))) {
                Hawk.put(AppConstants.HAWK_APP_HOST, tempUrl);
                restartApp();
            } else {
                restartApp3();
            }
            showMessage("配置已更改");
        } else {
        }
    }

    /**
     * 重新启动App -> 不杀进程,缓存的东西不清除,启动快
     */
    public void restartApp2() {
        final Intent intent = SaasApp.getInstance().getPackageManager()
                .getLaunchIntentForPackage(SaasApp.getInstance().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        SaasApp.getInstance().startActivity(intent);
    }

    public void restartApp3() {
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 50, restartIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    /**
     * 重新启动App -> 杀进程,会短暂黑屏,启动慢
     */
    public void restartApp() {
        //启动页
        Intent intent = ReflectUtils.getIntent(this, "com.tanker.mainmodule.view.WelcomeActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ReflectUtils.startActivity(this, intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}