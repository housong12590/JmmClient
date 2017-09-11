package com.jmm.client.module;

import com.jmm.client.R;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.widget.ToolBarLayout;
import com.jmm.common.utils.ToastUtils;
import com.jmm.common.weiget.LinkTextView;

import butterknife.BindView;

public class TestActivity extends BaseActivity {

    @BindView(R.id.toolbar) ToolBarLayout toolbar;
    @BindView(R.id.linkTextView) LinkTextView linkTextView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {
       toolbar.addRightImageButton(R.drawable.icon_delete,R.id.menu_share);
        linkTextView.setOnLinkClickListener(new LinkTextView.OnLinkClickListener() {
            @Override
            public void onTelLinkClick(String phoneNumber) {
                ToastUtils.showShort(phoneNumber);
            }

            @Override
            public void onMailLinkClick(String mailAddress) {
                ToastUtils.showShort(mailAddress);
            }

            @Override
            public void onWebUrlLinkClick(String url) {
                ToastUtils.showShort(url);
            }
        });
    }

    @Override
    public void initData() {
    }
}
