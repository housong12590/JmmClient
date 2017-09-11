package com.jmm.client.module.user.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jmm.client.R;
import com.jmm.client.http.ProgressObserver;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.common.utils.LogUtils;
import com.jmm.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_et_phone) EditText mEtUserName;
    @BindView(R.id.login_et_password) EditText mEtPassword;
    private String targetClass;

    @Override
    public void parseIntent(Intent intent) {
        targetClass = intent.getStringExtra("targetClass");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                String username = mEtUserName.getText().toString();
                String password = mEtPassword.getText().toString();
                login(username, password);
                break;
        }
    }

    public void login(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showShort("username not null");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("password not null");
            return;
        }
//        Observable<BaseResp> login = HttpUtils.getUserInfoService().login(new HashMap<>());
//        Observable.zip(login, emLogin(username, password), (baseResp, baseResp2) -> baseResp)
//                .compose(RxUtils.rxSchedulerHelper())
//                .subscribe(new BaseObserver<BaseResp>() {
//                    @Override
//                    public void onNext(@NonNull BaseResp baseResp) {
//
//                    }
//                });
        EMClient.getInstance().logout(true);
        emLogin(username, password).subscribe(new ProgressObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
//                Intent intent = new Intent();
//                intent.setClassName(mContext, targetClass);
//                startActivity(intent);
                finish();
            }
        });
    }

    private Observable<String> emLogin(String username, String password) {
        return Observable.create(e -> EMClient.getInstance()
                .login(username, password, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        e.onNext("");
                        e.onComplete();
                    }

                    @Override
                    public void onError(int i, String s) {
                        LogUtils.e(TAG, s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                }));
    }
}
