package com.socket.demo.net;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AbsResponse {

    protected byte[] body;

    public AbsResponse(byte[] head, byte[] originBody,byte[] aesKey) {
        ByteBuffer headBuffer = ByteBuffer.wrap(head);
        headBuffer.order(ByteOrder.LITTLE_ENDIAN);
        STradeBaseHead sTradeBaseHead = new STradeBaseHead(headBuffer);

        byte[] encryptBody;
        if (sTradeBaseHead.bEncrypt == 1) {
            //解密
            encryptBody = OpensslHelper.decrypt(aesKey, originBody, sTradeBaseHead.dwEncRawSize);
        } else {
            encryptBody = originBody;
        }

        if (sTradeBaseHead.btCompressFlag == 2) {
            //解压
            body = OpensslHelper.unPress(encryptBody.length, sTradeBaseHead.dwRawSize, encryptBody);
        }else{
            body = encryptBody;
        }

    }

    protected int sizeof(Object type){
        return NetUtil.sizeOf(type);
    }

}
