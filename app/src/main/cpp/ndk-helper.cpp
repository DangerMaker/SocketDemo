//
// Created by 刘小鱼 on 2019-09-27.
//

#include "ndk-helper.h"
#include "ZlibDll.h"

char* convertJByteArrayToChars(JNIEnv *env, jbyteArray bytearray) {
    char *chars = NULL;
    jbyte *bytes;
    bytes = env->GetByteArrayElements(bytearray, 0);
    int chars_len = env->GetArrayLength(bytearray);
    chars = new char[chars_len + 1];
    memset(chars, 0, chars_len + 1);
    memcpy(chars, bytes, chars_len);
    chars[chars_len] = 0;
    env->ReleaseByteArrayElements(bytearray, bytes, 0);
    return chars;
}

unsigned char* as_unsigned_char_array(JNIEnv *env, jbyteArray array,int &outlength)
{
    outlength = env->GetArrayLength (array);
    unsigned char* buf = new unsigned char[outlength];
    env->GetByteArrayRegion(array, 0, outlength, reinterpret_cast<jbyte*>(buf));
    return buf;
}

jbyteArray unsignedChar2JbyteArray(JNIEnv *env, unsigned char* buf, int len)
{
    jbyteArray array = env->NewByteArray(len);
    env->SetByteArrayRegion(array, 0, len, reinterpret_cast<jbyte*>(buf));
    return array;
}


BYTE* unPress(DWORD dwBodySize,DWORD dwRawSize,BYTE* body,int& outlength) {
    BYTE *buffer = new BYTE[dwRawSize];
    memset(buffer, 0,dwRawSize);

    long destlen = dwRawSize;
    int nRet = CZlibDll::UnCompressGZ(body,dwBodySize, buffer, destlen);
    if(nRet != Z_OK) {
        delete [] buffer;
        buffer = NULL;
        return buffer;
    }
    outlength = dwRawSize;
    return buffer;
}