package com.ez08.trade.net;

import android.util.Log;

import com.xuhao.didi.core.utils.BytesUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class STradeSessionUpdateA  extends AbsResponse {

    byte[] sSessionId = new byte[30];
    int[] dwReserved = new int[10];

    public STradeSessionUpdateA(byte[] head, byte[] originBody, byte[] aesKey) {
        super(head, originBody, aesKey);
        ByteBuffer buffer = ByteBuffer.wrap(body);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.get(sSessionId);

        for (int i = 0; i < dwReserved.length; i++) {
            dwReserved[i] = buffer.getInt(i);
        }

        Log.e("STradeSessionUpdateA", BytesUtils.toHexStringForLog(sSessionId));
    }

    public byte[] getsSessionId(){
        return sSessionId;
    }
}