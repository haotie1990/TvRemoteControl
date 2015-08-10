package com.tv.remote.net;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tv.remote.app.AppContext;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 凯阳 on 2015/8/7.
 */
public class NetUtils {

    private static NetUtils instance = null;

    private ExecutorService mPool;

    private volatile String ipClient = null;
    private DatagramSocket initClientSocket = null;
    private DatagramSocket reveiveSocket = null;
    private DatagramSocket sendKeySocket = null;
    private ReceiveRunnale receiveRunnale = null;
    private InitGetClient initGetClient = null;

    private Handler mHandler = null;

    private boolean isInitClient = false;

    private NetUtils() {
        mPool = Executors.newFixedThreadPool(6);
    }

    public static NetUtils getInstance() {
        if (instance == null) {
            instance = new NetUtils();
        }
        return instance;
    }

    public void release() {
        Log.i("gky","close all socket");
        ipClient = null;
        isInitClient = false;
        mHandler = null;
        if (initClientSocket != null && !initClientSocket.isClosed()) {
            initClientSocket.close();
            initClientSocket = null;
            initGetClient = null;
        }
        if (reveiveSocket != null && !reveiveSocket.isClosed()) {
            receiveRunnale.setFlag(false);
            receiveRunnale = null;
            reveiveSocket.close();
            reveiveSocket = null;
        }
        if (sendKeySocket != null && !sendKeySocket.isClosed()) {
            sendKeySocket.close();
            sendKeySocket = null;
        }
    }

    public boolean isConnectToClient() {
        return ipClient != null ? true :false;
    }

    public void init(Handler handler) {
        Log.i("gky", "init client send broadcast our ip and get client's ip");
        mHandler = handler;
        initGetClient = new InitGetClient();
        mPool.submit(initGetClient);
        receiveRunnale = new ReceiveRunnale();
        mPool.submit(receiveRunnale);
    }

    public void sendKey(int keyCode) {
        byte[] buffer = getByteBuffer(
                NetConst.STTP_LOAD_TYPE_IR_KEY,
                0
        );
        for (int i = 65;i < 69; i++) {
            buffer[i] = Integer.valueOf(keyCode & 0xFF).byteValue();
            keyCode = keyCode >> 8;
        }
        buffer[73] = 0;
        if (ipClient == null) {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(3);
            }
            return;
        }
        SendKeyRunnale sendKeyRunnale = new SendKeyRunnale(buffer);
        mPool.submit(sendKeyRunnale);
    }

    public void sendMsg(String msg) {

    }

    public byte[] getByteBuffer(int load_type, int sn) {
        byte[] buffer = new byte[1400];
        /*初始化版本*/
        int version = 1;
        for (int i = 0; i < 2; i++) {
            buffer[i] = Integer.valueOf(version & 0xFF).byteValue();
            version = version >> 8;
        }

        /*初始化设备ID*/
        int id = 55;
        for (int i = 2; i < 6; i++) {
            buffer[i] = Integer.valueOf(id & 0xFF).byteValue();
            id = id >> 8;
        }

        for (int i = 9; i < 13; i++) {
            buffer[i] = Integer.valueOf(load_type & 0xFF).byteValue();
            load_type = load_type >> 8;
        }

        for (int i = 16; i < 20; i++) {
            buffer[i] = Integer.valueOf(sn & 0xFF).byteValue();
            sn = sn >> 8;
        }

        return buffer;
    }

    private void parseRecieveBuffer(byte[] buffer) {
        int version = 0;
        for (int i = 0; i < 2; i++) {
            int shift = i * 8;
            version += (buffer[i] & 0xFF) << shift;
        }
        Log.i("gky","version is "+version);
        int id = 0;
        for (int i = 2; i < 6; i++) {
            int shift = (i-2) * 8;
            id += (buffer[i] & 0xFF) << shift;
        }
        Log.i("gky","id is "+id);
        int load_type = 0;
        for (int i = 9; i < 13; i++) {
            int shift = (i-9) * 8;
            load_type += (buffer[i] & 0xFF) << shift;
        }
        Log.i("gky","load_type is "+load_type);
        int sn = 0;
        for (int i = 16; i < 20; i++) {
            int shift = (i-16) * 8;
            sn += (buffer[i] & 0xFF) << shift;
        }
        Log.i("gky","sn is "+sn);
        if (load_type == NetConst.STTP_LOAD_TYPE_IR_KEY) {

        }else if (load_type == NetConst.STTP_LOAD_TYPE_REQUEST_CONNECTION) {
            Log.i("gky", "init get client's ip is " + ipClient);
            Message msg = mHandler.obtainMessage();
            msg.what = 1;
            if (mHandler != null && !isInitClient) {
                mHandler.sendMessage(msg);
                isInitClient = true;
            }
            if (initClientSocket != null) {
                initClientSocket.close();
            }
        }else if (load_type == NetConst.STTP_LOAD_TYPE_REQUEST_DISCONNECTION) {
            ipClient = null;
            isInitClient = false;
            if (mHandler != null) {
                mHandler.sendEmptyMessage(2);
            }
        }
    }

    private int getIpAddress() {
        WifiManager wifiManager = (WifiManager) AppContext.getContext().
                getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo info = wifiManager.getConnectionInfo();
        int ip = info.getIpAddress();
        return ip;

    }

    public class InitGetClient implements Runnable {

        private static final int BROADCAST_PORT = 5555;

        public InitGetClient() {
            super();
        }

        @Override
        public void run() {
            try {

                String broadcastIp = "255.255.255.255";
                if (initClientSocket == null || initClientSocket.isClosed()) {
                    initClientSocket = new DatagramSocket(null);
                    initClientSocket.setReuseAddress(true);
                    initClientSocket.bind(new InetSocketAddress(BROADCAST_PORT));
                }

                byte[] buffer = getByteBuffer(
                        NetConst.STTP_LOAD_TYPE_REQUEST_CONNECTION,
                        0
                );

                DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length,
                        InetAddress.getByName(broadcastIp),BROADCAST_PORT);
                boolean flag = false;
                while (ipClient == null) {/*循环发送广播,直到与TV建立连接或App退出*/
                    initClientSocket.send(datagramPacket);
                    Thread.sleep(500);
                    Log.i("gky","send broadcast our ip ");
                    if (mHandler != null && !flag) {
                        mHandler.sendEmptyMessage(0);
                        flag = true;
                    }
                }
                Log.i("gky","########ipClient is not null########");
            } catch (IOException e) {
                Log.e("gky",getClass()+":"+e.toString());
                e.printStackTrace();
            }catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                Log.w("gky","close InitGetClient socket");
                initClientSocket.close();
            }
        }
    }

    public class ReceiveRunnale implements Runnable {

        private static final int BROADCAST_PORT = 5556;
        private volatile boolean isFlag = true;

        public ReceiveRunnale() {
        }

        public void setFlag(boolean isFlag) {
            this.isFlag = isFlag;
        }

        @Override
        public void run() {
            byte[] reviveBuffer = new byte[1400];
            DatagramPacket datagramPacket = new DatagramPacket(reviveBuffer, reviveBuffer.length);
            try {
                if (reveiveSocket == null || reveiveSocket.isClosed()) {
                    reveiveSocket = new DatagramSocket(null);
                    reveiveSocket.setReuseAddress(true);
                    reveiveSocket.bind(new InetSocketAddress(BROADCAST_PORT));
                }
                while (isFlag) {
                    Log.i("gky", "enter loop and wait receive data");
                    reveiveSocket.receive(datagramPacket);
                    ipClient = datagramPacket.getAddress().getHostAddress();
                    Log.i("gky", "reveive data from " + ipClient);
                    parseRecieveBuffer(reviveBuffer);
                }
            } catch (SocketException e) {
                Log.e("gky",getClass()+":"+e.toString());
                e.printStackTrace();
            }catch (IOException e) {
                Log.e("gky",getClass()+":"+e.toString());
                e.printStackTrace();
            }finally {
                if (!reveiveSocket.isClosed()) {
                    Log.w("gky", "close ReceiveRunnale socket");
                    reveiveSocket.close();
                }
            }
        }
    }

    public class SendKeyRunnale implements Runnable {

        byte[] bf = null;
        private static final int BROADCAST_PORT = 5555;

        public SendKeyRunnale(byte[] buffer) {
            bf = buffer;
        }

        @Override
        public void run() {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(bf,bf.length,
                    InetAddress.getByName(ipClient),BROADCAST_PORT);
                if (sendKeySocket == null || sendKeySocket.isClosed()) {
                    sendKeySocket = new DatagramSocket(null);
                    sendKeySocket.setReuseAddress(true);
                    sendKeySocket.bind(new InetSocketAddress(BROADCAST_PORT));
                }
                sendKeySocket.send(datagramPacket);
                Log.i("gky", "send broadcast key success!");
            } catch (SocketException e) {
                Log.e("gky",getClass()+":"+e.toString());
                e.printStackTrace();
            }catch (IOException e) {
                Log.e("gky",getClass()+":"+e.toString());
                e.printStackTrace();
            } finally {
                Log.w("gky","close SendKeyRunnale socket");
                sendKeySocket.close();
            }
        }
    }
}
