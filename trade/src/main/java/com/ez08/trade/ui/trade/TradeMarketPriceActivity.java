package com.ez08.trade.ui.trade;

import com.ez08.trade.ui.SegmentsTemplateActivity;
import com.ez08.trade.ui.view.customtab.EasyFragment;

import java.util.ArrayList;

public class TradeMarketPriceActivity extends SegmentsTemplateActivity {

    @Override
    protected void setTitles() {
        titles = new String[]{"市价买入", "市价卖出"};
    }

    @Override
    protected void initFragmentList(ArrayList<EasyFragment> mFragmentList) {
        mFragmentList.add(new EasyFragment(TradeOptionFragment.newInstance(3), titles[0]));
        mFragmentList.add(new EasyFragment(TradeOptionFragment.newInstance(4), titles[1]));
    }

}
