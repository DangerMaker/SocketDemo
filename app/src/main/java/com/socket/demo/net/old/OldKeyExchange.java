package com.socket.demo.net.old;

import com.socket.demo.net.OpensslHelper;
import com.socket.demo.net.STradeBaseHead;
import com.xuhao.didi.core.iocore.interfaces.ISendable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * struct STradePacketKeyExchange
 * {
 * 	DWORD	dwIP;					//	服务器发送给客户端:	表示服务端看到的客户端IP
 * 	BYTE	btAES128;				//	客户端发送给服务器:	1==说明使用AES-128 CBC
 * 	BYTE	btClientType;			//	客户端发送给服务器:	0==客户端   1==手机
 * 	BYTE	btSupportVerification;	//	服务器发送给客户端:	表示前置机直接支持验证码服务
 * 	BYTE	btReserved[1];
 * 	DWORD	dwReserved[9];
 * 	char	szGX[0];			// 有零结尾的字符串
 * };
 * #define PID_TRADE_KEY_EXCHANGE      (PID_TRADE_COMM_BASE+1)
 * struct STradePacketKeyExchange
 * {
 *     DWORD    dwIP;
 *     //    DWORD    dwReserved[10];
 *     BYTE    btAES128;            //    1==说明使用rc4加密
 *     BYTE    btReserved[3];
 *     DWORD    dwReserved[9];
 *     char    szGX[0];            // 有零结尾的字符串
 *
 *     std::string toJSON(const char * inGX)
 *     {
 *         cJSON *json = cJSON_CreateObject();
 *
 *         cJSON_AddNumberToObject(json,"dwIP",dwIP);
 *         cJSON_AddNumberToObject(json,"btAES128",btAES128);
 *         cJSON_AddStringToObject(json,"szGX",inGX);
 *         std::string jsonstr = cJSON_Print(json);
 *         cJSON_Delete(json);
 *         return jsonstr;
 *     }
 * };
 */


public class OldKeyExchange implements ISendable {
    public int dwIP = 0;
    public byte btAES128 = 0;
    public byte[] btReserved = new byte[3];
    public int[] dwReserved = new int[9];
//    public byte szGX = 0; //??
    public byte empty = 0;

    public byte[] gx;

    @Override
    public byte[] parse() {
        gx = OpensslHelper.genPublicKey();
        //fill header
        STradeBaseHead header = new STradeBaseHead();
        header.wPid = 101;
        header.dwBodySize = header.dwRawSize = getLength() + gx.length;
        header.dwReqId = 1;

        //fill body
        dwIP = 0;
        btAES128 = 1;

        //parse
        ByteBuffer bb = ByteBuffer.allocate(header.getLength() + getLength() + gx.length);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(header.parse());
        bb.putInt(dwIP);
        bb.put(btAES128);
        bb.put(btReserved);
        for (int i = 0; i < 9; i++) {
            bb.putInt(dwReserved[i]);
        }
//        bb.put(szGX);
        bb.put(gx);
        bb.put(empty);
        return bb.array();
    }

    public int getLength(){
        return  4 + 1 + 3 + 4 * 9 + 1;
    }
}
