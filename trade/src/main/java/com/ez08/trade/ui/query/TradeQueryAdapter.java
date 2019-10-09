package com.ez08.trade.ui.query;

import android.content.Context;
import android.view.ViewGroup;

import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;

import java.security.InvalidParameterException;

public class TradeQueryAdapter extends BaseAdapter<Object> {

    public static final int TRADE_OTHER = 3;

    public TradeQueryAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof TradeOtherEntity) {
            return TRADE_OTHER;
        } else {
            return -1;
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TRADE_OTHER) {
            return new TradeQueryHolder(parent);
        } else {
            throw new InvalidParameterException();
        }
    }
}
