package com.ez08.trade.ui.bank.adpater;

import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.YiChuangUtils;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.bank.entity.TransferEntity;

public class TradeTransferHolder extends BaseViewHolder<TransferEntity> {

    TextView time;
    TextView price;
    TextView status;

    public TradeTransferHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_transfer);
        time = $(R.id.time);
        price = $(R.id.price);
        status = $(R.id.status);
    }

    @Override
    public void setData(TransferEntity data) {
        time.setText(data.operdate + " " + YiChuangUtils.getTime(data.opertime));
        price.setText(data.fundeffect);
        status.setText(YiChuangUtils.getTransferStatus(data.status));

    }
}
