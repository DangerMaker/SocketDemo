package com.ez08.trade.net;

import java.nio.ByteBuffer;

public class STradeHQOrderItem {
    public float fPrice;
    public float fOrder;

    public STradeHQOrderItem(ByteBuffer buffer) {
        fPrice = buffer.getFloat();
        fOrder = buffer.getFloat();
    }
}
