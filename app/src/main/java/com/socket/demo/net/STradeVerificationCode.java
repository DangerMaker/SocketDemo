package com.socket.demo.net;

import java.nio.ByteBuffer;

/**
 * //客户端发送登录请求前，申请验证码文件
 * #define PID_TRADE_VERIFICATION_CODE		(PID_TRADE_COMM_BASE+12) 112
 * struct STradeVerificationCode
 * {
 * 	DWORD		dwWidth;	//像素宽(是否起作用,根据服务器设置)
 * 	DWORD		dwHeight;	//像素高(是否起作用,根据服务器设置)
 * };
 */
public class STradeVerificationCode extends AbsSendable {

    private int dwWidth = 30;
    private int dwHeight = 15;

    @Override
    protected void getHead(STradeBaseHead header) {
        header.wPid = PID_TRADE_VERIFICATION_CODE;
    }

    @Override
    protected void getBody(ByteBuffer bodyBuffer) {
        bodyBuffer.putInt(dwWidth);
        bodyBuffer.putInt(dwHeight);
    }

    @Override
    protected int getBodyLength() {
        return sizeof(dwWidth) + sizeof(dwHeight);
    }
}
