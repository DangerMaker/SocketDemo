package com.ez08.trade.net;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import static com.ez08.trade.net.Constant.UTF8;
import static com.ez08.trade.net.NetUtil.byteCopy;

/**
 * //用户登陆（410301）
 * #define PID_TRADE_GATE__LOGIN                    (PID_TRADE_GATE_BASE+10)
 * struct STradeGateLogin
 * {
 * STradeGateUserInfo        userinfo;            //用户信息，必送
 * #define INPUTTYPE_FUND        'Z'                    //'Z'表示以资金帐户登陆  -登陆标识为资金帐户
 * #define INPUTTYPE_OTHER        'O'                    //其他表示以股东代码登陆  -登陆标识为对应市场的股东代码
 * char                    sz_inputtype[2];    //登陆类型    inputtype    char(1)    Y    见备注
 * char                    sz_inputid[65];        //登陆标识    inputid    char(64)    Y    见备注
 * char                    sz_market[2];        //市场标识    以股东代码登陆时，为对应的市场代码（这个字段文档里没有，是根据文档里的备注信息猜来的）
 * BYTE                    btMD5_of_Client[16];
 * union
 * {
 * struct
 * {
 * char            szVerificationId[21];
 * char            szVerificationCode[9];
 * };
 * struct
 * {
 * BYTE            btSessionId[30];                //Session登录
 * };
 * };
 * char                    szReserved[19];
 * BYTE                    bIsSessionId;                    //==FALSE，表示验证码
 * BYTE                    bIgnoreVerificationCode;        //前置机控制填入，如果前置机校验了验证码，这个域 == TRUE
 * };
 * <p>
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
public class STradeGateLogin extends AbsSendable {

    STradeGateUserInfo userinfo = STradeGateUserInfo.getInstance();
    byte[] sz_inputtype = new byte[2];
    byte[] sz_inputid = new byte[65];
    byte[] sz_market = new byte[2];
    byte[] btMD5_of_Client = {0x34, (byte) 0x8f, 0x7a, (byte) 0xfe, 0x5a, (byte) 0xe4, 0x61, 0x1c, (byte) 0xfc, (byte) 0xea, 0x3e, 0x28, (byte) 0x88, (byte) 0xd0, (byte) 0xb8, 0x2b};
    byte[] szVerificationId = new byte[21];
    byte[] szVerificationCode = new byte[9];
    byte[] szReserved = new byte[19];
    byte bIsSessionId = 0;
    byte bIgnoreVerificationCode = 0;
   //
    byte[] btSessionId = new byte[30];

    String userType;
    String userId;
    String password;
    String verifyCode;

    public void setVerifyCode(String verifyCode){
        this.verifyCode = verifyCode;
    }

    public void setBody(String userType, String userId, String password, String checkCode){
        this.userType = userType;
        this.userId = userId;
        this.password = password;
        this.verifyCode = checkCode;
        bIsSessionId = 0;
    }

    public void setBody(String userType, String userId, String password, byte[] btSessionId){
        this.userType = userType;
        this.userId = userId;
        this.password = password;
        NetUtil.byteCopy(btSessionId,this.btSessionId);
        bIsSessionId = 1;
    }


    @Override
    protected void getHead(STradeBaseHead header) {
        header.wPid = PID_TRADE_GATE_LOGIN;
    }

    @Override
    protected void getBody(ByteBuffer bb) {
        String strUserType = "Z";
        String strUserId = "109000512";
        String strVerifiCodeId = "1012"; //??
        String strPassword = "123123";
        String strNet2 = "ZNZ|000EC6CF8DA4|PLEXTOR";

        try {
            byteCopy(userType.getBytes(UTF8),sz_inputtype);
            byteCopy(userId.getBytes(UTF8),sz_inputid);
            byteCopy(verifyCode.getBytes(UTF8),szVerificationCode);
            byteCopy(password.getBytes(UTF8),userinfo.sz_trdpwd);
//            byteCopy(ip.getBytes(UTF8),userinfo.sz_netaddr);
            byteCopy(strNet2.getBytes(UTF8),userinfo.sz_netaddr2);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        bb.put(userinfo.parse());
        bb.put(sz_inputtype);
        bb.put(sz_inputid);
        bb.put(sz_market);
        bb.put(btMD5_of_Client);
        if(bIsSessionId == 0) {
            bb.put(szVerificationId);
            bb.put(szVerificationCode);
        }else{
            bb.put(btSessionId);
        }
        bb.put(szReserved);
        bb.put(bIsSessionId);
    }

    @Override
    protected int getBodyLength() {
        return  sizeof(userinfo) +
                sizeof(sz_inputtype) +
                sizeof(sz_inputid) +
                sizeof(sz_market) +
                sizeof(btMD5_of_Client) +
                sizeof(szVerificationId) +
                sizeof(szVerificationCode) +
                sizeof(szReserved) +
                sizeof(bIsSessionId) +
                sizeof(bIgnoreVerificationCode);
    }
}
