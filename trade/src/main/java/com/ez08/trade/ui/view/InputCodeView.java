package com.ez08.trade.ui.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.ScreenUtil;

public class InputCodeView extends RelativeLayout {

    EditText editText;
    TextView textView;

    public InputCodeView(Context context) {
        super(context);
    }

    public InputCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.trade_view_input_code, this);
        editText = findViewById(R.id.input);
        textView = findViewById(R.id.text);
        textView.setOnClickListener(v -> {
            textView.setVisibility(GONE);
            editText.setVisibility(VISIBLE);
            editText.requestFocus();
            ScreenUtil.showSoftBoard(getContext(),editText);
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().length() == 6) {
                    if (listener != null)
                        listener.search(editText.getText().toString());
                }
            }
        });
    }

    public void setData(String code, String name) {
        textView.setVisibility(VISIBLE);
        editText.setVisibility(GONE);
        textView.setText(code + "  " + name);
        editText.setText("");
        ScreenUtil.hideSoftBoard(getContext(),editText);
    }

    OnSearchListener listener;

    public void setOnSearchListener(OnSearchListener listener) {
        this.listener = listener;
    }

    public interface OnSearchListener {
        void search(String code);
    }

    public EditText getEditText() {
        return editText;
    }
}
