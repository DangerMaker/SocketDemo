package com.socket.demo.net;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * #define PID_TRADE_GATE__ERROR                    (PID_TRADE_GATE_BASE+9)                                //零表示出错了，非零为正确的解析包
 * struct STradeGateError
 * {
 * DWORD    dwReqId;                    //一个socket仅支持同时存在一个未完成的请求
 * DWORD    dwErrorCode;
 * char    szError[0];
 * }
 */
public class STradeGateError extends AbsResponse {
    int dwReqId;
    int dwErrorCode;
    byte[] szError;

    public STradeGateError(byte[] head, byte[] originBody, byte[] aesKey) {
        super(head, originBody, aesKey);
        ByteBuffer buffer = ByteBuffer.wrap(body);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        dwReqId = buffer.getInt();
        dwErrorCode = buffer.getInt();
        szError = new byte[body.length - sizeof(dwReqId) - sizeof(dwErrorCode)];
        buffer.get(szError);
        Log.e("STradeGateError", NetUtil.byteToStr(szError));
    }
}
