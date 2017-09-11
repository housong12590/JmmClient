package com.jmm.client.module.gold;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jmm.client.R;
import com.jmm.common.utils.DensityUtils;

/**
 * Created by Administrator on 2017/9/1/001.
 */

public class RvItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint mPaint;
    private final Paint mTextPaint;
    private Context mContext;

    public RvItemDecoration(Context mContext) {
        this.mContext = mContext;
        mPaint = new Paint();
        mPaint.setColor(mContext.getResources().getColor(R.color.edit_bg));
        mPaint.setStyle(Paint.Style.FILL);
        mTextPaint = new Paint();
        mTextPaint.setColor(mContext.getResources().getColor(R.color.red));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(DensityUtils.dp2px(mContext, 14));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        if (pos != 0) {
            outRect.top = 120;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int mDivider = 120;
            int left = parent.getPaddingLeft();
            // ItemView的下边界：ItemView 的 bottom坐标 + 距离RecyclerView底部距离 +translationY
            int right = parent.getWidth() - parent.getPaddingRight();
            int bottom = child.getTop();
            int top = bottom - mDivider;
            c.drawRect(left, top, right, bottom, mPaint);
            c.drawText("~~~~ 详情 ~~~~", (right - left) / 2, bottom - mTextPaint.descent() - mDivider / 2 + DensityUtils.dp2px(mContext,14) / 2 , mTextPaint);
        }
    }


}
