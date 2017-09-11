package com.jmm.client.module.order.activity;

import android.widget.RatingBar;
import android.widget.TextView;

import com.jmm.client.R;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.widget.ToolBarLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心--我卖出的--评价
 */
public class EvaluateActivity extends BaseActivity {

    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;
    @BindView(R.id.evaluate_tv_issue)
    TextView mEvaluateTvIssue;
    @BindView(R.id.toolbar)
    ToolBarLayout mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_evaluate;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.evaluate_tv_issue)
    public void onViewClicked() {
    }
}
