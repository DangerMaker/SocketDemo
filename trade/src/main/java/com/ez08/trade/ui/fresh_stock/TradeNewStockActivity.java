package com.ez08.trade.ui.fresh_stock;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.fresh_stock.adpater.TradeFreshListAdapter;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;
import com.ez08.trade.ui.view.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TradeNewStockActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;
    RecyclerView recyclerView;
    GridLayoutManager manager;
    List<Object> mList;

    TradeFreshListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_custom);
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        titleView = findViewById(R.id.title);
        titleView.setText("新股申购");

        recyclerView = findViewById(R.id.recycler_view);
        manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                int type = recyclerView.getAdapter().getItemViewType(i);
                switch (type) {
                    case TradeFreshListAdapter.TRADE_DRAW_LEFT:
                        return 2;
                }
                return 4;
            }
        });

        adapter = new TradeFreshListAdapter(this);
        recyclerView.setAdapter(adapter);

        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        mList = new ArrayList<>();
        mList.add(new TradeOtherEntity("配号查询"));
        mList.add(new TradeOtherEntity("中签查询"));
        mList.add(new TradeOtherEntity("新股申购代缴款查询"));
        adapter.addAll(mList);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_back){
            finish();
        }
    }
}
