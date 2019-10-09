#include "GenerateKey.h"

CGenerateKey::CGenerateKey() : m_pBN_G(NULL)
, m_pBN_Mod(NULL)
{

	m_pBN_G = BN_new();		BN_hex2bn( &m_pBN_G,	"3" );
	m_pBN_Mod = BN_new();
	BN_hex2bn(&m_pBN_Mod,"91C97C31D3497211D02C515DAECFFE01B02159CDFBE58C93F20041E94FF5ED1CE67B33A7462\
							44EB27414AEE4607F1A67DF3235BB36DC6D1296A1FA7BED59D346FE7517D58927106D2E5B2\
							12D3B850CD0BD522C2E85BCB74CF145B7CC6EAA52FC283740E8752CE85470F44D4D51EA2C59528FA9B7646B27DDBB95EF6DA8");
}

CGenerateKey::~CGenerateKey()
{
	if( m_pBN_G )
	{	BN_free( m_pBN_G );		m_pBN_G = NULL;		}
	if( m_pBN_Mod )
		{	BN_free( m_pBN_Mod );	m_pBN_Mod = NULL;	}
}

bool CGenerateKey::CBN_CreateLocalKey( char*& x, char*& gx )
{
	if( (NULL == m_pBN_G) || (NULL == m_pBN_Mod) )
		return false;
	
	BN_CTX* ctx = BN_CTX_new();
	BIGNUM* bnGx = BN_new(); 
	BIGNUM* bnX = BN_new();
	
	BN_rand( bnX, 120, 0, 0 );
	x = BN_bn2hex( bnX );
	
	BN_mod_exp( bnGx, m_pBN_G, bnX, m_pBN_Mod, ctx );
	gx = BN_bn2hex( bnGx );
	
	BN_free( bnGx );
	BN_free( bnX );
	BN_CTX_free( ctx );
	return true;
}

bool CGenerateKey::CBN_CalcKey( char* gy, char* x, char*& key )
{
	if( (NULL == m_pBN_G) || (NULL == m_pBN_Mod) )
		return false;
	
	BN_CTX* ctx = BN_CTX_new();
	BIGNUM* bnGy = BN_new();		BN_hex2bn( &bnGy, gy );
	BIGNUM* bnX = BN_new();			BN_hex2bn( &bnX, x );
	BIGNUM* bnKey = BN_new();
	
	BN_mod_exp( bnKey, bnGy, bnX, m_pBN_Mod, ctx );
	key = BN_bn2hex( bnKey );
	
	BN_free( bnGy );
	BN_free( bnX );
	BN_free( bnKey );
	BN_CTX_free( ctx );
	return true;
}

void CGenerateKey::CBN_Free( char*& ptr )
{
	if( ptr )
	{	free( ptr );	ptr = NULL;	}
}
