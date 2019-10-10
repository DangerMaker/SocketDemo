package com.ez08.trade.net;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * DWORD                    idMarket;
 * char                    szCode[13];                //证券代码
 * DWORD                    dwHQDate;
 * DWORD                    dwHHMMSSNNN;            //时间，hhmmssNNN
 * float                    fLastClose;
 * float                    fOpen;                    //开盘价
 * float                    fHigh;                    //最高价
 * float                    fLow;                    //最低价
 * float                    fNewest;                //最新价
 * float                    fVolume;                //总成交量，股
 * float                    fAmount;                //总成交额，元
 * float                    fPreCloseIOPV;            //基金行情
 * float                    fIOPV;                    //基金行情
 * STradeHQOrderItem        ask[5];
 * STradeHQOrderItem        bid[5];
 */
public class STradeHQQueryA extends AbsResponse {

    public int idMarket;
    public byte[] szCode = new byte[13]; //todo ??
    public int dwHQDate;
    public int dwHHMMSSNNN;
    public float fLastClose;
    public float fOpen;
    public float fHigh;
    public float fLow;
    public float fNewest;
    public float fVolume;
    public float fAmount;
    public float fPreCloseIOPV;
    public float fIOPV;
    public STradeHQOrderItem[] ask = new STradeHQOrderItem[5];
    public STradeHQOrderItem[] bid = new STradeHQOrderItem[5];

    public STradeHQQueryA(byte[] head, byte[] originBody, byte[] aesKey) {
        super(head, originBody, aesKey);

        ByteBuffer buffer = ByteBuffer.wrap(body);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        idMarket = buffer.getInt();
        buffer.get(szCode);
        dwHQDate = buffer.getInt();
        dwHHMMSSNNN = buffer.getInt();
        fLastClose = buffer.getFloat();
        fOpen = buffer.getFloat();
        fHigh = buffer.getFloat();
        fLow = buffer.getFloat();
        fNewest = buffer.getFloat();
        fVolume = buffer.getFloat();
        fAmount = buffer.getFloat();
        fPreCloseIOPV = buffer.getFloat();
        fIOPV = buffer.getFloat();
        for (int i = 0; i < ask.length; i++) {
            ask[i] = new STradeHQOrderItem(buffer);
        }

        for (int i = 0; i < bid.length; i++) {
            bid[i] = new STradeHQOrderItem(buffer);
        }
    }


}
