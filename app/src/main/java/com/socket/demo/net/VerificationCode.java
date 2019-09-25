package com.socket.demo.net;

import com.xuhao.didi.core.iocore.interfaces.ISendable;

import java.nio.ByteBuffer;


/**
 * WORD        wPid;
 * DWORD       dwBodyLen;
 * <p>
 * #define        VERIFICATION_CODE_SIZE__ID                (size_t(20))
 * char        szId[VERIFICATION_CODE_SIZE__ID+1];        //含'\0'
 * DWORD        dwWidth;     //像素宽(是否起作用,根据服务器设置)
 * DWORD        dwHeight;    //像素高(是否起作用,根据服务器设置)
 */
public class VerificationCode implements ISendable {
    @Override
    public byte[] parse() {

        ByteBuffer bb = ByteBuffer.allocate(6 + 21 + 4 + 4);
//        bb.order(ByteOrder.LITTLE_ENDIAN);
//        //pid
//        bb.putShort((short) 100);
//        //dwBodyLen
//        bb.putInt(21 + 4 + 4);
//        //szId
//        byte[] szId = new byte[21];
//        bb.put(szId);
//        //dwWidth;
//        bb.putInt(200);
//        bb.putInt(100);
        byte[] hehe = new byte[]{0x64, 0x00, 0x1d, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00,
                0x1e, 0x00, 0x00, 0x00,
                0x0f, 0x00, 0x00, 0x00,
        };

        bb.put(hehe);

        return bb.array();
    }
}
