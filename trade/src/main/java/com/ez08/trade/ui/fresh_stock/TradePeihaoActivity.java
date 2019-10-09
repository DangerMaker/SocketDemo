package com.ez08.trade.ui.fresh_stock;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.Callback;
import com.ez08.trade.net.Client;
import com.ez08.trade.tools.YCParser;
import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.TradeHisQueryActivity;
import com.ez08.trade.ui.fresh_stock.adpater.TradePhAdapter;
import com.ez08.trade.ui.fresh_stock.entity.TradePhEntity;
import com.ez08.trade.ui.user.entity.ShareHoldersEntity;
import com.ez08.trade.ui.user.entity.TradeShareHoldersTitle;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TradePeihaoActivity extends TradeHisQueryActivity {

    @Override
    public String getTitleString() {
        return "配号查询";
    }

    @Override
    public int getListTitleLayout() {
        return R.layout.trade_holder_ph_title;
    }

    @Override
    public BaseAdapter getAdapter() {
        return new TradePhAdapter(this);
    }

    @Override
    public void post(String beginValue, String endValue) {
        mList.clear();

        String body = "FUN=411518&TBL_IN=strdate,enddate,fundid,stkcode,secuid,qryflag,count,poststr;" +
                beginValue + "," +
                endValue + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "0" + "," +
                "100" + "," +
                ";";

        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            if (success) {
                List<Map<String, String>> result = YCParser.parseArray(data);
                for (int i = 0; i < result.size(); i++) {
                    TradePhEntity entity = new TradePhEntity();
                    entity.stkcode = result.get(i).get("stkcode");
                    entity.stkname = result.get(i).get("stkname");
                    entity.bizdate = result.get(i).get("bizdate");
                    entity.mateno = result.get(i).get("mateno");
                    entity.matchqty = result.get(i).get("matchqty");
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
