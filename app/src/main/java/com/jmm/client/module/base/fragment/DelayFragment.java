package com.jmm.client.module.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * author：hs
 * date: 2017/6/9 0009 15:06
 */

public abstract class DelayFragment extends BaseFragment {

    private boolean isCreated;
    private boolean isVisibleToUser;
    private boolean hasLoaded;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        needDelayLoad = true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isCreated = true;
        delayLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
        delayLoad();
    }

    private void delayLoad() {
        if (isVisibleToUser && isCreated && !hasLoaded) {
            initData();
            hasLoaded = true;
        }
    }
}
