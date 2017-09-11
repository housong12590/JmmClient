package com.jmm.client.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/8/31/031.
 */


public class MonitorScrollView extends ScrollView {
    ScrollChangerListener mScrollChangerListener;

    public MonitorScrollView(Context context) {
        super(context);
    }

    public MonitorScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonitorScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollChangerListener(ScrollChangerListener mScrollChangerListener) {
        this.mScrollChangerListener = mScrollChangerListener;
    }

    public interface ScrollChangerListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollChangerListener != null) {
            mScrollChangerListener.onScrollChanged(l, t, oldl, oldl);
        }
    }
}
