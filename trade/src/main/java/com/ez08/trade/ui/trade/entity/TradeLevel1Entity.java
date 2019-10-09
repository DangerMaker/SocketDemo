package com.ez08.trade.ui.trade.entity;

import java.util.List;

public class TradeLevel1Entity {
    public double fLastClose;
    public double fHigh;
    public double fLow;
    public double fNewest;
    public double fOpen;
    public List<Dang> ask;
    public List<Dang> bid;

    public static class Dang{
        public double fPrice;
        public int fOrder;
    }
}
