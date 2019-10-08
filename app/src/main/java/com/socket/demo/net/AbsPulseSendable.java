package com.socket.demo.net;

import com.xuhao.didi.core.iocore.interfaces.IPulseSendable;
import com.xuhao.didi.core.iocore.interfaces.ISendable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class AbsPulseSendable implements IPulseSendable {

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
}
