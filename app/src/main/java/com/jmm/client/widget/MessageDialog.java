package com.jmm.client.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jmm.client.R;
import com.jmm.common.utils.ScreenUtils;

public class MessageDialog extends Dialog {

    private TextView tvTitle;
    private TextView tvMessage;
    private TextView tvCancel;
    private TextView tvAffirm;

    public MessageDialog(@NonNull Context context) {
        this(context, R.style.comment_dialog);
    }

    public MessageDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_message_layout);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvMessage = (TextView) findViewById(R.id.tv_content);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvAffirm = (TextView) findViewById(R.id.tv_affirm);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        int screenWidth = ScreenUtils.getScreenWidth();
        params.width = (int) (screenWidth / 10 * 7.5);
        getWindow().setAttributes(params);
    }

    public MessageDialog setTitle(String text) {
        tvTitle.setText(text);
        return this;
    }

    public MessageDialog setMessage(String text) {
        tvMessage.setText(text);
        return this;
    }

    public MessageDialog setCancelButtonListener(String text, View.OnClickListener listener) {
        tvCancel.setText(text);
        tvCancel.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(v);
            }
            dismiss();
        });
        return this;
    }

    public MessageDialog setAffirmButtonListener(String text, View.OnClickListener listener) {
        tvAffirm.setText(text);
        tvAffirm.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(v);
            }
            dismiss();
        });
        return this;
    }
}
