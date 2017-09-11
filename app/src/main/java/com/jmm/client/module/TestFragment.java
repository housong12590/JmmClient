package com.jmm.client.module;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jmm.client.R;
import com.jmm.client.module.base.fragment.BaseFragment;
import com.jmm.client.http.bean.LoadStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/8/1/001.
 */

public class TestFragment extends BaseFragment {

    @BindView(R.id.test_rv)
    RecyclerView mTestRv;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    public void initView() {
        List a = new ArrayList();
        for (int i = 0; i < 50; i++) {
            a.add("1");
        }
        TestAdapter testAdapter = new TestAdapter(a);
        mTestRv.setAdapter(testAdapter);
        mTestRv.setLayoutManager(new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    protected void requestData(LoadStatus status) {

    }


    class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        public TestAdapter(@Nullable List<String> data) {
            super(R.layout.item_produc_message, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }
}
