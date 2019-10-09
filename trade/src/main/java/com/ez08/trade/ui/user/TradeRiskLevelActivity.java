package com.ez08.trade.ui.user;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.Client;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.user.UserHelper;

import java.util.Iterator;
import java.util.Set;

public class TradeRiskLevelActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;

    String typeValue;
    String scoreValue;
    TextView type;
    TextView score;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_risk_level);

        titleView = findViewById(R.id.title);
        titleView.setText("客户风险等级查询");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        type = findViewById(R.id.type);
        score = findViewById(R.id.score);

        String body = "FUN=99000120&TBL_IN=ANS_TYPE,USER_CODE;0," + UserHelper.getUser().custid + ";";
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
                        scoreValue = var[3];
                        typeValue = var[5];
                        type.setText(typeValue);
                        score.setText(scoreValue);
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_back){
            finish();
        }
    }
}
