package com.ez08.trade.ui.query;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.Callback;
import com.ez08.trade.net.Client;
import com.ez08.trade.tools.DatePickerCallback;
import com.ez08.trade.tools.YCParser;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.trade.adpater.TradeDealAdapter;
import com.ez08.trade.ui.trade.adpater.TradeEntrustAdapter;
import com.ez08.trade.ui.trade.entity.TradeDealEntity;
import com.ez08.trade.ui.trade.entity.TradeEntrustEntity;
import com.ez08.trade.ui.trade.entity.TradeTitleDealEntity;
import com.ez08.trade.ui.trade.entity.TradeTitleEntrustEntity;
import com.ez08.trade.ui.user.entity.ShareHoldersEntity;
import com.ez08.trade.ui.user.entity.TradeShareHoldersTitle;
import com.ez08.trade.ui.view.LinearItemDecoration;
import com.ez08.trade.user.UserHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TradeQueryEntrustActivity extends BaseActivity implements View.OnClickListener, DatePickerCallback {

    TextView titleView;
    ImageView backBtn;
    int UItype = 0;

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<Object> mList;
    BaseAdapter adapter;
    TextView beginTV;
    TextView closeTV;
    LinearLayout date_layout;

    int beginYear;
    int beginMonth;
    int beginDay;

    int closeYear;
    int closeMonth;
    int closeDay;
    SimpleDateFormat dateFormat;
    SimpleDateFormat postFormat;

    String beginValue;
    String endValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_query_entrust);
        if (getIntent() != null) {
            UItype = getIntent().getIntExtra("type", 0);
        }
        titleView = findViewById(R.id.title);
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        date_layout = findViewById(R.id.date_layout);
        if (UItype == 0) {
            titleView.setText("当日委托");
            date_layout.setVisibility(View.GONE);
        } else if (UItype == 1) {
            titleView.setText("历史委托");
        } else if (UItype == 2) {
            titleView.setText("当日成交");
            date_layout.setVisibility(View.GONE);
        } else if (UItype == 3) {
            titleView.setText("历史成交");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        if (UItype == 0 || UItype == 1) {
            adapter = new TradeEntrustAdapter(this);
            recyclerView.setAdapter(adapter);
            mList = new ArrayList<>();
            mList.add(new TradeTitleEntrustEntity());
            adapter.addAll(mList);
        } else {
            adapter = new TradeDealAdapter(this);
            recyclerView.setAdapter(adapter);
            mList = new ArrayList<>();
            mList.add(new TradeTitleDealEntity());
            adapter.addAll(mList);
        }

        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        beginTV = findViewById(R.id.date_begin);
        closeTV = findViewById(R.id.date_close);
        beginTV.setOnClickListener(this);
        closeTV.setOnClickListener(this);

        if (UItype == 0) {
            getWTInfo();
        } else if (UItype == 1) {
            Calendar calendar = Calendar.getInstance();
            Date date = new Date();
            calendar.setTime(date);
            closeYear = calendar.get(Calendar.YEAR);
            closeMonth = calendar.get(Calendar.MONTH);
            closeDay = calendar.get(Calendar.DAY_OF_MONTH);
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            closeTV.setText(dateFormat.format(date));
            postFormat = new SimpleDateFormat("yyyyMMdd");
            endValue = postFormat.format(date);

            calendar.add(Calendar.DATE, -7);
            Date begin = calendar.getTime();
            beginYear = calendar.get(Calendar.YEAR);
            beginMonth = calendar.get(Calendar.MONTH);
            beginDay = calendar.get(Calendar.DAY_OF_MONTH);
            beginTV.setText(dateFormat.format(begin));
            beginValue = postFormat.format(begin);
            getWTHisInfo();
        } else if (UItype == 2) {
            getInfo();
        } else if (UItype == 3) {
            Calendar calendar = Calendar.getInstance();
            Date date = new Date();
            calendar.setTime(date);
            closeYear = calendar.get(Calendar.YEAR);
            closeMonth = calendar.get(Calendar.MONTH);
            closeDay = calendar.get(Calendar.DAY_OF_MONTH);
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            closeTV.setText(dateFormat.format(date));
            postFormat = new SimpleDateFormat("yyyyMMdd");
            endValue = postFormat.format(date);

            calendar.add(Calendar.DATE, -7);
            Date begin = calendar.getTime();
            beginYear = calendar.get(Calendar.YEAR);
            beginMonth = calendar.get(Calendar.MONTH);
            beginDay = calendar.get(Calendar.DAY_OF_MONTH);
            beginTV.setText(dateFormat.format(begin));
            beginValue = postFormat.format(begin);

            getHisInfo();
        }

    }

    public void getInfo() {
        String body = "FUN=410512&TBL_IN=fundid,market,secuid,stkcode,ordersno,bankcode,qryflag,count,poststr,qryoperway;" +
                UserHelper.getUser().fundid + "," +
                "" + "," +
                UserHelper.getUser().secuid + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "1" + "," +
                "100" + "," +
                "" + "," +
                ";";

        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            if (success) {
                List<Map<String, String>> result = YCParser.parseArray(data);
                for (int i = 0; i < result.size(); i++) {
                    TradeDealEntity entity = new TradeDealEntity();
                    entity.stkcode = result.get(i).get("stkcode");
                    entity.stkname = result.get(i).get("stkname");
                    entity.matchtime = result.get(i).get("matchtime");
                    entity.matchprice = result.get(i).get("matchprice");
                    entity.matchqty = result.get(i).get("matchqty");
                    entity.trddate = result.get(i).get("trddate");
                    entity.bsFlag = result.get(i).get("bsflag");
                    mList.add(entity);
                }
                adapter.clearAndAddAll(mList);
            }
        });
    }

    public void getHisInfo() {
        String body = "FUN=411513&TBL_IN=strdate,enddate,fundid,market,secuid,stkcode,bankcode,qryflag,count,poststr;" +
                beginValue + "," +
                endValue + "," +
                UserHelper.getUser().fundid + "," +
                "" + "," +
                UserHelper.getUser().secuid + "," +
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
                    TradeDealEntity entity = new TradeDealEntity();
                    entity.stkcode = result.get(i).get("stkcode");
                    entity.stkname = result.get(i).get("stkname");
                    entity.matchtime = result.get(i).get("matchtime");
                    entity.matchprice = result.get(i).get("matchprice");
                    entity.matchqty = result.get(i).get("matchqty");
                    entity.trddate = result.get(i).get("trddate");
                    entity.bsFlag = result.get(i).get("bsflag");
                    mList.add(entity);
                }
                adapter.clearAndAddAll(mList);
            }
        });
    }

    public void getWTInfo() {
        String body = "FUN=410510&TBL_IN=market,fundid,secuid,stkcode,ordersno,Ordergroup,bankcode,qryflag,count,poststr,extsno,qryoperway;" +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "1" + "," +
                "100" + "," +
                "" + "," +
                "" + "," +
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
                    mList.add(entity);
                }
                adapter.clearAndAddAll(mList);
            }
        });
    }

    public void getWTHisInfo() {
        String body = "FUN=411511&TBL_IN=strdate,enddate,fundid,market,secuid,stkcode,ordersno,Ordergroup,bankcode,qryflag,count,poststr,extsno,qryoperway;" +
                beginValue + "," +
                endValue + "," +
                UserHelper.getUser().fundid + "," +
                "" + "," +
                UserHelper.getUser().secuid + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "1" + "," +
                "100" + "," +
                "" + "," +
                "" + "," +
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
                    mList.add(entity);
                }
                adapter.clearAndAddAll(mList);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == beginTV) {
            DatePickerFragment adf = DatePickerFragment.newInstance(0, beginYear, beginMonth, beginDay);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            adf.show(ft, "123");
        } else if (v == closeTV) {
            DatePickerFragment adf = DatePickerFragment.newInstance(1, closeYear, closeMonth, closeDay);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            adf.show(ft, "456");
        }else if(v == backBtn){
            finish();
        }
    }

    @Override
    public void callback(int type, int year, int month, int day) {
        if (type == 0) {
            if (beginYear == year && beginMonth == month && beginDay == day) {
                return;
            }

            beginYear = year;
            beginMonth = month;
            beginDay = day;
            Calendar calendar = Calendar.getInstance();
            calendar.set(beginYear, beginMonth, beginDay);
            beginTV.setText(dateFormat.format(calendar.getTime()));
            beginValue = postFormat.format(calendar.getTime());

            if(UItype == 0 || UItype == 1) {
                getWTHisInfo();
            }else{
                getHisInfo();
            }
        } else {
            if (closeYear == year && closeMonth == month && closeDay == day) {
                return;
            }

            closeYear = year;
            closeMonth = month;
            closeDay = day;
            Calendar calendar = Calendar.getInstance();
            calendar.set(closeYear, closeMonth, closeDay);
            closeTV.setText(dateFormat.format(calendar.getTime()));
            endValue = postFormat.format(calendar.getTime());

            if(UItype == 0 || UItype == 1) {
                getWTHisInfo();
            }else{
                getHisInfo();
            }
        }
    }
}
