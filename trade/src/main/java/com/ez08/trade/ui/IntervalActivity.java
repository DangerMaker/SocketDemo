package com.ez08.trade.ui;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

public abstract class IntervalActivity extends BaseActivity {
    private static final int INTERVAL_POST = 8001;
    private static final int INTERVAL_POST_DELAY = 8002;
    public static final int INTERVAL_TIME = 5000;

    public void setUserVisible(boolean visible) {
        if (visible) {
            intervalHandler.sendEmptyMessage(INTERVAL_POST_DELAY);
        } else {
            intervalHandler.removeMessages(INTERVAL_POST_DELAY);
        }
    }

    private void lazyLoad() {
        onLazyLoad();
    }

    public void resetTime(){
        intervalHandler.removeMessages(INTERVAL_POST_DELAY);
        intervalHandler.sendEmptyMessage(INTERVAL_POST_DELAY);
    }

    public abstract void onLazyLoad();

    @Override
    protected void onResume() {
        super.onResume();
        setUserVisible(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        setUserVisible(false);
    }

    @SuppressLint("HandlerLeak")
    public Handler intervalHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INTERVAL_POST:
                    lazyLoad();
                    break;
                case INTERVAL_POST_DELAY:
                    lazyLoad();
                    intervalHandler.sendEmptyMessageDelayed(INTERVAL_POST_DELAY, INTERVAL_TIME);
                    break;
            }
        }
    };

}
