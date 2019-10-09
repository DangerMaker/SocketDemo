package com.ez08.trade.net;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * #define PID_TRADE_GATE__BIZFUN                (PID_TRADE_GATE_BASE+15)
 * struct STradeGateBizFun
 * {
 * STradeGateUserInfo        userinfo;                //    用户信息，必送
 * <p>
 * DWORD                    dwContentLen;            //非空时含'\0'
 * DWORD                    reserve[4];
 * <p>
 * char                    szContent[0];            //见 BizFun字符串例子
 * };
 */
public class STradeGateBizFun extends AbsSendable {
    STradeGateUserInfo userinfo = STradeGateUserInfo.getInstance();
    int dwContentLen = 0;
    int[] reserve = new int[4];
    byte[] bizBytes;

    public STradeGateBizFun(String biz) {
        setBiz(biz);
    }

    public void setBiz(String biz){
        try {
            bizBytes = biz.getBytes("GB2312");
            dwContentLen = bizBytes.length + 1;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void getHead(STradeBaseHead header) {
        header.wPid = PID_TRADE_GATE_BIZFUN;
    }

    @Override
    protected void getBody(ByteBuffer bodyBuffer) {
        bodyBuffer.put(userinfo.parse());
        bodyBuffer.putInt(dwContentLen);
        for (int i = 0; i < reserve.length; i++) {
            bodyBuffer.putInt(reserve[i]);
        }
        bodyBuffer.put(bizBytes);
    }

    @Override
    protected int getBodyLength() {
        return sizeof(userinfo)
                + sizeof(reserve)
                + sizeof(dwContentLen)
                + sizeof(bizBytes) + 1;
    }
}
