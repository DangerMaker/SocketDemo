package com.ez08.trade;

import com.ez08.trade.net.Client;
import com.xuhao.didi.core.utils.SLog;

public class TradeInitalizer {

    public static void init(){
        System.loadLibrary("opensslLib");
        SLog.setIsDebug(true);
        Client.getInstance().connect();
    }
}
