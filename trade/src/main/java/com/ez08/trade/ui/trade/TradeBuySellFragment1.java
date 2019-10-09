package com.ez08.trade.ui.trade;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.Client;
import com.ez08.trade.tools.CommonUtils;
import com.ez08.trade.tools.DialogUtils;
import com.ez08.trade.tools.YCParser;
import com.ez08.trade.ui.BaseFragment;
import com.ez08.trade.ui.trade.entity.TradeLevel1Entity;
import com.ez08.trade.ui.trade.entity.TradeResultEntity;
import com.ez08.trade.ui.trade.entity.TradeStockEntity;
import com.ez08.trade.ui.view.AdjustEditText;
import com.ez08.trade.ui.view.FourPriceView;
import com.ez08.trade.ui.view.InputCodeView;
import com.ez08.trade.ui.view.Level1HorizontalView;
import com.ez08.trade.user.TradeUser;
import com.ez08.trade.user.UserHelper;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TradeBuySellFragment1 extends BaseFragment implements View.OnClickListener {

    FourPriceView fourPriceView;
    InputCodeView codeView;
    Level1HorizontalView level1View;
    AdjustEditText priceBuy;
    AdjustEditText priceSell;
    AdjustEditText numBuy;
    AdjustEditText numSell;
    TextView availableNum;
    TextView availableSellNum;
    Button submit;
    Button submitSell;

    TradeStockEntity stockEntity;

    public static TradeBuySellFragment1 newInstance() {
        Bundle args = new Bundle();
        TradeBuySellFragment1 fragment = new TradeBuySellFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_fragment_buy_sell1;
    }

    @Override
    protected void onCreateView(View rootView) {
        fourPriceView = rootView.findViewById(R.id.four);
        level1View = rootView.findViewById(R.id.level1_horizontal);
        priceBuy = rootView.findViewById(R.id.price);
        priceSell = rootView.findViewById(R.id.price_sell);
        numBuy = rootView.findViewById(R.id.total_num);
        numSell = rootView.findViewById(R.id.total_num_sell);
        codeView = rootView.findViewById(R.id.input_code);
        availableNum = rootView.findViewById(R.id.available_num);
        availableSellNum = rootView.findViewById(R.id.available_num_sell);
        submit = rootView.findViewById(R.id.submit);
        submitSell = rootView.findViewById(R.id.submit_sell);
        submit.setOnClickListener(this);
        submitSell.setOnClickListener(this);

        codeView.setOnSearchListener(code -> searchReq(code));

        level1View.setOnFiveListener(value -> {
            if (priceBuy!=null){
                priceBuy.setText(value);
            }
            if (priceSell!=null){
                priceSell.setText(value);
            }
        });
    }
    public void setStockCode(String stockCode) {
        searchReq(stockCode);
    }
    private void searchReq(String code) {
        showBusyDialog();

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
                TradeStockEntity stockEntity = new TradeStockEntity();
                stockEntity.market = result.get("market");
                stockEntity.stkname = result.get("stkname");
                stockEntity.stkcode = result.get("stkcode");
                stockEntity.stopflag = result.get("stopflag");
                stockEntity.maxqty = result.get("maxqty");
                stockEntity.minqty = result.get("minqty");
                stockEntity.fixprice = result.get("fixprice");
                onSucceed(stockEntity);
            }else{
                DialogUtils.showSimpleDialog(mContext, data);
            }
        });
    }

    private void getHQ(String market, String code) {
//
//        NetHelper.getLevel1(market, code, new OnResult<TradeLevel1Entity>() {
//            @Override
//            public void onSucceed(TradeLevel1Entity response) {
//                fourPriceView.setData(response);
//                level1View.setData(response);
//            }
//
//            @Override
//            public void onFailure(Error error) {
//                DialogUtils.showSimpleDialog(mContext, error.szError);
//            }
//        });
    }

    private void transaction(String flag) {
        String market = stockEntity.market;
        String code = stockEntity.stkcode;
        TradeUser user = UserHelper.getUserByMarket(market);
        if (user == null) {
            return;
        }
        String body = "FUN=410411&TBL_IN=market,secuid,fundid,stkcode,bsflag,price,qty,ordergroup,bankcode,remark" +
                ";" +
                market + "," +
                user.secuid + "," +
                user.fundid + "," +
                code + "," +
                flag + "," +
                priceBuy.getText() + "," +
                numBuy.getText() + "," +
                "0" + "," +
                "" + "," +
                ";";

        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);

            if (success) {
                Map<String, String> result = YCParser.parseObject(data);
                TradeResultEntity entity = new TradeResultEntity();
                entity.ordersno = result.get("ordersno");
                entity.orderid = result.get("orderid");
                entity.ordergroup = result.get("ordergroup");
                DialogUtils.showSimpleDialog(mContext, "委托成功" + "\n" +
                        "委托序号：" + entity.ordersno + "\n" +
                        "合同序号：" + entity.orderid + "\n" +
                        "委托批号：" + entity.ordergroup
                );
            } else {
                DialogUtils.showSimpleDialog(mContext, data);
            }
        });

    }

    private void getMax(String market, String code, String price, final String flag) {
        TradeUser user = UserHelper.getUserByMarket(market);
        if (user == null) {
            return;
        }

        String body = "FUN=410410&TBL_IN=market,secuid,fundid,stkcode,bsflag,price,bankcode,hiqtyflag,creditid,creditflag,linkmarket,linksecuid,sorttype,dzsaletype,prodcode;" +
                market + "," +
                user.secuid + "," +
                user.fundid + "," +
                code + "," +
                flag + "," +
                price + "," + "," + "," + "," + "," + "," + "," + "," + "," +
                ";";

        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);

            if (success) {
                Map<String, String> result = YCParser.parseObject(data);
                String maxstkqty = result.get("maxstkqty");
                if (flag.equals("0B")) {
                    availableNum.setText("最多可买" + maxstkqty + "股");
                } else {
                    availableSellNum.setText("最多可卖" + maxstkqty + "股");
                }
            } else {
                DialogUtils.showSimpleDialog(mContext, data);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == submit) {
            if (isInvalid(stockEntity.stkcode, priceBuy.getText(), numBuy.getText())){
                transaction("0B");
            }
        }
    }
    private boolean isInvalid(String stockCode, String price, String num) {
        if (stockEntity==null|| TextUtils.isEmpty(stockCode) || TextUtils.isEmpty(price) || TextUtils.isEmpty(num)) {
            CommonUtils.show(mContext, R.string.trade_notice_msg);
            return false;
        }
        return true;

    }

    public void onSucceed(TradeStockEntity response) {
        stockEntity = response;
        codeView.setData(response.stkcode, response.stkname);
        priceBuy.setText(response.fixprice);
        priceSell.setText(response.fixprice);
        getHQ(response.market, response.stkcode);
        getMax(response.market, response.stkcode, response.fixprice, "0B");
        getMax(response.market, response.stkcode, response.fixprice, "0S");
    }
}
