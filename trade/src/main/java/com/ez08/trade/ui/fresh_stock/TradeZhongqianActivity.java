package com.ez08.trade.ui.fresh_stock;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ez08.trade.R;
import com.ez08.trade.net.Client;
import com.ez08.trade.tools.YCParser;
import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.TradeHisQueryActivity;
import com.ez08.trade.ui.fresh_stock.adpater.TradePhAdapter;
import com.ez08.trade.ui.fresh_stock.entity.TradeZqEntity;

import java.util.List;
import java.util.Map;

public class TradeZhongqianActivity extends TradeHisQueryActivity {
    @Override
    public String getTitleString() {
        return "历史中签";
    }

    @Override
    public int getListTitleLayout() {
        return R.layout.trade_holder_zq_title;
    }

    @Override
    public BaseAdapter getAdapter() {
        return new TradePhAdapter(this);
    }

    @Override
    public void post(String beginValue, String endValue) {
        mList.clear();

        String body = "FUN=411560&TBL_IN=secuid,market,stkcode,issuetype,begindate,enddate,count,poststr;" +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                beginValue + "," +
                endValue + "," +
                "100" + "," +
                ";";

        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            if (success) {
                List<Map<String, String>> result = YCParser.parseArray(data);
                for (int i = 0; i < result.size(); i++) {
                    TradeZqEntity entity = new TradeZqEntity();
                    entity.stkname = result.get(i).get("stkname");
                    entity.matchdate =result.get(i).get("matchdate");
                    entity.hitqty = result.get(i).get("hitqty");
                    entity.status = result.get(i).get("status");
                    mList.add(entity);
                }

                adapter.clearAndAddAll(mList);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
