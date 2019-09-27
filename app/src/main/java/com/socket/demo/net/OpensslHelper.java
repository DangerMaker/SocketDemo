package com.socket.demo.net;

/**
 * openssl for java
 */
public class OpensslHelper {

    public static native byte[] genPublicKey();

    public static native byte[] genMD5(byte[] gy);
}
