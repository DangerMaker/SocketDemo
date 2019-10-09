package com.ez08.trade.ui.fresh_stock.holder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.fresh_stock.entity.TradePhEntity;
import com.ez08.trade.ui.view.SingleLineAutoResizeTextView;

public class TradePhHolder extends BaseViewHolder<TradePhEntity> {
    TextView date;
    TextView name;
    TextView code;
    SingleLineAutoResizeTextView id;
    TextView num;


    public TradePhHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_phl);
        date = $(R.id.date);
        name = $(R.id.name);
        code = $(R.id.code);
        id = $(R.id.id);
        num = $(R.id.num);
    }

    @Override
    public void setData(TradePhEntity data) {
        date.setText(data.bizdate);
        name.setText(data.stkname);
        code.setText(data.stkcode);
        id.setTextContent(data.mateno);
        num.setText(data.matchqty);
    }
}
