package com.ez08.trade.net;


import java.nio.ByteBuffer;

import static com.ez08.trade.net.NetUtil.byteCopy;

/**
 * #define PID_TRADE_HQ_QUERY    (PID_TRADE_HQ_BASE+2)
 * struct STradeHQQuery
 * {
 *     DWORD    idMarket;//'shhq'
 *     char    szCode[13];//
 * };
 */
public class STradeHQQuery extends AbsSendable{

    byte[] idMarket = new byte[4];
    byte[] szCode = new byte[13];

    public STradeHQQuery(String idMarket, String szCode) {
        byteCopy(idMarket, this.idMarket);
        byteCopy(szCode, this.szCode);
    }

    @Override
    protected void getHead(STradeBaseHead header) {
        header.wPid = PID_TRADE_HQ_QUERY;
    }

    @Override
    protected void getBody(ByteBuffer bodyBuffer) {
        bodyBuffer.put(idMarket);
        bodyBuffer.put(szCode);
    }

    @Override
    protected int getBodyLength() {
        return sizeof(idMarket) + sizeof(szCode);
    }
}
