package com.jmm.client.module.user.activity;

import android.widget.TextView;

import com.jmm.client.R;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.widget.KeyboardView;
import com.jmm.client.widget.PasswordEditText;
import com.jmm.client.widget.ToolBarLayout;
import com.jmm.common.utils.ToastUtils;

import butterknife.BindView;

/***
 * 设置和验证6位数字的支付密码
 */
public class SetPayPwdActivity extends BaseActivity {

    @BindView(R.id.jmmToolbar)
    ToolBarLayout mJmmToolbar;
    @BindView(R.id.et_pay)
    PasswordEditText mEtPay;
    @BindView(R.id.pay_keyboard)
    KeyboardView mPayKeyboard;
    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.tv_complete)
    TextView mTvComplete;
    String mPwd = "";
    String mNewPwd = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_pay_pwd;
    }

    @Override
    public void initView() {
        mTvComplete.setEnabled(false);
        mPayKeyboard.bindEditText(mEtPay);
        mPayKeyboard.setOnCompleteClickListener(() -> {
            if (mPwd.length() < 6) {
            } else {
                setNewPayPwd();
            }
        });
        mTvComplete.setOnClickListener(v -> {
            if (mPwd.length() >= 6)
                setNewPayPwd();
        });
    }

    @Override
    public void initData() {
        mEtPay.setOnTextChangedListener(s -> {
            mPwd = s;
            if (s.length() >= 6) {
                mTvComplete.setEnabled(true);
                mTvComplete.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_but_red));
            } else {
                mTvComplete.setEnabled(false);
                mTvComplete.setBackgroundColor(getResources().getColor(R.color.edit_hint));
            }
        });
    }

    //设置新支付密码
    private void setNewPayPwd() {
        ToastUtils.showShort(mPwd);
        gotoSetNewPwd();
    }

    private void gotoSetNewPwd() {
        mJmmToolbar.setTitle("设置新6位数字支付密码");
        mTvText.setText("设置新6位数字支付密码");
        mEtPay.setText("");
    }
}
