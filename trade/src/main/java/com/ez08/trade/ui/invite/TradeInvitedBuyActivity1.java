package com.ez08.trade.ui.invite;

import com.ez08.trade.ui.SegmentsTemplateActivity;
import com.ez08.trade.ui.view.customtab.EasyFragment;

import java.util.ArrayList;

public class TradeInvitedBuyActivity1 extends SegmentsTemplateActivity {

    TradeDeclareFragment fragment1;
    TradeDeclareFragment fragment2;

    @Override
    protected void setTitles() {
        titles = new String[]{"要约申报", "要约解除"};
    }

    @Override
    protected void initFragmentList(ArrayList<EasyFragment> mFragmentList) {
        fragment1 = TradeDeclareFragment.newInstance(0);
        fragment2 = TradeDeclareFragment.newInstance(1);
        mFragmentList.add(new EasyFragment(fragment1, titles[0]));
        mFragmentList.add(new EasyFragment(fragment2, titles[1]));
    }

}
