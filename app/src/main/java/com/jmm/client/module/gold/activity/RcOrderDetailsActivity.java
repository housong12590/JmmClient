package com.jmm.client.module.gold.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.jmm.client.R;
import com.jmm.client.image.bean.MediaItem;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.module.gold.adapter.OutlineImagAdapter;
import com.jmm.client.widget.NoScrollRecyclerView;
import com.jmm.common.utils.ToastUtils;
import com.luck.picture.lib.rxbus2.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class RcOrderDetailsActivity extends BaseActivity {

    @BindView(R.id.rv_outline)
    NoScrollRecyclerView mRvOutline;
    @BindView(R.id.tv_grcode)
    TextView mTvGrcode;
    @BindView(R.id.tv_close_order)
    TextView mTvCloseOrder;
    List<MediaItem> mList = new ArrayList<>();
    private OutlineImagAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rc_order_details;
    }

    @Override
    public void initView() {
        mTvGrcode.setOnTouchListener((v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_UP:
                    Intent intent = new Intent(RcOrderDetailsActivity.this, GrCodeActivity.class);
                    intent.putExtra("x", (int) event.getRawX());
                    intent.putExtra("y", (int) event.getRawY());
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
            }
            return true;
        });
    }


    @Override
    public void initData() {
        RxBus.getDefault().toObservableSticky(ArrayList.class).subscribe(list -> {
            ToastUtils.showLong(list.toString());
            mList = list;
        });
        initRv(mList);
    }

    private void initRv(List list) {
        mAdapter = new OutlineImagAdapter(list, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRvOutline.setLayoutManager(layoutManager);
        mRvOutline.setAdapter(mAdapter);
    }

    @OnClick({R.id.tv_close_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_close_order:
                new AlertDialog.Builder(mContext)
                        .setTitle("关闭订单")
                        .setMessage("确定是否关闭此订单?")
                        .setPositiveButton("确定", (dialog, which) -> {
                            //关闭订单
                            dialog.dismiss();
                        })
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .show();
                break;
        }
    }
}
