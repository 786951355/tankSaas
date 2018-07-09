package com.tanker.basemodule.http.func;


import com.tanker.basemodule.http.ExceptionEngine;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by ronny on 2017/9/20.
 */

public class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(@NonNull Throwable throwable) {
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
