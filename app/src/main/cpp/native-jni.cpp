#include "GenerateKey.h"
#include <jni.h>
#include <string>
#include "md5.h"
#include "ndk-helper.h"
#include <arpa/inet.h>
#include <android/log.h>


#define LOGI(...) \
  ((void)__android_log_print(ANDROID_LOG_INFO, "compass_trade::", __VA_ARGS__))


//
// Created by 刘小鱼 on 2019-09-25.
//
static CGenerateKey *cGenerateKey = NULL;
static char *mx = NULL;

extern "C" {

jbyteArray Java_com_socket_demo_net_OpensslHelper_genPublicKey(JNIEnv *env,
                                                               jclass clazz) {
    char *gx = NULL;
    char *x = NULL;
    cGenerateKey = new CGenerateKey();
    cGenerateKey->CBN_CreateLocalKey(x, gx);
    mx = x;
    int gxLength = (DWORD)strlen(gx);
    return unsignedChar2JByteArray(env, reinterpret_cast<unsigned char *>(gx), gxLength);

}

jbyteArray Java_com_socket_demo_net_OpensslHelper_genMD5(JNIEnv *env,
                                                         jclass clazz, jbyteArray arg2) {
    char *key = NULL;
    BYTE *result = new BYTE[16];
    int gy_length;
    unsigned char *gy = jByteArray2UnsignedChar(env, arg2, gy_length);
    cGenerateKey->CBN_CalcKey(reinterpret_cast<char *>(gy), mx, key);
    MD5((BYTE *) key, strlen(key), result);
    return unsignedChar2JByteArray(env, result, 16);
}

jbyteArray Java_com_socket_demo_net_OpensslHelper_unPress(JNIEnv *env, jclass type,
                                                          jint dwBodySize,
                                                          jint dwRawSize,
                                                          jbyteArray body) {
    int body_length;
    int out_length;
    unsigned char *body_buffer = jByteArray2UnsignedChar(env, body, body_length);
    unsigned char *press_buffer = unPress(dwBodySize, dwRawSize, body_buffer, out_length);
    return unsignedChar2JByteArray(env, press_buffer, out_length);
}


jbyteArray Java_com_socket_demo_net_OpensslHelper_decrypt(JNIEnv *env, jclass type,
                                                          jbyteArray key,
                                                          jbyteArray body,
                                                          jint dwEncRawSize) {

    int key_length;
    BYTE* key_buffer = jByteArray2UnsignedChar(env,key,key_length);
    int body_length;
    BYTE* body_buffer = jByteArray2UnsignedChar(env,body,body_length);

    LOGI("%d",dwEncRawSize);
    decrypt(key_buffer,dwEncRawSize,body_buffer);
    return unsignedChar2JByteArray(env,body_buffer,dwEncRawSize);
}

jbyteArray Java_com_socket_demo_net_OpensslHelper_ipConvert(JNIEnv *env, jclass type,
                                                            jint ip){
    char *paddress = inet_ntoa(*(in_addr*)ip);
    return unsignedChar2JByteArray(env, reinterpret_cast<unsigned char *>(paddress), strlen(paddress));
}




}