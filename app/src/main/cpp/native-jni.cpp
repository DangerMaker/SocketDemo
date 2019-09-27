#include "GenerateKey.h"
#include <jni.h>
#include <string>
#include "md5.h"
#include "ndk-helper.h"


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
    int gxLength = strlen(gx);
    jbyteArray array = env->NewByteArray(gxLength);
    env->SetByteArrayRegion(array, 0, gxLength, reinterpret_cast<const jbyte *>(gx));
    return array;
}

jbyteArray Java_com_socket_demo_net_OpensslHelper_genMD5(JNIEnv *env,
                                                         jclass clazz, jbyteArray arg2) {
    char *key = NULL;
    BYTE* result = new BYTE[16];
    char *gy = convertJByteaArrayToChars(env, arg2);
    cGenerateKey->CBN_CalcKey(gy, mx, key);
    MD5((BYTE*)key, strlen(key), result);
    int resultLength = 16;
    jbyteArray array = env->NewByteArray(resultLength);
    env->SetByteArrayRegion(array, 0, resultLength, reinterpret_cast<const jbyte *>(result));
    return array;
}


}