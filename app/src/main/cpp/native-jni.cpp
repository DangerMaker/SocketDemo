#include "GenerateKey.h"
#include <jni.h>
#include <string>

//
// Created by 刘小鱼 on 2019-09-25.
//
static CGenerateKey *cGenerateKey = NULL;

extern "C"{

jbyteArray Java_com_socket_demo_net_OpensslHelper_genPublicKey(JNIEnv* env,
                                                               jclass clazz){
    char *gx = NULL;
    char *x = NULL;
    cGenerateKey = new CGenerateKey();
    cGenerateKey->CBN_CreateLocalKey(x,gx);

    int gxLength = strlen(gx);
    jbyteArray array = env->NewByteArray(gxLength);
    env->SetByteArrayRegion(array, 0, gxLength, reinterpret_cast<const jbyte *>(gx));
    return array;
}

}