//package com.ez08.trade.ui.view;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.ez08.trade.R;
//import com.ez08.trade.tools.MathUtils;
//import com.ez08.trade.tools.ScreenUtil;
//import com.ez08.trade.ui.trade.entity.TradeLevel1Entity;
//
//import java.util.List;
//
//public class Level1HorizontalView extends LinearLayout implements View.OnClickListener {
//
//    TextView buyPrice1;
//    TextView buyPrice2;
//    TextView buyPrice3;
//    TextView buyPrice4;
//    TextView buyPrice5;
//
//    TextView sellPrice1;
//    TextView sellPrice2;
//    TextView sellPrice3;
//    TextView sellPrice4;
//    TextView sellPrice5;
//
//    TextView buyVol1;
//    TextView buyVol2;
//    TextView buyVol3;
//    TextView buyVol4;
//    TextView buyVol5;
//
//    TextView sellVol1;
//    TextView sellVol2;
//    TextView sellVol3;
//    TextView sellVol4;
//    TextView sellVol5;
//
//    public Level1HorizontalView(Context context) {
//        super(context);
//    }
//
//    public Level1HorizontalView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        inflate(context, R.layout.trade_view_horizontal_level1, this);
//
//        buyPrice1 = findViewById(R.id.buy_price1);
//        buyPrice2 = findViewById(R.id.buy_price2);
//        buyPrice3 = findViewById(R.id.buy_price3);
//        buyPrice4 = findViewById(R.id.buy_price4);
//        buyPrice5 = findViewById(R.id.buy_price5);
//        sellPrice1 = findViewById(R.id.sell_price1);
//        sellPrice2 = findViewById(R.id.sell_price2);
//        sellPrice3 = findViewById(R.id.sell_price3);
//        sellPrice4 = findViewById(R.id.sell_price4);
//        sellPrice5 = findViewById(R.id.sell_price5);
//        buyVol1 = findViewById(R.id.buy_volume1);
//        buyVol2 = findViewById(R.id.buy_volume2);
//        buyVol3 = findViewById(R.id.buy_volume3);
//        buyVol4 = findViewById(R.id.buy_volume4);
//        buyVol5 = findViewById(R.id.buy_volume5);
//        sellVol1 = findViewById(R.id.sell_volume1);
//        sellVol2 = findViewById(R.id.sell_volume2);
//        sellVol3 = findViewById(R.id.sell_volume3);
//        sellVol4 = findViewById(R.id.sell_volume4);
//        sellVol5 = findViewById(R.id.sell_volume5);
//    }
//
//
//    List<TradeLevel1Entity.Dang> ask;
//    List<TradeLevel1Entity.Dang> bid;
//    TradeLevel1Entity entity;
//
//    public void setData(TradeLevel1Entity entity) {
//        this.entity = entity;
//        this.ask = entity.ask;
//        this.bid = entity.bid;
//
//        bat(buyPrice1,buyVol1,bid,0);
//        bat(buyPrice2,buyVol2,bid,1);
//        bat(buyPrice3,buyVol3,bid,2);
//        bat(buyPrice4,buyVol4,bid,3);
//        bat(buyPrice5,buyVol5,bid,4);
//
//        bat(sellPrice1,sellVol1,ask,0);
//        bat(sellPrice2,sellVol2,ask,1);
//        bat(sellPrice3,sellVol3,ask,2);
//        bat(sellPrice4,sellVol4,ask,3);
//        bat(sellPrice5,sellVol5,ask,4);
//
//    }
//
//
//    private void bat(TextView price, TextView volume, List<TradeLevel1Entity.Dang> list, int position) {
//        price.setText(MathUtils.format2Num(list.get(position).fPrice));
//        price.setTextColor(list.get(position).fPrice > entity.fOpen ? ScreenUtil.setTextColor(getContext(), R.color.trade_red) : ScreenUtil.setTextColor(getContext(), R.color.trade_green));
//        volume.setText(MathUtils.getMost4Character(list.get(position).fOrder / 100));
//        price.setTag(MathUtils.format2Num(list.get(position).fPrice));
//        volume.setTag(MathUtils.format2Num(list.get(position).fPrice));
//        price.setOnClickListener(this);
//        volume.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getTag()!=null){
//            String s = v.getTag().toString();
//            onFiveListener.OnFive(s);
//        }
//    }
//
//    OnFiveListener onFiveListener;
//    public void setOnFiveListener(OnFiveListener listener){
//        onFiveListener = listener;
//    }
//}
