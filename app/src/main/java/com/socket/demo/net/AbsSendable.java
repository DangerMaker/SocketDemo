package com.socket.demo.net;

import com.xuhao.didi.core.iocore.interfaces.ISendable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class AbsSendable implements ISendable {

    protected int dwReqId = 0;

    public static final int PID_TRADE_COMM_OK = 110;
    public static final int PID_TRADE_KEY_EXCHANGE = 101;
    public static final int PID_TRADE_VERIFICATION_CODE = 112;
    public static final int PID_TRADE_GATE_LOGIN = 2010;
    public static final int PID_TRADE_GATE_BIZFUN = 2015;
    public static final int PID_TRADE_GATE_ERROR = 2009;



    protected int sizeof(Object type){
        return NetUtil.sizeOf(type);
    }

    @Override
    public byte[] parse() {
        ByteBuffer buffer = ByteBuffer.allocate(Constant.BIZ_HEAD_SIZE + getBodyLength());
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        //fill header
        STradeBaseHead header = new STradeBaseHead();
        header.dwBodySize = header.dwRawSize = getBodyLength();
        getHead(header);
        buffer.put(header.parse());
        //fill body
        getBody(buffer);
        return buffer.array();
    }

    protected abstract void getHead(STradeBaseHead header);

    protected abstract void getBody(ByteBuffer bodyBuffer);

    protected abstract int getBodyLength();

    public void setDwReqId(int dwReqId){
        this.dwReqId = dwReqId;
    }
}
