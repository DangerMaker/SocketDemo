package com.ez08.trade.net;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * struct STradeGateUserInfo
 * {
 * __int64        n64_custid;                    //    客户代码    custid    int    登陆后送，登陆客户代码
 * char        sz_custorgid[ORGID_LEN];    //    客户机构    custorgid     char(4)    登陆后送，客户所属机构
 * char        sz_trdpwd[33];                //    交易密码    trdpwd    char(32)    交易密码
 * char        sz_netaddr[271];            //    操作站点    netaddr    char(270)    网卡地址或电话号码, 必须送，不可以为空
 * char        sz_orgid[5];                //    操作机构    orgid     char(4)    操作地机构代码, 必须送，不可以为空 修改为 登录后送 系统缩写不超过4个字
 * char        sz_custcert[129];            //    客户证书    custcert    char(128)    客户证书，登陆时送空串,登陆后获得，后续请求传递
 * char        sz_netaddr2[256];            //    站点扩位    netaddr2    Char(255)    操作站点扩位，接收MAC地址和PC硬盘序列号/手机号
 * };
 */
public class STradeGateUserInfo {
    public long n64_custid = 0;
    public byte[] sz_custorgid = new byte[5];
    public byte[] sz_trdpwd = new byte[33];
    public byte[] sz_netaddr = new byte[271];
    public byte[] sz_orgid = new byte[5];
    public byte[] sz_custcert = new byte[129];
    public byte[] sz_netaddr2 = new byte[256];

    private static STradeGateUserInfo userInfo;

    public static STradeGateUserInfo getInstance() {
        if(userInfo == null){
            userInfo = new STradeGateUserInfo();
        }
        return userInfo;
    }

    public int getLength() {
        return 8 + 5 + 33 + 271 + 5 + 129 + 256;
    }

    public byte[] parse() {
        ByteBuffer bb = ByteBuffer.allocate(getLength());
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putLong(n64_custid);
        bb.put(sz_custorgid);
        bb.put(sz_trdpwd);
        bb.put(sz_netaddr);
        bb.put(sz_orgid);
        bb.put(sz_custcert);
        bb.put(sz_netaddr2);
        return bb.array();
    }
}
