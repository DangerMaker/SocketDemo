package com.ez08.trade.ui.other;

import java.io.Serializable;

public class OrderEntity implements Serializable {
//    stockEntity.market + "," +
//    user.secuid + "," +
//    user.fundid + "," +
//    code + "," +
//    postFlag + "," +
//    price + "," +
//    qty + "," +
//            "0" +
    public String market;
    public String code;
    public String name;
    public String bsflag;
    public String price;
    public String qty;
    public String dict;
    public String date;
    public boolean isSelect = false;
}
