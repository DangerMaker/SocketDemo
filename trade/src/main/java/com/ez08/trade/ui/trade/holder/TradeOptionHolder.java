package com.ez08.trade.ui.trade.holder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.JumpActivity;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.trade.entity.TradeOptionEntity;

public class TradeOptionHolder extends BaseViewHolder<Object> {
    TextView subtitleView;
    ImageView iconView;

    public TradeOptionHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_options);
        subtitleView = $(R.id.sub_title);
        iconView = $(R.id.icon);
    }

    @Override
    public void setData(Object data) {
        final TradeOptionEntity entity = (TradeOptionEntity) data;
        subtitleView.setText(entity.subtitle);
        iconView.setImageResource(entity.imgId);
        itemView.setOnClickListener(v -> JumpActivity.start(getContext(),entity.subtitle));
    }
}
