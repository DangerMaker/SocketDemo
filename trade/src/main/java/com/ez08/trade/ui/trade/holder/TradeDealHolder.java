package com.ez08.trade.ui.trade.holder;

import android.content.res.ColorStateList;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.ScreenUtil;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.trade.entity.TradeDealEntity;

public class TradeDealHolder extends BaseViewHolder<TradeDealEntity> {

    TextView date;
    TextView time;
    TextView name;
    TextView code;
    TextView price;
    TextView num;
    ColorStateList color;

    public TradeDealHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_deal);
        date = $(R.id.date);
        time = $(R.id.time);
        name = $(R.id.name);
        code = $(R.id.code);
        price = $(R.id.stock_deal_price);
        num = $(R.id.stock_deal_num);
    }

    @Override
    public void setData(TradeDealEntity data) {
        date.setText(data.trddate);
        if(data.matchtime.length() == 6) {
            String hour = data.matchtime.substring(0,2);
            String min = data.matchtime.substring(2,4);
            String sec = data.matchtime.substring(4,6);
            time.setText(hour + ":" + min + ":" + sec);
        }
        name.setText(data.stkname);
        code.setText(data.stkcode);
        price.setText(data.matchprice);
        num.setText(data.matchqty);

        if (data.bsFlag.equals("S")) {
            color = ScreenUtil.setTextColor(getContext(), R.color.trade_blue);
        } else {
            color = ScreenUtil.setTextColor(getContext(), R.color.trade_red);
        }

        date.setTextColor(color);
        name.setTextColor(color);
        code.setTextColor(color);
        price.setTextColor(color);
        num.setTextColor(color);
        time.setTextColor(color);
    }
}
