package com.socket.demo.net;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * struct STradeGateBizFunA
 * {
 * DWORD                    dwContentLen;
 * DWORD                    reserve[4];
 * <p>
 * char                    szContent[0];            //见 BizFun字符串例子
 * };
 */
public class STradeGateBizFunA extends AbsResponse {

    int dwContentLen;
    int[] reserve = new int[4];
    byte[] szContent;

    public STradeGateBizFunA(byte[] head, byte[] originBody, byte[] aesKey) {
        super(head, originBody, aesKey);

        ByteBuffer buffer = ByteBuffer.wrap(body);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        dwContentLen = buffer.getInt();
        for (int i = 0; i < reserve.length; i++) {
            reserve[i] = buffer.getInt();
        }

        szContent = new byte[body.length - sizeof(dwContentLen) - sizeof(reserve)];
        buffer.get(szContent);
        Log.e("STradeGateBizFunA", NetUtil.byteToStr(szContent));
    }
}
