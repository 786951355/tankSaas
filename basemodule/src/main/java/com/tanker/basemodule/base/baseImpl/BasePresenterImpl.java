package com.tanker.basemodule.base.baseImpl;


import com.tanker.basemodule.base.ActivityLifeCycleEvent;
import com.tanker.basemodule.base.BaseApi;
import com.tanker.basemodule.base.BasePresenter;
import com.tanker.basemodule.base.BaseView;
import com.tanker.basemodule.http.CommonObserver;
import com.tanker.basemodule.http.RetrofitCache;
import com.tanker.basemodule.http.api.HttpResult;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by :luore
 * Date on :2017/12/19 22:38
 * Describe :TODO
 */

public abstract class BasePresenterImpl<T extends BaseView> implements BasePresenter {

    public T mView;
    private CompositeDisposable compositeDisposable;
    protected PublishSubject<ActivityLifeCycleEvent> lifecycleSubject;


    public BasePresenterImpl(T view) {
        this.mView = view;
        compositeDisposable = new CompositeDisposable();
        start();
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    /**
     * 添加线程管理并订阅
     *
     * @param ob
     * @param subscriber
     * @param cacheKey         缓存kay
     * @param event            Activity 生命周期
     * @param lifecycleSubject
     * @param isSave           是否缓存
     * @param forceRefresh     是否强制刷新
     */
    public void toSubscribe(Observable ob, final CommonObserver subscriber, String cacheKey, final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject, boolean isSave, boolean forceRefresh) {
        //数据预处理
        ObservableTransformer<HttpResult<Object>, Object> result = BaseApi.handleResult(event, lifecycleSubject);
        Observable observable = ob.compose(result)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        addDisposable(disposable);
//                        subscriber.showProgress();
                    }
                });
        RetrofitCache.load(cacheKey, observable, isSave, forceRefresh).subscribe(subscriber);
    }

    protected void toSubscribe(Observable observable, CommonObserver subscriber, String loginKey, ActivityLifeCycleEvent event, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject, boolean isSave) {
        toSubscribe(observable, subscriber, loginKey, event, lifecycleSubject, isSave, true);
    }

    protected void toSubscribe(Observable observable, CommonObserver subscriber, String loginKey, boolean isSave) {
        toSubscribe(observable, subscriber, loginKey, ActivityLifeCycleEvent.DESTROY, lifecycleSubject, isSave, true);
    }

    protected void toSubscribe(Observable observable, CommonObserver subscriber, boolean isSave) {
        toSubscribe(observable, subscriber, "TempKey", ActivityLifeCycleEvent.DESTROY, lifecycleSubject, isSave, true);
    }


    protected void toSubscribe(Observable observable, CommonObserver subscriber) {
        toSubscribe(observable, subscriber, "TempKey", ActivityLifeCycleEvent.DESTROY, lifecycleSubject, false, true);
    }

    @Override
    public void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        if (mView != null) {
            mView = null;
        }
    }

    @Override
    public void start() {
        lifecycleSubject = mView.getLifecycleSubject();
    }
}
