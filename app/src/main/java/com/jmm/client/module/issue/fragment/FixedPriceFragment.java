package com.jmm.client.module.issue.fragment;

import android.content.Intent;

import com.jmm.client.R;
import com.jmm.client.module.issue.activity.IssueCategoryActivity;
import com.jmm.client.module.base.fragment.BaseFragment;
import com.jmm.client.http.bean.LoadStatus;

import butterknife.OnClick;


public class FixedPriceFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fixed_price;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void requestData(LoadStatus status) {

    }


    @OnClick(R.id.tv_select_category)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), IssueCategoryActivity.class));
    }
}
