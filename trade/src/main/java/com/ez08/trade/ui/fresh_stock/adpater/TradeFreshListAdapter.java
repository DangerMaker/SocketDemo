package com.ez08.trade.ui.fresh_stock.adpater;

import android.content.Context;
import android.view.ViewGroup;

import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.TradeLineHolder;
import com.ez08.trade.ui.fresh_stock.entity.TradeDrawLeftEntity;
import com.ez08.trade.ui.fresh_stock.entity.TradeLineEntity;
import com.ez08.trade.ui.fresh_stock.holder.TradeDrawLeftHolder;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;
import com.ez08.trade.ui.trade.holder.TradeOtherHolder;

import java.security.InvalidParameterException;

public class TradeFreshListAdapter extends BaseAdapter<Object> {

    public static final int TRADE_DRAW_LEFT = 1;
    public static final int TRADE_LINE = 2;
    public static final int TRADE_OTHER = 3;

    public TradeFreshListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof TradeDrawLeftEntity) {
            return TRADE_DRAW_LEFT;
        } else if (getItem(position) instanceof TradeLineEntity) {
            return TRADE_LINE;
        } else if (getItem(position) instanceof TradeOtherEntity) {
            return TRADE_OTHER;
        } else {
            return -1;
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TRADE_DRAW_LEFT) {
            return new TradeDrawLeftHolder(parent);
        } else if (viewType == TRADE_LINE) {
            return new TradeLineHolder(parent);
        } else if (viewType == TRADE_OTHER) {
            return new TradeOtherHolder(parent);
        } else {
            throw new InvalidParameterException();
        }
    }
}
