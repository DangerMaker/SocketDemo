package com.socket.demo.net;

import com.xuhao.didi.core.iocore.interfaces.ISendable;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.socket.demo.net.Constant.BIZ_HEAD_SIZE;
import static com.socket.demo.net.Constant.UTF8;

/**
 * 2010
 * struct STradeGateLogin
 * {
 * STradeGateUserInfo        userinfo;            //用户信息，必送
 * #define INPUTTYPE_FUND        'Z'                    //'Z'表示以资金帐户登陆  -登陆标识为资金帐户
 * #define INPUTTYPE_OTHER        'O'                    //其他表示以股东代码登陆  -登陆标识为对应市场的股东代码
 * char                    sz_inputtype[2];    //登陆类型    inputtype    char(1)    Y    见备注
 * char                    sz_inputid[65];        //登陆标识    inputid    char(64)    Y    见备注
 * char                    sz_market[2];        //市场标识    以股东代码登陆时，为对应的市场代码（这个字段文档里没有，是根据文档里的备注信息猜来的）
 * BYTE                    btMD5_of_Client[16];
 * char                    szVerificationId[21];
 * char                    szVerificationCode[9];
 * char                    szReserved[21];
 * <p>
 * };
 */
public class STradeGateLogin implements ISendable {

    STradeGateUserInfo userinfo = new STradeGateUserInfo();
    byte[] sz_inputtype = new byte[2];
    byte[] sz_inputid = new byte[65];
    byte[] sz_market = new byte[2];
    byte[] btMD5_of_Client = {0x34, (byte) 0x8f,0x7a, (byte) 0xfe,0x5a, (byte) 0xe4,0x61,0x1c, (byte) 0xfc, (byte) 0xea,0x3e,0x28, (byte) 0x88, (byte) 0xd0, (byte) 0xb8,0x2b};
    byte[] szVerificationId = new byte[21];
    byte[] szVerificationCode = new byte[9];
    byte[] szReserved = new byte[21];

    public int getLength() {
        return  BIZ_HEAD_SIZE +
                userinfo.getLength() +
                sz_inputtype.length +
                sz_inputid.length +
                sz_market.length +
                btMD5_of_Client.length +
                szVerificationId.length +
                szVerificationCode.length +
                szReserved.length
                ;
    }

    String ip;
    public void setIP(String ip){
        this.ip = ip;
    }

    @Override
    public byte[] parse() {
        STradeBaseHead header = new STradeBaseHead();
        header.wPid = 2010;
        header.dwBodySize = header.dwRawSize = getLength();
        header.dwReqId = 2;

        //fill body
        String strUserType = "Z";
        String strUserId = "109000512";
        String strCheckCode = "3333"; //??
        String strVerifiCodeId = "101"; //??
        String strPassword = "123123";
        String strNet2 = "ZNZ|000EC6CF8DA4|PLEXTOR";

        try {
            sz_inputtype = strUserType.getBytes(UTF8);
            sz_inputid = strUserId.getBytes(UTF8);
            szVerificationCode = strCheckCode.getBytes(UTF8);
            szVerificationId = strVerifiCodeId.getBytes(UTF8);
            userinfo.sz_trdpwd = strPassword.getBytes(UTF8);
            userinfo.sz_netaddr = ip.getBytes(UTF8);
            userinfo.sz_netaddr2 = strNet2.getBytes(UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        //parse
        ByteBuffer bb = ByteBuffer.allocate(getLength());
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(header.parse());
        bb.put(userinfo.parse());
        bb.put(sz_inputtype);
        bb.put(sz_inputid);
        bb.put(sz_market);
        bb.put(btMD5_of_Client);
        bb.put(szVerificationId);
        bb.put(szVerificationCode);
        bb.put(szReserved);
        return bb.array();
    }
}
