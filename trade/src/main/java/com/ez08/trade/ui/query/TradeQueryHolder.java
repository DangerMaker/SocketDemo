package com.ez08.trade.ui.query;

import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;

public class TradeQueryHolder extends BaseViewHolder<Object> {

    TextView textView;
    TextView extraView;

    public TradeQueryHolder(ViewGroup parent) {
        super(parent, R.layout.trade_holder_other);
        textView = $(R.id.title);
        extraView = $(R.id.extra);
    }

    @Override
    public void setData(Object data) {
        final TradeOtherEntity entity = (TradeOtherEntity) data;
        textView.setText(entity.title);
    }
}
