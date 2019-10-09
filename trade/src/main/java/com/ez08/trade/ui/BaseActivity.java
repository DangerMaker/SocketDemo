package com.ez08.trade.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected String TAG;
    protected Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    protected void dismissBusyDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }

    ProgressDialog pDialog;

    protected void showBusyDialog() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("请稍候...");
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            pDialog.show();
        } else {
            runOnUiThread(() -> pDialog.show());
        }
    }

    protected ColorStateList setTextColor(int color) {
        return context.getResources().getColorStateList(color);
    }
}
