//package com.ez08.trade.ui.trade;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.ez08.trade.R;
//import com.ez08.trade.ui.BaseActivity;
//
//public class TradeBuySellActivity extends BaseActivity implements View.OnClickListener {
//    ImageView backBtn;
//    TextView titleView;
//
//    FragmentManager fragmentManager;
//    RelativeLayout container;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.trade_activity_trans_stock);
//
//        container = findViewById(R.id.container);
//        titleView = findViewById(R.id.title);
//        titleView.setText("对买对卖");
//        backBtn = findViewById(R.id.img_back);
//        backBtn.setOnClickListener(this);
//
//        fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        Fragment fragment = TradeOptionFragment.newInstance(8);
//        fragmentTransaction.add(R.id.container, fragment);
//        fragmentTransaction.commit();
//    }
//
//    @Override
//    public void onClick(View v) {
//        if(v == backBtn){
//            finish();
//        }
//    }
//}
