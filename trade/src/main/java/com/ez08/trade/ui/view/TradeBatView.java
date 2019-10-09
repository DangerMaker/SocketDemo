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
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.CommonUtils;
import com.ez08.trade.tools.MathUtils;
import com.ez08.trade.ui.trade.ITradeView;
import com.ez08.trade.ui.trade.OptionsDelegate;
import com.ez08.trade.ui.trade.entity.TradeStockEntity;

public class TradeBatView extends LinearLayout implements ITradeView, View.OnClickListener {

    Context context;
    EditText inputCode;
    AdjustEditText price;
    AdjustEditText singeNum;
    AdjustEditText totalNum;

    TextView full;
    TextView half;
    TextView one_three;
    TextView one_fourth;
    Button submitBtn;

    TextView maxText;

    boolean bsflag = true;
    String quoteType = "限价委托";

    public TradeBatView(Context context, AttributeSet attrs) {
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
        singeNum = findViewById(R.id.single_num);
        totalNum = findViewById(R.id.total_num);
        full = findViewById(R.id.full);
        half = findViewById(R.id.half);
        one_three = findViewById(R.id.one_three);
        one_fourth = findViewById(R.id.one_fourth);
        submitBtn = findViewById(R.id.submit);
        maxText = findViewById(R.id.available_num);

        full.setOnClickListener(this);
        half.setOnClickListener(this);
        one_three.setOnClickListener(this);
        one_fourth.setOnClickListener(this);
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

        inputCode.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("onFocusChange", "" + hasFocus);
                if (hasFocus) {
                    if(inputCode.getText().toString().length() > 6) {
                        inputCode.setText("");
                    }
                }
            }
        });
    }

    public void change() {
        if (bsflag) {
            inputCode.setBackgroundResource(R.drawable.trade_input_bg);
            full.setBackgroundResource(R.drawable.trade_input_bg);
            half.setBackgroundResource(R.drawable.trade_input_bg);
            one_three.setBackgroundResource(R.drawable.trade_input_bg);
            one_fourth.setBackgroundResource(R.drawable.trade_input_bg);
            submitBtn.setBackgroundResource(R.drawable.trade_red_corner_full);
            price.setColor(R.color.trade_red);
            singeNum.setColor(R.color.trade_red);
            totalNum.setColor(R.color.trade_red);
            submitBtn.setText("立即买入");
        } else {
            inputCode.setBackgroundResource(R.drawable.trade_input_bg_blue);
            full.setBackgroundResource(R.drawable.trade_input_bg_blue);
            half.setBackgroundResource(R.drawable.trade_input_bg_blue);
            one_three.setBackgroundResource(R.drawable.trade_input_bg_blue);
            one_fourth.setBackgroundResource(R.drawable.trade_input_bg_blue);
            submitBtn.setBackgroundResource(R.drawable.trade_blue_corner_full);
            price.setColor(R.color.trade_blue);
            singeNum.setColor(R.color.trade_blue);
            totalNum.setColor(R.color.trade_blue);
            submitBtn.setText("立即卖出");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == full) {
            totalNum.setText(MathUtils.save100(maxValue));
        } else if (v == half) {
            totalNum.setText(MathUtils.save100(maxValue / 2));
        } else if (v == one_three) {
            totalNum.setText(MathUtils.save100(maxValue / 3));
        } else if (v == one_fourth) {
            totalNum.setText(MathUtils.save100(maxValue / 4));
        } else if (v == submitBtn) {
            String stockCode = inputCode.getText().toString().trim();
            String priceText = price.getText();
            String singleNum = singeNum.getText();
            String num = totalNum.getText();
            if (isInvalid(stockCode,priceText,singleNum,num)) {
                delegate.submit(entity.stkcode, price.getText(), Integer.parseInt(singeNum.getText()), totalNum.getText(), quoteType);
            }
        }
    }

    TradeStockEntity entity;
    int maxValue = 0;

    public void setStockEntity(TradeStockEntity entity) {
        this.entity = entity;
        inputCode.setText(entity.stkcode + "    " + entity.stkname);
        price.setText((MathUtils.format2Num(entity.fNewest)));
        delegate.getMax(entity.stkcode, price.getText());
        inputCode.clearFocus();
    }

    @Override
    public void setBsflag(boolean flag) {
        this.bsflag = flag;
        if (inputCode != null) {
            change();
        }
    }

    public void setMax(String max) {
        if (bsflag) {
            maxText.setText("最大可买 " + max + " 股");
        } else {
            maxText.setText("最大可卖 " + max + " 股");
        }
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
    private boolean isInvalid(String stockCode, String price, String singleNum, String num) {
        if (entity==null|| TextUtils.isEmpty(stockCode) || TextUtils.isEmpty(price) || TextUtils.isEmpty(singleNum)|| TextUtils.isEmpty(num)) {
            CommonUtils.show(context, R.string.trade_notice_msg);
            return false;
        }
        return true;

    }
}
