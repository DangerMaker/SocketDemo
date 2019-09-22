package com.socket.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xuhao.didi.core.protocol.IReaderProtocol;
import com.xuhao.didi.core.utils.SLog;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;

import java.lang.reflect.Array;
import java.nio.ByteOrder;
import java.util.Arrays;

import data.VerificationCode;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String IP = "118.26.24.26";
    public static final int PORT = 35502;
    Button button;
    Button send;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        SLog.setIsDebug(true);
        button = findViewById(R.id.start);
        button.setOnClickListener(this);

        send = findViewById(R.id.send);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                start();
                break;
            case R.id.send:
                VerificationCode entity = new VerificationCode();
                System.out.println(Arrays.toString(entity.parse()));
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
                SLog.i("getBodyLength:" + Arrays.toString(header));
                return 500;
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
        });
        //调用通道进行连接
        manager.connect();
    }
}
