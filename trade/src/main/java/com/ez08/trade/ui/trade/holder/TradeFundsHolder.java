package com.ez08.trade.ui.trade.holder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.trade.entity.TradeFundsEntity;

public class TradeFundsHolder extends BaseViewHolder<Object> {

    TextView titleView;
    TextView numberView;

    public TradeFundsHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_funds);
        titleView = $(R.id.title);
        numberView = $(R.id.funds);
    }

    @Override
    public void setData(Object data) {
        TradeFundsEntity entity = (TradeFundsEntity) data;
        titleView.setText(entity.title);
        numberView.setText(entity.number + "元");
    }
}
