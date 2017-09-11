package com.jmm.client.http.manager;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClientManager {


    private static final int TIME_OUT = 30 * 1000;
    private static HttpClientManager mInstance;

    private OkHttpClient mHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .build();

    private HttpClientManager() {

    }

    static HttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpClientManager();
                }
            }
        }
        return mInstance;
    }

    OkHttpClient getHttpClient() {
        return mHttpClient;
    }
}
