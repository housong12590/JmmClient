package com.jmm.client.module.order.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jmm.client.R;
import com.jmm.client.module.base.adapter.BaseRVAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31/031.
 */

public class OrderAdapter extends BaseRVAdapter<String> {

    public OrderAdapter(List<String> data) {
        super(R.layout.item_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
    }

}
