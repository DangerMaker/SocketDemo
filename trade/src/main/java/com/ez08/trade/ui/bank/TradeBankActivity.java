package com.ez08.trade.ui.bank;

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
import com.ez08.trade.ui.query.TradeQueryAdapter;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;
import com.ez08.trade.ui.view.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TradeBankActivity extends BaseActivity implements View.OnClickListener {

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
        titleView.setText("银行转账");

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
        mList.add(new TradeOtherEntity("银行转证券"));
        mList.add(new TradeOtherEntity("证券转银行"));
        mList.add(new TradeOtherEntity("资金余额"));
        mList.add(new TradeOtherEntity("转账查询"));
        adapter.addAll(mList);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position){
                    case 0:
                        Intent intent1 = new Intent();
                        intent1.putExtra("type",1);
                        intent1.setClass(TradeBankActivity.this, TradeBank2SecurityActivity.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent();
                        intent2.putExtra("type",2);
                        intent2.setClass(TradeBankActivity.this, TradeBank2SecurityActivity.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent4 = new Intent();
                        intent4.setClass(TradeBankActivity.this, TradeBank2SecurityActivity.class);
                        intent4.putExtra("type",3);
                        startActivity(intent4);
                        break;
                    case 3:
                        Intent intent5 = new Intent();
                        intent5.setClass(TradeBankActivity.this, TradeBankQueryActivity.class);
                        startActivity(intent5);
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
