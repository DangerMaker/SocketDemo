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

import com.socket.demo.net.Callback;
import com.socket.demo.net.Client;
import com.socket.demo.net.STradeGateLogin;
import com.socket.demo.net.STradeGateLoginA;
import com.socket.demo.net.STradeVerificationCode;
import com.socket.demo.net.STradeVerificationCodeA;
import com.socket.demo.net.SnFactory;
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
                    public void onResult(OriginalData data) {
                        STradeVerificationCodeA resp = new STradeVerificationCodeA(data.getHeadBytes(), data.getBodyBytes());
                        byte[] picReal = resp.getPic();
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(picReal, 0, picReal.length);
                        imageView.setImageBitmap(decodedByte);
                        Log.e("STradeVerificationCodeA", resp.toString());
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
            public void onResult(OriginalData data) {
                STradeGateLoginA gateLoginA = new STradeGateLoginA(data.getHeadBytes(),data.getBodyBytes(),Client.getInstance().aesKey);
            }
        });
    }
}
