package com.ez08.trade.ui.fresh_stock.entity;

public class TradeDrawLeftEntity {
    public String title;
    public int imgId;
    public String extra;

    public TradeDrawLeftEntity(String title, int imgId) {
        this.title = title;
        this.imgId = imgId;
    }

    public TradeDrawLeftEntity(String title, int imgId, String extra) {
        this.title = title;
        this.imgId = imgId;
        this.extra = extra;
    }
}
