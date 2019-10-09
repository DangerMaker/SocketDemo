package com.ez08.trade.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.core.protocol.IReaderProtocol;
import com.xuhao.didi.socket.client.impl.client.action.ActionDispatcher;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;
import com.xuhao.didi.socket.client.sdk.client.connection.NoneReconnect;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;

import static com.ez08.trade.net.Constant.BIZ_PORT;
import static com.ez08.trade.net.Constant.IP;

public class Client {

    IConnectionManager manager;
    public byte[] aesKey = null;
    private Hashtable<Integer, Callback> mRequestTable;

    private Client() {
        mRequestTable = new Hashtable<>();
    }

    private static Client instance = new Client();

    public static Client getInstance() {
        return instance;
    }

    public void connect() {
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

                if (head.wPid == AbsSendable.PID_TRADE_COMM_OK) {
                    manager.getPulseManager().feed();
                    return;
                }

                if (head.wPid == AbsSendable.PID_TRADE_KEY_EXCHANGE) {
                    STradePacketKeyExchangeResp exchange = new STradePacketKeyExchangeResp(data.getBodyBytes());
//                    Log.e("STradePacketKeyExchange", exchange.toString());
                    aesKey = OpensslHelper.genMD5(exchange.gy);
//                    Log.e("genMD5", BytesUtils.toHexStringForLog(aesKey));
                    manager.getPulseManager().pulse();
                    return;
                }

                if (head.dwReqId == 0) {
                    return;
                }

                Callback callback = mRequestTable.get(head.dwReqId);
                if (callback != null) {
                    if (head.wPid == AbsSendable.PID_TRADE_GATE_ERROR) {
                        callback.onResult(false, data);
                    } else {
                        callback.onResult(true, data);
                    }
                    mRequestTable.remove(head.dwReqId);
                }

            }
        });
        //调用通道进行连接
        manager.connect();
    }

    public void send(AbsSendable sendable, Callback callback) {
        int sn = SnFactory.getSnClient();
        sendable.setDwReqId(sn);
        mRequestTable.put(sn, callback);
        manager.send(sendable);
    }


    public void sendBiz(String request, final StringCallback callback) {
        send(new STradeGateBizFun(request), new Callback() {
            @Override
            public void onResult(boolean success, OriginalData data) {
                String result;
                if (success) {
                    STradeGateBizFunA gateBizFunA = new STradeGateBizFunA(data.getHeadBytes(), data.getBodyBytes(),
                            aesKey);
                    result = gateBizFunA.getResult();
                } else {
                    STradeGateError gateError = new STradeGateError(data.getHeadBytes(), data.getBodyBytes(),
                            aesKey);
                    result = gateError.getResult();
                }
                callback.onResult(success, result);
            }
        });
    }
}
