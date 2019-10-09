package com.ez08.trade.ui.trade;

import com.ez08.trade.ui.trade.entity.TradeStockEntity;

public interface ITradeView {
    void setBsflag(boolean flag);
    void setMax(String max);
    void setStockCode(String code);
    void setStockEntity(TradeStockEntity entity);
    void setDelegate(OptionsDelegate delegate);
}
