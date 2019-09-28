package com.socket.demo.net;

import com.xuhao.didi.core.iocore.interfaces.ISendable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * //客户端发送登录请求前，申请验证码文件
 * #define PID_TRADE_VERIFICATION_CODE		(PID_TRADE_COMM_BASE+12) 112
 * struct STradeVerificationCode
 * {
 * 	DWORD		dwWidth;	//像素宽(是否起作用,根据服务器设置)
 * 	DWORD		dwHeight;	//像素高(是否起作用,根据服务器设置)
 * };
 */
public class STradeVerificationCode implements ISendable {

    int dwWidth = 30;
    int dwHeight = 15;

    @Override
    public byte[] parse() {
        //fill header
        STradeBaseHead header = new STradeBaseHead();
        header.wPid = 112;
        header.dwBodySize = header.dwRawSize = getLength();
        header.dwReqId = 2;

        //fill body

        //parse
        ByteBuffer bb = ByteBuffer.allocate(header.getLength() + getLength());
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(header.parse());
        bb.putInt(dwWidth);
        bb.putInt(dwHeight);
        return bb.array();
    }

    public int getLength(){
        return 4 + 4;
    }
}
