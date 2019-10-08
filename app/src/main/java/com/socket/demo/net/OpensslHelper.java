package com.socket.demo.net;

/**
 * openssl for java
 */
public class OpensslHelper {

    public static native byte[] genPublicKey();

    public static native byte[] genMD5(byte[] gy);

    public static native byte[] unPress(int bodySize,int rawSize,byte[] body);

    public static native byte[] decrypt(byte[] key,byte[] body,int dwEncRawSize);
}
