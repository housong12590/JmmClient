package com.jmm.common.link;

import android.text.Spannable;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @author cginechen
 * @date 2017-03-20
 */

public class ScrollingMovementMethod extends android.text.method.ScrollingMovementMethod {

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        return sHelper.onTouchEvent(widget, buffer, event)
                || Touch.onTouchEvent(widget, buffer, event);
    }

    public static MovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new ScrollingMovementMethod();

        return sInstance;
    }

    private static ScrollingMovementMethod sInstance;
    private static LinkTouchDecorHelper sHelper = new LinkTouchDecorHelper();
}
