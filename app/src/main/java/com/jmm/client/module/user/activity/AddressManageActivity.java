package com.jmm.client.module.user.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.jmm.client.R;
import com.jmm.client.helper.SpaceItemDecoration;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.module.user.adapter.AddressManageAdapter;
import com.jmm.common.utils.ToastUtils;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressManageActivity extends BaseActivity {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private AddressManageAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_manage;
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        recyclerView.addOnItemTouchListener(new ItemClickListener());
        adapter = new AddressManageAdapter(Arrays.asList("", "", "", "", ""));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.tv_newAddress})
    public void onClick(View view) {
        startActivity(new Intent(this, NewAddressActivity.class));
    }

    private class ItemClickListener extends SimpleClickListener {

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            ToastUtils.showShort("" + position);
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

        }

        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            switch (view.getId()) {
                case R.id.tv_delete:

                    break;
                case R.id.tv_edit:
                    Intent intent = new Intent(mContext, NewAddressActivity.class);
                    intent.putExtra("", "");
                    startActivity(intent);
                    break;
            }
        }

        @Override
        public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

        }
    }
}
