package com.jmm.client.http;

import com.jmm.client.http.bean.BaseResp;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class BaseRespFunc<T> implements Function<BaseResp<T>, Observable<T>> {

    @Override
    public Observable<T> apply(@NonNull BaseResp<T> resp) throws Exception {
        if (!resp.getCode().equals("200")) {
            return Observable.error(new Exception(resp.getMessage()));
        } else {
            return Observable.just(resp.getData());
        }
    }
}

