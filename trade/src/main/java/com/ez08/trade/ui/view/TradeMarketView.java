package com.ez08.trade.ui.view;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class TradeMarketView extends LinearLayout implements ITradeView, View.OnClickListener {

    Context context;
    ImageView plusNumIv;
    ImageView reduceNumIv;
    EditText inputCode;
    EditText editText2;

    RelativeLayout layout2;
    RelativeLayout layoutQuote;
    TextView full;
    TextView half;
    TextView one_three;
    TextView one_fourth;
    Button submitBtn;

    TextView maxText;
    TextView quoteWay;

    boolean bsflag = true;
    String quoteType = "市价委托";

    public TradeMarketView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        init();
    }

    private void init() {
        plusNumIv = findViewById(R.id.right_plus_num);
        reduceNumIv = findViewById(R.id.left_reduce_num);
        editText2 = findViewById(R.id.trade_entrust_num);
        inputCode = findViewById(R.id.input_code);

        layout2 = findViewById(R.id.layout2);
        layoutQuote = findViewById(R.id.layout_quote);
        layoutQuote.setOnClickListener(this);
        full = findViewById(R.id.full);
        half = findViewById(R.id.half);
        one_three = findViewById(R.id.one_three);
        one_fourth = findViewById(R.id.one_fourth);

        full.setOnClickListener(this);
        half.setOnClickListener(this);
        one_three.setOnClickListener(this);
        one_fourth.setOnClickListener(this);

        submitBtn = findViewById(R.id.submit);
        submitBtn.setOnClickListener(this);
        maxText = findViewById(R.id.available_num);
        quoteWay = findViewById(R.id.quote_way);

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
                    inputCode.setText("");
                }
            }
        });

        plusNumIv.setOnClickListener(this);
        reduceNumIv.setOnClickListener(this);
    }

    public void change() {
        if (bsflag) {
            editText2.setBackgroundResource(R.drawable.trade_input_white_bg);
            plusNumIv.setColorFilter(getResources().getColor(R.color.trade_red));
            reduceNumIv.setColorFilter(getResources().getColor(R.color.trade_red));
            inputCode.setBackgroundResource(R.drawable.trade_input_bg);
            layout2.setBackgroundResource(R.drawable.trade_input_bg);
            layoutQuote.setBackgroundResource(R.drawable.trade_input_bg);
            full.setBackgroundResource(R.drawable.trade_input_bg);
            half.setBackgroundResource(R.drawable.trade_input_bg);
            one_three.setBackgroundResource(R.drawable.trade_input_bg);
            one_fourth.setBackgroundResource(R.drawable.trade_input_bg);
            submitBtn.setBackgroundResource(R.drawable.trade_red_corner_full);
            submitBtn.setText("立即买入");
        } else {
            editText2.setBackgroundResource(R.drawable.trade_input_white_bg_blue);
            plusNumIv.setColorFilter(getResources().getColor(R.color.trade_blue));
            reduceNumIv.setColorFilter(getResources().getColor(R.color.trade_blue));
            inputCode.setBackgroundResource(R.drawable.trade_input_bg_blue);
            layout2.setBackgroundResource(R.drawable.trade_input_bg_blue);
            layoutQuote.setBackgroundResource(R.drawable.trade_input_bg_blue);
            full.setBackgroundResource(R.drawable.trade_input_bg_blue);
            half.setBackgroundResource(R.drawable.trade_input_bg_blue);
            one_three.setBackgroundResource(R.drawable.trade_input_bg_blue);
            one_fourth.setBackgroundResource(R.drawable.trade_input_bg_blue);
            submitBtn.setBackgroundResource(R.drawable.trade_blue_corner_full);
            submitBtn.setText("立即卖出");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == plusNumIv) {
            String e1 = editText2.getText().toString();
            if (TextUtils.isEmpty(e1)) {
                e1 = "0";
            }
            int t1 = Integer.parseInt(e1);
            t1 = t1 + 100;
            editText2.setText(t1 + "");
        } else if (v == reduceNumIv) {
            String e1 = editText2.getText().toString();
            if (TextUtils.isEmpty(e1)) {
                return;
            }
            int t1 = Integer.parseInt(e1);
            t1 = t1 - 100;
            if (t1 < 0) {
                t1 = 0;
            }
            editText2.setText(t1 + "");
        } else if (v == full) {
            editText2.setText(MathUtils.save100(maxValue));
        } else if (v == half) {
            editText2.setText(MathUtils.save100(maxValue / 2));
        } else if (v == one_three) {
            editText2.setText(MathUtils.save100(maxValue / 3));
        } else if (v == one_fourth) {
            editText2.setText(MathUtils.save100(maxValue / 4));
        } else if (v == submitBtn) {
            String stockCode = inputCode.getText().toString().trim();
            String num = editText2.getText().toString().trim();
            if (isInvalid(stockCode,num)) {
                delegate.submit(entity.stkcode, price, 0,num, quoteWay.getText().toString());
            }
        } else if (v == layoutQuote) {
            if (entity == null || entity.market == null) {
                CommonUtils.show(context, "请输入股票代码");
                return;
            }

            final String[] select;
            if (entity.market.equals("0")) {
                select = YiChuangUtils.szQuoteType;
            } else if (entity.market.equals("1")) {
                select = YiChuangUtils.shQuoteType;
            } else {
                select = new String[]{};
            }
            DialogUtils.showSelectDialog(context, select, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    quoteWay.setText(select[which]);
                }
            });
        }
    }

    TradeStockEntity entity;
    int maxValue = 0;
    String price;

    public void setStockEntity(TradeStockEntity entity) {
        this.entity = entity;
        inputCode.setText(entity.stkcode + "    " + entity.stkname);
        price = MathUtils.format2Num(entity.fNewest);
        delegate.getMax(entity.stkcode, price);

        this.requestFocus();
        this.requestFocusFromTouch();
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

    private boolean isInvalid(String stockCode, String num) {
        if (entity==null|| TextUtils.isEmpty(stockCode) || TextUtils.isEmpty(num)) {
            CommonUtils.show(context, R.string.trade_notice_msg);
            return false;
        }
        return true;

    }
}
