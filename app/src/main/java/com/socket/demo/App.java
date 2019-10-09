package com.socket.demo;

import android.app.Application;

import com.ez08.trade.TradeInitalizer;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TradeInitalizer.init();
    }
}
