package com.ez08.trade.ui.other;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.Callback;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.STradeHQOrderItem;
import com.ez08.trade.net.STradeHQQuery;
import com.ez08.trade.net.STradeHQQueryA;
import com.ez08.trade.tools.CommonUtils;
import com.ez08.trade.tools.DialogUtils;
import com.ez08.trade.tools.MathUtils;
import com.ez08.trade.tools.YCParser;
import com.ez08.trade.tools.YiChuangUtils;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.trade.entity.TradeStockEntity;
import com.xuhao.didi.core.pojo.OriginalData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TradeNewOrderActivity extends BaseActivity implements View.OnClickListener {
    ImageView backBtn;
    TextView titleView;

    TextView newestPrice;
    TextView lastPrice;
    TextView upPrice;
    TextView downPrice;
    EditText code;
    LinearLayout typeLayout;
    LinearLayout quoteLayout;
    TextView buyDict;
    TextView quoteWay;
    EditText price;
    EditText number;
    Button submit;

    TradeStockEntity stockEntity;
    String[] bsSelect = new String[]{"买入", "卖出"};

    OrderEntity orderEntity;

    int position = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_new_order);

        if (getIntent() != null) {
            position = getIntent().getIntExtra("position", -1);
            orderEntity = (OrderEntity) getIntent().getSerializableExtra("entity");
        }

        titleView = findViewById(R.id.title);
        titleView.setText("新建预埋单");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        newestPrice = findViewById(R.id.newest_price);
        lastPrice = findViewById(R.id.last_price);
        upPrice = findViewById(R.id.limit_up_price);
        downPrice = findViewById(R.id.limit_down_price);
        code = findViewById(R.id.stock_code);
        typeLayout = findViewById(R.id.type_layout);
        quoteLayout = findViewById(R.id.quote_layout);
        buyDict = findViewById(R.id.buy_dict);
        quoteWay = findViewById(R.id.quote_way);
        price = findViewById(R.id.price);
        number = findViewById(R.id.num);
        submit = findViewById(R.id.submit);
        typeLayout.setOnClickListener(this);
        quoteLayout.setOnClickListener(this);
        submit.setOnClickListener(this);

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (code.getText().length() == 6) {
                    search(code.getText().toString());
                }
            }
        });

        if (position != -1) {
            code.setText(orderEntity.code);
            price.setText(orderEntity.price);
            number.setText(orderEntity.qty);
        }

        stockEntity = new TradeStockEntity();
    }

    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            finish();
        } else if (v == typeLayout) {
            DialogUtils.showSelectDialog(context, bsSelect, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    buyDict.setText(bsSelect[which]);
                }
            });
        } else if (v == quoteLayout) {
            if (stockEntity == null || stockEntity.market == null) {
                CommonUtils.show(context, "请输入股票代码");
                return;
            }
            final String[] select;
            if (stockEntity.market.equals("0")) {
                select = YiChuangUtils.szQuoteType;
            } else if (stockEntity.market.equals("1")) {
                select = YiChuangUtils.shQuoteType;
            } else {
                select = new String[]{};
            }
            DialogUtils.showSelectDialog(context, select, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    quoteWay.setText(select[which]);
                }
            });
        } else if (v == submit) {
            orderEntity = new OrderEntity();
            orderEntity.market = stockEntity.market;
            orderEntity.name = stockEntity.stkname;
            orderEntity.code = stockEntity.stkcode;
            String temp = "B";
            if (buyDict.getText().toString().equals("卖出")) {
                temp = "S";
            }
            orderEntity.bsflag = YiChuangUtils.getTagByQuoteName(temp, quoteWay.getText().toString());
            orderEntity.dict = buyDict.getText().toString();
            orderEntity.price = price.getText().toString();
            orderEntity.qty = number.getText().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String date = dateFormat.format(new Date());
            orderEntity.date = date;

            Intent intent = new Intent();
            intent.putExtra("entity", orderEntity);
            intent.putExtra("position", position);
            setResult(2, intent);
            finish();
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
                stockEntity.market = result.get("market");
                stockEntity.stkname = result.get("stkname");
                stockEntity.stkcode = result.get("stkcode");
                stockEntity.stopflag = result.get("stopflag");
                stockEntity.maxqty = result.get("maxqty");
                stockEntity.minqty = result.get("minqty");
                stockEntity.fixprice = result.get("fixprice");
                getHQQuery(stockEntity);
            } else {
                DialogUtils.showSimpleDialog(context, data);
            }
        });

        showBusyDialog();

    }

    private void getHQQuery(final TradeStockEntity entity) {
        Client.getInstance().send(new STradeHQQuery(YiChuangUtils.getMarketByTag(entity.market), entity.stkcode), new Callback() {
            @Override
            public void onResult(boolean success, OriginalData data) {
                dismissBusyDialog();
                if (success) {
                    STradeHQQueryA queryA = new STradeHQQueryA(data.getHeadBytes(), data.getBodyBytes(),
                            Client.getInstance().aesKey);

                    entity.fOpen = queryA.fOpen;
                    entity.fLastClose = queryA.fLastClose;
                    entity.fHigh = queryA.fHigh;
                    entity.fLow = queryA.fLow;
                    entity.fNewest = queryA.fNewest;

                    STradeHQOrderItem[] askItems = queryA.ask;
                    List<TradeStockEntity.Dang> list1 = new ArrayList<>();
                    for (int i = 0; i < askItems.length; i++) {
                        TradeStockEntity.Dang dang = new TradeStockEntity.Dang();
                        dang.fOrder = (int)askItems[i].fOrder;
                        dang.fPrice = askItems[i].fPrice;
                        list1.add(dang);
                    }
                    entity.ask = list1;

                    STradeHQOrderItem[] bidItems = queryA.bid;
                    List<TradeStockEntity.Dang> list2 = new ArrayList<>();
                    for (int i = 0; i < bidItems.length; i++) {
                        TradeStockEntity.Dang dang = new TradeStockEntity.Dang();
                        dang.fOrder = (int)bidItems[i].fOrder;
                        dang.fPrice = bidItems[i].fPrice;
                        list2.add(dang);
                    }
                    entity.bid = list2;

                    code.setText(stockEntity.stkcode + " " + stockEntity.stkname);
                    setIndex(stockEntity);
                }

            }
        });

        showBusyDialog();
    }
}
