package com.ez08.trade.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected View rootView;
    protected Context mContext;
    protected String TAG;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        TAG = getClass().getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
        }

        onCreateView(rootView);
        return rootView;
    }

    protected abstract int getLayoutResource();

    protected abstract void onCreateView(View rootView);

    protected void dismissBusyDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }

    ProgressDialog pDialog;

    protected void showBusyDialog() {
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("请稍候...");
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            pDialog.show();
        } else {
            ((Activity)mContext).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    pDialog.show();
                }
            });
        }
    }

    protected ColorStateList setTextColor(int color) {
        return mContext.getResources().getColorStateList(color);
    }
}
