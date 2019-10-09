package com.ez08.trade.ui.trade.adpater;

import android.content.Context;
import android.view.ViewGroup;

import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.trade.entity.TradeFundsEntity;
import com.ez08.trade.ui.trade.entity.TradeHandEntity;
import com.ez08.trade.ui.trade.entity.TradeTitleHandEntity;
import com.ez08.trade.ui.trade.holder.TradeFundsHolder;
import com.ez08.trade.ui.trade.holder.TradeHandHolder;
import com.ez08.trade.ui.trade.holder.TradeTitleHandHolder;

import java.security.InvalidParameterException;

public class TradeHandAdapter extends BaseAdapter<Object> {

    public static final int TRADE_FUNDS = 1;
    public static final int TRADE_HAND_TITLE = 2;
    public static final int TRADE_HAND = 3;

    public TradeHandAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof TradeFundsEntity) {
            return TRADE_FUNDS;
        } else if (getItem(position) instanceof TradeTitleHandEntity) {
            return TRADE_HAND_TITLE;
        } else if (getItem(position) instanceof TradeHandEntity) {
            return TRADE_HAND;
        } else {
            return -1;
        }
    }


    public int getSpan(int position){
        if (getItem(position) instanceof TradeFundsEntity) {
            return 1;
        } else{
            return 2;
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TRADE_FUNDS) {
            return new TradeFundsHolder(parent);
        } else if (viewType == TRADE_HAND_TITLE) {
            return new TradeTitleHandHolder(parent);
        } else if (viewType == TRADE_HAND) {
            return new TradeHandHolder(parent);
        } else {
            throw new InvalidParameterException();
        }
    }
}
