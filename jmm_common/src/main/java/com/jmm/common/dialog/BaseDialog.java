package com.jmm.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jmm.common.R;


public abstract class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.Base_Dialog);
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(getLayoutId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialogWidth();
    }

    private void initDialogWidth() {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        window.setDimAmount(0.6f); // 部分刷机会导致背景透明，这里保证一次
        WindowManager.LayoutParams wmlp = window.getAttributes();
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(wmlp);
    }

    public abstract int getLayoutId();

}
