package com.ez08.trade.ui.trade.adpater;

import android.content.Context;
import android.view.ViewGroup;

import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.trade.entity.TradeOptionEntity;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;
import com.ez08.trade.ui.trade.holder.TradeOptionHolder;
import com.ez08.trade.ui.trade.holder.TradeOtherHolder;

import java.security.InvalidParameterException;

public class TradeMainAdapter extends BaseAdapter<Object> {

    public static final int TRADE_OPTION = 2;
    public static final int TRADE_OTHER = 3;

    public TradeMainAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof TradeOptionEntity) {
            return TRADE_OPTION;
        } else if (getItem(position) instanceof TradeOtherEntity) {
            return TRADE_OTHER;
        } else {
            return -1;
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TRADE_OPTION) {
            return new TradeOptionHolder(parent);
        } else if (viewType == TRADE_OTHER) {
            return new TradeOtherHolder(parent);
        } else {
            throw new InvalidParameterException();
        }
    }
}
