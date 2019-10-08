package com.socket.demo.net;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * struct STradeGateLoginAItem
 * {
 * #define CUSTPROP_GENERAL '0'            //普通客户
 * #define CUSTPROP_AGENT     '1'            //客户代理人
 * #define    CUSTPROP_BROKER     '2'            //经纪人
 * <p>
 * char        sz_custprop[2];            //        客户性质    custprop    char(1)
 * char        sz_market[MARKET_LEN];    //        交易市场    market    char(1)
 * <p>
 * char        sz_secuid[SECUID_LEN];    //        股东代码    secuid    char(10)
 * char        sz_name[17];            //        股东姓名    name    char(16)
 * __int64        n64_fundid;                //        缺省资金帐户fundid    Int64
 * __int64        n64_custid;                //        客户代码    custid    Int64    登录后送，客户端要保存，以后的业务要带这个东西
 * char        sz_custname[17];        //        客户姓名    custname    char(16)
 * char        sz_orgid[ORGID_LEN];    //        机构编码    orgid    char(4)      登录后送，客户端要保存，以后的业务要带这个东西
 * <p>
 * char        sz_timeoutflag[2];        //        延时属性    timeoutflag    char(1)    1延时0不延时
 * char        sz_authlevel[2];        //        认证方式/级别    authlevel    char(1)    安全级别
 * int            n_pwderrtimes;            //        登陆错误次数    pwderrtimes    int
 * char        sz_singleflag[2];        //        客户标志        singleflag    char(1)    0 个人 1 机构
 * char        sz_checkpwdflag[2];        //        密码有效标志    checkpwdflag    char(1)    0 正常 1 过期 2 未修改
 * <p>
 * char        sz_custcert[129];        //        客户证书    custcert    char(128)，登录成功后返回的会话信息，以后做委托或查询时传入作为入参  登陆时送空串,登陆后获得，后续请求传递  登录成功后返回的会话信息，以后做委托或查询时传入作为入参
 * char        sz_tokenlen[9];            //        登录时输入的动态令牌长度    tokenlen    char(8)    如为普通方式，则是0
 * char        sz_lastlogindate[9];    //        最近登录日期    lastlogindate    char(8)
 * char        sz_lastlogintime[9];    //        最近登录时间    lastlogintime    char(8)
 * char        sz_lastloginip[65];        //        最近登录IP    lastloginip    char(64)
 * char        sz_lastloginmac[33];    //        最近登录MAC    lastloginmac    char(32)
 * char        sz_inputtype[2];        //        登陆类型    inputtype    char(1)
 * char        sz_inputid[65];            //        登陆标识    inputid    char(64)
 * char        sz_tokenenddate[9];        //        客户动态令牌结束日期    tokenenddate    char(8)
 * char        sz_bindflag[2];
 */
public class STradeGateLoginAItem {
    byte[] sz_custprop = new byte[2];
    byte[] sz_market = new byte[2];
    byte[] sz_secuid = new byte[11];
    byte[] sz_name = new byte[17];
    long n64_fundid = 0;
    long n64_custid = 0;
    byte[] sz_custname = new byte[17];
    byte[] sz_orgid = new byte[5];
    byte[] sz_timeoutflag = new byte[2];
    byte[] sz_authlevel = new byte[2];
    int n_pwderrtimes = 0;
    byte[] sz_singleflag = new byte[2];
    byte[] sz_checkpwdflag = new byte[2];
    byte[] sz_custcert = new byte[129];
    byte[] sz_tokenlen = new byte[9];
    byte[] sz_lastlogindate = new byte[9];
    byte[] sz_lastlogintime = new byte[9];
    byte[] sz_lastloginip = new byte[65];
    byte[] sz_lastloginmac = new byte[33];
    byte[] sz_inputtype = new byte[2];
    byte[] sz_inputid = new byte[65];
    byte[] sz_tokenenddate = new byte[9];
    byte[] sz_bindflag = new byte[2];

    public STradeGateLoginAItem(ByteBuffer buffer) {
        buffer.get(sz_custprop);
        buffer.get(sz_market);
        buffer.get(sz_secuid);
        buffer.get(sz_name);
        n64_fundid = buffer.getLong();
        n64_custid = buffer.getLong();
        buffer.get(sz_custname);
        buffer.get(sz_orgid);
        buffer.get(sz_timeoutflag);
        buffer.get(sz_authlevel);
        n_pwderrtimes = buffer.getInt();
        buffer.get(sz_singleflag);
        buffer.get(sz_checkpwdflag);
        buffer.get(sz_custcert);
        buffer.get(sz_tokenlen);
        buffer.get(sz_lastlogindate);
        buffer.get(sz_lastlogintime);
        buffer.get(sz_lastloginip);
        buffer.get(sz_lastloginmac);
        buffer.get(sz_inputtype);
        buffer.get(sz_inputid);
        buffer.get(sz_tokenenddate);
        buffer.get(sz_bindflag);
    }

    @Override
    public String toString() {
        return "STradeGateLoginAItem{" +
                "sz_custprop=" + byteToStr(sz_custprop) +
                ", sz_market=" + byteToStr(sz_market) +
                ", sz_secuid=" + byteToStr(sz_secuid) +
                ", sz_name=" + byteToStr(sz_name) +
                ", n64_fundid=" + n64_fundid +
                ", n64_custid=" + n64_custid +
                ", sz_custname=" + byteToStr(sz_custname) +
                ", sz_orgid=" + byteToStr(sz_orgid) +
                ", sz_timeoutflag=" + byteToStr(sz_timeoutflag) +
                ", sz_authlevel=" + byteToStr(sz_authlevel) +
                ", n_pwderrtimes=" + n_pwderrtimes +
                ", sz_singleflag=" + byteToStr(sz_singleflag) +
                ", sz_checkpwdflag=" + byteToStr(sz_checkpwdflag) +
                ", sz_custcert=" + byteToStr(sz_custcert) +
                ", sz_tokenlen=" + byteToStr(sz_tokenlen) +
                ", sz_lastlogindate=" + byteToStr(sz_lastlogindate) +
                ", sz_lastlogintime=" + byteToStr(sz_lastlogintime) +
                ", sz_lastloginip=" + byteToStr(sz_lastloginip) +
                ", sz_lastloginmac=" + byteToStr(sz_lastloginmac) +
                ", sz_inputtype=" + byteToStr(sz_inputtype) +
                ", sz_inputid=" + byteToStr(sz_inputid) +
                ", sz_tokenenddate=" + byteToStr(sz_tokenenddate) +
                ", sz_bindflag=" + byteToStr(sz_bindflag) +
                '}';
    }

    public String byteToStr(byte[] buffer) {
        try {
            int length = 0;
            for (int i = 0; i < buffer.length; ++i) {
                if (buffer[i] == 0) {
                    length = i;
                    break;
                }
            }
            return new String(buffer, 0, length, "GBK");
        } catch (Exception e) {
            return "";
        }

    }
}
