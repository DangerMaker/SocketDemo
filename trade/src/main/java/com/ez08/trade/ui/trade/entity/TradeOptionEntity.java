package com.ez08.trade.ui.trade.entity;

public class TradeOptionEntity {
    public String title;
    public String subtitle;
    public int imgId;
    public int extra;

    public TradeOptionEntity(String title, String subtitle, int imgId, int extra) {
        this.title = title;
        this.subtitle = subtitle;
        this.imgId = imgId;
        this.extra = extra;
    }
}
