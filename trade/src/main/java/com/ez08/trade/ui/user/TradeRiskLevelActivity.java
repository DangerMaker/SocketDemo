//package com.ez08.trade.ui.user;
//
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.ez08.trade.Constant;
//import com.ez08.trade.R;
//import com.ez08.trade.net.Callback;
//import com.ez08.trade.net.Client;
//import com.ez08.trade.net.ClientHelper;
//import com.ez08.trade.net.Response;
//import com.ez08.trade.net.request.BizRequest;
//import com.ez08.trade.ui.BaseActivity;
//import com.ez08.trade.user.UserHelper;
//
//import org.json.JSONObject;
//
//import java.util.Iterator;
//import java.util.Set;
//
//public class TradeRiskLevelActivity extends BaseActivity implements View.OnClickListener {
//
//    ImageView backBtn;
//    TextView titleView;
//
//    String typeValue;
//    String scoreValue;
//    TextView type;
//    TextView score;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.trade_activity_risk_level);
//
//        titleView = findViewById(R.id.title);
//        titleView.setText("客户风险等级查询");
//        backBtn = findViewById(R.id.img_back);
//        backBtn.setOnClickListener(this);
//        type = findViewById(R.id.type);
//        score = findViewById(R.id.score);
//
//        String body = "FUN=99000120&TBL_IN=ANS_TYPE,USER_CODE;0," + UserHelper.getUser().custid + ";";
//
//        BizRequest request = new BizRequest();
//        request.setBody(body);
//        request.setCallback(new Callback() {
//            @Override
//            public void callback(Client client, Response data) {
//                dismissBusyDialog();
//                if (data.isSucceed()) {
//                    Log.e(TAG, data.getData());
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(data.getData());
//                        String content = jsonObject.getString("content");
//                        Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
//                        Set<String> pn = uri.getQueryParameterNames();
//                        for (Iterator it = pn.iterator(); it.hasNext(); ) {
//                            String key = it.next().toString();
//                            if ("TBL_OUT".equals(key)) {
//                                String out = uri.getQueryParameter(key);
//                                String[] split = out.split(";");
//                                String[] var = split[1].split(",");
//                                scoreValue = var[3];
//                                typeValue = var[5];
//                                type.setText(typeValue);
//                                score.setText(scoreValue);
//                            }
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                    }
//                }
//
//            }
//        });
//        showBusyDialog();
//        ClientHelper.get().send(request);
//    }
//
//    @Override
//    public void onClick(View v) {
//        if(v.getId() == R.id.img_back){
//            finish();
//        }
//    }
//}
