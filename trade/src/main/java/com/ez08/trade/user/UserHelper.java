package com.ez08.trade.user;

import java.util.List;

public class UserHelper {

    public static List<TradeUser> userList;

    public static TradeUser user;

    public static TradeUser getUser(){
        return user;
    }

    public static TradeUser getUserByMarket(String market){
        for (int i = 0; i < userList.size(); i++) {
            TradeUser user = userList.get(i);
            if(user.market.equals(market)){
                return user;
            }
        }

        return null;
    }

    public static List<TradeUser> getUserList(){
        return userList;
    }

    public static void setUserList(List<TradeUser> args){
        userList = args;
        user = userList.get(0);
    }

}
