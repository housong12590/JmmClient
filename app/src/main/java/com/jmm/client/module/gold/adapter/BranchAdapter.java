package com.jmm.client.module.gold.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jmm.client.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18/018.
 */


public class BranchAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public BranchAdapter(@Nullable List<String> data) {
        super(R.layout.item_branch, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_branch_name, "北京银行顺义支行");
        helper.setText(R.id.tv_branch_city, "北京");
        helper.setText(R.id.tv_branch_distance, "100km");
    }
}
