package com.ez08.trade.ui.user.entity;

//FUN=410501&TBL_OUT=custid,regflag,bondreg,opendate,market,secuid,name,fundid,secuseq,status;
//                   109000512,0,0,20070801,0,0001262540,闻之梅,0,0,0;
//                   109000512,1,0,20070801,1,A337929351,闻之梅,0,0,0;
//                   109000512,0,0,20070810,2,2091163681,闻之梅,0,0,0;
//                   109000512,1,0,20070801,3,C103869886,闻之梅,0,0,0;
//                   109000512,1,0,20141230,5,A337929351,闻之梅,0,0,0;
//                   109000512,0,0,20180418,6,0001262540,闻之梅,0,0,0;
//                   109000512,0,0,20180420,S,0001262540,闻之梅,0,0,0;


public class ShareHoldersEntity {
    public String custid;
    public String regflag;
    public String bondreg;
    public String opendate;
    public String market;
    public String secuid;
    public String name;
    public String fundid;
    public String secuseq;
    public String status;

    public TradeShareHoldersItem getItem(){
        return new TradeShareHoldersItem(custid,market,secuid,name);
    }
}
