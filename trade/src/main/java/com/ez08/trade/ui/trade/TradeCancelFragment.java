package com.ez08.trade.ui.trade;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.Callback;
import com.ez08.trade.net.Client;
import com.ez08.trade.tools.DialogUtils;
import com.ez08.trade.tools.YCParser;
import com.ez08.trade.tools.YiChuangUtils;
import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.BaseFragment;
import com.ez08.trade.ui.Interval;
import com.ez08.trade.ui.trade.adpater.TradeEntrustAdapter;
import com.ez08.trade.ui.trade.entity.TradeEntrustEntity;
import com.ez08.trade.ui.user.entity.ShareHoldersEntity;
import com.ez08.trade.ui.user.entity.TradeShareHoldersTitle;
import com.ez08.trade.ui.view.LinearItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TradeCancelFragment extends BaseFragment implements Interval {

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<Object> mList;

    TradeEntrustAdapter adapter;

    public static TradeCancelFragment newInstance() {
        Bundle args = new Bundle();
        TradeCancelFragment fragment = new TradeCancelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_fragment_cancel;
    }

    @Override
    protected void onCreateView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(manager);

        adapter = new TradeEntrustAdapter(mContext);
        recyclerView.setAdapter(adapter);

        LinearItemDecoration divider = new LinearItemDecoration(mContext);
        recyclerView.addItemDecoration(divider);

        mList = new ArrayList<>();
        adapter.addAll(mList);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final TradeEntrustEntity entrustEntity = (TradeEntrustEntity) mList.get(position);
                DialogUtils.showTwoButtonDialog(mContext, "撤单", "确定",
                        "操作类型：" + "撤单" + "\n" +
                                "买卖方向：" + YiChuangUtils.getBSStringByTag(entrustEntity.bsflag) + "\n" +
                                "证券代码：" + entrustEntity.stkcode + " " + entrustEntity.stkname + "\n" +
                                "合同编号：" + entrustEntity.ordersno
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                post(entrustEntity);
                            }
                        });
            }
        });

        getWTInfo();
    }

    @Override
    public void OnPost() {

    }

    public void getWTInfo() {
        String body = "FUN=410415&TBL_IN=orderdate,fundid,secuid,stkcode,ordersno,qryflag,count,poststr;" +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "1" + "," +
                "100" + "," +
                ";";
        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            if (success) {
                List<Map<String, String>> result = YCParser.parseArray(data);
                for (int i = 0; i < result.size(); i++) {
                    TradeEntrustEntity entity = new TradeEntrustEntity();
                    entity.stkcode = result.get(i).get("stkcode");
                    entity.stkname = result.get(i).get("stkname");
                    entity.orderprice = result.get(i).get("orderprice");
                    entity.opertime = result.get(i).get("opertime");
                    entity.orderdate = result.get(i).get("orderdate");
                    entity.orderqty = result.get(i).get("orderqty");
                    entity.matchqty = result.get(i).get("matchqty");
                    entity.bsflag = result.get(i).get("bsflag");
                    entity.orderstatus = result.get(i).get("orderstatus");
                    entity.ordersno = result.get(i).get("ordersno");
                    entity.fundid = result.get(i).get("fundid");
                    mList.add(entity);
                }

                adapter.clearAndAddAll(mList);
            }
        });
    }

    private void post(TradeEntrustEntity entrustEntity) {
        String body = "FUN=410413&TBL_IN=orderdate,fundid,ordersno,bsflag;" +
                entrustEntity.orderdate + "," +
                entrustEntity.fundid + "," +
                entrustEntity.ordersno + "," +
                entrustEntity.bsflag +
                ";";

        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            dismissBusyDialog();
            if (success) {
                Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + data);
                Set<String> pn = uri.getQueryParameterNames();
                for (Iterator it = pn.iterator(); it.hasNext(); ) {
                    String key = it.next().toString();
                    if ("TBL_OUT".equals(key)) {
                        String out = uri.getQueryParameter(key);
                        String[] split = out.split(";");
                        String[] var = split[1].split(",");
                        DialogUtils.showSimpleDialog(mContext, var[0]);
                    }
                }
            }else{
                DialogUtils.showSimpleDialog(mContext, data);
            }
        });
        showBusyDialog();
    }
}
