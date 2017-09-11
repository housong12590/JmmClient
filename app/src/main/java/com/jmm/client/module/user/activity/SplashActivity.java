package com.jmm.client.module.user.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.hyphenate.chat.EMClient;
import com.jmm.client.R;
import com.jmm.client.helper.EMChatHelper;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.module.main.activity.MainActivity;
import com.jmm.client.widget.CountDownView;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.sp_bg) ImageView mSpBg;
    @BindView(R.id.countDownView) CountDownView countDownView;

    @Override

    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        countDownView.start();
        countDownView.setOnLoadingFinishListener(this::goToActivity);
    }

    @Override
    public void initData() {
        //加载所有对话
        if (EMChatHelper.getInstance().isLogin()) {
            EMClient.getInstance().chatManager().loadAllConversations();
        }
    }

    @OnClick({R.id.sp_bg, R.id.countDownView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sp_bg:
                break;
            case R.id.countDownView:
                countDownView.stop();
                break;
        }
    }

//    private CountDownTimer countDownTimer = new CountDownTimer(3200, 1000) {
//        @Override
//        public void onTick(long millisUntilFinished) {
//            mSpJumpBtn.setText("跳过(" + millisUntilFinished / 1000 + "s)");
//        }
//
//        @Override
//        public void onFinish() {
//            mSpJumpBtn.setText("跳过(" + 0 + "s)");
//            goToActivity();
//        }
//    };

    private void goToActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(0, 0);
    }

//    private void startClock() {
//        mSpJumpBtn.setVisibility(View.VISIBLE);
//        countDownTimer.start();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownView != null) {
            countDownView.stop();
        }
//        if (countDownTimer != null)
//            countDownTimer.cancel();
    }
}
