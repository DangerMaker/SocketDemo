package com.socket.demo.net;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * WORD        wPid;
 * union{
 * DWORD    dwBodyLen;
 * DWORD    dwBodySize;
 * };
 * DWORD        dwReqId;        //dwReqId>0
 * BYTE        btCompressFlag;
 * DWORD        dwRawSize;
 * bool        bEncrypt;
 * DWORD        dwCRC_BeforeEnc;
 * //++++
 * DWORD		dwEncRawSize;
 * DWORD		dwCRC_Raw_With_Id;		//原始数据CRC，加上wPid
 * DWORD		dwReserved;
 */
public class STradeBaseHead {
    public short wPid = 0; // exchange 101
    public int dwBodySize = 0;
    public int dwReqId = 0;
    public byte btCompressFlag = 0;
    public int dwRawSize = 0;
    public byte bEncrypt = 0;
    public int dwCRC_BeforeEnc = 0;
    public int dwEncRawSize = 0;
    public int dwCRC_Raw_With_Id = 0;
    public int dwReserved = 0;

    public STradeBaseHead(){

    }

    public STradeBaseHead(ByteBuffer bb){
        wPid = bb.getShort();
        dwBodySize = bb.getInt();
        dwReqId = bb.getInt();
        btCompressFlag = bb.get();
        dwRawSize = bb.getInt();
        bEncrypt = bb.get();
        dwCRC_BeforeEnc = bb.getInt();
        dwEncRawSize = bb.getInt();
        dwCRC_Raw_With_Id = bb.getInt();
        dwReserved = bb.getInt();
    }

    public byte[] parse(){
        ByteBuffer bb = ByteBuffer.allocate(getLength());
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putShort(wPid);
        bb.putInt(dwBodySize);
        bb.putInt(dwReqId);
        bb.put(btCompressFlag);
        bb.putInt(dwRawSize);
        bb.put(bEncrypt);
        bb.putInt(dwCRC_BeforeEnc);
        bb.putInt(dwEncRawSize);
        bb.putInt(dwCRC_Raw_With_Id);
        bb.putInt(dwReserved);
        return bb.array();
    }

    public int getLength(){
        return Constant.BIZ_HEAD_SIZE;
    }

    @Override
    public String toString() {
        return "STradeBaseHead{" +
                "wPid=" + wPid +
                ", dwBodySize=" + dwBodySize +
                ", dwReqId=" + dwReqId +
                ", btCompressFlag=" + btCompressFlag +
                ", dwRawSize=" + dwRawSize +
                ", bEncrypt=" + bEncrypt +
                ", dwCRC_BeforeEnc=" + dwCRC_BeforeEnc +
                ", dwEncRawSize=" + dwEncRawSize +
                ", dwCRC_Raw_With_Id=" + dwCRC_Raw_With_Id +
                ", dwReserved=" + dwReserved +
                '}';
    }
}
