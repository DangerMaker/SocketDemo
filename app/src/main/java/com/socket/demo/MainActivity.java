package com.socket.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ez08.trade.net.AbsSendable;
import com.ez08.trade.net.Constant;
import com.ez08.trade.net.OpensslHelper;
import com.ez08.trade.net.STradeBaseHead;
import com.ez08.trade.net.STradeCommOK;
import com.ez08.trade.net.STradeGateBizFun;
import com.ez08.trade.net.STradeGateError;
import com.ez08.trade.net.STradeGateLogin;
import com.ez08.trade.net.STradeGateLoginA;
import com.ez08.trade.net.STradePacketKeyExchange;
import com.ez08.trade.net.STradePacketKeyExchangeResp;
import com.ez08.trade.net.STradeVerificationCode;
import com.ez08.trade.net.STradeVerificationCodeA;
import com.ez08.trade.net.old.OldGateLogin;
import com.ez08.trade.net.old.OldKeyExchange;
import com.ez08.trade.net.old.OldKeyExchangeResp;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.core.protocol.IReaderProtocol;
import com.xuhao.didi.core.utils.BytesUtils;
import com.xuhao.didi.core.utils.SLog;
import com.xuhao.didi.socket.client.impl.client.action.ActionDispatcher;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;
import com.xuhao.didi.socket.client.sdk.client.connection.NoneReconnect;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.ez08.trade.net.Constant.BIZ_PORT;
import static com.ez08.trade.net.Constant.IP;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    Button send;
    Button change;
    Button login;
    Button test_activity;
    ImageView imageView;
    EditText editVerify;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        SLog.setIsDebug(true);
        button = findViewById(R.id.start);
        button.setOnClickListener(this);

        change = findViewById(R.id.start1);
        change.setOnClickListener(this);

        send = findViewById(R.id.send);
        send.setOnClickListener(this);

        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        imageView = findViewById(R.id.imageView);
        editVerify = findViewById(R.id.edit_verify);
        test_activity = findViewById(R.id.test_activity);
        test_activity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                start();
                break;
            case R.id.send:
                old();
                break;
            case R.id.start1:
                exchange();
                break;
            case R.id.login:
                login();
                break;
            case R.id.test_activity:
                startActivity(new Intent(this,TestActivity.class));
                break;
            default:
                break;
        }
    }

    private void start() {
        String body = "FUN=410501&TBL_IN=fundid,market,secuid,qryflag,count,poststr;,,,1,10,;";
        manager.send(new STradeGateBizFun(body));
    }

    IConnectionManager manager;
    int ip;
    byte[] szId;
    byte[] aesKey = null;

    private void exchange() {
        ConnectionInfo info = new ConnectionInfo(IP, BIZ_PORT);
        manager = OkSocket.open(info);
        OkSocketOptions options = manager.getOption();
        //基于当前参配对象构建一个参配建造者类
        OkSocketOptions.Builder builder = new OkSocketOptions.Builder(options);
        builder.setReadByteOrder(ByteOrder.LITTLE_ENDIAN);
        builder.setWriteByteOrder(ByteOrder.LITTLE_ENDIAN);
        builder.setPulseFrequency(10 * 1000);
        builder.setReaderProtocol(new IReaderProtocol() {
            @Override
            public int getHeaderLength() {
                return Constant.BIZ_HEAD_SIZE;
            }

            @Override
            public int getBodyLength(byte[] header, ByteOrder byteOrder) {
                ByteBuffer buffer = ByteBuffer.wrap(header);
                buffer.order(byteOrder);
                STradeBaseHead head = new STradeBaseHead(buffer);
                Log.e("STradeBaseHead Response", head.toString());
                return head.dwBodySize;
            }
        });

        builder.setReconnectionManager(new NoneReconnect());
        final Handler handler = new Handler(Looper.getMainLooper());
        builder.setCallbackThreadModeToken(new OkSocketOptions.ThreadModeToken() {
            @Override
            public void handleCallbackEvent(ActionDispatcher.ActionRunnable runnable) {
                handler.post(runnable);
            }
        });

        manager.option(builder.build());
        //注册Socket行为监听器,SocketActionAdapter是回调的Simple类,其他回调方法请参阅类文档
        manager.registerReceiver(new SocketActionAdapter() {
            @Override
            public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
                manager.send(new STradePacketKeyExchange());
                manager.getPulseManager().setPulseSendable(new STradeCommOK());
            }

            @Override
            public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
                ByteBuffer buffer = ByteBuffer.wrap(data.getHeadBytes());
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                STradeBaseHead head = new STradeBaseHead(buffer);

                if(head.wPid == AbsSendable.PID_TRADE_COMM_OK){
                    manager.getPulseManager().feed();
                }

                if(head.wPid == AbsSendable.PID_TRADE_GATE_ERROR){
                    STradeGateError gateError = new STradeGateError(data.getHeadBytes(),data.getBodyBytes(),aesKey);
                }

                if (head.dwReqId == 1) {
                    STradePacketKeyExchangeResp exchange = new STradePacketKeyExchangeResp(data.getBodyBytes());
                    Log.e("STradePacketKeyExchange", exchange.toString());
                    aesKey = OpensslHelper.genMD5(exchange.gy);
                    Log.e("genMD5", BytesUtils.toHexStringForLog(aesKey));
                    ip = exchange.dwIP;
                    manager.send(new STradeVerificationCode());
                } else if (head.dwReqId == 2) {
                    STradeVerificationCodeA resp = new STradeVerificationCodeA(data.getHeadBytes(), data.getBodyBytes());
                    byte[] picReal = resp.getPic();
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(picReal, 0, picReal.length);
                    imageView.setImageBitmap(decodedByte);
                    szId = resp.szId;
                    Log.e("STradeVerificationCodeA", resp.toString());
//                    manager.getPulseManager().pulse();
                }
                else if (head.dwReqId == 10) {
                    STradeGateLoginA gateLoginA = new STradeGateLoginA(data.getHeadBytes(),data.getBodyBytes(),aesKey);
//                    ByteBuffer loginBuffer = ByteBuffer.wrap(data.getHeadBytes());
//                    buffer.order(ByteOrder.LITTLE_ENDIAN);
//                    STradeBaseHead head1 = new STradeBaseHead(loginBuffer);
//                    byte[] bytes = OpensslHelper.decrypt(aesKey,data.getBodyBytes(),head1.dwEncRawSize);
//                    Log.e("LoginResponse", BytesUtils.toHexStringForLog(data.getBodyBytes()));
                }else if(head.dwReqId == 5){

                }

            }
        });
        //调用通道进行连接
        manager.connect();
    }

    private void login() {
        String verify = editVerify.getText().toString();

        STradeGateLogin tradeGateLogin = new STradeGateLogin();
        tradeGateLogin.setVerifyCode(verify);
//        tradeGateLogin.setIP(NetUtil.intToIp(ip));
//        tradeGateLogin.setSzId(szId);
        manager.send(tradeGateLogin);

    }

    private void old() {
        ConnectionInfo info = new ConnectionInfo(IP, BIZ_PORT);
        manager = OkSocket.open(info);
        OkSocketOptions options = manager.getOption();
        //基于当前参配对象构建一个参配建造者类
        OkSocketOptions.Builder builder = new OkSocketOptions.Builder(options);
        builder.setReadByteOrder(ByteOrder.LITTLE_ENDIAN);
        builder.setWriteByteOrder(ByteOrder.LITTLE_ENDIAN);
        builder.setReaderProtocol(new IReaderProtocol() {
            @Override
            public int getHeaderLength() {
                return Constant.BIZ_HEAD_SIZE;
            }

            @Override
            public int getBodyLength(byte[] header, ByteOrder byteOrder) {
                ByteBuffer buffer = ByteBuffer.wrap(header);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                STradeBaseHead head = new STradeBaseHead(buffer);
                Log.e("STradeBaseHead", head.toString());
                return head.dwBodySize;
            }
        });

        builder.setReconnectionManager(new NoneReconnect());
        final Handler handler = new Handler(Looper.getMainLooper());
        builder.setCallbackThreadModeToken(new OkSocketOptions.ThreadModeToken() {
            @Override
            public void handleCallbackEvent(ActionDispatcher.ActionRunnable runnable) {
                handler.post(runnable);
            }
        });

        manager.option(builder.build());
        //注册Socket行为监听器,SocketActionAdapter是回调的Simple类,其他回调方法请参阅类文档
        manager.registerReceiver(new SocketActionAdapter() {
            @Override
            public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
                manager.send(new OldKeyExchange());
            }

            @Override
            public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {

                ByteBuffer buffer = ByteBuffer.wrap(data.getHeadBytes());
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                STradeBaseHead head = new STradeBaseHead(buffer);


                if(head.wPid == AbsSendable.PID_TRADE_GATE_ERROR){
                    STradeGateError gateError = new STradeGateError(data.getHeadBytes(),data.getBodyBytes(),aesKey);
                }

                if (head.dwReqId == 1) {
                    OldKeyExchangeResp exchange = new OldKeyExchangeResp(data.getBodyBytes());
                    Log.e("STradePacketKeyExchange", exchange.toString());
                    aesKey = OpensslHelper.genMD5(exchange.gy);
                    Log.e("genMD5", BytesUtils.toHexStringForLog(aesKey));

                    manager.send(new OldGateLogin());
                } else if (head.dwReqId == 101) {
                    STradeGateLoginA gateLoginA = new STradeGateLoginA(data.getHeadBytes(),data.getBodyBytes(),aesKey);
//                    byte[] bytes = OpensslHelper.decrypt(aesKey, data.getBodyBytes(), head.dwEncRawSize);
//                    Log.e("LoginDecrypt", BytesUtils.toHexStringForLog(bytes));
//                    byte[] unPress = OpensslHelper.unPress(bytes.length,head.dwRawSize,bytes);
//                    Log.e("LoginUnPress", unPress.length + "");
                }

            }
        });
        //调用通道进行连接
        manager.connect();
    }

}
