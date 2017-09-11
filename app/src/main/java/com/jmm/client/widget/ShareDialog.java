package com.jmm.client.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jmm.client.R;
import com.jmm.common.utils.ToastUtils;


/**
 * Created by Administrator on 2017/8/17/017.
 * 分享dialog
 */

public class ShareDialog extends Dialog implements View.OnClickListener {

    //传入的分享链接
    String url = "";

    Context mContext;

    public ShareDialog(@NonNull Context context, String url) {
        this(context, R.style.Theme_Light_NoTitle_Dialog);
        this.url = url;
        mContext = context;
        initView();
    }

    public ShareDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected ShareDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_share, null);
        this.setContentView(view);
        view.findViewById(R.id.iv_dismiss).setOnClickListener(this);
        view.findViewById(R.id.tv_share_wx).setOnClickListener(this);
        view.findViewById(R.id.tv_share_wx_moments).setOnClickListener(this);
        view.findViewById(R.id.tv_share_qq).setOnClickListener(this);
        view.findViewById(R.id.tv_share_qzone).setOnClickListener(this);
        view.findViewById(R.id.tv_share_weibo).setOnClickListener(this);
        view.findViewById(R.id.tv_share_alipay).setOnClickListener(this);
        setBottomLayout();
    }

    private void setBottomLayout() {
        Window win = getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        // dialog 布局位于底部
        win.setGravity(Gravity.BOTTOM);
        // 设置进出场动画
        win.setWindowAnimations(R.style.Animation_Bottom);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dismiss:
                dismiss();
                break;
            case R.id.tv_share_wx:
                ToastUtils.showShort("wx" + url);
                break;
            case R.id.tv_share_wx_moments:
                ToastUtils.showShort("wx_moments + url");
                break;
            case R.id.tv_share_qq:
                ToastUtils.showShort("qq + url");
                break;
            case R.id.tv_share_qzone:
                ToastUtils.showShort("qzone + url");
                break;
            case R.id.tv_share_weibo:
                ToastUtils.showShort("weibo + url");
                break;
            case R.id.tv_share_alipay:
                ToastUtils.showShort("alipay + url");
                break;
        }
    }
}
