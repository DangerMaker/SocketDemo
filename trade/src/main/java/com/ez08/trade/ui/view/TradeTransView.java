package com.ez08.trade.ui.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.CommonUtils;
import com.ez08.trade.tools.DialogUtils;
import com.ez08.trade.tools.MathUtils;
import com.ez08.trade.tools.YiChuangUtils;
import com.ez08.trade.ui.trade.ITradeView;
import com.ez08.trade.ui.trade.OptionsDelegate;
import com.ez08.trade.ui.trade.entity.TradeStockEntity;

public class TradeTransView extends LinearLayout implements ITradeView, View.OnClickListener {

    Context context;
    RelativeLayout quoteLayout;
    TextView quoteWay;
    EditText inputCode;
    AdjustEditText price;
    AdjustEditText totalNum;
    Button submitBtn;
    TextView maxText;

    boolean bsflag = true;
    String quoteType = "可转债转股";

    public TradeTransView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        init();
    }

    private void init() {
        inputCode = findViewById(R.id.input_code);
        price = findViewById(R.id.price);
        totalNum = findViewById(R.id.total_num);
        submitBtn = findViewById(R.id.submit);
        maxText = findViewById(R.id.available_num);
        quoteLayout = findViewById(R.id.layout_quote);
        quoteWay = findViewById(R.id.quote_way);
        quoteLayout.setOnClickListener(this);

        submitBtn.setOnClickListener(this);
        change();

        inputCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (inputCode.getText().length() == 6) {
                    delegate.search(inputCode.getText().toString());
                }
            }
        });

        inputCode.setOnFocusChangeListener((v, hasFocus) -> {
            Log.e("onFocusChange", "" + hasFocus);
            if (hasFocus) {
                if (inputCode.getText().toString().length() > 6) {
                    inputCode.setText("");
                }
            }
        });
    }

    public void change() {
        inputCode.setBackgroundResource(R.drawable.trade_input_bg);
        submitBtn.setBackgroundResource(R.drawable.trade_red_corner_full);
        price.setColor(R.color.trade_red);
        totalNum.setColor(R.color.trade_red);
        submitBtn.setText("确定");
    }

    @Override
    public void onClick(View v) {
        if (v == submitBtn) {
            String stockCode = inputCode.getText().toString().trim();
            String priceText = price.getText();
            String num = totalNum.getText();
            if (isInvalid(stockCode,priceText,num)) {
                delegate.submit(entity.stkcode, price.getText(), 0, totalNum.getText(), quoteType);
            }
        }else if(v == quoteLayout){
            DialogUtils.showSelectDialog(context, YiChuangUtils.conversionType, (dialog, which) -> {
                String result = YiChuangUtils.conversionType[which];
                quoteType = result;
                quoteWay.setText(quoteType);
            });
        }
    }

    TradeStockEntity entity;
    int maxValue = 0;

    public void setStockEntity(TradeStockEntity entity) {
        this.entity = entity;
        inputCode.setText(entity.stkcode + "    " + entity.stkname);
        price.setText((MathUtils.format2Num(entity.fNewest)));
//        delegate.getMax(entity.stkcode, price.getText());
    }

    @Override
    public void setBsflag(boolean flag) {
        this.bsflag = flag;
        if (inputCode != null) {
            change();
        }
    }

    public void setMax(String max) {
        maxText.setText("最大 " + max + " 股");
        maxValue = Integer.parseInt(max);
    }

    @Override
    public void setStockCode(String code) {
        inputCode.setText(code);
    }


    OptionsDelegate delegate;

    public void setDelegate(OptionsDelegate delegate) {
        this.delegate = delegate;

    }

    private boolean isInvalid(String stockCode, String price, String num) {
        if (entity==null|| TextUtils.isEmpty(stockCode) || TextUtils.isEmpty(price) || TextUtils.isEmpty(num)) {
            CommonUtils.show(context, R.string.trade_notice_msg);
            return false;
        }
        return true;

    }
}
