package com.ez08.trade.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ez08.trade.R;
import com.ez08.trade.tools.CommonUtils;
import com.ez08.trade.ui.view.customtab.EasyFragment;
import com.ez08.trade.ui.view.customtab.FragmentAdapter;
import com.ez08.trade.ui.view.customtab.SlidingTabLayout;

import java.util.ArrayList;

public abstract class SegmentsTemplateActivity extends IntervalActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;

    SlidingTabLayout sliding_tabs;
    ImageView backBtn;

    private ArrayList<EasyFragment> mFragmentList;
    public String[] titles;
    private int mIndex = 0;

    protected abstract void setTitles();

    protected abstract void initFragmentList(ArrayList<EasyFragment> mFragmentList);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_segments_template);
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        mViewPager = (ViewPager) findViewById(R.id.info_tab_pager);
        sliding_tabs = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        if (mFragmentList != null) {
            mFragmentList.clear();
        } else {
            mFragmentList = new ArrayList<>();
        }

        setTitles();

        if(titles == null){
            CommonUtils.show(context,"缺少title");
            return;
        }

        if (savedInstanceState != null) {
            for (int i = 0; i < titles.length; i++) {
                if (getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + i) != null) {
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + i);
                    mFragmentList.add(new EasyFragment(fragment, titles[i]));
                }
            }
        } else {
            initFragmentList(mFragmentList);
        }


        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.addOnPageChangeListener(new PageChangeListener());
        mViewPager.setAdapter(mAdapter);
        sliding_tabs.setViewPager(mViewPager);

        Intent intent = getIntent();
        if (intent != null) {
            mIndex = intent.getIntExtra("extra", -1);
        }

        if (mIndex != -1) {
            mViewPager.setCurrentItem(mIndex);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            mIndex = arg0;
            resetTime();
        }
    }

    @Override
    public void onLazyLoad() {
        if (mIndex < 0) {
            return;
        }

        if(!(mFragmentList.get(mIndex).getFragment() instanceof Interval)){
            return;
        }

        ((Interval) mFragmentList.get(mIndex).getFragment()).OnPost();
    }
}