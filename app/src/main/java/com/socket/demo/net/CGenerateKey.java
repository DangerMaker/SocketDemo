package com.socket.demo.net;

import com.xuhao.didi.core.utils.SLog;

public class CGenerateKey {

    static {
        SLog.e("System.loadLibrary(\"opensslLib\");");
    }

    private byte[] localKey;
    private byte[] forRemoteKey;

    public CGenerateKey(){
        //初始化参数
    }

    public void CreateLocalKey(){
        localKey = new byte[1];
        forRemoteKey = new byte[1];
    }

    public static byte[] CalcKey(byte[] localKey,byte[] remoteKey){

        return null;
    }
}
