package com.ez08.trade.ui.view;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class BlueUnderLineClickableSpan  extends ClickableSpan {
    @Override
    public void onClick(View widget) {

    }

    /**
     * 设置超链接颜色为蓝色，没有下划线
     * @param ds
     */
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.BLUE);
        ds.setUnderlineText(true);
    }
}