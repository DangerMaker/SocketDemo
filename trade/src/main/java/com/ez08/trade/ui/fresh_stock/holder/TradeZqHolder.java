package com.ez08.trade.ui.fresh_stock.holder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.YiChuangUtils;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.fresh_stock.entity.TradeZqEntity;
import com.ez08.trade.ui.view.SingleLineAutoResizeTextView;

public class TradeZqHolder extends BaseViewHolder<TradeZqEntity> {
    TextView date;
    TextView name;
    SingleLineAutoResizeTextView num;
    TextView status;


    public TradeZqHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_zq);
        date = $(R.id.date);
        name = $(R.id.name);
        status = $(R.id.status);
        num = $(R.id.num);
    }

    @Override
    public void setData(TradeZqEntity data) {
        date.setText(data.matchdate);
        name.setText(data.stkname);
        num.setTextContent(data.hitqty);
        status.setText(YiChuangUtils.getZhongqianStatus(data.status));
    }
}
