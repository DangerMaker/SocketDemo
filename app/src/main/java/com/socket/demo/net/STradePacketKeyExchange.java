package com.socket.demo.net;

import com.xuhao.didi.core.iocore.interfaces.ISendable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * DWORD    dwIP;
 * BYTE    btAES128;            //    1==说明使用rc4加密
 * BYTE    btReserved[3];
 * DWORD    dwReserved[9];
 * char    szGX[0];
 */
public class STradePacketKeyExchange implements ISendable {
    public int dwIP = 0;
    public byte btAES128 = 0;
    public byte[] btReserved = new byte[3];
    public int[] dwReserved = new int[9];
    public byte szGX = 0; //??

    public byte empty = 0;
    public byte[] gx;

    @Override
    public byte[] parse() {
        gx = OpensslHelper.genPublicKey();
        //fill header
        STradeBaseHead header = new STradeBaseHead();
        header.wPid = 101;
        header.dwBodySize = header.dwRawSize = getLength() + gx.length;
        header.dwReqId = 1;

        //fill body
        dwIP = 0;
        btAES128 = 1;

        //parse
        ByteBuffer bb = ByteBuffer.allocate(header.getLength() + getLength() + gx.length);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(header.parse());
        bb.putInt(dwIP);
        bb.put(btAES128);
        bb.put(btReserved);
        for (int i = 0; i < 9; i++) {
            bb.putInt(dwReserved[i]);
        }
        bb.put(szGX);
        bb.put(gx);
        bb.put(empty);
        return bb.array();
    }

    public int getLength(){
        return  4 + 1 + 3 + 4 * 9 + 1 + 1;
    }
}
