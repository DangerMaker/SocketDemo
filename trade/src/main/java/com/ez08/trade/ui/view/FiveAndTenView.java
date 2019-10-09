package com.ez08.trade.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.ez08.trade.R;
import com.ez08.trade.tools.MathUtils;
import com.ez08.trade.tools.ScreenUtil;
import com.ez08.trade.ui.trade.entity.TradeStockEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FiveAndTenView extends RelativeLayout implements View.OnClickListener {

    LinearLayout container;
    String[] title = {"卖5", "卖4", "卖3", "卖2", "卖1",
            "买1", "买2", "买3", "买4", "买5"};

    float price = 0f;
    Context context;

    public FiveAndTenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.trade_view_five, this);
        this.context = context;
        total = new ArrayList<>();
        setOnClickListener(this);
        container = findViewById(R.id.container);

        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        ll.weight = 1;

        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);

        for (int i = 0; i < title.length; i++) {
            if (i == 5) {
                View line = new View(context);
                line.setBackgroundColor(ContextCompat.getColor(context, R.color.trade_gray));
                container.addView(line, lineParams);
            }

            View item = inflate(context, R.layout.trade_item_five, null);
            TextView nameView = item.findViewById(R.id.name);
            price = price - 0.01f;
            if (price>0){
                item.setTag(MathUtils.formatNum(price,4));
                item.setOnClickListener(this);
                TextView priceView =  item.findViewById(R.id.price);
                priceView.setText(MathUtils.formatNum(price,4));
            }
            nameView.setText(title[i]);
            container.addView(item, ll);
        }
    }

    List<TradeStockEntity.Dang> ask;
    List<TradeStockEntity.Dang> bid;

    List<TradeStockEntity.Dang> total;

    public void setLevel1(TradeStockEntity entity){
        Collections.reverse(entity.ask);
        this.ask = entity.ask;
        this.bid = entity.bid;

        total.clear();
        total.addAll(ask);
        total.addAll(bid);

        container.removeAllViews();

        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        ll.weight = 1;

        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);

        for (int i = 0; i < title.length; i++) {
            if (i == 5) {
                View line = new View(context);
                line.setBackgroundColor(ContextCompat.getColor(context, R.color.trade_gray));
                container.addView(line, lineParams);
            }

            View item = inflate(context, R.layout.trade_item_five, null);
            TextView nameView = (TextView) item.findViewById(R.id.name);
            item.setOnClickListener(this);
            nameView.setText(title[i]);
            TextView priceView = (TextView) item.findViewById(R.id.price);
            priceView.setText(MathUtils.format2Num(total.get(i).fPrice));
            priceView.setTextColor(total.get(i).fPrice > entity.fOpen ? ScreenUtil.setTextColor(context, R.color.trade_red) : ScreenUtil.setTextColor(context, R.color.trade_green));
            item.setTag(MathUtils.format2Num(total.get(i).fPrice));
            TextView volumeView =  item.findViewById(R.id.volume);
            volumeView.setText(MathUtils.getMost4Character(total.get(i).fOrder / 100));
            container.addView(item, ll);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getTag()!=null){
            String s =v.getTag() .toString();
            onFiveListener.OnFive(s);
        }
    }


    OnFiveListener onFiveListener;
    public void setOnFiveListener(OnFiveListener listener){
        onFiveListener = listener;
    }
}
