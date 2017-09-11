package com.jmm.client.module.user.fragment;

import android.graphics.Paint;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.jmm.client.R;
import com.jmm.client.module.base.fragment.BaseFragment;
import com.jmm.client.http.bean.LoadStatus;
import com.jmm.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 验证身份
 */

public class AuthenticationFragment extends BaseFragment {

    @BindView(R.id.tv_voice)
    TextView mTvVoice;
    @BindView(R.id.tv_next)
    TextView mTvNext;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_authentication;
    }

    @Override
    public void initView() {
        mTvVoice.setTextColor(0xff75B1DE);
        mTvVoice.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected void requestData(LoadStatus status) {

    }

    @OnClick({R.id.tv_voice, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_voice:
                ToastUtils.showShort("语音验证");
                break;
            case R.id.tv_next:
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_contain,new SetPwdFragment());
                fragmentTransaction.commit();
                break;
        }
    }
}


