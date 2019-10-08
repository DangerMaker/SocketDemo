package com.socket.demo;

import android.app.Application;

import com.socket.demo.net.Client;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Client.getInstance().connect();
    }
}
