package com.ez08.trade.ui.fresh_stock.adpater;

import android.content.Context;
import android.view.ViewGroup;

import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.TradeLineHolder;
import com.ez08.trade.ui.fresh_stock.entity.TradeFreshBuyEntity;
import com.ez08.trade.ui.fresh_stock.entity.TradeLineEntity;
import com.ez08.trade.ui.fresh_stock.holder.TradeFreshStockHolder;

import java.security.InvalidParameterException;

public class TradeFreshBuyAdapter extends BaseAdapter<Object> {

    public static final int TRADE_ITEM = 1;
    public static final int TRADE_LINE = 2;

    public TradeFreshBuyAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof TradeFreshBuyEntity) {
            return TRADE_ITEM;
        } else if (getItem(position) instanceof TradeLineEntity) {
            return TRADE_LINE;
        } else {
            return -1;
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TRADE_ITEM) {
            return new TradeFreshStockHolder(parent);
        } else if (viewType == TRADE_LINE) {
            return new TradeLineHolder(parent);
        } else {
            throw new InvalidParameterException();
        }
    }
}
