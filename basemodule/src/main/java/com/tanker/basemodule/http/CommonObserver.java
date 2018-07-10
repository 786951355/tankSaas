package com.tanker.basemodule.http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.orhanobut.hawk.Hawk;
import com.tanker.basemodule.R;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.common.SaasApp;
import com.tanker.basemodule.dialog.TankerDialog;
import com.tanker.basemodule.utils.CommonUtils;
import com.tanker.basemodule.utils.NetUtil;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by ronny on 2017/9/20.
 */

public abstract class CommonObserver<T> implements Observer<T> {
    private final WeakReference<BaseActivity> context;
    private final String cacheKey;
    private boolean isShowNetErrorBg = false;
    Disposable disposable;
    private boolean isShowProgress = true;


    public void setmNetListener(NetListener mNetListener) {
        this.mNetListener = mNetListener;
    }

    NetListener mNetListener;


    public CommonObserver(String cacheKey, Context context) {
        this.cacheKey = cacheKey;
        this.context = new WeakReference(context);
    }

    public CommonObserver(Context context) {
        this.cacheKey = null;
        this.context = new WeakReference(context);
        isShowProgress = true;
    }

    public CommonObserver(String cacheKey, Context context, boolean isShowProgress) {
        this.cacheKey = cacheKey;
        this.context = new WeakReference(context);
        this.isShowProgress = isShowProgress;

    }

    public CommonObserver(Context context, boolean isShowProgress) {
        this.cacheKey = null;
        this.context = new WeakReference(context);
        this.isShowProgress = isShowProgress;
    }

    public CommonObserver(Context context, boolean isShowProgress, NetListener listener) {
        this.cacheKey = null;
        this.context = new WeakReference(context);
        this.isShowProgress = isShowProgress;
        this.mNetListener = listener;
    }


    @Override
    public void onSubscribe(Disposable d) {
        if (isShowProgress) {
            showProgress();
        }
        disposable = d;
        if (!NetUtil.isNetworkAvailable(context.get().getApplicationContext())) {
            if (cacheKey != null) {
                T t = Hawk.get(cacheKey);
                context.get().showMessage(context.get().getString(R.string.error_net));
                onNext(t);
            } else {
                ExceptionEngine.ResponseThrowable netWorkThrowable = new ExceptionEngine.ResponseThrowable(new Throwable(), ExceptionEngine.ERROR.NETWORD_ERROR);
                netWorkThrowable.message = context.get().getString(R.string.error_net);

                onError(netWorkThrowable);
            }
            onComplete();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (isShowProgress) {
            dismissProgress();
        }
        Logger.e(e.toString());
        if (e.getCause() instanceof ServerException
                && ((ServerException) e.getCause()).getCode() == 30000) {//token失效
            context.get().showAlertDialogNoCancel(((ServerException) e.getCause()).getMsg(), "去登录", new TankerDialog.OptionListener() {
                @Override
                public void onConfirm(TankerDialog obDialog) {
                    navigationToLogin(context.get());
                    SaasApp.getInstance().exit();
                }
            });
            return;
        }

        if (e instanceof ExceptionEngine.ResponseThrowable) {
            if (((ExceptionEngine.ResponseThrowable) e).code == 900) {//承运商Id不存在
                context.get().showAlertDialog(((ExceptionEngine.ResponseThrowable) e).message, new TankerDialog.OptionListener() {
                    @Override
                    public void onConfirm(TankerDialog obDialog) {
                        navigationToLogin(context.get());
                        SaasApp.getInstance().exit();
                    }
                });
                return;
            }
            onError((ExceptionEngine.ResponseThrowable) e);
        } else {
            if (cacheKey != null) {
                T t = Hawk.get(cacheKey);
                onNext(t);
            }
            onError(new ExceptionEngine.ResponseThrowable(e, ExceptionEngine.ERROR.UNKNOWN));
        }

        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }


    public abstract void onError(ExceptionEngine.ResponseThrowable t);

    @Override
    public void onComplete() {
        if (isShowProgress) {
            dismissProgress();
        }
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void showProgress() {
        if (context.get() != null && context.get() instanceof BaseActivity) {
            context.get().showProgress();
        }
    }

    public void dismissProgress() {
        if (context.get() != null && context.get() instanceof BaseActivity) {
            context.get().dismissProgress();
        }
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

    public static void navigationToLogin(Context context) {
        Intent intent = CommonObserver.getIntent(context, "com.tanker.loginmodule.view.TankerLoginActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("actionCode", 0);
        CommonObserver.startActivity(context, intent);

    }

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public interface NetListener {
        void onNetError();
    }


    class ObserverBuilder {

    }
}
