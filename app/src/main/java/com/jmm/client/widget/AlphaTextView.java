package com.jmm.client.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jmm.client.helper.AlphaViewHelper;

public class AlphaTextView extends TextView {

    private AlphaViewHelper mAlphaViewHelper;

    public AlphaTextView(Context context) {
        super(context);
    }

    public AlphaTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private AlphaViewHelper getAlphaViewHelper() {
        if (mAlphaViewHelper == null) {
            mAlphaViewHelper = new AlphaViewHelper(this);
        }
        return mAlphaViewHelper;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        getAlphaViewHelper().onPressedChanged(this, pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        getAlphaViewHelper().onEnabledChanged(this, enabled);
    }

    /**
     * 设置是否要在 press 时改变透明度
     *
     * @param changeAlphaWhenPress 是否要在 press 时改变透明度
     */
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        getAlphaViewHelper().setChangeAlphaWhenPress(changeAlphaWhenPress);
    }

    /**
     * 设置是否要在 disabled 时改变透明度
     *
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        getAlphaViewHelper().setChangeAlphaWhenDisable(changeAlphaWhenDisable);
    }
}
