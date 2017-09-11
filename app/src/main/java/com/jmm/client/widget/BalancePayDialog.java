package com.jmm.client.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jmm.client.R;
import com.jmm.common.utils.ToastUtils;

/**
 * Created by Administrator on 2017/8/9/009.
 * <p>
 * 余额支付Dialog
 */

public class BalancePayDialog extends Dialog {

    Context mContext;
    private PasswordEditText mPasswordEditText;
    int times = 2;
    private KeyboardView mKeyboardView;
    boolean flag = true;

    public BalancePayDialog(@NonNull Context context) {
        this(context, R.style.Theme_Light_NoTitle_Dialog);
        mContext = context;
        initView();
    }

    public BalancePayDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected BalancePayDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_balance_pay, null);
        this.setContentView(view);
        mPasswordEditText = (PasswordEditText) findViewById(R.id.dialog_payet);
        mKeyboardView = (KeyboardView) findViewById(R.id.pay_keyboard);
        mKeyboardView.bindEditText(mPasswordEditText);
        //监听密码输入
        mPasswordEditText.setOnTextChangedListener(s -> {
            if (s.toString().length() >= 6 && times > 0 && flag) {
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setMessage("数字密码输入错误，您还可以输入" + times + "次")
                        .setNegativeButton("重新输入",
                                (dialog1, which) -> {
                                    times--;
                                    mPasswordEditText.setText("");
                                }
                        )
                        .setPositiveButton("忘记密码",
                                (dialog12, whichButton) -> ToastUtils.showShort("忘记密码")
                        )
                        .create();
                dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
                dialog.show();
            } else if (s.toString().length() >= 6 && times <= 0) {
                ToastUtils.showShort("支付密码已经锁定,三小时后自动解锁");
                flag = false;
                dismiss();
            }
        });
        // 设置 dialog 位于屏幕底部，并且设置出入动画
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


}
