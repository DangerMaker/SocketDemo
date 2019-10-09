package com.ez08.trade.ui.trade;

import com.ez08.trade.ui.SegmentsTemplateActivity;
import com.ez08.trade.ui.view.customtab.EasyFragment;

import java.util.ArrayList;

public class TradeBatActivity extends SegmentsTemplateActivity {

    @Override
    protected void setTitles() {
        titles = new String[]{"批量买入", "批量卖出"};
    }

    @Override
    protected void initFragmentList(ArrayList<EasyFragment> mFragmentList) {
        mFragmentList.add(new EasyFragment(TradeOptionFragment.newInstance(5), titles[0]));
        mFragmentList.add(new EasyFragment(TradeOptionFragment.newInstance(6), titles[1]));
    }

}
