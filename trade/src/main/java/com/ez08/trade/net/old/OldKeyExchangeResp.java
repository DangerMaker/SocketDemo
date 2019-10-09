package com.ez08.trade.net.old;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * DWORD    dwIP;
 * BYTE    btAES128;            //    1==说明使用rc4加密
 * BYTE    btReserved[3];
 * DWORD    dwReserved[9];
 * char    szGX[0];
 */
public class OldKeyExchangeResp {
    public int dwIP = 0;
    public byte btAES128 = 0;
    public byte[] btReserved = new byte[3];
    public int[] dwReserved = new int[9];
    //    public byte szGX;
    public byte[] gy;
    public byte empty = 0;

    public OldKeyExchangeResp(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        dwIP = buffer.getInt();
        btAES128 = buffer.get();
        buffer.get(btReserved);
        for (int i = 0; i < 9; i++) {
            dwReserved[i] = buffer.getInt();
        }

//        szGX = buffer.get();
        gy = new byte[data.length - getLength() - 1];
        buffer.get(gy);
        empty = buffer.get();
    }

    public int getLength() {
        return 4 + 1 + 1 + 1 + 1 + 4 * 9;
    }

    @Override
    public String toString() {
        return "STradePacketKeyExchangeResp{" +
                "dwIP=" + dwIP +
                ", btAES128=" + btAES128 +
                ", btReserved=" + Arrays.toString(btReserved) +
                ", dwReserved=" + Arrays.toString(dwReserved) +
                ", gy=" + Arrays.toString(gy) +
                ", empty=" + empty +
                '}';
    }
}
