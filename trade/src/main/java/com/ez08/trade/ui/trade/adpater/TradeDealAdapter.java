package com.ez08.trade.ui.trade.adpater;

import android.content.Context;
import android.view.ViewGroup;

import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.trade.entity.TradeDealEntity;
import com.ez08.trade.ui.trade.entity.TradeTitleDealEntity;
import com.ez08.trade.ui.trade.holder.TradeDealHolder;
import com.ez08.trade.ui.trade.holder.TradeTitleDealHolder;

import java.security.InvalidParameterException;

public class TradeDealAdapter extends BaseAdapter<Object> {

    public static final int TRADE_DEAL_TITLE = 1;
    public static final int TRADE_DEAL = 2;

    public TradeDealAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof TradeTitleDealEntity) {
            return TRADE_DEAL_TITLE;
        } else if (getItem(position) instanceof TradeDealEntity) {
            return TRADE_DEAL;
        } else {
            return -1;
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TRADE_DEAL_TITLE) {
            return new TradeTitleDealHolder(parent);
        } else if (viewType == TRADE_DEAL) {
            return new TradeDealHolder(parent);
        } else {
            throw new InvalidParameterException();
        }
    }
}
