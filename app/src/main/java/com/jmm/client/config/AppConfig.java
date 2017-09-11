package com.jmm.client.config;


public final class AppConfig {

    public static boolean isLogin = true;

    public static boolean isRealName = true;

    public static final RouteConfig ROUTE_CONFIG = RouteConfig.TEST;

    public static final String BASE_URL = ROUTE_CONFIG.getBaseHttpUrl();



}
