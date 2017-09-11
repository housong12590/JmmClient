package com.jmm.client.http.manager;


import com.jmm.client.config.AppConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static RetrofitManager mInstance;

    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL)
            .client(HttpClientManager.getInstance().getHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    private RetrofitManager() {

    }

    public static RetrofitManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
