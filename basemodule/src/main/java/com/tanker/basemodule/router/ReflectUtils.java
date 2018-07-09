package com.tanker.basemodule.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.utils.CommonUtils;


/**
 * author ronny
 * created on 2018/3/21
 */

public class ReflectUtils {

    public static Fragment getFragment(String name) {
        Fragment fragment;
        try {
            Class fragmentClass = Class.forName(name);
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Log.d("error--->", e.toString());
            return null;
        }
        return fragment;
    }


    public static Object getModuleCall(String name) {
        Object object;
        try {
            Class aClass = Class.forName(name);
            object = aClass.newInstance();
        } catch (Exception e) {
            Log.d("error--->", e.toString());
            return null;
        }

        return object;
    }


    public static void startActivityWithNameForResult(Activity context, String name, int requestCode) {
        try {
            Class clazz = Class.forName(name);
            startActivityForResult(context, clazz, requestCode);
        } catch (ClassNotFoundException e) {
            CommonUtils.showToast(context, "业务组件单独调试不应该跟其他业务Module产生交互，如果你依然想要在运行期依赖其它组件");
            Log.d("error--->", e.toString());
        }
    }


    public static void startActivityForResult(Activity context, Class clazz, int requestCode) {
        context.startActivityForResult(new Intent(context, clazz), requestCode);
    }

    public static void startActivityForResult(Activity context, Intent intent, int requestCode) {
        context.startActivityForResult(intent, requestCode);
    }

    public static void startActivityWithName(Context context, String name) {
        try {
            Class clazz = Class.forName(name);
            startActivity(context, clazz);
        } catch (ClassNotFoundException e) {
            CommonUtils.showToast(context, "业务组件单独调试不应该跟其他业务Module产生交互，如果你依然想要在运行期依赖其它组件");
            Log.d("error--->", e.toString());
        }
    }

    public static void startActivity(Context context, Class clazz) {
        context.startActivity(getIntent(context, clazz));
    }

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static Intent getIntent(Context context, Class clazz) {
        return new Intent(context, clazz);
    }

    public static Intent getIntent(Context context, String name) {
        Class clazz = null;
        try {
            clazz = Class.forName(name);
        } catch (ClassNotFoundException e) {
            CommonUtils.showToast(context, "业务组件单独调试不应该跟其他业务Module产生交互，如果你依然想要在运行期依赖其它组件");
            Log.d("error--->", e.toString());
        }
        return new Intent(context, clazz);
    }


    public static void navigationToHome(Context context, int index) {
        Intent intent = ReflectUtils.getIntent(context, "com.tanker.mainmodule.view.NewMainActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(AppConstants.HOME_PAGE_INDEX, index);
        ReflectUtils.startActivity(context, intent);
    }

    public static void navigationToLogin(Context context) {
        Intent intent = ReflectUtils.getIntent(context, "com.tanker.loginmodule.view.TankerLoginActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("actionCode", 0);
        ReflectUtils.startActivity(context, intent);

    }
}
