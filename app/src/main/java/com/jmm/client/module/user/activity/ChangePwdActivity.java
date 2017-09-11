package com.jmm.client.module.user.activity;


import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.jmm.client.R;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.module.user.fragment.AuthenticationFragment;

import butterknife.BindView;

public class ChangePwdActivity extends BaseActivity {


    @BindView(R.id.fragment_contain)
    FrameLayout mFragmentContain;
    private AuthenticationFragment mAuthenticationFragment;
    private FragmentTransaction mFragmentTransaction;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    public void initView() {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mAuthenticationFragment = new AuthenticationFragment();
        mFragmentTransaction.replace(R.id.fragment_contain,mAuthenticationFragment);
        mFragmentTransaction.commit();
    }

    @Override
    public void initData() {

    }
}
