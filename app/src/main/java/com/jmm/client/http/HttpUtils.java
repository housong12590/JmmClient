package com.jmm.client.http;


import com.jmm.client.http.manager.RetrofitManager;
import com.jmm.client.http.service.UserInfoService;

public class HttpUtils {

    public static <T> T getService(Class<T> clazz) {
        return RetrofitManager.getInstance().getRetrofit().create(clazz);
    }

    public static UserInfoService getUserInfoService() {
        return getService(UserInfoService.class);
    }
}
