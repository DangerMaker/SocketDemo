package com.ez08.trade.ui.fresh_stock.holder;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.fresh_stock.entity.TradeFreshBuyEntity;

public class TradeFreshStockHolder extends BaseViewHolder<TradeFreshBuyEntity> {

    TextView name;
    TextView code;
    TextView num;

    ImageView left_reduce_num;
    ImageView right_plus_num;

    AppCompatCheckBox checkBox;
    public TradeFreshStockHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_fresh_item);
        left_reduce_num = $(R.id.left_reduce_num);
        right_plus_num = $(R.id.right_plus_num);
        left_reduce_num.setColorFilter(Color.RED);
        right_plus_num.setColorFilter(Color.RED);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });

        checkBox = $(R.id.checkbox);
        name = $(R.id.txt_name);
        code = $(R.id.txt_code);
        num = $(R.id.txt_num);
    }

    @Override
    public void setData(TradeFreshBuyEntity data) {
        name.setText(data.stkname);
        code.setText(data.stkcode);
        num.setText(data.maxqty);


    }
}
