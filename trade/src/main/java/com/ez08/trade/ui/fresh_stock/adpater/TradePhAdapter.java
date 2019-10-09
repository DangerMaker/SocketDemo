package com.ez08.trade.ui.fresh_stock.adpater;

import android.content.Context;
import android.view.ViewGroup;

import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.fresh_stock.entity.TradePhEntity;
import com.ez08.trade.ui.fresh_stock.entity.TradePhTitleEntity;
import com.ez08.trade.ui.fresh_stock.entity.TradeZqEntity;
import com.ez08.trade.ui.fresh_stock.holder.TradePhHolder;
import com.ez08.trade.ui.fresh_stock.holder.TradeTitlePhHolder;
import com.ez08.trade.ui.fresh_stock.holder.TradeZqHolder;

import java.security.InvalidParameterException;

public class TradePhAdapter extends BaseAdapter<Object> {

    public static final int TITLE = 1;
    public static final int CHILD = 2;
    public static final int ZQ = 3;
    public static final int VOTE = 4;


    public TradePhAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof TradePhTitleEntity) {
            return TITLE;
        } else if (getItem(position) instanceof TradePhEntity) {
            return CHILD;
        } else if (getItem(position) instanceof TradeZqEntity) {
            return ZQ;
        } else {
            return -1;
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TITLE) {
            return new TradeTitlePhHolder(parent);
        } else if (viewType == CHILD) {
            return new TradePhHolder(parent);
        } else if (viewType == ZQ) {
            return new TradeZqHolder(parent);
        } else {
            throw new InvalidParameterException();
        }
    }
}
