package com.jmm.client.http.service;

import com.jmm.client.http.bean.BaseResp;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface UserInfoService {

    @FormUrlEncoded
    @POST("api/login")
    Observable<BaseResp> login(@FieldMap() Map<String, String> params);
}
