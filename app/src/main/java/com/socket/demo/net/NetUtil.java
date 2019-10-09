package com.socket.demo.net;

public class NetUtil {

    public static String intToIp(int ipInt) {
        return new StringBuilder().append(((ipInt >> 24) & 0xff)).append('.')
                .append((ipInt >> 16) & 0xff).append('.').append(
                        (ipInt >> 8) & 0xff).append('.').append((ipInt & 0xff))
                .toString();
    }

    public static int sizeOf(Object type){
        if(type instanceof Integer){
            return 4;
        }else if(type instanceof Byte){
            return 1;
        }else if(type instanceof STradeGateUserInfo){
            return ((STradeGateUserInfo) type).getLength();
        }
        else if(type instanceof byte[]){
            return ((byte[]) type).length;
        }else if(type instanceof int[]){
            return ((int[]) type).length * 4;
        }
        else{
            return 0;
        }
    }

    public static String byteToStr(byte[] buffer) {
        try {
            int length = 0;
            for (int i = 0; i < buffer.length; ++i) {
                if (buffer[i] == 0) {
                    length = i;
                    break;
                }
            }
            return new String(buffer, 0, length, "GB2312");
        } catch (Exception e) {
            return "";
        }

    }

    public static void byteCopy(byte[] origin,byte[] target){
        for (int i = 0; i < origin.length; i++) {
            target[i] = origin[i];
        }
    }
}
