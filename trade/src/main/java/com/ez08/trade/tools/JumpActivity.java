package com.ez08.trade.tools;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.ez08.trade.ui.TradeWebActivity;
//import com.ez08.trade.ui.bank.TradeBankActivity;
//import com.ez08.trade.ui.fresh_stock.TradeDaijiaoActivity;
//import com.ez08.trade.ui.fresh_stock.TradeFastStockActivity;
//import com.ez08.trade.ui.fresh_stock.TradeNewStockActivity;
//import com.ez08.trade.ui.fresh_stock.TradePeihaoActivity;
//import com.ez08.trade.ui.fresh_stock.TradeZhongqianActivity;
//import com.ez08.trade.ui.invite.TradeInvitedBuyActivity1;
//import com.ez08.trade.ui.other.TradeOrderActivity;
//import com.ez08.trade.ui.other.TradeOtherServiceActivity;
//import com.ez08.trade.ui.other.TradeTransStockActivity;
//import com.ez08.trade.ui.query.TradeQueryActivity;
//import com.ez08.trade.ui.trade.TradeActivity;
//import com.ez08.trade.ui.trade.TradeBatActivity;
//import com.ez08.trade.ui.trade.TradeBuySellActivity;
//import com.ez08.trade.ui.trade.TradeMarketPriceActivity;
//import com.ez08.trade.ui.user.TradeChangeMsgActivity;
//import com.ez08.trade.ui.user.TradeChangePwdActivity;
import com.ez08.trade.ui.bank.TradeBankActivity;
import com.ez08.trade.ui.fresh_stock.TradeDaijiaoActivity;
import com.ez08.trade.ui.fresh_stock.TradeFastStockActivity;
import com.ez08.trade.ui.fresh_stock.TradeNewStockActivity;
import com.ez08.trade.ui.fresh_stock.TradePeihaoActivity;
import com.ez08.trade.ui.fresh_stock.TradeZhongqianActivity;
import com.ez08.trade.ui.invite.TradeInvitedBuyActivity1;
import com.ez08.trade.ui.query.TradeQueryActivity;
import com.ez08.trade.ui.trade.TradeActivity;
import com.ez08.trade.ui.trade.TradeBatActivity;
import com.ez08.trade.ui.trade.TradeBuySellActivity;
import com.ez08.trade.ui.trade.TradeMarketPriceActivity;
import com.ez08.trade.ui.user.TradeChangeMsgActivity;
import com.ez08.trade.ui.user.TradeChangePwdActivity;
import com.ez08.trade.ui.user.TradeLoginActivity;
//import com.ez08.trade.ui.user.TradeRiskLevelActivity;
import com.ez08.trade.ui.user.TradeRiskLevelActivity;
import com.ez08.trade.ui.user.TradeShareholdersActivity;
//import com.ez08.trade.ui.vote.TradeNetVoteActivity;

import java.util.HashMap;
import java.util.Map;

public class JumpActivity {
    public static Map<String, Class> classMap = new HashMap<>();

    static {
        classMap.put("买入", TradeActivity.class);
        classMap.put("卖出", TradeActivity.class);
        classMap.put("撤单", TradeActivity.class);
        classMap.put("持仓", TradeActivity.class);
        classMap.put("查询", TradeQueryActivity.class);
        classMap.put("市价买卖", TradeMarketPriceActivity.class);
        classMap.put("批量委托", TradeBatActivity.class);
        classMap.put("新股申购", TradeFastStockActivity.class);
        classMap.put("新股申购查询", TradeNewStockActivity.class);
        classMap.put("配号查询", TradePeihaoActivity.class);
        classMap.put("中签查询", TradeZhongqianActivity.class);
        classMap.put("新股申购代缴款查询", TradeDaijiaoActivity.class);
        classMap.put("银行转账", TradeBankActivity.class);
//        classMap.put("预埋单", TradeOrderActivity.class);
        classMap.put("预受要约", TradeInvitedBuyActivity1.class);
//        classMap.put("转股回售", TradeTransStockActivity.class);
//        classMap.put("网络投票", TradeNetVoteActivity.class);
//        classMap.put("权证行权", TradeOtherServiceActivity.class);
        classMap.put("股东资料", TradeShareholdersActivity.class);
        classMap.put("客户风险级别查询", TradeRiskLevelActivity.class);
        classMap.put("客户风险级别测评", TradeWebActivity.class);
        classMap.put("修改资料", TradeChangeMsgActivity.class);
        classMap.put("修改密码", TradeChangePwdActivity.class);
        classMap.put("对买对卖", TradeBuySellActivity.class);
        classMap.put("登录", TradeLoginActivity.class);
    }

    public static void start(Context context, String action) {
        if (!TextUtils.isEmpty(action)) {
            try {
                Intent intent = new Intent();
                if (action.equals("买入")) {
                    intent.putExtra("extra", 0);
                }
                if (action.equals("卖出")) {
                    intent.putExtra("extra", 1);
                }
                if (action.equals("撤单")) {
                    intent.putExtra("extra", 2);
                }
                if (action.equals("持仓")) {
                    intent.putExtra("extra", 3);
                }
                intent.setClass(context, classMap.get(action));
                context.startActivity(intent);
            } catch (Exception e) {
                Log.e("exp", "跳转出错");
            }

        }
    }

    public static void start(Context context, Intent intent, String action) {
        if (!TextUtils.isEmpty(action)) {
            try {
                intent.setClass(context, classMap.get(action));
                context.startActivity(intent);
            } catch (Exception e) {
                Log.e("exp", "跳转出错");
            }
        }
    }
}
