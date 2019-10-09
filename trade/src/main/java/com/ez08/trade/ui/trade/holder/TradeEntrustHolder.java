package com.ez08.trade.ui.trade.holder;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.CommonUtils;
import com.ez08.trade.tools.ScreenUtil;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.trade.entity.TradeEntrustEntity;

public class TradeEntrustHolder extends BaseViewHolder<TradeEntrustEntity> {

    TextView name;
    TextView time;
    TextView date;
    TextView price;
    TextView num1;
    TextView num2;
    TextView direction;
    TextView state;

    ColorStateList color;

    public TradeEntrustHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_entrust);
        name = $(R.id.txt_name);
        time = $(R.id.time);
        date = $(R.id.date);
        price = $(R.id.stock_entrust_price);
        num1 = $(R.id.stock_entrust_num);
        num2 = $(R.id.stock_deal_num);
        direction = $(R.id.stock_entrust_option);
        state = $(R.id.stock_entrust_state);
    }

    @Override
    public void setData(TradeEntrustEntity data) {
        name.setText(data.stkname);
        if (data.opertime.length() == 6 || data.opertime.length() == 8) {
            String hour = data.opertime.substring(0, 2);
            String min = data.opertime.substring(2, 4);
            String sec = data.opertime.substring(4, 6);
            String temp = data.orderdate;
            if (temp.equals(CommonUtils.getCurrentDate())) {
                date.setVisibility(View.GONE);
            }
            date.setText(temp);
            time.setText(hour + ":" + min + ":" + sec);
        } else if (data.opertime.length() == 7) {
            String hour = data.opertime.substring(0, 1);
            String min = data.opertime.substring(1, 3);
            String sec = data.opertime.substring(3, 5);
            String temp = data.orderdate;
            if (temp.equals(CommonUtils.getCurrentDate())) {
                date.setVisibility(View.GONE);
            }
            date.setText(temp);
            time.setText("0" + hour + ":" + min + ":" + sec);
        }
        price.setText(data.orderprice);
        num1.setText(data.orderqty);
        num2.setText(data.matchqty);
        if (data.bsflag.equals("S")) {
            direction.setText("卖出");
            color = ScreenUtil.setTextColor(getContext(), R.color.trade_blue);
        } else {
            direction.setText("买入");
            color = ScreenUtil.setTextColor(getContext(), R.color.trade_red);
        }

        String status;
        if (data.orderstatus.equals("0")) {
            status = "未报";
        } else if (data.orderstatus.equals("1")) {
            status = "正报";
        } else if (data.orderstatus.equals("2")) {
            status = "已报";
        } else if (data.orderstatus.equals("3")) {
            status = "已报待撤";
        } else if (data.orderstatus.equals("4")) {
            status = "部成待撤";
        } else if (data.orderstatus.equals("5")) {
            status = "部撤";
        } else if (data.orderstatus.equals("6")) {
            status = "已撤";
        } else if (data.orderstatus.equals("7")) {
            status = "部成";
        } else if (data.orderstatus.equals("8")) {
            status = "已成";
        } else if (data.orderstatus.equals("9")) {
            status = "废单";
        } else if (data.orderstatus.equals("A")) {
            status = "待报";
        } else if (data.orderstatus.equals("B")) {
            status = "正报";
        } else {
            status = "未知";
        }
        state.setText(status);
        setTextColor();
    }

    private void setTextColor() {
        name.setTextColor(color);
        price.setTextColor(color);
        num1.setTextColor(color);
        num2.setTextColor(color);
        direction.setTextColor(color);
        state.setTextColor(color);
        date.setTextColor(color);
        time.setTextColor(color);
    }
}
