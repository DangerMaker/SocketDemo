package com.socket.demo.net;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * SVerificationCodeBody_ReqPicA
 * char        szId[VERIFICATION_CODE_SIZE__ID+1];        //含'\0'
 * DWORD        reserve;
 * DWORD        dwLife;            //寿命毫秒
 * BYTE        yType;            //0:默认(BMP)
 * DWORD        dwPicLen;        //
 * BYTE        bufPic[0];
 */
public class VerificationCodeResp {

    private byte[] szId = new byte[21]; //21
    private int reserve;
    private int dwLife;
    private byte type;
    private int dwPicLen;
    private byte[] pic;

    public VerificationCodeResp(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.get(szId);
        reserve = buffer.getInt();
        dwLife = buffer.getInt();
        type = buffer.get();
        dwPicLen = buffer.getInt();
        pic = new byte[dwPicLen];
        buffer.get(pic);
    }

    public byte[] getPic() {
        return pic;
    }
}
