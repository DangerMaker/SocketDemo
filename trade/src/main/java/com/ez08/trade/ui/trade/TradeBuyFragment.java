package com.ez08.trade.ui.trade;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.Callback;
import com.ez08.trade.net.Client;
import com.ez08.trade.tools.CommonUtils;
import com.ez08.trade.tools.DialogUtils;
import com.ez08.trade.tools.MathUtils;
import com.ez08.trade.tools.YCParser;
import com.ez08.trade.tools.YiChuangUtils;
import com.ez08.trade.ui.BaseFragment;
import com.ez08.trade.ui.trade.entity.TradeStockEntity;
import com.ez08.trade.ui.view.AdjustEditText;
import com.ez08.trade.ui.view.FiveAndTenView;
import com.ez08.trade.user.TradeUser;
import com.ez08.trade.user.UserHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TradeBuyFragment extends BaseFragment implements OptionsDelegate {

    public static TradeBuyFragment newInstance(int type) {
        Bundle args = new Bundle();
        TradeBuyFragment fragment = new TradeBuyFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    RelativeLayout tradeViewContainer;
    ITradeView tradeViewImpl;
    FiveAndTenView fiveAndTenView;
    EditText editText;
    AdjustEditText currPrice;

    TextView newestPrice;
    TextView lastPrice;
    TextView upPrice;
    TextView downPrice;

    int type;
    boolean bsflag = true;
    int layout = -1;

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_fragment_buy;
    }

    @Override
    protected void onCreateView(View rootView) {
        tradeViewContainer = rootView.findViewById(R.id.trade_view);
        fiveAndTenView = rootView.findViewById(R.id.five_ten_view);
        fiveAndTenView.setOnFiveListener(value -> {
            Log.e("TradeBuyFragment", value);
            if (editText!=null){
                editText.setText(value);
            }
            if (currPrice!=null){
                currPrice.setText(value);
            }
        });

        type = getArguments().getInt("type");
        if (type == 0) {
            bsflag = true;
            layout = R.layout.trade_view_limite_quote;
        } else if (type == 1) {
            bsflag = false;
            layout = R.layout.trade_view_limite_quote;
        } else if (type == 2) {
            bsflag = true;
            layout = R.layout.trade_view_market_quote;
        } else if (type == 3) {
            bsflag = false;
            layout = R.layout.trade_view_market_quote;
        } else if (type == 4) {
            bsflag = true;
            layout = R.layout.trade_view_bat_quote;
        } else if (type == 5) {
            bsflag = false;
            layout = R.layout.trade_view_bat_quote;
        } else if (type == 6) {
            bsflag = true;
            layout = R.layout.trade_view_trans_quote;
        }

        View view = View.inflate(mContext, layout, null);
        editText = view.findViewById(R.id.trade_entrust_price);
        currPrice = view.findViewById(R.id.price);
        tradeViewContainer.addView(view);
        tradeViewImpl = (ITradeView) view;
        tradeViewImpl.setBsflag(bsflag);
        tradeViewImpl.setDelegate(this);

        newestPrice = rootView.findViewById(R.id.newest_price);
        lastPrice = rootView.findViewById(R.id.last_price);
        upPrice = rootView.findViewById(R.id.limit_up_price);
        downPrice = rootView.findViewById(R.id.limit_down_price);
    }

    TradeStockEntity stockEntity;

    @Override
    public void search(String code) {
        String body = "FUN=410203&TBL_IN=market,stklevel,stkcode,poststr,rowcount,stktype;" +
                "" + "," +
                "" + "," +
                code + "," +
                "" + "," +
                "" + "," +
                ";";

        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            dismissBusyDialog();

            if (success) {
                Map<String, String> result = YCParser.parseObject(data);
                stockEntity = new TradeStockEntity();
                if (result == null) {
                    DialogUtils.showSimpleDialog(mContext, "股票输入有误");
                    return;
                }
                stockEntity.market = result.get("market");
                stockEntity.stkname = result.get("stkname");
                stockEntity.stkcode = result.get("stkcode");
                stockEntity.stopflag = result.get("stopflag");
                stockEntity.maxqty = result.get("maxqty");
                stockEntity.minqty = result.get("minqty");
                getHQQuery(stockEntity);
            }else{
                DialogUtils.showSimpleDialog(mContext, data);
            }
        });

    }

    @Override
    public void getMax(String code, String price) {
        TradeUser user = UserHelper.getUserByMarket(stockEntity.market);
        if (user == null) {
            return;
        }

        String body = "FUN=410410&TBL_IN=market,secuid,fundid,stkcode,bsflag,price,bankcode,hiqtyflag,creditid,creditflag,linkmarket,linksecuid,sorttype,dzsaletype,prodcode;" +
                stockEntity.market + "," +
                user.secuid + "," +
                user.fundid + "," +
                code + "," +
                (bsflag ? "B" : "S") + "," +
                price + "," + "," + "," + "," + "," + "," + "," + "," + "," +
                ";";

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
//                                String max = var[0];
//                                tradeViewImpl.setMax(max);
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
    }

    @Override
    public void submit(final String code, final String price, final int single, final String num, final String quoteType) {
        final String postFlag = YiChuangUtils.getTagByQuoteName(bsflag ? "B" : "S", quoteType);
        if (TextUtils.isEmpty(postFlag)) {
            CommonUtils.show(mContext, "请选择报价方式");
            return;
        }
        if (stockEntity==null) return;
        TradeUser user = UserHelper.getUserByMarket(stockEntity.market);
        if (user == null) {
            return;
        }
        String option = bsflag ? "买入" : "卖出";
        DialogUtils.showTwoButtonDialog(mContext, option + "交易确认", "确定" + option,
                "操作类型：" + option + "\n" +
                        "股票代码：" + stockEntity.stkcode + "  " + stockEntity.stkname + "\n" +
                        "委托价格：" + price + "\n" +
                        "委托数量：" + num + "\n" +
                        (single == 0 ? "" : ("单笔数量：" + single + "\n")) +
                        "委托方式：" + quoteType + "\n" +
                        "股东代码：" + user.secuid
                , (dialog, which) -> {
                    if (single != 0) {
                        int times = Integer.parseInt(num) / single;
                        for (int i = 0; i < times; i++) {
                            post(code, price, single + "", postFlag);
                        }
                    } else {
                        post(code, price, num, postFlag);
                    }
                });

    }

    private void post(String code, String price, String qty, String postFlag) {
//        TradeUser user = UserHelper.getUserByMarket(stockEntity.market);
//        if (user == null) {
//            return;
//        }
//
//        String body = "FUN=410411&TBL_IN=market,secuid,fundid,stkcode,bsflag,price,qty,ordergroup," +
//                "bankcode,creditid,creditflag,remark,targetseat,promiseno,risksno,autoflag," +
//                "enddate,linkman,linkway,linkmarket,linksecuid,sorttype,mergematchcode,mergematchdate" +
////                "oldorderid,prodcode,pricetype,blackflag,dzsaletype,risksignsno" +
//                ";" +
//                stockEntity.market + "," +
//                user.secuid + "," +
//                user.fundid + "," +
//                code + "," +
//                postFlag + "," +
//                price + "," +
//                qty + "," +
//                "0" +
////                "," + "," + "," + "," + ","
//                "," + "," + "," + "," + "," +
//                "," + "," + "," + "," + "," + "," + "," + "," + "," + "," + "," +
//                ";";
//
//        BizRequest request = new BizRequest();
//        request.setBody(body);
//        request.setCallback((client, data) -> {
//            dismissBusyDialog();
//            Log.e(TAG, data.getData());
//            try {
//                if (data.isSucceed()) {
//                    JSONObject jsonObject = new JSONObject(data.getData());
//                    String content = jsonObject.getString("content");
//                    Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
//                    Set<String> pn = uri.getQueryParameterNames();
//                    for (Iterator it = pn.iterator(); it.hasNext(); ) {
//                        String key = it.next().toString();
//                        if ("TBL_OUT".equals(key)) {
//                            String out = uri.getQueryParameter(key);
//                            String[] split = out.split(";");
//                            String[] var = split[1].split(",");
//                            DialogUtils.showSimpleDialog(mContext, "委托成功" + "\n" +
//                                    "委托序号：" + var[0] + "\n" +
//                                    "合同序号：" + var[1] + "\n" +
//                                    "委托批号：" + var[2]
//                            );
//                        }
//                    }
//
//                } else {
//                    JSONObject jsonObject = new JSONObject(data.getData());
//                    String msg = jsonObject.getString("szError");
//                    DialogUtils.showSimpleDialog(mContext, msg);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//        });
//
//        showBusyDialog();
//        ClientHelper.get().send(request);
    }

    private void getHQQuery(final TradeStockEntity entity) {
//        QueryRequest request = new QueryRequest();
//        request.setBody(entity.market, entity.stkcode);
//        request.setCallback((client, data) -> {
//            dismissBusyDialog();
//            try {
//                if (data.isSucceed()) {
//                    Log.e(TAG, data.getData());
//                    JSONObject jsonObject = new JSONObject(data.getData());
//                    entity.fOpen = jsonObject.getDouble("fOpen");
//                    entity.fLastClose = jsonObject.getDouble("fLastClose");
//                    entity.fHigh = jsonObject.getDouble("fHigh");
//                    entity.fLow = jsonObject.getDouble("fLow");
//                    entity.fNewest = jsonObject.getDouble("fNewest");
//
//                    JSONArray jsonArray = jsonObject.getJSONArray("ask");
//                    List<TradeStockEntity.Dang> list1 = new ArrayList<>();
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        TradeStockEntity.Dang dang = new TradeStockEntity.Dang();
//                        dang.fOrder = jsonArray.getJSONObject(i).getInt("fOrder");
//                        dang.fPrice = jsonArray.getJSONObject(i).getDouble("fPrice");
//                        list1.add(dang);
//                    }
//                    entity.ask = list1;
//
//                    JSONArray jsonArray1 = jsonObject.getJSONArray("bid");
//                    List<TradeStockEntity.Dang> list2 = new ArrayList<>();
//                    for (int i = 0; i < jsonArray1.length(); i++) {
//                        TradeStockEntity.Dang dang = new TradeStockEntity.Dang();
//                        dang.fOrder = jsonArray1.getJSONObject(i).getInt("fOrder");
//                        dang.fPrice = jsonArray1.getJSONObject(i).getDouble("fPrice");
//                        list2.add(dang);
//                    }
//                    entity.bid = list2;
//
//                    tradeViewImpl.setStockEntity(stockEntity);
//                    fiveAndTenView.setLevel1(stockEntity);
//                    setIndex(stockEntity);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        });
//
//        showBusyDialog();
//        ClientHelper.get().send(request);
    }

    public void setStockCode(String stockCode) {
        if (tradeViewImpl != null) {
            tradeViewImpl.setStockCode(stockCode);

        }
    }


    private void setIndex(TradeStockEntity entity) {
        newestPrice.setText(MathUtils.format2Num(entity.fNewest));
        newestPrice.setTextColor(entity.fNewest > entity.fOpen ? setTextColor(R.color.trade_red) : setTextColor(R.color.trade_green));
        lastPrice.setText(MathUtils.format2Num(entity.fLastClose));
        upPrice.setText(MathUtils.format2Num(entity.fLastClose * 1.1f));
        upPrice.setTextColor(setTextColor(R.color.trade_red));
        downPrice.setText(MathUtils.format2Num(entity.fLastClose * 0.9f));
        downPrice.setTextColor(setTextColor(R.color.trade_green));
    }

}
