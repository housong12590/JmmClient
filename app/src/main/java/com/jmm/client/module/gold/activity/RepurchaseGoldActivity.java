package com.jmm.client.module.gold.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jmm.client.R;
import com.jmm.client.helper.DividerItemDecoration;
import com.jmm.client.helper.LocationHelper;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.module.gold.adapter.BranchAdapter;
import com.jmm.client.widget.ToolBarLayout;
import com.jmm.common.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 黄金回购
 */
public class RepurchaseGoldActivity extends BaseActivity {


    boolean flag = true;
    TextView tvMenu;
    @BindView(R.id.toolbar)
    ToolBarLayout mToolbar;
    @BindView(R.id.rv_branch)
    RecyclerView mRvBranch;

    @Override
    public int getLayoutId() {
        return R.layout.activity_repurchase_gold;
    }

    @Override
    public void initView() {
        mToolbar.addRightTextButton("", R.id.menu_text);
        tvMenu = mToolbar.getView(R.id.menu_text);
        tvMenu.setTextColor(getResources().getColor(R.color.yellow_ff));
        if (flag)
            tvMenu.setText("北京");
        ArrayList<String> alist = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            alist.add(i + 100 + "");
        }
        BranchAdapter branchAdapter = new BranchAdapter(alist);
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_branch, null);
        TextView viewById = (TextView) inflate.findViewById(R.id.tv_branch_name);
        viewById.setCompoundDrawables(null, null, null, null);
        branchAdapter.addHeaderView(inflate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvBranch.setLayoutManager(layoutManager);
        mRvBranch.addItemDecoration(new DividerItemDecoration());
        mRvBranch.setAdapter(branchAdapter);
        mRvBranch.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String o = adapter.getData().get(position).toString();
                ToastUtils.showShort(o);
                startActivity(new Intent(RepurchaseGoldActivity.this, RepurchaseInfoActivity.class));
            }
        });
    }

    @Override
    public void initData() {
        tvMenu.setOnClickListener(v -> LocationHelper.getInstance().initLocationHelper(RepurchaseGoldActivity.this)
                .setLocationListener(location -> {
                    if (location.getErrorCode() == 0) {
                        String cityName = location.getCity().toString();
                        tvMenu.setText(cityName.substring(0, cityName.length() - 1));
                        flag = false;
                    } else {
                        ToastUtils.showShort("定位失败.是否未开启网络或者定位权限未给......");
                    }
                }));
    }
}
