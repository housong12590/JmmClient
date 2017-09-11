package com.jmm.client.module.user.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jmm.client.R;

import java.util.List;

public class AddressManageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public AddressManageAdapter(@Nullable List<String> data) {
        super(R.layout.item_address_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.tv_delete)
                .addOnClickListener(R.id.tv_edit);
    }
}
