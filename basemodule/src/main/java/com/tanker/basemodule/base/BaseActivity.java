package com.tanker.basemodule.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.R;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.constants.DialogConst;
import com.tanker.basemodule.dialog.TankerDialog;
import com.tanker.basemodule.dialog.TankerProgressDialog;
import com.tanker.basemodule.utils.DensityUtils;
import com.tanker.basemodule.utils.NetUtil;
import com.tanker.basemodule.utils.ShowMassageUtil;
import com.tanker.basemodule.utils.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by ronny on 2017/8/16.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity
        implements BaseView {
    //对svg是两处显示进行适配
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public T mPresenter;
    protected TankerProgressDialog progressDialog;
    protected BaseActivity mContext;
    protected TankerDialog dialog;
    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();
    protected InputMethodManager imm;
    protected static final int RESULT_CAMERA_IMAGE = 1, RESULT_LOAD_IMAGE = 2;
    protected ImageView leftAction;
    protected TextView tvTitle;
    protected AppCompatTextView rightAction;
    protected RelativeLayout toolbar;
    protected RelativeLayout viewContainer;
    protected CustomToolbar mCustomToolbar;
    private boolean tokenDialogIsShowed = false;
    protected ImageView rightIcon;
    protected RelativeLayout rootView;
    protected View statusLine;
    protected ImageView errorImg;
    private TextView tvMessageNum;
    private ArrayList<Disposable> disposables = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE);
        setContentView(R.layout.activity_base);
        viewContainer = findViewById(R.id.view_container);
        rootView = findViewById(R.id.root_view);
        setCustomContentView(getContentView());
        leftAction = findViewById(R.id.left_action);
        tvTitle = findViewById(R.id.tv_title);
        rightAction = findViewById(R.id.right_action);
        rightIcon = findViewById(R.id.right_icon);
        toolbar = findViewById(R.id.toolbar);
        statusLine = findViewById(R.id.status_line);
        errorImg = findViewById(R.id.iv_error_data);
        tvMessageNum = findViewById(R.id.tv_message_num);
        mContext = this;
        // 友盟-设置为U-APP场景为普通
        MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_UM_NORMAL);
        StatusBarUtil.StatusBarLightMode(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags =
                    (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
//    Logger.setTag(TAG);
        initDialog();
        initToolbar();
        initView();
        initData();
        initEvent();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

    }


    protected abstract void initView();

    protected void initData() {
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void initEvent() {
        //点击空白处隐藏键盘
        rootView.setOnTouchListener((v, motionEvent) -> {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (manager != null && getCurrentFocus() != null
                        && getCurrentFocus().getWindowToken() != null) {
                    if (isSoftShowing()){
                        hideSoftKeybord();
                    }
//                    manager.hideSoftInputFromWindow(getCurrentFocus()
//                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
            return false;
        });
        leftAction.setOnClickListener(view -> {
            if (mCustomToolbar != null && mCustomToolbar.getOnLeftClickListener() != null) {
                mCustomToolbar.getOnLeftClickListener().onClick(view);
            } else {
                finish();
            }
        });
        rightAction.setOnClickListener(view -> {
            if (mCustomToolbar != null && mCustomToolbar.getOnRightClickListener() != null) {
                mCustomToolbar.getOnRightClickListener().onClick(view);
            }
        });
        rightIcon.setOnClickListener(view -> {
            if (mCustomToolbar != null && mCustomToolbar.getOnRightIconClickListener() != null) {
                mCustomToolbar.getOnRightIconClickListener().onClick(view);
            }
        });
    }

    protected void initToolbar() {
        mCustomToolbar = CustomToolbar.newInstance(toolbar, leftAction, rightAction, rightIcon, tvTitle, statusLine, tvMessageNum);
        configToolbar(mCustomToolbar);
    }

    @Override
    public void setCustomContentView(@LayoutRes int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, viewContainer);
    }

    @Override
    public void showProgress() {
        showProgress(false);
    }

    @Override
    public void showProgress(boolean cancelable) {
        if (!progressDialog.isShowing()) {
            progressDialog.setText("加载中···");
            progressDialog.setCanceledOnTouchOutside(cancelable);
            progressDialog.show();
        }
    }

    @Override
    public void showProgress(String msg) {
        progressDialog.setText(msg);
        progressDialog.show();
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected void initDialog() {
        dialog = new TankerDialog(this);
        progressDialog = new TankerProgressDialog(mContext, R.style.CustomDialog);
        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setText("加载中···");
    }


    @Override
    public BaseActivity getContext() {
        return mContext;
    }

    /**
     * 只有提示语的弹窗
     *
     * @param msg
     */
    @Override
    public void showAlertDialog(String msg, TankerDialog.OptionListener optionListener) {
        showAlertDialog(msg, -1, optionListener);
    }

    /**
     * 只有提示语的弹窗
     */

    public void showEditDialog(String title, String hint, String transportTonnage, TankerDialog.OptionListener optionListener) {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(title)) {
            bundle.putString(DialogConst.DIALOG_TITLE_TEXT, title);
        }
        bundle.putString(DialogConst.DIALOG_CONTENT_TEXT, transportTonnage);
        bundle.putInt(DialogConst.DIALOG_MESSAGE_TYPE, TankerDialog.EDIT_DIALOG);
        bundle.putBoolean(DialogConst.DIALOG_NOT_CANCEL_DIALOG_AFTER_CLICK, true);
        bundle.putString(DialogConst.DIALOG_CONTENT_HINT, hint);
        showDialog(bundle, optionListener);
    }


    public void showAlertDialog(String title, String msg, int ImageRes, TankerDialog.OptionListener optionListener) {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(title)) {
            bundle.putString(DialogConst.DIALOG_TITLE_TEXT, title);
        }
        bundle.putInt(DialogConst.DIALOG_MESSAGE_TYPE, TankerDialog.IMAGE_DIALOG_WITH_TWO_CONTENT);
        bundle.putInt(DialogConst.DIALOG_HEIGHT, ImageRes == -1 ? DialogConst.DIALOG_DEFAULT_HEIGHT : DialogConst.DIALOG_SECOND_HEIGHT);
        bundle.putString(DialogConst.DIALOG_CONTENT_TEXT, msg);
        if (ImageRes != -1) {
            bundle.putInt(DialogConst.DIALOG_IMAGE_RES_ID, ImageRes);
        }
        showDialog(bundle, optionListener);

    }

    public void showDialog(Bundle bundle, TankerDialog.OptionListener optionListener) {
        dialog.switchStyleAndShow(bundle, optionListener);
    }

    /**
     * 默认title 工作时间：早9:00-晚8:00
     *
     * @param msg
     * @param optionListener
     */
    public void showPhoneDialog(String msg, TankerDialog.OptionListener optionListener) {
        Bundle bundle = new Bundle();
        bundle.putInt(DialogConst.DIALOG_MESSAGE_TYPE, TankerDialog.PHONE_DIALOG);
        bundle.putString(DialogConst.DIALOG_CONTENT_TEXT, msg);
        showDialog(bundle, optionListener);
    }

    /**
     * @Description: 打电话弹出框可以传递title的方法
     * @date 2018/5/1
     * @author lwj
     */
    public void showPhoneDialog(String msg, String title, TankerDialog.OptionListener optionListener) {
        Bundle bundle = new Bundle();
        bundle.putInt(DialogConst.DIALOG_MESSAGE_TYPE, TankerDialog.PHONE_DIALOG);
        bundle.putString(DialogConst.DIALOG_CONTENT_TEXT, msg);
        if (!TextUtils.isEmpty(title)) {
            bundle.putString(DialogConst.DIALOG_TITLE_TEXT, title);
        }
        showDialog(bundle, optionListener);
    }


    @Override
    public void showAlertDialog(String msg, int ImageRes, TankerDialog.OptionListener optionListener) {
        showAlertDialog(null, msg, ImageRes, optionListener);
    }

    @Override
    public void showAlertDialogNoCancel(String content, String secondContent, int ImageRes, TankerDialog.OptionListener optionListener) {
        Bundle bundle = new Bundle();
        bundle.putInt(DialogConst.DIALOG_MESSAGE_TYPE, TankerDialog.IMAGE_DIALOG);
        bundle.putString(DialogConst.DIALOG_CONTENT_TEXT, content);
        bundle.putInt(DialogConst.DIALOG_HEIGHT, DialogConst.DIALOG_SECOND_HEIGHT);
        bundle.putString(DialogConst.DIALOG_SECOND_CONTENT_TEXT, secondContent);
        bundle.putBoolean(DialogConst.DIALOG_SHOW_CANCEL, false);
        if (ImageRes != -1) {
            bundle.putInt(DialogConst.DIALOG_IMAGE_RES_ID, ImageRes);
        }
        dialog.switchStyleAndShow(bundle, optionListener);
    }

    @Override
    public void showAlertDialogNoCancel(String content, String confirmText, TankerDialog.OptionListener optionListener) {
        Bundle bundle = new Bundle();
        bundle.putInt(DialogConst.DIALOG_MESSAGE_TYPE, TankerDialog.IMAGE_DIALOG_WITHOUT_IMG);
        bundle.putString(DialogConst.DIALOG_CONTENT_TEXT, content);
        bundle.putString(DialogConst.DIALOG_CONFIRM_TEXT, confirmText);
        bundle.putBoolean(DialogConst.DIALOG_SHOW_CANCEL, false);
        dialog.switchStyleAndShow(bundle, optionListener);
    }


    @Override
    public void showMessage(String error) {
        ShowMassageUtil.showErrorToast(error);
    }

    @Override
    public void showErrorMessage(int code, String msg) {
        ShowMassageUtil.showErrorToast(code + "", msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        for (Disposable disposable : disposables) {
            if (disposable != null && disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        fixInputMethodManagerLeak(this);
    }

    @Override
    public void navigationTo(Class cls) {
        Intent intent = new Intent(this, cls);
        navigationTo(intent);
    }

    @Override
    public void navigationTo(Intent intent) {
        if (NetUtil.isNetworkAvailable(this)) {
            startActivity(intent);
        } else {
            showMessage(getString(R.string.error_net));
        }
    }

    private String getRunningActivityName() {
        String contextString = getLocalClassName();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.length());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 1 && permissions.length > 0) {
            if (permissions[0].equals(Manifest.permission.CALL_PHONE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + AppConstants.getServicePhone()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    startActivity(intent);
                }
            } else {//没有获得到权限
                showMessage("您已禁止该权限，需要重新开启。");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟统计时长-开始
        MobclickAgent.onResume(this); // 基础指标统计，不能遗漏
    }

    @Override
    protected void onPause() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE);
        super.onPause();
        //友盟统计时长-结束
        MobclickAgent.onPause(this); // 基础指标统计，不能遗漏
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP);
        super.onStop();
    }

    @Override
    public PublishSubject<ActivityLifeCycleEvent> getLifecycleSubject() {
        return lifecycleSubject;
    }

    @Override
    public Drawable getmDrawable(@DrawableRes int drawableRes) {
        return getResources().getDrawable(drawableRes);
    }

    @Override
    public int getmColor(int resColor) {
        return getResources().getColor(resColor);
    }

    public void hideSoftKeybord() {
        boolean isOpen = isSoftShowing();
        if (isOpen) {//打开则执行下面方法进行关闭
            // 隐藏软键盘
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected boolean isSoftShowing() {
        //获取当前屏幕内容的高度
        int screenHeight = getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //底部又一个button 60dp高
        return screenHeight - rect.bottom > DensityUtils.dip2px(70);
    }

    protected void hideSoftKeybord(View view) {
        boolean isOpen = imm.isActive(view);
        if (isOpen) {//打开则执行下面方法进行关闭
            // 隐藏软键盘
//            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void showNoDataImgTip() {
        errorImg.setImageResource(R.drawable.no_data_img);
        errorImg.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoLinesTip() {
        errorImg.setImageResource(R.drawable.no_lines_img);
        errorImg.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoSearchDataImgTip() {
        errorImg.setImageResource(R.drawable.no_search_result_img);
        errorImg.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideImgTip() {
        errorImg.setVisibility(View.GONE);
    }


    public void applyWritePermission(int requestCode) {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCameraPermission = ContextCompat.checkSelfPermission(this, permissions[0]);
            int checkWritePermission = ContextCompat.checkSelfPermission(this, permissions[1]);
            if (checkCameraPermission != PackageManager.PERMISSION_GRANTED
                    && checkWritePermission == PackageManager.PERMISSION_GRANTED) {//相机无权限
                ActivityCompat.requestPermissions(this, new String[]{permissions[0]}, requestCode);
            } else if (checkCameraPermission == PackageManager.PERMISSION_GRANTED
                    && checkWritePermission != PackageManager.PERMISSION_GRANTED) {//写入无权限
                ActivityCompat.requestPermissions(this, new String[]{permissions[1]}, requestCode);
            } else if (checkCameraPermission != PackageManager.PERMISSION_GRANTED
                    && checkWritePermission != PackageManager.PERMISSION_GRANTED) {//都没有权限
                ActivityCompat.requestPermissions(this, permissions, requestCode);
            } else {
                switch (requestCode) {
                    case RESULT_CAMERA_IMAGE:
                        takePhoto();
                        break;
                    case RESULT_LOAD_IMAGE:
                        choosePhoto();
                        break;
                }
            }
        } else {
            switch (requestCode) {
                case RESULT_CAMERA_IMAGE:
                    takePhoto();
                    break;
                case RESULT_LOAD_IMAGE:
                    choosePhoto();
                    break;
            }
        }
    }

    /**
     * 调用相机的空实现
     */
    protected void takePhoto() {
    }

    protected void addDisposable(Disposable disposable) {
        if (disposable == null) {
            return;
        }
        disposables.add(disposable);
    }

    /**
     * 从相册选取图片
     */
    protected void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }


    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                }
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public void onCallPermission(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断当前系统的SDK版本是否大于23
            //如果当前申请的权限没有授权
            if (!(checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED)) {
                //第一次请求权限的时候返回false,第二次shouldShowRequestPermissionRationale返回true
                //如果用户选择了“不再提醒”永远返回false。
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(mContext, "您已禁止该权限，需要重新开启。", Toast.LENGTH_LONG).show();
                }
                //请求权限
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {//已经授权了就走这条分支
                Logger.i("onClick granted");
                startActivity(intent);
            }
        } else {
            startActivity(intent);
        }
    }
}
