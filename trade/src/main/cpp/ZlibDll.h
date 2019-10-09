#include "zlib.h"
#include "ndk-helper.h"

#define        GZ_HEAD_FTEXT        0x01
#define        GZ_HEAD_FHCRC        0x02
#define        GZ_HEAD_FEXTRA        0X04
#define        GZ_HEAD_FNAME        0x08
#define        GZ_HEAD_FCOMMENT    0X10

/* ========================================================================= */
#define DO1(buf) crc = crc_table[((int)crc ^ (*buf++)) & 0xff] ^ (crc >> 8);
#define DO2(buf)  DO1(buf); DO1(buf);
#define DO4(buf)  DO2(buf); DO2(buf);
#define DO8(buf)  DO4(buf); DO4(buf);

/* ========================================================================= */
/* gzip flag byte */
#define ASCII_FLAG   0x01 /* bit 0 set: file probably ascii text */
#define HEAD_CRC     0x02 /* bit 1 set: header CRC present */
#define EXTRA_FIELD  0x04 /* bit 2 set: extra field present */
#define ORIG_NAME    0x08 /* bit 3 set: original file name present */
#define COMMENT      0x10 /* bit 4 set: file comment present */
#define RESERVED     0xE0 /* bits 5..7: reserved */

#  define DEF_MEM_LEVEL 8

/* ========================================================================= */
/* 数据类型 */
//#define BYTE            unsigned char
//#define LPBYTE          unsigned char *
//#define INT             int
//#define UINT            unsigned int
//#define WORD            unsigned short
//#define BOOL            bool

//#define LPBYTE          unsigned char *
//#define INT             int32_t
//#define UINT            uint32_t
//#define BOOL            bool

typedef struct gz_stream {
    z_stream stream;
    int z_err;   /* error code for last stream operation */
    int z_eof;   /* set if end of input file */
    BYTE *file;   /* .gz file */
    Byte *inbuf;  /* input buffer */
    Byte *outbuf; /* output buffer */
    uLong crc;     /* crc32 of uncompressed data */
    char *msg;    /* error message */
    char *path;   /* path name for debugging only */
    int transparent; /* 1 if input file is not a .gz file */
    char mode;    /* 'w' or 'r' */
    long startpos; /* start of compressed data in file (header skipped) */
    long gzlen;
} gz_stream;

class CZlibDll {
public:
    static int GetGZHead(const LPBYTE pZBuf, long ZBufSize, long *srclen, long *destlen, LPBYTE*pSrcStart);

    static int UnCompressGZ(const LPBYTE pZBuf, long srclen, LPBYTE pDestBuf, long &destlen);

    static int CompressGZ(const BYTE *pSrc, long srclen, LPBYTE pDestBuf,
                          long &destlen);    //, LPCSTR filename = NULL); rem by linmaosheng

private:
    static void check_header(gz_stream *s);

    static int get_byte(gz_stream *s);

    static uLong getLong(gz_stream *s);

    static void putlong(LPBYTE pBuf, unsigned long x);

};

