package com.socket.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ez08.trade.net.Callback;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.STradeGateBizFun;
import com.ez08.trade.net.STradeGateLogin;
import com.ez08.trade.net.STradeGateLoginA;
import com.ez08.trade.net.STradeVerificationCode;
import com.ez08.trade.net.STradeVerificationCodeA;
import com.ez08.trade.net.StringCallback;
import com.xuhao.didi.core.pojo.OriginalData;

public class TestActivity extends AppCompatActivity {

    ImageView imageView;
    EditText editVerify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        imageView = findViewById(R.id.imageView);
        editVerify = findViewById(R.id.edit_verify);

        new Handler().postDelayed(new Runnable() {

            public void run() {
                Client.getInstance().send(new STradeVerificationCode(), new Callback() {

                    @Override
                    public void onResult(boolean success, OriginalData data) {
                        STradeVerificationCodeA resp = new STradeVerificationCodeA(data.getHeadBytes(), data.getBodyBytes());
                        byte[] picReal = resp.getPic();
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(picReal, 0, picReal.length);
                        imageView.setImageBitmap(decodedByte);
                    }
                });
            }

        }, 2000);
    }


    public void login(View view) {
        String verify = editVerify.getText().toString();
        STradeGateLogin tradeGateLogin = new STradeGateLogin();
        tradeGateLogin.setVerifyCode(verify);
        Client.getInstance().send(tradeGateLogin, new Callback() {
            @Override
            public void onResult(boolean success, OriginalData data) {
                STradeGateLoginA gateLoginA = new STradeGateLoginA(data.getHeadBytes(), data.getBodyBytes(), Client.getInstance().aesKey);
            }
        });
    }

    public void holder(View view) {
        String body = "FUN=410501&TBL_IN=fundid,market,secuid,qryflag,count,poststr;,,,1,10,;";
        Client.getInstance().sendBiz(body, new StringCallback() {
            @Override
            public void onResult(boolean success, String data) {
                Log.e("sendBiz", data);
            }
        });
    }
}
