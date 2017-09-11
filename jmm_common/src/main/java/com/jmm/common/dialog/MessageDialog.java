package com.jmm.common.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;


public class MessageDialog extends BaseDialog {

    public MessageDialog(@NonNull Context context) {
        super(context);
    }

    public MessageDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }
}
