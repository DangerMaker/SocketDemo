package com.ez08.trade.ui.fresh_stock.holder;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.JumpActivity;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.fresh_stock.entity.TradeDrawLeftEntity;

public class TradeDrawLeftHolder extends BaseViewHolder<Object> {
    TextView titleView;
    ImageView imgView;
    TextView extraView;

    public TradeDrawLeftHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_draw_left);
        titleView = $(R.id.title);
        imgView = $(R.id.icon);
        extraView = $(R.id.extra);
    }

    @Override
    public void setData(Object data) {
        final TradeDrawLeftEntity entity = (TradeDrawLeftEntity) data;
        titleView.setText(entity.title);
        imgView.setImageResource(entity.imgId);
        if (!TextUtils.isEmpty(entity.extra)) {
            extraView.setVisibility(View.VISIBLE);
            extraView.setText(Html.fromHtml(entity.extra));
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpActivity.start(getContext(),entity.title);
            }

        });
    }
}
