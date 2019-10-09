package com.ez08.trade.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ez08.trade.R;


public class LinearItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDividerVer;

    private final Rect mBounds = new Rect();

    public LinearItemDecoration(Context context) {
        this.mDividerVer = ContextCompat.getDrawable(context, R.drawable.line_light_1px);
    }

    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left;
        int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            c.clipRect(left, parent.getPaddingTop(), right, parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View view = parent.getChildAt(i);
            drawHorizontal(c, parent, view, left, right);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent, View child, int left, int right) {
        canvas.save();
        parent.getLayoutManager().getDecoratedBoundsWithMargins(child, this.mBounds);
        int bottom = this.mBounds.bottom;
        int top = bottom - this.mDividerVer.getIntrinsicHeight();
        this.mDividerVer.setBounds(left, top, right, bottom);
        this.mDividerVer.draw(canvas);
        canvas.restore();
    }


    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, 1);
    }
}