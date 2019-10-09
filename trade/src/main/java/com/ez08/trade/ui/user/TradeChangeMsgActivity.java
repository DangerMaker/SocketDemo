package com.ez08.trade.ui.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.Callback;
import com.ez08.trade.net.Client;
import com.ez08.trade.tools.CommonUtils;
import com.ez08.trade.ui.BaseActivity;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

public class TradeChangeMsgActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;
    Button submit;

    EditText name;
    EditText sex;
    EditText idType;
    EditText idCard;
    EditText phone;
    EditText postCode;
    EditText email;
    EditText address;

    String customIdValue;
    String nameValue;
    String sexValue;
    String idTypeValue;
    String idCardValue;
    String phoneValue;
    String postCodeValue;
    String emailValue;
    String addressValue;

    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_change_msg);

        mContext = this;
        titleView = findViewById(R.id.title);
        titleView.setText("修改资料");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        name = findViewById(R.id.name);
        sex = findViewById(R.id.sex);
        idType = findViewById(R.id.id_t);
        idCard = findViewById(R.id.id_c);
        phone = findViewById(R.id.phone);
        postCode = findViewById(R.id.post_code);
        email = findViewById(R.id.email);
        address = findViewById(R.id.addr);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);

        String body = "FUN=410321";

        Client.getInstance().sendBiz(body, (success, data) -> {
            dismissBusyDialog();
            Log.e("sendBiz", data);
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
                            customIdValue = var[0];
                            nameValue = var[1];
                            sexValue = var[8];
                            idTypeValue = var[6];
                            idCardValue = var[7];
                            phoneValue = var[12];
                            postCodeValue = var[10];
                            emailValue = var[13];
                            addressValue = var[9];

                            name.setText(nameValue);
                            sex.setText(Integer.parseInt(sexValue) == 1 ? "男" : "女");
                            idType.setText(idTypeValue);
                            idCard.setText(idCardValue);
                            phone.setText(phoneValue);
                            postCode.setText(postCodeValue);
                            email.setText(emailValue);
                            address.setText(addressValue);
                        }
                    }
                }
            }
        });

        showBusyDialog();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        } else if (v.getId() == R.id.submit) {
            String body = "FUN=410320&TBL_IN=idtype,idno,mobileno,postid,email,addr;"
                    + idTypeValue + ","
                    + idCardValue + ","
                    + phone.getText().toString() + ","
                    + postCode.getText().toString() + ","
                    + email.getText().toString() + ","
                    + address.getText().toString()
                    + ";";

            Client.getInstance().sendBiz(body, (success, data) -> {
                dismissBusyDialog();
                Log.e("sendBiz", data);
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
                                CommonUtils.show(mContext, var[0]);
                                finish();
                            }
                        }
                    }
                }
            });
        }
    }
}
