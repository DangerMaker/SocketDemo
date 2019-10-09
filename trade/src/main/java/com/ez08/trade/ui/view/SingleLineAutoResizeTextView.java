package com.ez08.trade.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.ez08.trade.R;

public class SingleLineAutoResizeTextView extends AppCompatTextView {
    private String textContent;
    private int textSize;
    private int textColor;
    private Paint paint;

    private float textWidth;
    private float textHeight;

    int gravity = 0; //1靠右


    public SingleLineAutoResizeTextView(Context context) {
        this(context, null);
    }

    public SingleLineAutoResizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleLineAutoResizeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.SingleLineTextView, defStyle, 0);

        //获取文本
        textContent = ta.getString(R.styleable.SingleLineTextView_textContent);
        //字体大小
        textSize = ta.getDimensionPixelSize(R.styleable.SingleLineTextView_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        //字体颜色
        textColor = ta.getColor(R.styleable.SingleLineTextView_textColor, Color.BLACK);

        gravity = ta.getInt(R.styleable.SingleLineTextView_gravity,0);

        ta.recycle();

        initPaint();
    }

    //初始化画笔
    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        //获取文本的宽度
        textWidth = paint.measureText(textContent);
        //文本的高度
        textHeight = paint.descent() - paint.ascent();
    }

    public void setTextContent(String content) {
        this.textContent = content;
        textWidth = paint.measureText(textContent);
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);

        int widthsize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = widthsize;
        } else {
            width = getPaddingLeft() + (int) (textWidth + 0.5) + getPaddingRight();
        }

        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingTop() + (int) (textHeight + 0.5) + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //如果文本的宽度大于显示的宽度,将字体大小减小
        while (textWidth > getWidth()) {
            paint.setTextSize(textSize--);
            textWidth = paint.measureText(textContent);
        }

        Log.i("AUTO_SIZE", "textcontent=" + textContent);
        Log.i("AUTO_SIZE", "getWidth=" + getWidth());
        Log.i("AUTO_SIZE", "textWidth=" + textWidth);
        Log.i("AUTO_SIZE", "textSize=" + textSize);
        //画出文本
        float textBaseline = (getHeight() - (paint.descent() + paint.ascent())) / 2.0f;

        if (gravity == 1) {
            canvas.drawText(textContent, getWidth() - textWidth, textBaseline, paint);
        } else {
            canvas.drawText(textContent, (getWidth() - textWidth) / 2.0f, textBaseline, paint);
        }
    }

}
