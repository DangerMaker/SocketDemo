//package com.ez08.trade.ui.trade;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.ez08.trade.R;
//import com.ez08.trade.net.NetHelper;
//import com.ez08.trade.net.OnResult;
//import com.ez08.trade.tools.CommonUtils;
//import com.ez08.trade.tools.DialogUtils;
//import com.ez08.trade.ui.BaseFragment;
//import com.ez08.trade.ui.trade.entity.TradeLevel1Entity;
//import com.ez08.trade.ui.trade.entity.TradeResultEntity;
//import com.ez08.trade.ui.trade.entity.TradeStockEntity;
//import com.ez08.trade.ui.view.AdjustEditText;
//import com.ez08.trade.ui.view.FourPriceView;
//import com.ez08.trade.ui.view.InputCodeView;
//import com.ez08.trade.ui.view.Level1HorizontalView;
//
//public class TradeBuySellFragment1 extends BaseFragment implements View.OnClickListener {
//
//    FourPriceView fourPriceView;
//    InputCodeView codeView;
//    Level1HorizontalView level1View;
//    AdjustEditText priceBuy;
//    AdjustEditText priceSell;
//    AdjustEditText numBuy;
//    AdjustEditText numSell;
//    TextView availableNum;
//    TextView availableSellNum;
//    Button submit;
//    Button submitSell;
//
//    TradeStockEntity stockEntity;
//
//    public static TradeBuySellFragment1 newInstance() {
//        Bundle args = new Bundle();
//        TradeBuySellFragment1 fragment = new TradeBuySellFragment1();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    protected int getLayoutResource() {
//        return R.layout.trade_fragment_buy_sell1;
//    }
//
//    @Override
//    protected void onCreateView(View rootView) {
//        fourPriceView = rootView.findViewById(R.id.four);
//        level1View = rootView.findViewById(R.id.level1_horizontal);
//        priceBuy = rootView.findViewById(R.id.price);
//        priceSell = rootView.findViewById(R.id.price_sell);
//        numBuy = rootView.findViewById(R.id.total_num);
//        numSell = rootView.findViewById(R.id.total_num_sell);
//        codeView = rootView.findViewById(R.id.input_code);
//        availableNum = rootView.findViewById(R.id.available_num);
//        availableSellNum = rootView.findViewById(R.id.available_num_sell);
//        submit = rootView.findViewById(R.id.submit);
//        submitSell = rootView.findViewById(R.id.submit_sell);
//        submit.setOnClickListener(this);
//        submitSell.setOnClickListener(this);
//
//        codeView.setOnSearchListener(code -> searchReq(code));
//
//        level1View.setOnFiveListener(value -> {
//            if (priceBuy!=null){
//                priceBuy.setText(value);
//            }
//            if (priceSell!=null){
//                priceSell.setText(value);
//            }
//        });
//    }
//    public void setStockCode(String stockCode) {
//        searchReq(stockCode);
//    }
//    private void searchReq(String code) {
//        showBusyDialog();
//        NetHelper.searchStock(code, new OnResult<TradeStockEntity>() {
//            @Override
//            public void onSucceed(TradeStockEntity response) {
//                dismissBusyDialog();
//                stockEntity = response;
//                codeView.setData(response.stkcode, response.stkname);
//                priceBuy.setText(response.fixprice);
//                priceSell.setText(response.fixprice);
//                getHQ(response.market, response.stkcode);
//                getMax(response.market, response.stkcode, response.fixprice, "0B");
//                getMax(response.market, response.stkcode, response.fixprice, "0S");
//            }
//
//            @Override
//            public void onFailure(Error error) {
//                dismissBusyDialog();
//                DialogUtils.showSimpleDialog(mContext, error.szError);
//            }
//        });
//    }
//
//    private void getHQ(String market, String code) {
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
//    }
//
//    private void transaction(String flag) {
//        NetHelper.transaction(stockEntity.market, stockEntity.stkcode, priceBuy.getText(),
//                numBuy.getText(), flag, new OnResult<TradeResultEntity>() {
//                    @Override
//                    public void onSucceed(TradeResultEntity response) {
//                        DialogUtils.showSimpleDialog(mContext, "委托成功" + "\n" +
//                                "委托序号：" + response.ordersno + "\n" +
//                                "合同序号：" + response.orderid + "\n" +
//                                "委托批号：" + response.ordergroup
//                        );
//                    }
//
//                    @Override
//                    public void onFailure(Error error) {
//                        DialogUtils.showSimpleDialog(mContext, error.szError);
//                    }
//                });
//    }
//
//    private void getMax(String market, String code, String price, final String flag) {
//        NetHelper.getMax(market, code, price, flag, new OnResult<String>() {
//            @Override
//            public void onSucceed(String response) {
//                if (flag.equals("0B")) {
//                    availableNum.setText("最多可买" + response + "股");
//                } else {
//                    availableSellNum.setText("最多可卖" + response + "股");
//                }
//            }
//
//            @Override
//            public void onFailure(Error error) {
//
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v == submit) {
//            if (isInvalid(stockEntity.stkcode, priceBuy.getText(), numBuy.getText())){
//                transaction("0B");
//            }
//        }
//    }
//    private boolean isInvalid(String stockCode, String price, String num) {
//        if (stockEntity==null|| TextUtils.isEmpty(stockCode) || TextUtils.isEmpty(price) || TextUtils.isEmpty(num)) {
//            CommonUtils.show(mContext, R.string.trade_notice_msg);
//            return false;
//        }
//        return true;
//
//    }
//}
