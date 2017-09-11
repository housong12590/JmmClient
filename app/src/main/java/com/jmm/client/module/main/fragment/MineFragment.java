package com.jmm.client.module.main.fragment;

import android.content.Intent;
import android.view.View;

import com.jmm.client.R;
import com.jmm.client.module.setting.FeedbackActivity;
import com.jmm.client.module.order.activity.OrderActivity;
import com.jmm.client.module.base.fragment.DelayFragment;
import com.jmm.client.http.bean.LoadStatus;

import butterknife.OnClick;


public class MineFragment extends DelayFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void requestData(LoadStatus status) {

    }

    @OnClick({R.id.tv_issue, R.id.tv_sale, R.id.tv_buy, R.id.tv_attention,
            R.id.tv_order, R.id.tv_act_manage, R.id.tv_feedback,R.id.tv_returns_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_issue://我发布的

                break;
            case R.id.tv_sale://我卖出的

                break;
            case R.id.tv_buy://我买到的

                break;
            case R.id.tv_attention://我关注的

                break;
            case R.id.tv_order://我的订单
                startActivity(new Intent(getActivity(), OrderActivity.class));
                break;
            case R.id.tv_act_manage://活动管理

                break;
            case R.id.tv_feedback://帮助与反馈
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.tv_returns_order://回购订单
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
        }
    }
}
