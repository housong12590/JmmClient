package com.jmm.client.module.base.impl;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * author：hs
 * date: 2017/6/8 0008 14:15
 */

public interface BaseView {

    void showDialog();

    void dismissDialog();

    void showLoading();

    void showError();

    void showContent();

    void showEmpty();

    /**
     * 绑定生命周期
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();
}
