package com.socket.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.socket.demo.net.CGenerateKey;
import com.socket.demo.net.Constant;
import com.socket.demo.net.OpensslHelper;
import com.socket.demo.net.STradeBaseHead;
import com.socket.demo.net.STradePacketKeyExchange;
import com.socket.demo.net.STradePacketKeyExchangeResp;
import com.socket.demo.net.VerificationCode;
import com.socket.demo.net.VerificationCodeResp;
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
import java.util.Arrays;

import static com.socket.demo.net.Constant.BIZ_PORT;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String IP = "118.26.24.26";
    public static final int PORT = 35502;
    public static final int PORT1 = 15001;
    Button button;
    Button send;
    Button change;
    ImageView imageView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.loadLibrary("opensslLib");

        context = this;
        SLog.setIsDebug(true);
        button = findViewById(R.id.start);
        button.setOnClickListener(this);

        change = findViewById(R.id.start1);
        change.setOnClickListener(this);

        send = findViewById(R.id.send);
        send.setOnClickListener(this);

        imageView = findViewById(R.id.imageView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                start();
                break;
            case R.id.send:
                SLog.i(BytesUtils.toHexStringForLog(OpensslHelper.genPublicKey()));
                break;
            case R.id.start1:
                exchange();
                break;
            default:
                break;
        }
    }

    private void start() {
        ConnectionInfo info = new ConnectionInfo(IP,PORT);
        final IConnectionManager manager = OkSocket.open(info);
        OkSocketOptions options= manager.getOption();
        //基于当前参配对象构建一个参配建造者类
        OkSocketOptions.Builder builder = new OkSocketOptions.Builder(options);
        builder.setReaderProtocol(new IReaderProtocol() {
            @Override
            public int getHeaderLength() {
                return 6;
            }

            @Override
            public int getBodyLength(byte[] header, ByteOrder byteOrder) {
                ByteBuffer buffer = ByteBuffer.wrap(header);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                short pid = buffer.getShort();
                int length = buffer.getInt();
                return length;
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
        manager.registerReceiver(new SocketActionAdapter(){
            @Override
            public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
//                Toast.makeText(context, "连接成功", LENGTH_SHORT).show();
                manager.send(new VerificationCode());
            }

            @Override
            public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
                VerificationCodeResp resp = new VerificationCodeResp(data.getBodyBytes());
                byte[] picReal = resp.getPic();
                Bitmap decodedByte = BitmapFactory.decodeByteArray(picReal, 0, picReal.length);
                imageView.setImageBitmap(decodedByte);
            }
        });
        //调用通道进行连接
        manager.connect();
    }

    private void exchange() {
        ConnectionInfo info = new ConnectionInfo(IP,BIZ_PORT);
        final IConnectionManager manager = OkSocket.open(info);
        OkSocketOptions options= manager.getOption();
        //基于当前参配对象构建一个参配建造者类
        OkSocketOptions.Builder builder = new OkSocketOptions.Builder(options);
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
                Log.e("STradeBaseHead",head.toString());
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
        manager.registerReceiver(new SocketActionAdapter(){
            @Override
            public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
//                Toast.makeText(context, "连接成功", LENGTH_SHORT).show();
                manager.send(new STradePacketKeyExchange());
            }

            @Override
            public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
                STradePacketKeyExchangeResp exchange = new STradePacketKeyExchangeResp(data.getBodyBytes());
                Log.e("STradePacketKeyExchange",exchange.toString());

            }
        });
        //调用通道进行连接
        manager.connect();    }
}
