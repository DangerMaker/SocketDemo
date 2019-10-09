package com.ez08.trade.net;

import com.xuhao.didi.core.pojo.OriginalData;

public interface Callback {
    void onResult(boolean success, OriginalData data);
}
