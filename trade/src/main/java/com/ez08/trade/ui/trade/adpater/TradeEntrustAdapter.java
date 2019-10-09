package com.ez08.trade.ui.trade.adpater;

import android.content.Context;
import android.view.ViewGroup;

import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.trade.entity.TradeEntrustEntity;
import com.ez08.trade.ui.trade.entity.TradeTitleEntrustEntity;
import com.ez08.trade.ui.trade.holder.TradeEntrustHolder;
import com.ez08.trade.ui.trade.holder.TradeTitleEntrustHolder;

import java.security.InvalidParameterException;

public class TradeEntrustAdapter extends BaseAdapter<Object> {

    public static final int TRADE_ENTRUST_TITLE = 1;
    public static final int TRADE_ENTRUS = 2;

    public TradeEntrustAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof TradeTitleEntrustEntity) {
            return TRADE_ENTRUST_TITLE;
        } else if (getItem(position) instanceof TradeEntrustEntity) {
            return TRADE_ENTRUS;
        } else {
            return -1;
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TRADE_ENTRUST_TITLE) {
            return new TradeTitleEntrustHolder(parent);
        } else if (viewType == TRADE_ENTRUS) {
            return new TradeEntrustHolder(parent);
        } else {
            throw new InvalidParameterException();
        }
    }
}
