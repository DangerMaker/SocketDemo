package com.ez08.trade.ui.user;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.CommonUtils;
import com.ez08.trade.ui.BasePopupWindows;
import com.ez08.trade.ui.view.MyDelEditetext;

/**
 * 手机号登录校验
 */
public class TradePhoneCheckWindows extends BasePopupWindows implements View.OnClickListener {


    private TextView btnSendVeritify;
    private MyDelEditetext etInputPhoneNum;
    private MyDelEditetext etInputVertify;
    private String phoneNum="";
    private String phoneVerity="";
    private OnPhoneCheckListener phoneCheckListener;

    public TradePhoneCheckWindows(Activity context) {
        super(context);
    }

    @Override
    protected void onCreateView(View view) {
        TextView btnLoginCancel = view.findViewById(R.id.login_input_cancel);
        btnLoginCancel.setOnClickListener(this);
        TextView btnLoginCommit = view.findViewById(R.id.login_input_commit);
        btnLoginCommit.setOnClickListener(this);
        btnSendVeritify = view.findViewById(R.id.btnSendVerfity);
        btnSendVeritify.setOnClickListener(this);
        etInputPhoneNum = view.findViewById(R.id.etInputPhoneNum);
        etInputVertify = view.findViewById(R.id.etInputVertify);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_phone_login;
    }

    @Override
    protected void setData(Object data) {

    }

    @Override
    public void showPopupWindow(View parent) {
        if (this.isShowing()){
            this.dismiss();
        }else {
            this.bgAlpha(context,0.5f);
            this.showAtLocation(parent, Gravity.CENTER,0,0);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.btnSendVerfity){
            phoneNum = etInputPhoneNum.getText().toString().trim();
            if (!TextUtils.isEmpty(phoneNum)){
                cdTimer.start();
                btnSendVeritify.setClickable(false);
                if (phoneCheckListener!=null){
                    phoneCheckListener.onSendNum(phoneNum);
                }
            }
        }else if (v.getId()== R.id.login_input_commit){
            phoneNum = etInputPhoneNum.getText().toString().trim();
            String vertify = etInputVertify.getText().toString().trim();
          if (isInvalid(phoneNum,vertify)){
             dismiss();
          }
        }else if (v.getId()== R.id.login_input_cancel){
            dismiss();
        }
    }
    private CountDownTimer cdTimer = new CountDownTimer(60000, 1000) {
        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            btnSendVeritify.setText((millisUntilFinished / 1000) + "s");
        }

        @Override
        public void onFinish() {
            btnSendVeritify.setClickable(true);
            btnSendVeritify.setText(R.string.trade_retry_verifity);
        }
    };
    private boolean isInvalid(String num, String verity) {
        if ( TextUtils.isEmpty(num) ) {
            CommonUtils.show(context, R.string.trade_input_phoneNum);
            return false;
        }
        if ( TextUtils.isEmpty(verity)) {
            CommonUtils.show(context, R.string.trade_login_verity);
            return false;
        }else {
            return phoneVerity.equals(verity);
        }
    }
    public interface OnPhoneCheckListener{
        void onSendNum(String phoneNum);
    }

    public void setPhoneCheckListener(OnPhoneCheckListener phoneCheckListener) {
        this.phoneCheckListener = phoneCheckListener;
    }

    public void setPhoneVerity(String phoneVerity) {
        this.phoneVerity = phoneVerity;
    }
}
