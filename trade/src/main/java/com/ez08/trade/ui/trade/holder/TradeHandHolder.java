package com.ez08.trade.ui.trade.holder;

import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.MathUtils;
import com.ez08.trade.tools.ScreenUtil;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.trade.entity.TradeHandEntity;

public class TradeHandHolder extends BaseViewHolder<TradeHandEntity> {

    TextView txt_name;
    TextView txt_code;
    TextView profit_num;
    TextView profit_percent;
    TextView stock_hand;
    TextView stock_available;
    TextView stock_prime;
    TextView stock_last;
    ColorStateList color;

    public TradeHandHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_handing);
        txt_name = $(R.id.txt_name);
        txt_code = $(R.id.txt_code);
        profit_num = $(R.id.profit_num);
        profit_percent = $(R.id.profit_percent);
        stock_hand = $(R.id.stock_hand);
        stock_available = $(R.id.stock_available);
        stock_prime = $(R.id.stock_prime);
        stock_last = $(R.id.stock_last);
    }

    @Override
    public void setData(TradeHandEntity data) {
        if (Double.parseDouble(data.income) <= 0) {
            color = ScreenUtil.setTextColor(getContext(), R.color.trade_blue);
        } else {
            color = ScreenUtil.setTextColor(getContext(), R.color.trade_red);
        }
        if (!TextUtils.isEmpty(data.costprice)){
            double v = Double.parseDouble(data.costprice) * 100;
            double scale = (Double.parseDouble(data.lastprice) - Double.parseDouble(data.costprice)) / Double.parseDouble(data.costprice) * 100;
            profit_percent.setText(v>0? MathUtils.format2Num(scale) + "%":"--");
        }
        txt_name.setText(data.stkname);
        txt_code.setText(data.stkcode);
        profit_num.setText(data.income);
        stock_hand.setText(data.stkbal);
        stock_available.setText(data.stkavl);
        stock_prime.setText(data.costprice);
        stock_last.setText(data.lastprice);

        setTextColor();
    }


    private void setTextColor() {
        txt_name.setTextColor(color);
        txt_code.setTextColor(color);
        profit_num.setTextColor(color);
        profit_percent.setTextColor(color);
        stock_hand.setTextColor(color);
        stock_available.setTextColor(color);
        stock_prime.setTextColor(color);
        stock_last.setTextColor(color);
    }
}
