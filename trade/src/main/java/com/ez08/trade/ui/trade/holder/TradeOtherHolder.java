package com.ez08.trade.ui.trade.holder;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.JumpActivity;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;

public class TradeOtherHolder extends BaseViewHolder<Object> {

    TextView textView;
    TextView extraView;

    public TradeOtherHolder(ViewGroup parent) {
        super(parent, R.layout.trade_holder_other);
        textView = $(R.id.title);
        extraView = $(R.id.extra);
    }

    @Override
    public void setData(Object data) {
        final TradeOtherEntity entity = (TradeOtherEntity) data;
        textView.setText(entity.title);
        if (!TextUtils.isEmpty(entity.extra)) {
            extraView.setVisibility(View.VISIBLE);
            extraView.setText(Html.fromHtml(entity.extra));
        }
        itemView.setOnClickListener(v -> {

            if(entity.title.equals("客户风险级别测评")){
                Intent intent = new Intent();
                intent.putExtra("title","客户风险级别测评");
                intent.putExtra("url","http://www.baidu.com");
                JumpActivity.start(getContext(),intent,"客户风险级别测评");
                return;
            }

            if(entity.title.equals("网络投票")){
                Intent intent = new Intent();
                intent.putExtra("title","网络投票");
                intent.putExtra("url","http://www.baidu.com");
                JumpActivity.start(getContext(),intent,"网络投票");
                return;
            }

            if(entity.title.equals("退出登录")){
                JumpActivity.start(getContext(),"登录");
                ((Activity)getContext()).finish();
            }
            JumpActivity.start(getContext(),entity.title);
        });
    }
}
