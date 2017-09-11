package com.jmm.common.link;

import android.view.View;


public interface ITouchableSpan {
    void setPressed(boolean pressed);
    void onClick(View widget);
}
