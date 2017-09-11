package com.jmm.client.module.user.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jmm.client.R;
import com.jmm.client.RxUtils;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tv_next)
    TextView mTvNext;
    @BindView(R.id.tv_regulations)
    TextView mTvRegulations;
    @BindView(R.id.et_password) EditText mEtPassword;
    @BindView(R.id.et_phone) EditText mEtPhone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        mTvRegulations.setTextColor(Color.BLUE);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tv_next, R.id.tv_regulations})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                String phone = mEtPhone.getText().toString();
                String password = mEtPassword.getText().toString();
                Observable.create(e -> {
                    try {
                        EMClient.getInstance().createAccount(phone, password);
                        e.onComplete();
                    } catch (HyphenateException e1) {
                        e.onError(e1);
                    }
                }).compose(RxUtils.rxSchedulerHelper()).subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object o) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtils.showShort("注册失败" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.showShort("注册成功");
                        EMClient.getInstance().login(phone, password, new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                try {
                                    EMClient.getInstance().contactManager().addContact("pss123546", "");
                                } catch (HyphenateException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(int i, String s) {

                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });
                        finish();
                    }
                });

                break;
            case R.id.tv_regulations:
                //隐私条例
                break;
        }
    }
}
