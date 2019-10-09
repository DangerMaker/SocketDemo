package com.ez08.trade.ui.user.entity;

public class TradeShareHoldersItem {
//    public String code;
//    public String name;
//    public String market;
//    public String account;
//
//    public TradeShareHoldersItem(String code, String name, String market, String account) {
//        this.code = code;
//        this.name = name;
//        this.market = market;
//        this.account = account;
//    }
    public String custid; //客户代码
    public String market; //交易市场
    public String secuid; //股东代码
    public String name; //股东姓名
    public String secuseq; //序号
    public String regflag; //指定交易状态

    public TradeShareHoldersItem(String custid, String market, String secuid, String name) {
        this.custid = custid;
        this.market = market;
        this.secuid = secuid;
        this.name = name;
    }
}
