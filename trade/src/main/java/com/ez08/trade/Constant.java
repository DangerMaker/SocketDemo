package com.ez08.trade;

import com.ez08.trade.tools.CommonUtils;

public class Constant {
    public static final String SERVER_IP = "118.26.24.26";
    public static final int VERITY_SERVER_PORT = 35502;
    public static final int BIZ_SERVER_PORT = 15001;

    public static final String SERVER_CHARSET = "GB2312";
    public static final String URI_DEFAULT_HELPER = "http://123.com?";
    public static final String STORE_LOGIN_NAME = "store_login_name";

    public static String getWebUrl(){
        String token = CommonUtils.getCurrentTime();
        return "http://ecywtest.95358.com:8145/fcsc-risk-app/?" +
                "service_type=1&product_type=4&product_code=131575" +
                "&tacode=95561" +
                "&goto_url=L21hbGwvcHJvamVjdC92aWV3cy9sY3QvY2pneS9wdXJjaGFzZUJrLmh0bWw%2FcHJvZHV" +
                "jdF90eXBlPTQmcHJvZHVjdF9jb2RlPTEzMTU3NSZ0YV9jb2RlPTk1NTYxJmlzSGlnaFJpc2s9MCZpc0ludm" +
                "VzdG9yPTA%3D&fail_url=L2Zjc2MvcHJvcGVyL3Jpc2tUcmFuc2Zlci5odG1sPw%3D%3D" +
                "&czzd=1121&token="+token+"#/";
    }

}
