package com.ez08.trade.ui.user.adpater;

import android.content.Context;
import android.view.ViewGroup;

import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.user.entity.TradeShareHoldersItem;
import com.ez08.trade.ui.user.entity.TradeShareHoldersTitle;
import com.ez08.trade.ui.user.holder.TradeShareHoldersHolder;
import com.ez08.trade.ui.user.holder.TradeShareHoldersTitleHolder;

import java.security.InvalidParameterException;

public class TradeShareHoldersAdapter extends BaseAdapter<Object> {

    public static final int TRADE_SHAREHOLDERS_TITLE = 1;
    public static final int TRADE_SHAREHOLDERS = 2;

    public TradeShareHoldersAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof TradeShareHoldersTitle) {
            return TRADE_SHAREHOLDERS_TITLE;
        } else if (getItem(position) instanceof TradeShareHoldersItem) {
            return TRADE_SHAREHOLDERS;
        } else {
            return -1;
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TRADE_SHAREHOLDERS_TITLE) {
            return new TradeShareHoldersTitleHolder(parent);
        } else if (viewType == TRADE_SHAREHOLDERS) {
            return new TradeShareHoldersHolder(parent);
        } else {
            throw new InvalidParameterException();
        }
    }
}
