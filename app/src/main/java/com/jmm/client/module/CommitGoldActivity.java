package com.jmm.client.module;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmm.client.R;
import com.jmm.client.module.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 缴纳保证金
 */
public class CommitGoldActivity extends BaseActivity {

    @BindView(R.id.commit_iv_wxpay)
    ImageView mCommitIvWxpay;
    @BindView(R.id.commit_iv_alipay)
    ImageView mCommitIvAlipay;
    @BindView(R.id.commit_tv_payment)
    TextView mEvaluateTvPayment;

    Boolean mChoose = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_commit_gold;
    }

    @Override
    public void initView() {
        mCommitIvWxpay.setSelected(true);
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.commit_iv_wxpay, R.id.commit_iv_alipay, R.id.commit_tv_payment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.commit_iv_wxpay:
                if (mChoose) {
                } else {
                    mCommitIvWxpay.setSelected(true);
                    mCommitIvAlipay.setSelected(false);
                    mChoose = true;
                }
                break;
            case R.id.commit_iv_alipay:
                if (mChoose) {
                    mCommitIvAlipay.setSelected(true);
                    mCommitIvWxpay.setSelected(false);
                    mChoose = false;
                } else {
                }
                break;
            case R.id.commit_tv_payment:
                if (mChoose) {
                    //选择微信支付
                }else {
                    //选择支付宝支付
                }
                break;
        }
    }
}
