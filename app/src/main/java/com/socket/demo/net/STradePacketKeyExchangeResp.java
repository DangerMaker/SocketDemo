package com.socket.demo.net;

import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.utils.BytesUtils;
import com.xuhao.didi.core.utils.SLog;

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
public class STradePacketKeyExchangeResp {
    public int dwIP = 0;
    public byte btAES128 = 0;
    public byte[] btReserved = new byte[3];
    public int[] dwReserved = new int[9];
    public byte szGX = 0; //??
    public byte[] gx;
    public byte empty = 0;

    public STradePacketKeyExchangeResp(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        dwIP = buffer.getInt();
        btAES128 = buffer.get();
        buffer.get(btReserved);
        for (int i = 0; i < 9; i++) {
            dwReserved[i] = buffer.getInt();
        }
        buffer.get(szGX);
        gx = new byte[data.length - getLength()];
        buffer.get(gx);
    }

    public int getLength() {
        return 4 + 1 + 3 + 4 * 9 + 1 + 1;
    }

    @Override
    public String toString() {
        return "STradePacketKeyExchangeResp{" +
                "dwIP=" + dwIP +
                ", btAES128=" + btAES128 +
                ", btReserved=" + Arrays.toString(btReserved) +
                ", dwReserved=" + Arrays.toString(dwReserved) +
                ", szGX=" + szGX +
                ", gx=" + BytesUtils.toHexStringForLog(OpensslHelper.genPublicKey()) +
                ", empty=" + empty +
                '}';
    }
}
