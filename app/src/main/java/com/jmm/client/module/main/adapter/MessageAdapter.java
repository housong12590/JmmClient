package com.jmm.client.module.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jmm.client.R;

import java.util.List;


public class MessageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MessageAdapter(@Nullable List<String> data) {
        super(R.layout.item_message_list_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item);
    }
}
