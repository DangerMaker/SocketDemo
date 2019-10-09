package com.socket.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ez08.trade.ui.user.TradeLoginActivity;


public class DemoActivity extends AppCompatActivity {
    TextView pageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        pageName = findViewById(R.id.page_name);
        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            Intent intent = new Intent(this, TradeLoginActivity.class);
            startActivity(intent);
        });
    }
}
