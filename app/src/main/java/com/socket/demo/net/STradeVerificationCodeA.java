package com.socket.demo.net;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * struct STradeVerificationCodeA
 * {
 * 	char		szId[21];		//含'\0'
 * 	DWORD		reserve;
 * 	DWORD		dwLife;			//寿命毫秒
 * 	BYTE		yType;			//0:默认(BMP)
 * 	DWORD		dwPicLen;		//
 * 	BYTE		bufPic[0];
 * };
 */
public class STradeVerificationCodeA {
    public byte[] szId = new byte[21]; //21
    public int reserve;
    public int dwLife;
    public byte type;
    public int dwPicLen;
    public byte[] pic;

    public STradeVerificationCodeA(byte[] head,byte[] originBody) {
        ByteBuffer headBuffer = ByteBuffer.wrap(head);
        headBuffer.order(ByteOrder.LITTLE_ENDIAN);
        STradeBaseHead sTradeBaseHead = new STradeBaseHead(headBuffer);
        byte[] body = unPress(sTradeBaseHead.dwBodySize,sTradeBaseHead.dwRawSize,originBody);

        ByteBuffer buffer = ByteBuffer.wrap(body);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.get(szId);
        reserve = buffer.getInt();
        dwLife = buffer.getInt();
        type = buffer.get();
        dwPicLen = buffer.getInt();
        pic = new byte[dwPicLen];
        buffer.get(pic);
    }

    private byte[] unPress(int bodySize,int rawSize,byte[] body){
        return OpensslHelper.unPress(bodySize,rawSize,body);
    }

    public byte[] getPic() {
        return pic;
    }

    @Override
    public String toString() {
        return "STradeVerificationCodeA{" +
                "szId=" + Arrays.toString(szId) +
                ", reserve=" + reserve +
                ", dwLife=" + dwLife +
                ", type=" + type +
                ", dwPicLen=" + dwPicLen +
//                ", pic=" + Arrays.toString(pic) +
                '}';
    }
}
