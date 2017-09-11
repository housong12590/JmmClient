package com.jmm.client.module.base.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jmm.client.config.AppConfig;
import com.jmm.client.module.TestActivity;
import com.jmm.client.module.base.impl.BaseView;
import com.jmm.client.module.user.activity.LoginActivity;
import com.jmm.client.module.user.activity.RealNameActivity;
import com.jmm.client.widget.NetworkStateView;
import com.jmm.common.utils.ActivityUtils;
import com.jmm.common.utils.LogUtils;
import com.luck.picture.lib.rxbus2.RxBus;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author：hs
 * date: 2017/6/8 0008 14:14
 */

public abstract class BaseActivity extends RxAppCompatActivity implements BaseView {

    private Unbinder bind;
    private NetworkStateView networkStateView;
    protected AppCompatActivity mContext;
    protected String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        setContentView(getLayoutId());
        RxBus.getDefault().register(this);
        this.mContext = this;
        parseIntent(getIntent());
        bind = ButterKnife.bind(this);
        ActivityUtils.addActivity(this);
        bindNetworkLoadingView();
        initView();
        initData();
    }


    public void parseIntent(Intent intent) {

    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void showLoading() {
        if (networkStateView != null) {
            networkStateView.showLoadingView();
        }
    }

    @Override
    public void showEmpty() {
        if (networkStateView != null) {
            networkStateView.showEmptyView();
        }
    }

    @Override
    public void showError() {
        if (networkStateView != null) {
            networkStateView.showErrorView();
        }
    }

    @Override
    public void showContent() {
        if (networkStateView != null) {
            networkStateView.showContentView();
        }
    }

    private void bindNetworkLoadingView() {
        View view = getNetworkLoadingView();
        if (view != null) {
            networkStateView = new NetworkStateView(view);
        }
    }

    public View getNetworkLoadingView() {
        return null;
    }


    @Override
    public void startActivity(Intent intent) {
        if (intent.getComponent() == null) {
            super.startActivity(intent);
            return;
        }
        String className = intent.getComponent().getClassName();
        if (AppConfig.isLogin) {
            if (needRealNameClassList.contains(className) && !AppConfig.isRealName) {
                intent.setClass(this, RealNameActivity.class);
                intent.putExtra("targetClass", className);
                super.startActivity(intent);
            } else {
                super.startActivity(intent);
            }
        } else {
            if (filterLoginClassList.contains(className)) {
                super.startActivity(intent);
            } else {
                LogUtils.d("targetClass", className);
                intent.setClass(this, LoginActivity.class);
                intent.putExtra("targetClass", className);
                super.startActivity(intent);
            }
        }
    }

    private static List<String> filterLoginClassList = Arrays.asList(
            LoginActivity.class.getName(),
            BaseTitleBarActivity.class.getName());

    private static List<String> needRealNameClassList = Arrays.asList(
            TestActivity.class.getName(),
            "");


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.removeActivity(this);
        bind.unbind();
        RxBus.getDefault().unregister(this);
    }

}
