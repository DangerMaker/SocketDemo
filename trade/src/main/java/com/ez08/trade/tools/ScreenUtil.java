package com.ez08.trade.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ScreenUtil {
    public ScreenUtil() {
    }

    public static float dpToPx(Context context, float dp) {
        return context == null ? -1.0F : dp * context.getResources().getDisplayMetrics().density;
    }

    public static float pxToDp(Context context, float px) {
        return context == null ? -1.0F : px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxToSp(Context context, float pxValue) {
        if (context == null) {
            return -1.0F;
        } else {
            float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return pxValue / fontScale + 0.5F;
        }
    }

    public static float spToPx(Context context, float spValue) {
        if (context == null) {
            return -1.0F;
        } else {
            float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return spValue * fontScale + 0.5F;
        }
    }

    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static float getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return (float) point.x;
    }

    public static float getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return (float) point.y;
    }

    public static int getIntScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    public static int getIntScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.y;
    }

    public static ColorStateList setTextColor(Context mContext, int color) {
        return mContext.getResources().getColorStateList(color);
    }

    public static void hideSoftBoard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void showSoftBoard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }
}