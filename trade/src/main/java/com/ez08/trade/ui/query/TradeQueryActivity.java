package com.ez08.trade.ui.query;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;
import com.ez08.trade.ui.view.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TradeQueryActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;
    RecyclerView recyclerView;
    GridLayoutManager manager;
    List<Object> mList;

    TradeQueryAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_custom);
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        titleView = findViewById(R.id.title);
        titleView.setText("查询");

        recyclerView = findViewById(R.id.recycler_view);
        manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                return 4;
            }
        });

        adapter = new TradeQueryAdapter(this);
        recyclerView.setAdapter(adapter);

        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        mList = new ArrayList<>();
        mList.add(new TradeOtherEntity("当日成交"));
        mList.add(new TradeOtherEntity("历史成交"));
        mList.add(new TradeOtherEntity("当日委托"));
        mList.add(new TradeOtherEntity("历史委托"));
        adapter.addAll(mList);

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
               switch (position){
                   case 0:
                       Intent intent1 = new Intent();
                       intent1.setClass(TradeQueryActivity.this, TradeQueryEntrustActivity.class);
                       intent1.putExtra("type",2);
                       startActivity(intent1);
                       break;
                   case 1:
                       Intent intent2 = new Intent();
                       intent2.setClass(TradeQueryActivity.this, TradeQueryEntrustActivity.class);
                       intent2.putExtra("type",3);
                       startActivity(intent2);
                       break;
                   case 2:
                       Intent intent3 = new Intent();
                       intent3.putExtra("type",0);
                       intent3.setClass(TradeQueryActivity.this, TradeQueryEntrustActivity.class);
                       startActivity(intent3);
                       break;
                   case 3:
                       Intent intent4 = new Intent();
                       intent4.putExtra("type",1);
                       intent4.setClass(TradeQueryActivity.this, TradeQueryEntrustActivity.class);
                       startActivity(intent4);
                       break;
               }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_back){
            finish();
        }
    }
}
