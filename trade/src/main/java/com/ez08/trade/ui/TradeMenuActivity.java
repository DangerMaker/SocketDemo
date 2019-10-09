package com.ez08.trade.ui;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ez08.trade.R;

public class TradeMenuActivity extends BaseActivity{
    FragmentManager fragmentManager;
    RelativeLayout container;
    TextView pageName;
    TradeMenuFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_menu);
        container = findViewById(R.id.container);
        pageName = findViewById(R.id.page_name);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (savedInstanceState != null) {
            fragment = (TradeMenuFragment) fragmentManager.findFragmentByTag("tradeMenuFragment");
        }else {
            fragment = TradeMenuFragment.newInstance();
            fragmentTransaction.add(R.id.container, fragment);
            fragmentTransaction.commit();
        }
    }
}
