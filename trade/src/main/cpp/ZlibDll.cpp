#include "ZlibDll.h"
#include "zlib.h"

#define Z_BUFSIZE 16384
static int gz_magic[2] = {0x1f, 0x8b}; /* gzip magic header */

/****************************************

ZBufsize: 压缩的文件长度＋zlib文件头结构
pSrcStart: 压缩内容的起始地址
srclen: 压缩后的文件长度
destlen: 原文件的长度
****************************************/
int CZlibDll::GetGZHead(const LPBYTE pZBuf, long ZBufSize , long *srclen, long *destlen, LPBYTE *pSrcStart)
{
	INT		skiplen = 10 ;

	if(ZBufSize<=skiplen)
		return -1;

	BYTE	gzflag = pZBuf[3];

	if ( pZBuf[0] != 0x1F || pZBuf[1] != 0x8B)
		return -1 ;

	if ( ( gzflag & GZ_HEAD_FTEXT) == GZ_HEAD_FTEXT)
		skiplen += ((WORD)*(pZBuf +10) + 2);

	if ( (gzflag & GZ_HEAD_FNAME) == GZ_HEAD_FNAME)
	{
//#ifdef _DEBUG
//		TCHAR msg[200] = {0};
//		_sntprintf_s( msg, sizeof(msg), _TRUNCATE, "%s", pZBuf + skiplen );
//		//sprintf((char*)msg, "%s", pZBuf + skiplen);
//#endif
		skiplen += (int)strlen((char*)(pZBuf + skiplen)) +1 ;
	}

	if ( (gzflag & GZ_HEAD_FCOMMENT) == GZ_HEAD_FCOMMENT)
	{
		skiplen += (INT)strlen((char*)(pZBuf + skiplen)) +1 ;
	}

	if ( (gzflag & GZ_HEAD_FHCRC) == GZ_HEAD_FHCRC)
	{
		skiplen += 2;
	}

	if(ZBufSize<=(skiplen+8))
		return -1;

	*destlen = *((int *)(pZBuf + ZBufSize - 4));
	*srclen = ZBufSize - skiplen -8 ;
	*pSrcStart = (LPBYTE)pZBuf + skiplen ;
	return 0 ;
}

/***********************************************************
inflate init
pZBuf: 压缩后的文件长度
srclen :解压缩前文件长度
pDestBuf: 解压缩后缓冲起始地址
destlen：用于存放解压缩后缓存的长度
***********************************************************/
int CZlibDll::UnCompressGZ(const LPBYTE pZBuf, long srclen, LPBYTE pDestBuf, long& destlen)
{
	if ( srclen <= 0 )
		return Z_ERRNO;
	if ( destlen <= 0 )
		return Z_ERRNO;

	gz_stream gzs ;
    z_stream &s = gzs.stream ;
	long uclen ;	//原文件长度
//	long colen ;	//压缩后的长度
	BOOLEAN	bEof = false;
	LPBYTE	pUCStart;	//压缩后的起始位置
	if (	GetGZHead(pZBuf, srclen, &(gzs.gzlen), &uclen, &pUCStart) != Z_OK)
		return Z_ERRNO;
	if ( uclen > destlen) //解压缩的空间不够大
		return Z_ERRNO;
//	pZBuf[srclen] = EOF ;
    int err;
    int level = Z_DEFAULT_COMPRESSION; /* compression level */
    int strategy = Z_DEFAULT_STRATEGY; /* compression strategy */

	BYTE* next_out  ;

	gzs.crc = crc32(0L, Z_NULL, 0);
	gzs.transparent = 0 ;
	gzs.file = pUCStart ;
	gzs.z_err = Z_OK ;
	gzs.msg = NULL ;
	gzs.startpos = 0 ;
	gzs.z_eof = 0 ;
	LPBYTE	start = pDestBuf ;
	s.zalloc = (alloc_func)0;
	s.zfree = (free_func)0;
	s.opaque = (voidpf)0;

	gzs.mode = 'r'; 
	gzs.outbuf = Z_NULL ;
	gzs.inbuf = s.next_in  = pUCStart;
	s.avail_in = ( gzs.gzlen > Z_BUFSIZE ) ? Z_BUFSIZE: gzs.gzlen;
	s.next_out =next_out= pDestBuf;
	s.avail_out = destlen;
	destlen = uclen ;
/*
    Bytef *start = (Bytef*)pUCStart; 
	next_out = pUCStart; 
    s.zalloc = (alloc_func)0;
    s.zfree = (free_func)0;
    s.opaque = (voidpf)0;
    s.next_in = gzs.inbuf = Z_NULL;
    s.next_out = gzs.outbuf = Z_NULL;
    s.avail_in = gzs.stream.avail_out = 0;
    gzs.file = NULL;
    gzs.z_err = Z_OK;
    gzs.z_eof = 0;
    gzs.crc = crc32(0L, Z_NULL, 0);
    gzs.msg = NULL;
    gzs.transparent = 0;
	gzs.mode = 'r';
	
	s.avail_out = destlen ;
	destlen = uclen ;
    s.next_out =next_out= pDestBuf;
	s.next_in = gzs.inbuf = pUCStart;
	s.avail_in = ( gzs.gzlen > Z_BUFSIZE ) ? Z_BUFSIZE: gzs.gzlen;
*/
	/* windowBits is passed < 0 to tell that there is no zlib header.
   * Note that in this case inflate *requires* an extra "dummy" byte
   * after the compressed stream in order to complete decompression and
   * return Z_STREAM_END. Here the gzip CRC32 ensures that 4 bytes are
   * present after the compressed stream.
   */
	try
	{
		err = inflateInit2(&(s), -MAX_WBITS);
		if (err != Z_OK ) 
		{
			//		TRACE1("inflate init err:%d \n", err);
			//		CString msg;
			//		msg.Format( "hello: %d" ,  err) ;
			//		AfxMessageBox(msg);
			//	inflateend(&s);
			return  Z_ERRNO;
		}
		while( s.avail_out != 0)
		{
			if (gzs.transparent) 
			{
				/* Copy first the lookahead bytes: */
				uInt n = s.avail_in;
				if (n > s.avail_out) 
					n = s.avail_out;
				if (n > 0) 
				{
					memcpy(s.next_out, s.next_in, n);
					next_out += n; 
					s.next_out = next_out;
					s.next_in   += n;
					s.avail_out -= n;
					s.avail_in  -= n;
				}
				if (s.avail_out > 0) 
				{
					memcpy(s.next_out, gzs.file, s.avail_out);
					s.avail_out = 0 ;
				}
				uclen -= s.avail_out;
				s.total_in  += (uLong)uclen;
				s.total_out += (uLong)uclen;
				if (uclen == 0) 
					gzs.z_eof = 1;
				return Z_OK;
			}
			if ( s.avail_in == 0 && !gzs.z_eof)
			{
				if ( (gzs.gzlen -s.total_in ) == 0)
				   s.avail_in = 0 ;
				else 
				   s.avail_in =	( ( gzs.gzlen - s.total_in) > Z_BUFSIZE) ? Z_BUFSIZE : (gzs.gzlen - s.total_in);
				s.next_in = pUCStart+ s.total_in;
				if (s.avail_in == 0)
				{
				   gzs.z_eof = 1 ;
				}
			}
			gzs.z_err = inflate(&(s), Z_NO_FLUSH);
			if (gzs.z_err == Z_STREAM_END ) 
			{
				/* Check CRC and original size */
				gzs.crc = crc32(gzs.crc, start, (UINT)(s.next_out - start));
				start = s.next_out ;

				if (getLong(&gzs)!= gzs.crc)
				{
					gzs.z_err  = Z_DATA_ERROR;
				}
				else 
				{
					(void)getLong(&gzs);
					/* The uncompressed length returned by above getlong() may
					 * be different from s->stream.total_out) in case of
					 * concatenated .gz files. Check for such files:
					*/
					check_header(&gzs);
					if (gzs.z_err == Z_OK) 
					{
						uLong total_in = s.total_in;
						uLong total_out = s.total_out;
						inflateReset(&s);
						s.total_in = total_in;
						s.total_out = total_out;
						gzs.crc = crc32(0L, Z_NULL, 0);
					}
				}	
			}
			if (gzs.z_err != Z_OK || gzs.z_eof) 
				break;
		}
	}
    catch(const std::bad_alloc& e)
    {
        inflateEnd(&s);
        throw ;
    }

//    catch(CMemoryException* pEx)
//    {
//        pEx->Delete();
//        inflateEnd(&s);
//        throw ;
//    }
	inflateEnd(&s);
	if ( s.avail_in == 0  )
		return Z_OK;
	else
		return Z_ERRNO;
}

int CZlibDll::CompressGZ(const BYTE* pSrc, long srclen , LPBYTE pDestBuf, long & destlen)//, LPCSTR filename )
{
	if (srclen<128) return Z_ERRNO;

#define  GZ_HEAD_LEN 10L
#  define OS_CODE  0x0b
	BYTE magic[GZ_HEAD_LEN] = {0x1f, 0x8b,Z_DEFLATED, 0 /*flags*/, 0,0,0,0 /*time*/, 0 /*xflags*/, OS_CODE}; /* gzip magic header */
	
	memset(pDestBuf, '\0', destlen);
    int err;
	int headlen = GZ_HEAD_LEN ;
    int level = Z_DEFAULT_COMPRESSION; /* compression level */
    int strategy = Z_DEFAULT_STRATEGY; /* compression strategy */
    z_stream s;
	unsigned long crc ;
	crc = crc32(0L, Z_NULL, 0);
	LPBYTE pStartBuf = pDestBuf + headlen ;

// 	if ( filename != NULL ) {
// 		magic[3] |= GZ_HEAD_FNAME ;
// 		sprintf((LPSTR)pStartBuf, "%s", filename);
// 		pStartBuf += strlen(filename) +1 ;
// 		headlen += strlen(filename) +1 ;
// 	}
	memcpy(pDestBuf, magic, GZ_HEAD_LEN);

    s.zalloc = (alloc_func)0;
    s.zfree = (free_func)0;
    s.opaque = (voidpf)0;
 
    s.next_in  = (LPBYTE)pSrc;
	s.avail_in = srclen;
    s.next_out = pStartBuf;
    s.avail_out = Z_BUFSIZE;

	err = deflateInit2(&s, level, Z_DEFLATED, -MAX_WBITS, DEF_MEM_LEVEL, strategy);
	if ( err != Z_OK)
		return err ;
	while ( s.avail_in !=0 )
	{
		if ( s.avail_out == 0)
		{
			s.next_out = pStartBuf+ s.total_out;
			s.avail_out = Z_BUFSIZE ;
		}
		err =deflate(&(s), Z_NO_FLUSH);
		if ( err != Z_OK) 
			break;
	}
	crc = crc32(crc, pSrc, srclen);

	s.avail_in = 0 ;
	int done = 0 , len = 0 ;
	for(;;)
	{
		len =Z_BUFSIZE -s.avail_out ;
		if ( len!= 0)
		{
			s.next_out = pStartBuf +s.total_out ;
			s.avail_out = Z_BUFSIZE;
		}
		if (done ) 
			break;
		err = deflate(&(s), Z_FINISH);
		if ( len == 0 && err == Z_BUF_ERROR) 
			err= Z_OK ;
		done = (s.avail_out != 0 || err == Z_STREAM_END ) ;
		if ( err != Z_OK && err != Z_STREAM_END) 
			break;
	}//for(;;)
	if ( err == Z_STREAM_END)
	{
		putlong(pStartBuf+ s.total_out , crc);
		putlong(pStartBuf+ s.total_out +4, s.total_in);
		destlen = s.total_out + headlen + 8 ;
		err = deflateEnd(&s); 
		return Z_OK ;
	}
	else
	{
		return deflateEnd(&s); 
	}
}

void CZlibDll::putlong(LPBYTE pBuf, unsigned long x)
{
    int n;
    for (n = 0; n < 4; n++) 
	{
        pBuf[n]= (BYTE)(x & 0xff);
        x >>= 8;
    }
}


/* ===========================================================================
      Check the gzip header of a gz_stream opened for reading. Set the stream
    mode to transparent if the gzip magic header is not present; set s->err
    to Z_DATA_ERROR if the magic header is present but the rest of the header
    is incorrect.
    IN assertion: the stream s has already been created sucessfully;
       s->stream.avail_in is zero for the first time, but may be non-zero
       for concatenated .gz files.
*/
void CZlibDll::check_header(gz_stream *s)
{
    int method; /* method byte */
    int flags;  /* flags byte */
    uInt len;
    int c;
    /* Check the gzip magic header */
    for (len = 0; len < 2; len++) 
	{
		c = get_byte(s);
		if (c != gz_magic[len]) 
		{
			if (len != 0) 
				s->stream.avail_in++, s->stream.next_in--;
			if (c != EOF) 
			{
				s->stream.avail_in++, s->stream.next_in--;
				s->transparent = 1;
			}
			s->z_err = s->stream.avail_in != 0 ? Z_OK : Z_STREAM_END;
			return;
		}
    }
    method = get_byte(s);
    flags = get_byte(s);
    if (method != Z_DEFLATED || (flags & RESERVED) != 0) 
	{
		s->z_err = Z_DATA_ERROR;
		return;
    }

    /* Discard time, xflags and OS code: */
    for (len = 0; len < 6; len++) 
		(void)get_byte(s);

    if ((flags & EXTRA_FIELD) != 0) 
	{	/* skip the extra field */
		len  =  (uInt)get_byte(s);
		len += ((uInt)get_byte(s))<<8;
		/* len is garbage if EOF but the loop below will quit anyway */
		while (len-- != 0 && get_byte(s) != EOF) ;
    }
    if ((flags & ORIG_NAME) != 0) 
	{ /* skip the original file name */
		while ((c = get_byte(s)) != 0 && c != EOF) ;
    }
    if ((flags & COMMENT) != 0) 
	{   /* skip the .gz file comment */
		while ((c = get_byte(s)) != 0 && c != EOF) ;
    }
    if ((flags & HEAD_CRC) != 0) 
	{  /* skip the header crc */
		for (len = 0; len < 2; len++) 
			(void)get_byte(s);
    }
    s->z_err = s->z_eof ? Z_DATA_ERROR : Z_OK;
}


/* ===========================================================================
     Read a byte from a gz_stream; update next_in and avail_in. Return EOF
   for end of file.
   IN assertion: the stream s has been sucessfully opened for reading.
*/
int CZlibDll::get_byte(gz_stream *s)
{
    if (s->z_eof) 
		return EOF;
    if (s->stream.avail_in == 0) 
	{
		if ( (s->gzlen -s->stream.total_in) == 0) 
			s->stream.avail_in = 0 ;
		else
			s->stream.avail_in =( ( s->gzlen - s->stream.total_in) > Z_BUFSIZE) ? Z_BUFSIZE : (s->gzlen - s->stream.total_in);
		if (s->stream.avail_in == 0) 
		{
			s->z_eof = 1;
			//if (ferror(s->file)) s->z_err = Z_ERRNO;
			return EOF;
		}
		s->stream.next_in = s->inbuf;
    }
    s->stream.avail_in--;
	int c = *(s->stream.next_in) ;
	s->stream.next_in ++ ;
    return c;
}



/* ===========================================================================
   Reads a long in LSB order from the given gz_stream. Sets z_err in case
   of error.
*/
uLong CZlibDll::getLong (gz_stream *s)
{
    uLong x = (uLong)get_byte(s);
    int c;

    x += ((uLong)get_byte(s))<<8;
    x += ((uLong)get_byte(s))<<16;
    c = get_byte(s);
    if (c == EOF) 
		s->z_err = Z_DATA_ERROR;
    x += ((uLong)c)<<24;
    return x;
}
