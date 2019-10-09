package com.ez08.trade.ui.fresh_stock;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ez08.trade.R;
import com.ez08.trade.net.Client;
import com.ez08.trade.tools.YCParser;
import com.ez08.trade.tools.YiChuangUtils;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.view.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradeDaijiaoActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;
    RecyclerView recyclerView;
    List<Map<String, String>> list;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_new_stock_hit);

        titleView = findViewById(R.id.title);
        titleView.setText("新股申购代缴查询");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        list = new ArrayList<>();
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        getList();
    }

    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            finish();
        }
    }

    private void getList() {
        String body = "FUN=411547&TBL_IN=secuid,market,stkcode,issuetype;" +
                "," +
                "," +
                "," +
                ";";

        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            dismissBusyDialog();
            if (success) {
                List<Map<String, String>> result = YCParser.parseArray(data);
                list = result;
                myAdapter.notifyDataSetChanged();
            }
        });

    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.trade_holder_daijiao, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
            final Map<String, String> entity = list.get(i);
            myHolder.name.setText(entity.get("stkname"));
            myHolder.code.setText(entity.get("stkcode"));
            myHolder.date.setText(entity.get("matchdate"));
            myHolder.marketName.setText(YiChuangUtils.getMarketType(entity.get("market")));
            myHolder.hitNum.setText(entity.get("hitqty"));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView code;
        TextView date;
        TextView marketName;
        TextView hitNum;

        public MyHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            code = view.findViewById(R.id.code);
            date = view.findViewById(R.id.date);
            marketName = view.findViewById(R.id.market);
            hitNum = view.findViewById(R.id.hit);

        }
    }

}
