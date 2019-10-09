package com.ez08.trade.ui.trade;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.Client;
import com.ez08.trade.tools.MathUtils;
import com.ez08.trade.tools.YCParser;
import com.ez08.trade.ui.BaseFragment;
import com.ez08.trade.ui.Interval;
import com.ez08.trade.ui.trade.adpater.TradeActionAdapter;
import com.ez08.trade.ui.trade.entity.TradeHandEntity;
import com.ez08.trade.ui.user.entity.ShareHoldersEntity;
import com.ez08.trade.ui.user.entity.TradeShareHoldersTitle;
import com.ez08.trade.ui.view.LinearItemDecoration;
import com.ez08.trade.user.UserHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TradeOptionFragment extends BaseFragment implements Interval, TradeHandFragment.OnHandFragmentListener {
    Fragment fragment;
    RecyclerView recyclerView;
    NestedScrollView scrollView;
    LinearLayoutManager manager;
    List<Object> mList;

    TradeActionAdapter adapter;
    FragmentManager fragmentManager;
    int type = 0; //0 限价买入 1限价卖出 2持仓 3市价买入 4市价卖出 5批量买入 6 批量卖出 7转股回售 8对买对卖

    public static TradeOptionFragment newInstance(int type) {
        Bundle args = new Bundle();
        TradeOptionFragment fragment = new TradeOptionFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_fragment_option;
    }

    @Override
    protected void onCreateView(View rootView) {

        type = getArguments().getInt("type");
        fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (type == 0) {
            fragment = TradeBuyFragment.newInstance(0);
        } else if (type == 1) {
            fragment = TradeBuyFragment.newInstance(1);
        } else if (type == 2) {
            fragment = TradeHandFragment.newInstance();
            ((TradeHandFragment)fragment).setOnHandFragmentListener(this);
        } else if (type == 3) {
            fragment = TradeBuyFragment.newInstance(2);
        } else if (type == 4) {
            fragment = TradeBuyFragment.newInstance(3);
        } else if (type == 5) {
            fragment = TradeBuyFragment.newInstance(4);
        } else if (type == 6) {
            fragment = TradeBuyFragment.newInstance(5);
        } else if (type == 7) {
            fragment = TradeBuyFragment.newInstance(6);
        } else if (type == 8) {
            fragment = TradeBuySellFragment1.newInstance();
        }

        transaction.add(R.id.container, fragment);
        transaction.commitNowAllowingStateLoss();

        recyclerView = rootView.findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(manager);
        adapter = new TradeActionAdapter(mContext);
        recyclerView.setAdapter(adapter);
        LinearItemDecoration divider = new LinearItemDecoration(mContext);
        recyclerView.addItemDecoration(divider);
        scrollView = rootView.findViewById(R.id.nested_scroll);

        mList = new ArrayList<>();
        adapter.addAll(mList);
        adapter.setOnItemClickListener(position -> {
            scrollView.scrollTo(0, 0);
            Log.e("TradeOptionFragment", ((TradeHandEntity) mList.get(position)).stkname);
            if (fragment instanceof TradeBuyFragment) {
                ((TradeBuyFragment) fragment).setStockCode(((TradeHandEntity) mList.get(position)).stkcode);
            }else if (fragment instanceof TradeBuySellFragment1){
                ((TradeBuySellFragment1)fragment).setStockCode(((TradeHandEntity) mList.get(position)).stkcode);
            }
        });

        getHandler();
    }

    private void getHandler() {
        String body = "FUN=410503&TBL_IN=market,fundid,secuid,stkcode,qryflag,count,poststr;" +
                "," +
                UserHelper.getUser().fundid + "," +
                ",,1,100,;";

        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            if (success) {
                List<Map<String, String>> result = YCParser.parseArray(data);
                for (int i = 0; i < result.size(); i++) {
                    TradeHandEntity entity = new TradeHandEntity();
                    entity.stkcode = result.get(i).get("stkcode");
                    entity.stkname = result.get(i).get("stkname");
                    entity.market = result.get(i).get("market");
                    entity.stkbal = result.get(i).get("stkbal");
                    entity.stkavl = result.get(i).get("stkavl");
                    entity.costprice = result.get(i).get("costprice");
                    entity.mktval = result.get(i).get("mktval");
                    entity.income = result.get(i).get("income");
                    entity.lastprice = result.get(i).get("lastprice");
                    entity.moneyType = result.get(i).get("moneyType");
                    mList.add(entity);
                }

                adapter.clearAndAddAll(mList);
            }
        });
    }

    @Override
    public void OnPost() {

    }

    @Override
    public void onHandValue(String currMoneytype) {
        if (!mList.isEmpty()){
            double income = 0;
            for (int i = 0; i <mList.size() ; i++) {
                String proincome = ((TradeHandEntity) mList.get(i)).income;
                String moneyType = ((TradeHandEntity) mList.get(i)).moneyType;
                if (!TextUtils.isEmpty(proincome)&&currMoneytype.equals(moneyType)){
                    income += Double.parseDouble(proincome);
                }
            }
            ((TradeHandFragment)fragment).setYingkui(MathUtils.format2Num(income));
        }
    }
}
