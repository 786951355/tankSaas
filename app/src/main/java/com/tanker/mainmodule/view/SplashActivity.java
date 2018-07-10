package com.tanker.mainmodule.view;

import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.orhanobut.hawk.Hawk;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.common.SaasApp;
import com.tanker.basemodule.model.app_model.SplashPicModel;
import com.tanker.basemodule.router.ReflectUtils;
import com.tanker.mainmodule.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    RelativeLayout rlBg;
    Button skip;
    private Disposable disposable;
    private boolean hasSkip = false;


    @Override
    public void onClick(View view) {
        skipSplash();
    }

    private void skipSplash() {
        if (!hasSkip) {
            if (SaasApp.getInstance().getUserManager().getUser() != null) {
                navigationTo(MainActivity.class);
            } else {
                if (SaasApp.getInstance().getUserManager().getUser() != null
                        && SaasApp.getInstance().getUserManager().getUser().getUserId() != null) {
                    ReflectUtils.navigationToHome(this, 0);
                } else {
                    ReflectUtils.startActivityWithName(this, "com.tanker.loginmodule.view.TankerLoginActivity");
                }
            }
            hasSkip = true;
        }
        finish();
    }

    /**
     * 返回键不可用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setToolbarVisible(false);
    }

    @Override
    public void initView() {
        rlBg = findViewById(R.id.rl_bg);
        skip = findViewById(R.id.skip);
        skip.setOnClickListener(this);

        Glide.with(this)
                .load(((SplashPicModel) Hawk.get(AppConstants.HAWK_SPLASH_IMG)).getPictureSrc())
                .listener(new RequestListener<Drawable>() { //防止图片路径404启动页停留
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        skipSplash();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        rlBg.setBackground(resource);
                    }
                });

    }

    @Override
    protected void initData() {
        final int count_time = 5;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(count_time + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) {
                        return count_time - aLong;
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Long aLong) {
                Logger.d("onNext" + aLong);
                skip.setText("点击跳过(" + aLong + "S)");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                skip.setText("点击跳过");
                skipSplash();
            }
        });
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}

