package com.jmm.client.module.order.activity;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.jmm.client.R;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.widget.BalancePayDialog;
import com.jmm.client.widget.ToolBarLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 支付中心
 */
public class PayActivity extends BaseActivity {

    @BindView(R.id.pay_price)
    TextView mPayPrice;
    @BindView(R.id.tv_alipay)
    TextView mTvAlipay;
    @BindView(R.id.tv_wx_pay)
    TextView mTvWxPay;
    @BindView(R.id.tv_bank_pay)
    TextView mTvBankPay;
    @BindView(R.id.toolbar)
    ToolBarLayout mToolbar;
    @BindView(R.id.tv_yue)
    TextView mTvYue;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initView() {
        String s = "¥：610.00";
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new AbsoluteSizeSpan(20, true), 2, s.length() - 3,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mPayPrice.setText(spannableString);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tv_alipay, R.id.tv_wx_pay, R.id.tv_bank_pay, R.id.tv_yue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_alipay:
                break;
            case R.id.tv_wx_pay:
                break;
            case R.id.tv_bank_pay:
                break;
            case R.id.tv_yue:
                BalancePayDialog dialog = new BalancePayDialog(this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;
        }
    }
}
