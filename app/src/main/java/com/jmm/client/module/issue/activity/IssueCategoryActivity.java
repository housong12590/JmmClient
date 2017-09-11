package com.jmm.client.module.issue.activity;

import com.jmm.client.R;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.widget.FlowLayout;

import butterknife.BindView;

/**
 * 发布--分类
 */
public class IssueCategoryActivity extends BaseActivity {


    @BindView(R.id.cateory_flow)
    FlowLayout mCateoryFlow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_issue_category;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

}
