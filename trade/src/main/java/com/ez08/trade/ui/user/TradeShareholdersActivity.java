package com.ez08.trade.ui.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ez08.trade.R;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.StringCallback;
import com.ez08.trade.tools.YCParser;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.user.adpater.TradeShareHoldersAdapter;
import com.ez08.trade.ui.user.entity.ShareHoldersEntity;
import com.ez08.trade.ui.user.entity.TradeShareHoldersTitle;
import com.ez08.trade.ui.view.LinearItemDecoration;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradeShareholdersActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<Object> mList;
    TradeShareHoldersAdapter adapter;

    List<ShareHoldersEntity> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_shareholders);
        titleView = findViewById(R.id.title);
        titleView.setText("股东资料");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new TradeShareHoldersAdapter(this);
        recyclerView.setAdapter(adapter);

        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        list = new ArrayList<>();
        mList = new ArrayList<>();
        mList.add(new TradeShareHoldersTitle());
        adapter.addAll(mList);

        String body = "FUN=410501&TBL_IN=fundid,market,secuid,qryflag,count,poststr;,,,1,10,;";
        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            if (success) {
                List<Map<String, String>> result = YCParser.parseArray(data);
                for (int i = 0; i < result.size(); i++) {
                    ShareHoldersEntity entity = new ShareHoldersEntity();
                    entity.custid = result.get(i).get("custid");
                    entity.regflag = result.get(i).get("regflag");
                    entity.bondreg = result.get(i).get("bondreg");
                    entity.opendate = result.get(i).get("opendate");
                    entity.market = result.get(i).get("market");
                    entity.secuid = result.get(i).get("secuid");
                    entity.name = result.get(i).get("name");
                    entity.fundid = result.get(i).get("fundid");
                    entity.secuseq = result.get(i).get("secuseq");
                    entity.status = result.get(i).get("status");
                    list.add(entity);
                }

                mList.clear();
                mList.add(new TradeShareHoldersTitle());
                for (int i = 0; i < list.size(); i++) {
                    mList.add(list.get(i).getItem());
                }
                adapter.clearAndAddAll(mList);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        }
    }
}
