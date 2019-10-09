//package com.ez08.trade.ui.trade;
//
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.widget.NestedScrollView;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//
//import com.ez08.trade.Constant;
//import com.ez08.trade.R;
//import com.ez08.trade.net.ClientHelper;
//import com.ez08.trade.net.request.BizRequest;
//import com.ez08.trade.tools.MathUtils;
//import com.ez08.trade.ui.BaseFragment;
//import com.ez08.trade.ui.Interval;
//import com.ez08.trade.ui.trade.adpater.TradeActionAdapter;
//import com.ez08.trade.ui.trade.entity.TradeHandEntity;
//import com.ez08.trade.ui.view.LinearItemDecoration;
//import com.ez08.trade.user.UserHelper;
//
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//
//public class TradeOptionFragment extends BaseFragment implements Interval, TradeHandFragment.OnHandFragmentListener {
//    Fragment fragment;
//    RecyclerView recyclerView;
//    NestedScrollView scrollView;
//    LinearLayoutManager manager;
//    List<Object> mList;
//
//    TradeActionAdapter adapter;
//    FragmentManager fragmentManager;
//    int type = 0; //0 限价买入 1限价卖出 2持仓 3市价买入 4市价卖出 5批量买入 6 批量卖出 7转股回售 8对买对卖
//
//    public static TradeOptionFragment newInstance(int type) {
//        Bundle args = new Bundle();
//        TradeOptionFragment fragment = new TradeOptionFragment();
//        args.putInt("type", type);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    protected int getLayoutResource() {
//        return R.layout.trade_fragment_option;
//    }
//
//    @Override
//    protected void onCreateView(View rootView) {
//
//        type = getArguments().getInt("type");
//        fragmentManager = getChildFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        if (type == 0) {
//            fragment = TradeBuyFragment.newInstance(0);
//        } else if (type == 1) {
//            fragment = TradeBuyFragment.newInstance(1);
//        } else if (type == 2) {
//            fragment = TradeHandFragment.newInstance();
//            ((TradeHandFragment)fragment).setOnHandFragmentListener(this);
//        } else if (type == 3) {
//            fragment = TradeBuyFragment.newInstance(2);
//        } else if (type == 4) {
//            fragment = TradeBuyFragment.newInstance(3);
//        } else if (type == 5) {
//            fragment = TradeBuyFragment.newInstance(4);
//        } else if (type == 6) {
//            fragment = TradeBuyFragment.newInstance(5);
//        } else if (type == 7) {
//            fragment = TradeBuyFragment.newInstance(6);
//        } else if (type == 8) {
//            fragment = TradeBuySellFragment1.newInstance();
//        }
//
//        transaction.add(R.id.container, fragment);
//        transaction.commitNowAllowingStateLoss();
//
//        recyclerView = rootView.findViewById(R.id.recycler_view);
//        manager = new LinearLayoutManager(mContext);
//        recyclerView.setLayoutManager(manager);
//        adapter = new TradeActionAdapter(mContext);
//        recyclerView.setAdapter(adapter);
//        LinearItemDecoration divider = new LinearItemDecoration(mContext);
//        recyclerView.addItemDecoration(divider);
//        scrollView = rootView.findViewById(R.id.nested_scroll);
//
//        mList = new ArrayList<>();
//        adapter.addAll(mList);
//        adapter.setOnItemClickListener(position -> {
//            scrollView.scrollTo(0, 0);
//            Log.e("TradeOptionFragment", ((TradeHandEntity) mList.get(position)).stkname);
//            if (fragment instanceof TradeBuyFragment) {
//                ((TradeBuyFragment) fragment).setStockCode(((TradeHandEntity) mList.get(position)).stkcode);
//            }else if (fragment instanceof TradeBuySellFragment1){
//                ((TradeBuySellFragment1)fragment).setStockCode(((TradeHandEntity) mList.get(position)).stkcode);
//            }
//        });
//
//        getHandler();
//    }
//
//    private void getHandler() {
//        String body = "FUN=410503&TBL_IN=market,fundid,secuid,stkcode,qryflag,count,poststr;" +
//                "," +
//                UserHelper.getUser().fundid + "," +
//                ",,1,100,;";
//        BizRequest request = new BizRequest();
//        request.setBody(body);
//        request.setCallback((client, data) -> {
//            if (data.isSucceed()) {
//                Log.e("TradeHandFragment", data.getData());
//                try {
//                    JSONObject jsonObject = new JSONObject(data.getData());
//                    String content = jsonObject.getString("content");
//                    Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
//                    Set<String> pn = uri.getQueryParameterNames();
//                    for (Iterator it = pn.iterator(); it.hasNext(); ) {
//                        String key = it.next().toString();
//                        if ("TBL_OUT".equals(key)) {
//                            String out = uri.getQueryParameter(key);
//                            String[] split = out.split(";");
//                            for (int i = 1; i < split.length; i++) {
//                                String[] var = split[i].split(",");
//                                TradeHandEntity entity = new TradeHandEntity();
//                                entity.stkcode = var[5];
//                                entity.stkname = var[6];
//                                entity.market = var[3];
//                                entity.stkbal = var[15];
//                                entity.stkavl = var[10];
//                                entity.costprice = var[31];
//                                entity.mktval = var[16];
//                                entity.income = var[20];
//                                entity.lastprice = var[19];
//                                entity.moneyType = var[8];
//                                mList.add(entity);
//                                adapter.clearAndAddAll(mList);
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        ClientHelper.get().send(request);
//    }
//
//    @Override
//    public void OnPost() {
//
//    }
//
//    @Override
//    public void onHandValue(String currMoneytype) {
//        if (!mList.isEmpty()){
//            double income = 0;
//            for (int i = 0; i <mList.size() ; i++) {
//                String proincome = ((TradeHandEntity) mList.get(i)).income;
//                String moneyType = ((TradeHandEntity) mList.get(i)).moneyType;
//                if (!TextUtils.isEmpty(proincome)&&currMoneytype.equals(moneyType)){
//                    income += Double.parseDouble(proincome);
//                }
//            }
//            ((TradeHandFragment)fragment).setYingkui(MathUtils.format2Num(income));
//        }
//    }
//}
