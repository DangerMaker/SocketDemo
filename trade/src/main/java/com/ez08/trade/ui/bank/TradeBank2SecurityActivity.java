package com.ez08.trade.ui.bank;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.Callback;
import com.ez08.trade.net.Client;
import com.ez08.trade.tools.DialogUtils;
import com.ez08.trade.tools.YCParser;
import com.ez08.trade.tools.YiChuangUtils;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.bank.entity.BankBaseEntity;
import com.ez08.trade.ui.user.entity.ShareHoldersEntity;
import com.ez08.trade.ui.user.entity.TradeShareHoldersTitle;
import com.ez08.trade.user.UserHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TradeBank2SecurityActivity extends BaseActivity implements View.OnClickListener {

    ImageView img_back;
    TextView titleView;

    TextView bankName;
    TextView RMB;
    EditText funds;
    EditText bankPwd;
    EditText fundsPwd;
    Button submit;
    LinearLayout bankPwdLayout;
    LinearLayout fundsPwdLayout;
    ImageView bankNav;
    ImageView rmbNav;
    LinearLayout bankLayout;
    LinearLayout rmbLayout;
    LinearLayout fundsLayout;
    LinearLayout resultLayout;
    TextView fundsId;
    TextView fundsTotal;

    String bankCodeValue;
    String bankNameValue;
    String moneyTypeValue = "";

    boolean needFundsPwd = false;
    boolean needBankPwd = false;

    List<BankBaseEntity> bankBaseList;

    List<String> bankNames;
    List<String> moneyTypes;

    int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_bank2_security);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        titleView = findViewById(R.id.title);
        submit = findViewById(R.id.submit);

        type = getIntent().getIntExtra("type", 1);
        if (type == 1) {
            titleView.setText("银行转证券");
            submit.setText("转入证券账户");
        } else if (type == 2) {
            titleView.setText("证券转银行");
            submit.setText("转入银行账户");
        } else {
            titleView.setText("资金余额");
            submit.setText("查询余额");
        }

        bankName = findViewById(R.id.bank_name);
        RMB = findViewById(R.id.rmb);
        funds = findViewById(R.id.funds);
        bankPwd = findViewById(R.id.bank_pwd);
        fundsPwd = findViewById(R.id.funds_pwd);

        submit.setOnClickListener(this);
        bankPwdLayout = findViewById(R.id.bank_pwd_layout);
        fundsPwdLayout = findViewById(R.id.funds_pwd_layout);
        bankPwdLayout.setVisibility(needBankPwd ? View.VISIBLE : View.GONE);
        fundsPwdLayout.setVisibility(needFundsPwd ? View.VISIBLE : View.GONE);
        bankLayout = findViewById(R.id.bank_layout);
        rmbLayout = findViewById(R.id.rmb_layout);
        fundsLayout = findViewById(R.id.funds_layout);
        bankLayout.setOnClickListener(this);
        rmbLayout.setOnClickListener(this);
        fundsLayout.setVisibility(type == 3 ? View.GONE : View.VISIBLE);
        resultLayout = findViewById(R.id.result_layout);
        fundsId = findViewById(R.id.funds_id);
        fundsTotal = findViewById(R.id.funds_total);

        bankBaseList = new ArrayList<>();

        String body = "FUN=410601&TBL_IN=bankcode,moneytype,fundid;," + moneyTypeValue + "," + UserHelper.getUser().fundid + ";";
        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            dismissBusyDialog();
            if (success) {
                List<Map<String, String>> result = YCParser.parseArray(data);
                for (int i = 0; i < result.size(); i++) {
                    BankBaseEntity entity = new BankBaseEntity();
                    entity.bankcode = result.get(i).get("bankcode");
                    entity.bankname = result.get(i).get("bankname");
                    entity.moneytype = result.get(i).get("moneytype");
                    bankBaseList.add(entity);
                }

                if (!bankBaseList.isEmpty()) {
                    bankNames = getBankNames();
                    bankNameValue = bankNames.get(0);
                    bankCodeValue = getBankCodeByName(bankNameValue);
                    moneyTypes = getBankMoneyTypes(bankNameValue);
                    moneyTypeValue = moneyTypes.get(0);

                    bankName.setText(bankNameValue);
                    RMB.setText(YiChuangUtils.getMoneyType(moneyTypeValue));
                    getInfo();
                }
            }
        });

        showBusyDialog();
    }

    public void getInfo() {
        String body = "FUN=410211&TBL_IN=moneytype,bankcode;" +
                moneyTypeValue + "," +
                bankCodeValue +
                ";";
        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            dismissBusyDialog();
            if (success) {
                Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + data);
                Set<String> pn = uri.getQueryParameterNames();
                for (Iterator it = pn.iterator(); it.hasNext(); ) {
                    String key = it.next().toString();
                    if ("TBL_OUT".equals(key)) {
                        String out = uri.getQueryParameter(key);
                        String[] split = out.split(";");
                        for (int i = 1; i < split.length; i++) {
                            String[] var = split[i].split(",");
                            if (Integer.parseInt(var[4]) == 2) {
                                needFundsPwd = Boolean.parseBoolean(var[5]);
                                needBankPwd = Boolean.parseBoolean(var[6]);
                                bankPwdLayout.setVisibility(needBankPwd ? View.VISIBLE : View.GONE);
                                fundsPwdLayout.setVisibility(needFundsPwd ? View.VISIBLE : View.GONE);
                                break;
                            }

                        }
                    }
                }
            }
        });

        showBusyDialog();
    }

    String result;

    public void post() {
        String body = "FUN=410605&TBL_IN=fundid,moneytype,fundpwd,bankcode,bankpwd,banktrantype,tranamt,pwdflag,extsno;" +
                UserHelper.getUser().fundid + "," +
                moneyTypeValue + "," +
                fundsPwd.getText().toString() + "," +
                bankCodeValue + "," +
                bankPwd.getText().toString() + "," +
                type + "," +
                funds.getText().toString() + "," +
                "" + "," +
                ";";
        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            if (success) {
                Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + data);
                Set<String> pn = uri.getQueryParameterNames();
                for (Iterator it = pn.iterator(); it.hasNext(); ) {
                    String key = it.next().toString();
                    if ("TBL_OUT".equals(key)) {
                        String out = uri.getQueryParameter(key);
                        String[] split = out.split(";");
                        String[] var = split[1].split(",");
                        result = var[0];
                        funds.setText("");
                        DialogUtils.showSimpleDialog(context, "转账成功，流水号：" + result);
                    }
                }
            }else{
                DialogUtils.showSimpleDialog(context, data);

            }
        });
    }

    String yue;

    public void getCheckResult() {
        String body = "FUN=410606&TBL_IN=fundid,moneytype,fundpwd,bankcode,bankpwd;" +
                UserHelper.getUser().fundid + "," +
                moneyTypeValue + "," +
                fundsPwd.getText().toString() + "," +
                bankCodeValue + "," +
                bankPwd.getText().toString() +
                ";";

        Client.getInstance().sendBiz(body, (success, data) -> {
            Log.e("sendBiz", data);
            dismissBusyDialog();
            if (success) {
                Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + data);
                Set<String> pn = uri.getQueryParameterNames();
                for (Iterator it = pn.iterator(); it.hasNext(); ) {
                    String key = it.next().toString();
                    if ("TBL_OUT".equals(key)) {
                        String out = uri.getQueryParameter(key);
                        String[] split = out.split(";");
                        String[] var = split[1].split(",");
                        yue = var[3];
                        funds.setText(yue + "元");
                        resultLayout.setVisibility(View.VISIBLE);
                        fundsId.setText(UserHelper.getUser().fundid);
                        fundsTotal.setText(yue);
                    }
                }
            }else{
                DialogUtils.showSimpleDialog(context, data);
            }
        });

        showBusyDialog();

    }

    private List<String> getBankNames() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < bankBaseList.size(); i++) {
            if (!list.contains(bankBaseList.get(i).bankname)) {
                list.add(bankBaseList.get(i).bankname);
            }
        }
        return list;
    }

    private List<String> getBankMoneyTypes(String bankNameValue) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < bankBaseList.size(); i++) {
            if (bankNameValue.equals(bankBaseList.get(i).bankname) && !list.contains(bankBaseList.get(i).moneytype)) {
                list.add(bankBaseList.get(i).moneytype);
            }
        }
        return list;
    }

    private String getBankCodeByName(String name) {
        for (int i = 0; i < bankBaseList.size(); i++) {
            BankBaseEntity entity = bankBaseList.get(i);
            if (entity.bankname.equals(name)) {
                return entity.bankcode;
            }
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        } else if (v == submit) {
            if (type == 3) {
                getCheckResult();
            } else {
                post();
            }
        } else if (v == bankLayout) {
            DialogUtils.showSelectDialog(context, bankNames.toArray(new String[0]), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    bankNameValue = bankNames.get(which);
                    bankCodeValue = getBankCodeByName(bankNameValue);
                    moneyTypes = getBankMoneyTypes(bankNameValue);
                    moneyTypeValue = getBankMoneyTypes(bankNameValue).get(0);

                    bankName.setText(bankNameValue);
                    RMB.setText(YiChuangUtils.getMoneyType(moneyTypeValue));

                    getInfo();
                }
            });
        } else if (v == rmbLayout) {
            String[] temp = new String[moneyTypes.size()];
            for (int i = 0; i < moneyTypes.size(); i++) {
                temp[i] = YiChuangUtils.getMoneyType(moneyTypes.get(i));
            }

            DialogUtils.showSelectDialog(context, temp, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    moneyTypeValue = moneyTypes.get(which);
                    RMB.setText(YiChuangUtils.getMoneyType(moneyTypeValue));

                    getInfo();
                }
            });
        }

    }


}
