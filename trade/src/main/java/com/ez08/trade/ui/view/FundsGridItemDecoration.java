package com.ez08.trade.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ez08.trade.R;
import com.ez08.trade.ui.trade.holder.TradeFundsHolder;

public class FundsGridItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDividerVer;

    private final Rect mBounds = new Rect();

    public FundsGridItemDecoration(Context context) {
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
            if (parent.getChildViewHolder(view) instanceof TradeFundsHolder) {

            } else {
                if (i == childCount - 1) {
                    drawTop(c, parent, view, left, right);
                } else {
                    drawHorizontal(c, parent, view, left, right);
                }

            }
        }
    }

    private void drawTop(Canvas canvas, RecyclerView parent, View child, int left, int right) {
        canvas.save();
        parent.getLayoutManager().getDecoratedBoundsWithMargins(child, this.mBounds);
        int bottom1 = this.mBounds.bottom;
        int top1 = bottom1 - this.mDividerVer.getIntrinsicHeight();
        this.mDividerVer.setBounds(left, top1, right, bottom1);
        this.mDividerVer.draw(canvas);

        int bottom2 = this.mBounds.top;
        int top2 = bottom2 - this.mDividerVer.getIntrinsicHeight();
        this.mDividerVer.setBounds(left, top2, right, bottom2);
        this.mDividerVer.draw(canvas);
        canvas.restore();
    }


    private void drawHorizontal(Canvas canvas, RecyclerView parent, View child, int left, int right) {
        canvas.save();
        parent.getLayoutManager().getDecoratedBoundsWithMargins(child, this.mBounds);
        int bottom2 = this.mBounds.top;
        int top2 = bottom2 - this.mDividerVer.getIntrinsicHeight();
        this.mDividerVer.setBounds(left, top2, right, bottom2);
        this.mDividerVer.draw(canvas);
        canvas.restore();
    }


    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 1, 0, 1);
    }
}