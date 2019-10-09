package com.ez08.trade.ui.fresh_stock;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ez08.trade.R;
import com.ez08.trade.net.Client;
import com.ez08.trade.tools.JumpActivity;
import com.ez08.trade.tools.YCParser;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.fresh_stock.entity.TradeFreshBuyEntity;
import com.ez08.trade.ui.fresh_stock.entity.TradeNewStockLinesEntity;
import com.ez08.trade.ui.view.LinearItemDecoration;
import com.ez08.trade.user.UserHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradeFastStockActivity extends BaseActivity implements View.OnClickListener {

    Button submit;
    ImageView backBtn;
    TextView titleView;
    TextView subTitleView;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<TradeFreshBuyEntity> mList;
    List<TradeNewStockLinesEntity> linesEntityList;
    TextView tips;

    TradeNewStockAdapter adapter;
    String ALines = "0";
    String Slines = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_fast_buy);
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        titleView = findViewById(R.id.title);
        titleView.setText("新股申购");
        subTitleView = findViewById(R.id.sub_title);
        subTitleView.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new TradeNewStockAdapter();
        recyclerView.setAdapter(adapter);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        tips = findViewById(R.id.tips);

        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        mList = new ArrayList<>();
        linesEntityList = new ArrayList<>();
        getTips();
        post();
    }

    public void getTips(){
        String body = "FUN=410610&TBL_IN=market,secuid,orgid,count,posstr;" +
                "" + "," +
                UserHelper.getUser().secuid + "," +
                "" + "," +
                "100" + "," +
                ";";

        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            if (success) {
                List<Map<String, String>> result = YCParser.parseArray(data);
                for (int i = 0; i < result.size(); i++) {
                    TradeNewStockLinesEntity entity = new TradeNewStockLinesEntity();
                    entity.market = result.get(i).get("market");
                    entity.custquota = result.get(i).get("custquota");
                    entity.receivedate = result.get(i).get("receivedate");
                    linesEntityList.add(entity);
                }

                for (int i = 0; i < linesEntityList.size(); i++) {
                    if(linesEntityList.get(i).market.equals("0")){
                        Slines = linesEntityList.get(i).custquota;
                    }else if(linesEntityList.get(i).market.equals("1")){
                        ALines = linesEntityList.get(i).custquota;
                    }
                }

                tips.setText("您今日的申购额度为沪市" + ALines + "股，深市" + Slines + "股");
            }
        });
    }


    public void post() {
        mList.clear();

        String body = "FUN=411549&TBL_IN=market,stkcode,issuedate;" +
                "" + "," +
                "" + "," +
                ";";

        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            if (success) {
                List<Map<String, String>> result = YCParser.parseArray(data);
                for (int i = 0; i < result.size(); i++) {
                    TradeFreshBuyEntity entity = new TradeFreshBuyEntity();
                    entity.stkcode = result.get(i).get("stkcode");
                    entity.stkname = result.get(i).get("stkname");
                    entity.linkstk = result.get(i).get("linkstk");
                    entity.maxqty = result.get(i).get("maxqty");
                    entity.minqty = result.get(i).get("minqty");
                    mList.add(entity);
                }

                adapter.setData(mList);
            }
        });
    }

    class TradeNewStockAdapter extends RecyclerView.Adapter<TradeFreshStockHolder> {

        List<TradeFreshBuyEntity> list;
        List<String> codes;

        public TradeNewStockAdapter() {
            this.list = new ArrayList<>();
            this.codes = new ArrayList<>();
        }

        @Override
        public TradeFreshStockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.trade_holder_fresh_item, parent,
                    false);
            return new TradeFreshStockHolder(view);
        }

        @Override
        public void onBindViewHolder(final TradeFreshStockHolder holder, int position) {
            final TradeFreshBuyEntity model = list.get(position);

            holder.name.setText(model.stkname);
            holder.code.setText(model.stkcode);
            holder.num.setText(model.maxqty);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    model.isSelect = b;
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        public void setData(List<TradeFreshBuyEntity> array) {
            this.list = array;
            notifyDataSetChanged();
        }
    }

    class TradeFreshStockHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView code;
        public TextView num;
        public ImageView left_reduce_num;
        public ImageView right_plus_num;
        public AppCompatCheckBox checkBox;

        public TradeFreshStockHolder(View itemView) {
            super(itemView);
            left_reduce_num = (ImageView) itemView.findViewById(R.id.left_reduce_num);
            right_plus_num = (ImageView) itemView.findViewById(R.id.right_plus_num);
            left_reduce_num.setColorFilter(Color.RED);
            right_plus_num.setColorFilter(Color.RED);
            checkBox = (AppCompatCheckBox) itemView.findViewById(R.id.checkbox);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            code = (TextView) itemView.findViewById(R.id.txt_code);
            num = (TextView) itemView.findViewById(R.id.txt_num);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        } else if (v.getId() == R.id.sub_title) {
            JumpActivity.start(this, "新股申购查询");
        }else if(v.getId() == R.id.submit){
            for (int i = 0; i < mList.size(); i++) {
                Log.e(TAG, mList.get(i).stkname + ":" +mList.get(i).isSelect );

            }
        }
    }
}
