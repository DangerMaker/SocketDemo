package com.ez08.trade.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


public abstract class BasePopupWindows<T> extends PopupWindow {

    public Activity context;
    public View container;

    public BasePopupWindows(Activity context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = inflater.inflate(getLayoutResource(), null);
        onCreateView(container);
        this.setContentView(container);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);
        this.setOnDismissListener(() -> {
            bgAlpha(context,1f);
        });
    }

    public BasePopupWindows(Activity context, int width, int height) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = inflater.inflate(getLayoutResource(), null);
        onCreateView(container);
        this.setContentView(container);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);
        this.setOnDismissListener(() -> {
               bgAlpha(context,1f);
        });
        setWidth(width);
        setHeight(height);
    }


    protected abstract void onCreateView(View view);

    protected abstract int getLayoutResource();

    protected abstract void setData(T data);

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            showAsDropDown(parent);
        } else {
            this.dismiss();
        }
    }
    protected void bgAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}
