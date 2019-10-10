package com.ez08.trade.ui.other;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.Callback;
import com.ez08.trade.net.Client;
import com.ez08.trade.tools.DialogUtils;
import com.ez08.trade.tools.YCParser;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.view.LinearItemDecoration;
import com.ez08.trade.user.TradeUser;
import com.ez08.trade.user.UserHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TradeOrderActivity extends BaseActivity implements View.OnClickListener {
    ImageView backBtn;
    TextView titleView;

    TextView submmit;
    TextView copy;
    TextView newItem;
    TextView alter;
    TextView delete;
    RecyclerView recyclerView;

    List<OrderEntity> list;
    MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_order);

        titleView = findViewById(R.id.title);
        titleView.setText("预埋单");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        submmit = findViewById(R.id.submit);
        copy = findViewById(R.id.copy);
        newItem = findViewById(R.id.new_item);
        alter = findViewById(R.id.alter);
        delete = findViewById(R.id.delete);
        submmit.setOnClickListener(this);
        copy.setOnClickListener(this);
        newItem.setOnClickListener(this);
        alter.setOnClickListener(this);
        delete.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        list = new ArrayList<>();
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
    }


    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.trade_holder_order, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
            final OrderEntity entity = list.get(i);
            myHolder.checkBox.setChecked(entity.isSelect);
            myHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    entity.isSelect = isChecked;
                }
            });
            myHolder.name.setText(entity.name);
            myHolder.dict.setText(entity.dict);
            myHolder.date.setText(entity.date);
            myHolder.price.setText(entity.price);
            myHolder.number.setText(entity.qty);

            if (entity.dict.equals("买入")) {
                myHolder.dict.setTextColor(setTextColor(R.color.trade_red));
            } else {
                myHolder.dict.setTextColor(setTextColor(R.color.trade_green));
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView date;
        TextView dict;
        TextView name;
        TextView price;
        TextView number;

        public MyHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkbox);
            date = view.findViewById(R.id.date);
            dict = view.findViewById(R.id.dict);
            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            number = view.findViewById(R.id.num);
        }
    }


    @Override
    public void onClick(View v) {
        if (backBtn == v) {
            finish();
        } else if (submmit == v) {
            for (int i = 0; i < list.size(); i++) {
                OrderEntity entity = list.get(i);
                if (entity.isSelect) {
                    Log.e("Order", entity.name);
                    post(entity.market, entity.code, entity.price, entity.qty, entity.bsflag);
                }
            }
        } else if (copy == v) {
            List<OrderEntity> temp = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isSelect) {
                    OrderEntity entity = new OrderEntity();
                    entity.market = list.get(i).market;
                    entity.name = list.get(i).name;
                    entity.code = list.get(i).code;
                    entity.bsflag = list.get(i).bsflag;
                    entity.price = list.get(i).price;
                    entity.dict = list.get(i).dict;
                    entity.qty = list.get(i).qty;
                    entity.date = list.get(i).date;
                    entity.isSelect = list.get(i).isSelect;
                    temp.add(entity);
                }
            }
            list.addAll(temp);
            adapter.notifyDataSetChanged();
        } else if (newItem == v) {
            Intent intent = new Intent(context, TradeNewOrderActivity.class);
            startActivityForResult(intent, 1);
        } else if (alter == v) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isSelect) {
                    OrderEntity entity = new OrderEntity();
                    entity.market = list.get(i).market;
                    entity.name = list.get(i).name;
                    entity.code = list.get(i).code;
                    entity.bsflag = list.get(i).bsflag;
                    entity.price = list.get(i).price;
                    entity.dict = list.get(i).dict;
                    entity.qty = list.get(i).qty;
                    entity.date = list.get(i).date;
                    entity.isSelect = list.get(i).isSelect;
                    Intent intent = new Intent(context, TradeNewOrderActivity.class);
                    intent.putExtra("position", i);
                    intent.putExtra("entity", entity);
                    startActivityForResult(intent, 1);
                }
            }
        } else if (delete == v) {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (list.get(i).isSelect) {
                    list.remove(i);
                }
            }
            adapter.notifyDataSetChanged();
        }

    }

    private void post(String market, String code, String price, String qty, String postFlag) {
        TradeUser user = UserHelper.getUserByMarket(market);
        if (user == null) {
            return;
        }
        String body = "FUN=410411&TBL_IN=market,secuid,fundid,stkcode,bsflag,price,qty,ordergroup,bankcode,remark;" +
                market + "," +
                user.secuid + "," +
                user.fundid + "," +
                code + "," +
                postFlag + "," +
                price + "," +
                qty + "," +
                "0" + "," +
                "" + "," +
                ";";

        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            if (success) {
                Map<String, String> result = YCParser.parseObject(data);
                DialogUtils.showSimpleDialog(context, "委托成功" + "\n" +
                        "委托序号：" + result.get("ordersno") + "\n" +
                        "合同序号：" + result.get("orderid") + "\n" +
                        "委托批号：" + result.get("ordergroup")
                );
            }else{
                DialogUtils.showSimpleDialog(context, data);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            OrderEntity entity = (OrderEntity) data.getSerializableExtra("entity");
            if (data.getIntExtra("position", -1) == -1) {
                list.add(entity);
            } else {
                int pos = data.getIntExtra("position", -1);
                list.remove(pos);
                list.add(pos, entity);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
