package com.jmm.client.widget;

import android.content.Context;
import android.util.AttributeSet;


public class CountDownButton extends android.support.v7.widget.AppCompatButton {

    public CountDownButton(Context context) {
        this(context, null);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }
}
