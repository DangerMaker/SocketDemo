package com.ez08.trade.ui.bank.adpater;

import android.content.Context;
import android.view.ViewGroup;

import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.bank.entity.TransferEntity;
import com.ez08.trade.ui.bank.entity.TransferTitleEntity;

import java.security.InvalidParameterException;

public class TradeTransAdapter extends BaseAdapter<Object> {

    public static final int TRADE_TITLE = 1;
    public static final int TRADE = 2;

    public TradeTransAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof TransferTitleEntity) {
            return TRADE_TITLE;
        } else if (getItem(position) instanceof TransferEntity) {
            return TRADE;
        } else {
            return -1;
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TRADE_TITLE) {
            return new TradeTransferTitleHolder(parent);
        } else if (viewType == TRADE) {
            return new TradeTransferHolder(parent);
        } else {
            throw new InvalidParameterException();
        }
    }
}
