package com.jmm.client.module.base.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmm.client.module.base.impl.BaseView;
import com.jmm.client.module.user.activity.LoginActivity;
import com.jmm.client.http.bean.LoadStatus;
import com.jmm.client.config.AppConfig;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author：hs
 * date: 2017/6/8 0008 14:28
 */

public abstract class BaseFragment extends RxFragment implements BaseView {

    protected Context mContext;
    protected boolean needDelayLoad;
    private Unbinder bind;
    protected String TAG = getClass().getSimpleName();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            parseIntent(bundle);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        bind = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!needDelayLoad) {
            initData();
        }
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public void parseIntent(Bundle bundle) {

    }

    public void initData() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    @Override
    public void startActivity(Intent intent) {
        if (AppConfig.isLogin) {
            super.startActivity(intent);
        } else {
            ComponentName name = intent.getComponent();
            String className = name.getClassName();
            intent.setClass(getActivity(), LoginActivity.class);
            intent.putExtra("targetClass", className);
            super.startActivity(intent);
        }
    }

    protected abstract void requestData(LoadStatus status);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
