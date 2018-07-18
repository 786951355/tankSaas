package com.tanker.basemodule.base;

import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.http.func.HttpResponseFunc;
import com.tanker.basemodule.http.func.ServerResponseFunc;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by ronny on 2018/3/28.
 */

public class BaseApi {
    /**
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<HttpResult<T>, T> handleResult(final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        return tObservable -> {
            Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                    lifecycleSubject.filter(activityLifeCycleEvent -> activityLifeCycleEvent.equals(event));

            return tObservable.map(new ServerResponseFunc<T>())
                    .takeUntil(compareLifecycleObservable)
                    .onErrorResumeNext(new HttpResponseFunc())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
        };
    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(e -> {
            try {
                e.onNext(data);
                e.onComplete();
            } catch (Exception exception) {
                e.onError(exception);
            }
        });
    }


}
