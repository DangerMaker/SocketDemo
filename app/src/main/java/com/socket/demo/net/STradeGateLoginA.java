package com.socket.demo.net;

import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * struct STradeGateLoginA
 * {
 * BOOLEAN        bLoginSucc;        //登陆是否成功
 * union
 * {
 * struct{//bLoginSucc=TRUE
 * DWORD                    dwCount;
 * STradeGateLoginAItem    pItem[0];
 * };
 * struct{//bLoginSucc=FALSE, dwLen包含'\0'
 * DWORD                    dwLen;
 * char                    szErrMsg[0];    //end with '\0'
 * };
 * };
 */
public class STradeGateLoginA {
    public int bLoginSucc;
    public int dwCount;
    public List<STradeGateLoginAItem> list;
    public byte[] szErrMsg;
    public String msg;

    public STradeGateLoginA(byte[] head, byte[] originBody, byte[] aesKey) {
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

        byte[] unPressBody;
        if (sTradeBaseHead.btCompressFlag == 2) {
            //解压
            unPressBody = OpensslHelper.unPress(encryptBody.length, sTradeBaseHead.dwRawSize, encryptBody);
        } else {
            unPressBody = encryptBody;
        }

        ByteBuffer buffer = ByteBuffer.wrap(unPressBody);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        bLoginSucc = buffer.getInt();
        if (bLoginSucc == 0) {
            dwCount = buffer.getInt();
            szErrMsg = new byte[dwCount];
            buffer.get(szErrMsg);
            Log.e("STradeGateLoginA", NetUtil.byteToStr(szErrMsg));
        } else {
            dwCount = buffer.getInt();
            list = new ArrayList<>();
            for (int i = 0; i < dwCount; i++) {
                STradeGateLoginAItem item = new STradeGateLoginAItem(buffer);
                Log.e("STradeGateLoginA", item.toString());
                list.add(item);
                if (i == 0) {
                    STradeGateUserInfo.getInstance().n64_custid = list.get(i).n64_custid;
                    NetUtil.byteCopy(list.get(i).sz_orgid, STradeGateUserInfo.getInstance().sz_custorgid);
                    NetUtil.byteCopy(list.get(i).sz_orgid,  STradeGateUserInfo.getInstance().sz_orgid);
                    NetUtil.byteCopy(list.get(i).sz_custcert, STradeGateUserInfo.getInstance().sz_custcert);
                }
            }
        }
    }
}
