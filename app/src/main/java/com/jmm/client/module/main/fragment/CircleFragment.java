package com.jmm.client.module.main.fragment;

import android.content.Intent;
import android.view.View;

import com.jmm.client.R;
import com.jmm.client.http.bean.LoadStatus;
import com.jmm.client.module.CommitGoldActivity;
import com.jmm.client.module.base.fragment.DelayFragment;
import com.jmm.client.module.gold.activity.RepurchaseGoldActivity;
import com.jmm.client.module.issue.activity.ProducDetailstActivity;
import com.jmm.client.module.main.activity.SearchActivity;
import com.jmm.client.module.order.activity.EvaluateActivity;
import com.jmm.client.module.order.activity.PayActivity;
import com.jmm.client.module.setting.FeedbackActivity;
import com.jmm.client.module.user.activity.AddressManageActivity;
import com.jmm.client.module.user.activity.LoginActivity;
import com.jmm.client.module.user.activity.RealNameActivity;
import com.jmm.client.module.user.activity.RegisterActivity;
import com.jmm.client.module.user.activity.SetPayPwdActivity;
import com.jmm.client.module.user.activity.SplashActivity;

import butterknife.OnClick;


public class CircleFragment extends DelayFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_circle;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        System.out.println("CircleFragment");
    }

    @Override
    protected void requestData(LoadStatus status) {

    }


    @OnClick({R.id.but_commit, R.id.but_evaluate, R.id.but_feedback, R.id.but_pay, R.id.but_search,
            R.id.but_realname, R.id.but_issue, R.id.but_address, R.id.but_splash, R.id.but_pd , R.id.but_rs
    ,R.id.but_returns,R.id.but_gold})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.but_commit:
                getContext().startActivity(new Intent(getContext(), CommitGoldActivity.class));
                break;
            case R.id.but_evaluate:
                getContext().startActivity(new Intent(getContext(), EvaluateActivity.class));
                break;
            case R.id.but_feedback:
                getContext().startActivity(new Intent(getContext(), FeedbackActivity.class));
                break;
            case R.id.but_pay:
                getContext().startActivity(new Intent(getContext(), PayActivity.class));
                break;
            case R.id.but_search:
                getContext().startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.but_realname:
                getContext().startActivity(new Intent(getContext(), RealNameActivity.class));
                break;
            case R.id.but_issue:
                getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.but_address:
                startActivity(new Intent(getActivity(), AddressManageActivity.class));
                break;
            case R.id.but_splash:
                getContext().startActivity(new Intent(getContext(), SplashActivity.class));
                break;
            case R.id.but_pd:
                getContext().startActivity(new Intent(getContext(), ProducDetailstActivity.class));
                break;
            case R.id.but_rs:
                getContext().startActivity(new Intent(getContext(), RegisterActivity.class));
                break;
            case R.id.but_returns:
                getContext().startActivity(new Intent(getContext(), SetPayPwdActivity.class));
                break;
            case R.id.but_gold:
                getContext().startActivity(new Intent(getContext(), RepurchaseGoldActivity.class));
                break;
        }
    }
}
