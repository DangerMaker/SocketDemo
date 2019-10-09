//package com.ez08.trade.ui.trade;
//
//import android.content.DialogInterface;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//
//import com.ez08.trade.Constant;
//import com.ez08.trade.R;
//import com.ez08.trade.net.Callback;
//import com.ez08.trade.net.Client;
//import com.ez08.trade.net.ClientHelper;
//import com.ez08.trade.net.Response;
//import com.ez08.trade.net.request.BizRequest;
//import com.ez08.trade.tools.DialogUtils;
//import com.ez08.trade.tools.YiChuangUtils;
//import com.ez08.trade.ui.BaseAdapter;
//import com.ez08.trade.ui.BaseFragment;
//import com.ez08.trade.ui.Interval;
//import com.ez08.trade.ui.trade.adpater.TradeEntrustAdapter;
//import com.ez08.trade.ui.trade.entity.TradeEntrustEntity;
//import com.ez08.trade.ui.view.LinearItemDecoration;
//
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//
//public class TradeCancelFragment extends BaseFragment implements Interval {
//
//    RecyclerView recyclerView;
//    LinearLayoutManager manager;
//    List<Object> mList;
//
//    TradeEntrustAdapter adapter;
//
//    public static TradeCancelFragment newInstance() {
//        Bundle args = new Bundle();
//        TradeCancelFragment fragment = new TradeCancelFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    protected int getLayoutResource() {
//        return R.layout.trade_fragment_cancel;
//    }
//
//    @Override
//    protected void onCreateView(View rootView) {
//        recyclerView = rootView.findViewById(R.id.recycler_view);
//        manager = new LinearLayoutManager(mContext);
//        recyclerView.setLayoutManager(manager);
//
//        adapter = new TradeEntrustAdapter(mContext);
//        recyclerView.setAdapter(adapter);
//
//        LinearItemDecoration divider = new LinearItemDecoration(mContext);
//        recyclerView.addItemDecoration(divider);
//
//        mList = new ArrayList<>();
//        adapter.addAll(mList);
//        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                final TradeEntrustEntity entrustEntity = (TradeEntrustEntity) mList.get(position);
//                DialogUtils.showTwoButtonDialog(mContext, "撤单", "确定",
//                        "操作类型：" + "撤单" + "\n" +
//                                "买卖方向：" + YiChuangUtils.getBSStringByTag(entrustEntity.bsflag) + "\n" +
//                                "证券代码：" + entrustEntity.stkcode + " " + entrustEntity.stkname + "\n" +
//                                "合同编号：" + entrustEntity.ordersno
//                        , new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                post(entrustEntity);
//                            }
//                        });
//            }
//        });
//
//        getWTInfo();
//    }
//
//    @Override
//    public void OnPost() {
//
//    }
//
//    public void getWTInfo() {
//        String body = "FUN=410415&TBL_IN=orderdate,fundid,secuid,stkcode,ordersno,qryflag,count,poststr;" +
//                "" + "," +
//                "" + "," +
//                "" + "," +
//                "" + "," +
//                "" + "," +
//                "1" + "," +
//                "100" + "," +
//                ";";
//
//        BizRequest request = new BizRequest();
//        request.setBody(body);
//        request.setCallback(new Callback() {
//            @Override
//            public void callback(Client client, Response data) {
//                if (data.isSucceed()) {
//                    Log.e(TAG, data.getData());
//                    try {
//                        JSONObject jsonObject = new JSONObject(data.getData());
//                        String content = jsonObject.getString("content");
//                        Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
//                        Set<String> pn = uri.getQueryParameterNames();
//                        for (Iterator it = pn.iterator(); it.hasNext(); ) {
//                            String key = it.next().toString();
//                            if ("TBL_OUT".equals(key)) {
//                                String out = uri.getQueryParameter(key);
//                                String[] split = out.split(";");
//                                for (int i = 1; i < split.length; i++) {
//                                    String[] var = split[i].split(",");
//                                    TradeEntrustEntity entity = new TradeEntrustEntity();
//                                    entity.stkcode = var[9];
//                                    entity.stkname = var[10];
//                                    entity.orderprice = var[12];
//                                    entity.opertime = var[5];
//                                    entity.orderdate = var[4];
//                                    entity.orderqty = var[13];
//                                    entity.matchqty = var[14];
//                                    entity.bsflag = var[11];
//                                    entity.orderstatus = var[15];
//                                    entity.ordersno = var[1];
//                                    entity.fundid = var[6];
//                                    mList.add(entity);
//                                }
//                                adapter.clearAndAddAll(mList);
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        });
//        ClientHelper.get().send(request);
//    }
//
//    private void post(TradeEntrustEntity entrustEntity) {
//        String body = "FUN=410413&TBL_IN=orderdate,fundid,ordersno,bsflag;" +
//                entrustEntity.orderdate + "," +
//                entrustEntity.fundid + "," +
//                entrustEntity.ordersno + "," +
//                entrustEntity.bsflag +
//                ";";
//
//        BizRequest request = new BizRequest();
//        request.setBody(body);
//        request.setCallback(new Callback() {
//            @Override
//            public void callback(Client client, Response data) {
//                dismissBusyDialog();
//                Log.e(TAG, data.getData());
//                try {
//                    if (data.isSucceed()) {
//                        JSONObject jsonObject = new JSONObject(data.getData());
//                        String content = jsonObject.getString("content");
//                        Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
//                        Set<String> pn = uri.getQueryParameterNames();
//                        for (Iterator it = pn.iterator(); it.hasNext(); ) {
//                            String key = it.next().toString();
//                            if ("TBL_OUT".equals(key)) {
//                                String out = uri.getQueryParameter(key);
//                                String[] split = out.split(";");
//                                String[] var = split[1].split(",");
//                                DialogUtils.showSimpleDialog(mContext, var[1]);
//                            }
//                        }
//
//                    } else {
//                        JSONObject jsonObject = new JSONObject(data.getData());
//                        String msg = jsonObject.getString("szError");
//                        DialogUtils.showSimpleDialog(mContext, msg);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//        });
//
//        showBusyDialog();
//        ClientHelper.get().send(request);
//    }
//}
