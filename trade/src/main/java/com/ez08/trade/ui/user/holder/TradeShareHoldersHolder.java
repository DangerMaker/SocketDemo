package com.ez08.trade.ui.user.holder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.YiChuangUtils;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.user.entity.TradeShareHoldersItem;

public class TradeShareHoldersHolder extends BaseViewHolder<TradeShareHoldersItem> {

    TextView stockholders_code;
    TextView stockholders_name;
    TextView market_type;
    TextView funds_account;
    public TradeShareHoldersHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_shareholders_item);
        stockholders_code = $(R.id.stockholders_code);
        stockholders_name = $(R.id.stockholders_name);
        market_type = $(R.id.market_type);
        funds_account = $(R.id.funds_account);
    }

    @Override
    public void setData(TradeShareHoldersItem data) {
        stockholders_code.setText(data.secuid);
        stockholders_name.setText(data.name);
        market_type.setText(YiChuangUtils.getMarketType(data.market));
        funds_account.setText(data.custid);
    }
}
