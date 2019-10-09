package com.ez08.trade.ui.user;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.ScreenUtil;
import com.ez08.trade.ui.BasePopupWindows;

public class TradePwdPopupWindows extends BasePopupWindows {

    TextView trade;
    TextView funds;

    public TradePwdPopupWindows(Activity context) {
        super(context);
    }

    @Override
    protected void onCreateView(View view) {
        trade = view.findViewById(R.id.trade);
        funds = view.findViewById(R.id.funds);
        trade.setOnClickListener(v -> callback.callback(0));

        funds.setOnClickListener(v -> callback.callback(1));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_view_select_pwd;
    }

    @Override
    protected void setData(Object data) {

    }

    @Override
    public void showAsDropDown(View anchor) {
        if (!this.isShowing()) {
            showAsDropDown(anchor, (int) ScreenUtil.getScreenWidth(context) - container.getWidth(), 0);
        } else {
            this.dismiss();
        }
    }

    Callback callback;

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    public interface Callback{
        void callback(int type);
    }
}
