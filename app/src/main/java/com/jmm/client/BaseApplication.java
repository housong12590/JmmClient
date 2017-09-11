package com.jmm.client;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.jmm.common.utils.SPUtils;
import com.jmm.common.utils.Utils;

/**
 * author：hs
 * date: 2017/6/16 0016 18:08
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //是否开启非WIFI环境下手动加载图片  默认关闭
        Utils.init(this);
        SPUtils.getInstance().put("isWifi", false);
        //初始化环信SDK
        initEM();
    }

    private void initEM() {
        EaseUI.getInstance().init(this, new EMOptions());
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

