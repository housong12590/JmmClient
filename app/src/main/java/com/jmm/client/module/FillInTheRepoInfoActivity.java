package com.jmm.client.module;

import android.view.View;

import com.jmm.client.R;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.widget.MediaSelectLayout;

import butterknife.BindView;

public class FillInTheRepoInfoActivity extends BaseActivity implements MediaSelectLayout.InitListener {

    @BindView(R.id.selectLayout) MediaSelectLayout selectLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fill_in_the_repo_info;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }

    @Override
    public void addImageClick() {

    }

    @Override
    public void addVideoClick() {

    }

    @Override
    public void imageItemClick(View view, int position) {

    }

    @Override
    public void videoItemClick(View view, int position) {

    }

    @Override
    public View getImageItemView(Object val, int position) {
        return null;
    }

    @Override
    public View getVideoItemView(Object val, int position) {
        return null;
    }
}
