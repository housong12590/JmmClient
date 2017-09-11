package com.jmm.client.module.order.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jmm.client.module.base.adapter.BaseRVAdapter;
import com.jmm.client.module.base.fragment.BaseRVFragment;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/7/31/031.
 */

public class OrderListFragment extends BaseRVFragment<String> {

    @Override
    protected String getCurrentPage() {
        return null;
    }

    @Override
    protected Observable<List<String>> getApi(String currNum) {
        return null;
    }

    @Override
    protected BaseRVAdapter getRecyclerViewAdapter() {
        return null;
    }

    @Override
    protected void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

}
