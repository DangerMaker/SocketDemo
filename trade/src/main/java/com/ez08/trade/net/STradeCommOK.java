package com.ez08.trade.net;

import java.nio.ByteBuffer;

/**
 * #define PID_TRADE_COMM_OK		(PID_TRADE_COMM_BASE+10)
 * struct STradeCommOK
 * {
 * DWORD	dwSystemDate;
 * DWORD	dwSystemTime;
 * //DWORD dwReserved[2];
 * };
 */
public class STradeCommOK extends AbsPulseSendable {

    private int dwSystemDate;
    private int dwSystemTime;

    public STradeCommOK() {
        dwSystemDate = 0;
        dwSystemTime = 0;
    }

    @Override
    protected void getHead(STradeBaseHead header) {
        header.wPid = AbsSendable.PID_TRADE_COMM_OK;
        header.dwReqId = 0;
    }

    @Override
    protected void getBody(ByteBuffer bodyBuffer) {
        bodyBuffer.putInt(dwSystemDate);
        bodyBuffer.putInt(dwSystemTime);
    }

    @Override
    protected int getBodyLength() {
        return sizeof(dwSystemDate) + sizeof(dwSystemTime);
    }
}
