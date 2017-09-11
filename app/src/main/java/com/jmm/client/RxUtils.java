package com.jmm.client;

import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> rxClick(long milliseconds) {
        return observable -> observable
                .throttleFirst(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

}