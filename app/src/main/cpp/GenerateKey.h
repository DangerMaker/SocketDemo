#include "bn.h"

class CGenerateKey  
{
public:
	CGenerateKey();
	virtual ~CGenerateKey();

//function
public:
	//产生本的一半密钥，
	//如果没有调用过 CBN_Init ，返回 false
	// x 和 gx 是函数返回的数据，如果 x 或 gx 非空，使用完毕后应该调用 CBN_Free 释放内存
	// x 是本地的一半密钥， gx 应该发送给对端
	bool  CBN_CreateLocalKey( char*& x, char*& gx );

	//通过异地的一半密钥和本地的一半密钥计算会话密钥
	//如果没有调用过 CBN_Init ，返回 false
	// gy 是对端传过来的字符串，对应于本地产生的 gx
	// x 是本地调用 CBN_CreateLocalKey 所产生的 x
	// key 是函数返回的数据，如果 key 非空，使用完毕后应该调用 CBN_Free 释放内存
	bool  CBN_CalcKey( char* gy, char* x, char*& key );

	void  CBN_Free( char*& ptr );


protected:
private:

//variable
public:
protected:
	BIGNUM* m_pBN_G;
	BIGNUM* m_pBN_Mod;
private:

};
