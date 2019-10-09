//package com.ez08.trade.ui.user;
//
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.ez08.trade.Constant;
//import com.ez08.trade.R;
//import com.ez08.trade.net.Callback;
//import com.ez08.trade.net.Client;
//import com.ez08.trade.net.ClientHelper;
//import com.ez08.trade.net.Response;
//import com.ez08.trade.net.request.BizRequest;
//import com.ez08.trade.tools.CommonUtils;
//import com.ez08.trade.ui.BaseActivity;
//
//import org.json.JSONObject;
//
//import java.util.Iterator;
//import java.util.Set;
//
//public class TradeChangePwdActivity extends BaseActivity implements View.OnClickListener {
//
//    TradePwdPopupWindows popupWindows;
//    LinearLayout selectPwdType;
//    ImageView backBtn;
//    TextView titleView;
//
//    TextView typeTV;
//    TextView title1;
//    TextView title2;
//    TextView title3;
//    EditText original;
//    EditText newPwd;
//    EditText repeat;
//    Button submit;
//
//    Context mContext;
//
//    int type = 0; // 0 交易密码 1资金密码
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.trade_activity_change_pwd);
//
//        mContext = this;
//        titleView = findViewById(R.id.title);
//        titleView.setText("修改密码");
//        backBtn = findViewById(R.id.img_back);
//        backBtn.setOnClickListener(this);
//        selectPwdType = findViewById(R.id.select_type);
//        selectPwdType.setOnClickListener(this);
//
//        typeTV = findViewById(R.id.type);
//        title1 = findViewById(R.id.title1);
//        title2 = findViewById(R.id.title2);
//        title3 = findViewById(R.id.title3);
//        original = findViewById(R.id.original);
//        newPwd = findViewById(R.id.new_pwd);
//        repeat = findViewById(R.id.repeat);
//        submit = findViewById(R.id.submit);
//        submit.setOnClickListener(this);
//
//        if (type == 0) {
//            typeTV.setText("交易密码");
//            title1.setText("当前交易密码");
//            title2.setText("新交易密码　");
//            title3.setText("确认交易密码");
//        } else {
//            typeTV.setText("资金密码");
//            title1.setText("当前资金密码");
//            title2.setText("新资金密码　");
//            title3.setText("确认资金密码");
//        }
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.select_type) {
//            if (popupWindows == null) {
//                popupWindows = new TradePwdPopupWindows(this);
//            }
//            popupWindows.showAsDropDown(selectPwdType);
//            popupWindows.setCallback(new TradePwdPopupWindows.Callback() {
//                @Override
//                public void callback(int type) {
//                    TradeChangePwdActivity.this.type = type;
//                    if (type == 0) {
//                        typeTV.setText("交易密码");
//                        title1.setText("当前交易密码");
//                        title2.setText("新交易密码　");
//                        title3.setText("确认交易密码");
//                    } else {
//                        typeTV.setText("资金密码");
//                        title1.setText("当前资金密码");
//                        title2.setText("新资金密码　");
//                        title3.setText("确认资金密码");
//                    }
//                    popupWindows.dismiss();
//                }
//            });
//        } else if (v.getId() == R.id.img_back) {
//            finish();
//        } else if (v.getId() == R.id.submit) {
//            String oldPwdValue = original.getText().toString();
//            String newPwdValue = newPwd.getText().toString();
//            String repeatPwdValue = repeat.getText().toString();
//
//            if(repeatPwdValue.equals(newPwdValue)){
//                CommonUtils.show(mContext, "两次输入密码不一致");
//            }
//
//            if (type == 0) {
//                String body = "FUN=410302&TBL_IN=newpwd;" +
//                        newPwdValue + ";";
//                BizRequest request = new BizRequest();
//                request.setBody(body);
//                request.setCallback(new Callback() {
//                    @Override
//                    public void callback(Client client, Response data) {
//                        if (data.isSucceed()) {
//                            Log.e("TradeChangeMsgActivity", data.getData());
//                            try {
//                                JSONObject jsonObject = new JSONObject(data.getData());
//                                String content = jsonObject.getString("content");
//                                Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
//                                Set<String> pn = uri.getQueryParameterNames();
//                                for (Iterator it = pn.iterator(); it.hasNext(); ) {
//                                    String key = it.next().toString();
//                                    if ("TBL_OUT".equals(key)) {
//                                        String out = uri.getQueryParameter(key);
//                                        String[] split = out.split(";");
//                                        for (int i = 1; i < split.length; i++) {
//                                            String[] var = split[i].split(",");
//                                            CommonUtils.show(mContext, var[0]);
//                                            finish();
//                                        }
//                                    }
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//                ClientHelper.get().send(request);
//            } else {
//                String body = "FUN=410303&TBL_IN=fundid,oldfundpwd,newfundpwd;" +
//                        "" + "," +
//                        oldPwdValue + "," +
//                        newPwdValue + ";";
//                BizRequest request = new BizRequest();
//                request.setBody(body);
//                request.setCallback(new Callback() {
//                    @Override
//                    public void callback(Client client, Response data) {
//                        if (data.isSucceed()) {
//                            Log.e("TradeChangeMsgActivity", data.getData());
//                            try {
//                                JSONObject jsonObject = new JSONObject(data.getData());
//                                String content = jsonObject.getString("content");
//                                Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
//                                Set<String> pn = uri.getQueryParameterNames();
//                                for (Iterator it = pn.iterator(); it.hasNext(); ) {
//                                    String key = it.next().toString();
//                                    if ("TBL_OUT".equals(key)) {
//                                        String out = uri.getQueryParameter(key);
//                                        String[] split = out.split(";");
//                                        for (int i = 1; i < split.length; i++) {
//                                            String[] var = split[i].split(",");
//                                            CommonUtils.show(mContext, var[0]);
//                                            finish();
//                                        }
//                                    }
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//                ClientHelper.get().send(request);
//            }
//        }
//    }
//}
