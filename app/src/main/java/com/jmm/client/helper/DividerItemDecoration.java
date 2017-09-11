package com.jmm.client.helper;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: 30453
 * Date: 2016/7/27 18:24
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private int mOrientation = LinearLayoutManager.VERTICAL;
    private float mItemSize = 0.8f;
    private Paint mPaint;
    private int dividerColor = Color.parseColor("#f1f1f1");
    private float margeSize = 0;

    public DividerItemDecoration(int margeSize) {
        this(margeSize, LinearLayoutManager.VERTICAL);
    }

    public DividerItemDecoration() {
        this(LinearLayoutManager.VERTICAL);
    }

    public DividerItemDecoration(int margeSize, int orientation) {
        this.margeSize = dp2px(margeSize);
        this.mOrientation = orientation;
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请传入正确的参数");
        }
//        mItemSize = (int) TypedValue.applyDimension(mItemSize, TypedValue.COMPLEX_UNIT_DIP, context.getResources().getDisplayMetrics());
        mItemSize = dp2px(mItemSize);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        float left = parent.getPaddingLeft() + margeSize;
        float right = parent.getMeasuredWidth() - parent.getPaddingRight() - margeSize;
        int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            float top = child.getBottom() + layoutParams.bottomMargin;
            float bottom = top + mItemSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        float top = parent.getPaddingTop();
        float bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            float left = child.getRight() + layoutParams.rightMargin;
            float right = left + mItemSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, (int) (mItemSize + 0.5f));
        } else {
            outRect.set(0, 0, (int) (mItemSize + 0.5f), 0);
        }
    }

    private float dp2px(float space) {
        return Resources.getSystem().getDisplayMetrics().density * space;
    }
}
