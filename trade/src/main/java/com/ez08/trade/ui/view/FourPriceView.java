package com.ez08.trade.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.MathUtils;
import com.ez08.trade.tools.ScreenUtil;
import com.ez08.trade.ui.trade.entity.TradeLevel1Entity;

public class FourPriceView extends LinearLayout {
    TextView newestPrice;
    TextView lastPrice;
    TextView upPrice;
    TextView downPrice;

    public FourPriceView(Context context) {
        super(context);
    }

    public FourPriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.trade_view_four_pirce, this);
        newestPrice = findViewById(R.id.newest_price);
        lastPrice = findViewById(R.id.last_price);
        upPrice = findViewById(R.id.limit_up_price);
        downPrice = findViewById(R.id.limit_down_price);
    }

    public void setData(TradeLevel1Entity entity) {
        newestPrice.setText(MathUtils.format2Num(entity.fNewest));
        newestPrice.setTextColor(entity.fNewest > entity.fOpen ? setTextColor(R.color.trade_red) : setTextColor(R.color.trade_green));
        lastPrice.setText(MathUtils.format2Num(entity.fLastClose));
        upPrice.setText(MathUtils.format2Num(entity.fLastClose * 1.1f));
        upPrice.setTextColor(setTextColor(R.color.trade_red));
        downPrice.setText(MathUtils.format2Num(entity.fLastClose * 0.9f));
        downPrice.setTextColor(setTextColor(R.color.trade_green));
    }

    private ColorStateList setTextColor(int color) {
        return ScreenUtil.setTextColor(getContext(), color);
    }
}
