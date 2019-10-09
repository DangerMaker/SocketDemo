//package com.ez08.trade.ui.trade;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import androidx.annotation.Nullable;
//import androidx.viewpager.widget.ViewPager;
//
//import com.ez08.trade.R;
//import com.ez08.trade.ui.Interval;
//import com.ez08.trade.ui.IntervalActivity;
//import com.ez08.trade.ui.view.customtab.EasyFragment;
//import com.ez08.trade.ui.view.customtab.FragmentAdapter;
//import com.ez08.trade.ui.view.customtab.SlidingTabLayout;
//
//import java.util.ArrayList;
//
//public class TradeActivity extends IntervalActivity implements View.OnClickListener {
//
//    private ViewPager mViewPager;
//    private FragmentAdapter mAdapter;
//    private ArrayList<EasyFragment> mFragmentList = new ArrayList<>();
//    private int mIndex = 0;
//    SlidingTabLayout sliding_tabs;
//    ImageView backBtn;
//
//    TradeOptionFragment fragment1;
//    TradeOptionFragment fragment2;
//    TradeCancelFragment fragment3;
//    TradeOptionFragment fragment4;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.trade_activity);
//        backBtn = findViewById(R.id.img_back);
//        backBtn.setOnClickListener(this);
//        mViewPager = findViewById(R.id.info_tab_pager);
//        sliding_tabs = findViewById(R.id.sliding_tabs);
//
//        mFragmentList = new ArrayList<>();
//        if (savedInstanceState != null) {
//            if (getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + 0) != null)
//                fragment1 = (TradeOptionFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + 0);
//            if (getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + 1) != null)
//                fragment2 = (TradeOptionFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + 1);
//            if (getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + 2) != null)
//                fragment3 = (TradeCancelFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + 2);
//            if (getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + 3) != null)
//                fragment4 = (TradeOptionFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + 3);
//
//        } else {
//            fragment1 = TradeOptionFragment.newInstance(0);
//            fragment2 = TradeOptionFragment.newInstance(1);
//            fragment3 = TradeCancelFragment.newInstance();
//            fragment4 = TradeOptionFragment.newInstance(2);
//        }
//
//        mFragmentList.clear();
//        mFragmentList.add(new EasyFragment(fragment1, "买入"));
//        mFragmentList.add(new EasyFragment(fragment2, "卖出"));
//        mFragmentList.add(new EasyFragment(fragment3, "撤单"));
//        mFragmentList.add(new EasyFragment(fragment4, "持仓"));
//
//        mViewPager.setOffscreenPageLimit(mFragmentList.size());
//        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
//        mViewPager.addOnPageChangeListener(new PageChangeListener());
//        mViewPager.setAdapter(mAdapter);
//        sliding_tabs.setViewPager(mViewPager);
//
//        Intent intent = getIntent();
//        if(intent != null){
//            mIndex = intent.getIntExtra("extra",-1);
//        }
//
//        if(mIndex != -1){
//            mViewPager.setCurrentItem(mIndex);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.img_back) {
//            finish();
//        }
//    }
//
//    private class PageChangeListener implements ViewPager.OnPageChangeListener {
//
//        @Override
//        public void onPageScrollStateChanged(int arg0) {
//
//        }
//
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//        }
//
//        @Override
//        public void onPageSelected(int arg0) {
//            mIndex = arg0;
//            resetTime();
//        }
//    }
//
//    @Override
//    public void onLazyLoad() {
//        if(mIndex < 0){
//            return;
//        }
//        ((Interval) mFragmentList.get(mIndex).getFragment()).OnPost();
//    }
//}
