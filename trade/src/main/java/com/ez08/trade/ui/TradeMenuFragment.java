package com.ez08.trade.ui;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ez08.trade.R;
import com.ez08.trade.ui.trade.adpater.TradeMainAdapter;
import com.ez08.trade.ui.trade.entity.TradeOptionEntity;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;
import com.ez08.trade.ui.view.MenuGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TradeMenuFragment extends BaseFragment {

    RecyclerView recyclerView;
    GridLayoutManager manager;
    List<Object> mList;

    TradeMainAdapter adapter;

    public static TradeMenuFragment newInstance() {
        Bundle args = new Bundle();
        TradeMenuFragment fragment = new TradeMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.trade_fragment_main;
    }

    @Override
    protected void onCreateView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recycler_view);
        manager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                int type = recyclerView.getAdapter().getItemViewType(i);
                switch (type) {
                    case TradeMainAdapter.TRADE_OPTION:
                        return 1;
                }
                return 4;
            }
        });

        adapter = new TradeMainAdapter(mContext);
        recyclerView.setAdapter(adapter);

        MenuGridItemDecoration divider = new MenuGridItemDecoration(mContext);
        recyclerView.addItemDecoration(divider);

        mList = new ArrayList<>();
        mList.add(new TradeOptionEntity("买","买入", R.drawable.menu_buy,0));
        mList.add(new TradeOptionEntity("卖","卖出", R.drawable.menu_sell,1));
        mList.add(new TradeOptionEntity("撤","撤单", R.drawable.menu_cancel,2));
        mList.add(new TradeOptionEntity("持","持仓", R.drawable.menu_holder,3));
        mList.add(new TradeOptionEntity("市","市价买卖", R.drawable.meun_market_price,-1));
        mList.add(new TradeOptionEntity("对","对买对卖", R.drawable.menu_buy_sell,-1));
        mList.add(new TradeOptionEntity("批","批量委托", R.drawable.menu_batch,-1));
        mList.add(new TradeOptionEntity("查","查询", R.drawable.menu_query,-1));
        mList.add(new TradeOtherEntity("新股申购"));
        mList.add(new TradeOtherEntity("银行转账"));
        mList.add(new TradeOtherEntity("预埋单"));
        mList.add(new TradeOtherEntity("预受要约"));
        mList.add(new TradeOtherEntity("网络投票"));
        mList.add(new TradeOtherEntity("转股回售"));
        mList.add(new TradeOtherEntity("股东资料"));
        mList.add(new TradeOtherEntity("客户风险级别查询"));
        mList.add(new TradeOtherEntity("客户风险级别测评"));
        mList.add(new TradeOtherEntity("修改资料"));
        mList.add(new TradeOtherEntity("修改密码"));
        mList.add(new TradeOtherEntity("退出登录"));
        adapter.addAll(mList);

    }
}
