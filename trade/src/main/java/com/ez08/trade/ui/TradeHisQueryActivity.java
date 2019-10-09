//package com.ez08.trade.ui;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewStub;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.ez08.trade.R;
//import com.ez08.trade.tools.DatePickerCallback;
//import com.ez08.trade.ui.query.DatePickerFragment;
//import com.ez08.trade.ui.view.LinearItemDecoration;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//public abstract class TradeHisQueryActivity extends BaseActivity implements View.OnClickListener, DatePickerCallback {
//
//    protected ImageView backBtn;
//    protected TextView titleView;
//    protected RecyclerView recyclerView;
//    protected LinearLayoutManager manager;
//    protected List<Object> mList;
//    protected BaseAdapter adapter;
//    protected TextView beginTV;
//    protected TextView closeTV;
//    protected LinearLayout date_layout;
//    ViewStub titleStub;
//
//    protected int beginYear;
//    protected int beginMonth;
//    protected int beginDay;
//
//    protected int closeYear;
//    protected int closeMonth;
//    protected int closeDay;
//    protected SimpleDateFormat dateFormat;
//    protected SimpleDateFormat postFormat;
//
//    protected String beginValue;
//    protected String endValue;
//
//    public abstract String getTitleString();
//
//    public abstract int getListTitleLayout();
//
//    public abstract BaseAdapter getAdapter();
//
//    public abstract void post(String beginValue, String endValue);
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.trade_activity_query_his);
//
//        titleView = findViewById(R.id.title);
//        date_layout = findViewById(R.id.date_layout);
//        titleView.setText(getTitleString());
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        manager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(manager);
//        adapter = getAdapter();
//        recyclerView.setAdapter(adapter);
//        LinearItemDecoration divider = new LinearItemDecoration(this);
//        recyclerView.addItemDecoration(divider);
//        beginTV = findViewById(R.id.date_begin);
//        closeTV = findViewById(R.id.date_close);
//        beginTV.setOnClickListener(this);
//        closeTV.setOnClickListener(this);
//
//        titleStub = findViewById(R.id.stub_title);
//        titleStub.setLayoutResource(getListTitleLayout());
//        titleStub.setVisibility(View.VISIBLE);
//
//        backBtn = findViewById(R.id.img_back);
//        backBtn.setOnClickListener(this);
//
//        mList = new ArrayList<>();
//
//        Calendar calendar = Calendar.getInstance();
//        Date date = new Date();
//        calendar.setTime(date);
//        closeYear = calendar.get(Calendar.YEAR);
//        closeMonth = calendar.get(Calendar.MONTH);
//        closeDay = calendar.get(Calendar.DAY_OF_MONTH);
//        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        closeTV.setText(dateFormat.format(date));
//        postFormat = new SimpleDateFormat("yyyyMMdd");
//        endValue = postFormat.format(date);
//
//        calendar.add(Calendar.DATE, -7);
//        Date begin = calendar.getTime();
//        beginYear = calendar.get(Calendar.YEAR);
//        beginMonth = calendar.get(Calendar.MONTH);
//        beginDay = calendar.get(Calendar.DAY_OF_MONTH);
//        beginTV.setText(dateFormat.format(begin));
//        beginValue = postFormat.format(begin);
//        post(beginValue,endValue);
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v == beginTV) {
//            DatePickerFragment adf = DatePickerFragment.newInstance(0, beginYear, beginMonth, beginDay);
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            adf.show(ft, "123");
//        } else if (v == closeTV) {
//            DatePickerFragment adf = DatePickerFragment.newInstance(1, closeYear, closeMonth, closeDay);
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            adf.show(ft, "456");
//        }else  if(v == backBtn){
//            finish();
//        }
//    }
//
//    @Override
//    public void callback(int type, int year, int month, int day) {
//        if (type == 0) {
//            if (beginYear == year && beginMonth == month && beginDay == day) {
//                return;
//            }
//
//            beginYear = year;
//            beginMonth = month;
//            beginDay = day;
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(beginYear, beginMonth, beginDay);
//            beginTV.setText(dateFormat.format(calendar.getTime()));
//            beginValue = postFormat.format(calendar.getTime());
//
//            post(beginValue,endValue);
//        } else {
//            if (closeYear == year && closeMonth == month && closeDay == day) {
//                return;
//            }
//
//            closeYear = year;
//            closeMonth = month;
//            closeDay = day;
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(closeYear, closeMonth, closeDay);
//            closeTV.setText(dateFormat.format(calendar.getTime()));
//            endValue = postFormat.format(calendar.getTime());
//
//            post(beginValue,endValue);
//        }
//    }
//}
