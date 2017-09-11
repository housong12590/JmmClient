package com.jmm.client.module.gold.activity;

import android.animation.Animator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.jmm.client.R;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.widget.ToolBarLayout;

import butterknife.BindView;

public class QrCodeActivity extends BaseActivity {

    @BindView(R.id.toolbar) ToolBarLayout mToolbar;
    private RelativeLayout mContent;
    private Animator mAnimator;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qr_code;
    }

    @Override
    public void initView() {
        mToolbar.setDefaultBackButton(v -> {
            onBackPressed();
            overridePendingTransition(0, 0);
        });
        // 获取根布局
        mContent = (RelativeLayout) findViewById(R.id.reveal_content);
        // 让根布局进行动画
        mContent.post(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int mX = getIntent().getIntExtra("x", 0);
                int mY = getIntent().getIntExtra("y", 0);
                mAnimator = createRevealAnimator(false, mX, mY);
                mAnimator.start();
            }
        });
    }

    @Override
    public void initData() {

    }

    // 动画
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Animator createRevealAnimator(boolean reversed, int x, int y) {
        float hypot = (float) Math.hypot(mContent.getHeight(), mContent.getWidth());
        float startRadius = reversed ? hypot : 0;
        float endRadius = reversed ? 0 : hypot;

        Animator animator = ViewAnimationUtils.createCircularReveal(
                mContent, x, y,
                startRadius,
                endRadius);
        animator.setDuration(600);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        if (reversed)
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        return animator;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(0, 0);
        }
        return super.onKeyDown(keyCode, event);
    }
}