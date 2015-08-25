package com.tv.remote.net;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tv.remote.view.DeviceInfo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 凯阳 on 2015/8/7.
 */
public class NetUtils extends Handler{

    private static NetUtils instance = null;

    private ExecutorService mPool;

    private volatile String ipClient = null;
    private List<String> ipList;

    private volatile boolean isLongKeyFlag = false;

    private DatagramSocket initClientSocket = null;
    private DatagramSocket receiveSocket = null;
    private DatagramSocket sendSocket = null;
    private ReceiveRunnale receiveRunnale = null;
    private InitGetClient initGetClient = null;

    private static final int SUSPENDED_STATE = 0x1101;

    private volatile byte[] subBuffer = null;

    private Map<Integer, byte[]> subMap;

    private Handler mHandler = null;

    private boolean isInitClient = false;

    private NetUtils() {
        mPool = Executors.newFixedThreadPool(10);
        ipList = new ArrayList<>();
    }

    public static NetUtils getInstance() {
        if (instance == null) {
            instance = new NetUtils();
        }
        return instance;
    }

    public void stopInitClient() {
        if (initClientSocket != null && !initClientSocket.isClosed()) {
            initGetClient.stop();
            initClientSocket.close();
            initClientSocket = null;
            initGetClient = null;
        }
    }

    public void release() {
        Log.i("gky","close all socket");
        ipClient = null;
        isInitClient = false;
        mHandler = null;
        if (initClientSocket != null && !initClientSocket.isClosed()) {
            initGetClient.stop();
            initClientSocket.close();
            initClientSocket = null;
            initGetClient = null;
        }
        if (receiveSocket != null && !receiveSocket.isClosed()) {
            receiveRunnale.stop();
            receiveRunnale = null;
            receiveSocket.close();
            receiveSocket = null;
        }
        if (sendSocket != null && !sendSocket.isClosed()) {
            sendSocket.close();
            sendSocket = null;
        }
    }

    public boolean isConnectToClient() {
        return ipClient != null ? true :false;
    }

    public boolean isLongKeyFlag() {
        return isLongKeyFlag;
    }

    public void init(Handler handler) {
        Log.i("gky", "init client send broadcast our ip and get tv's ip");
        mHandler = handler;
        initGetClient = new InitGetClient();
        mPool.submit(initGetClient);
        receiveRunnale = new ReceiveRunnale();
        mPool.submit(receiveRunnale);
    }

    public void startFindDevices() {
        if (initGetClient == null) {
            initGetClient = new InitGetClient();
        }
        mPool.submit(initGetClient);
    }

    public void setIpClient(String ip) {
        ipClient = ip;
    }

    public String getIpClient() {
        return ipClient;
    }

    public void sendKey(int keyCode) {

        if (ipClient == null) {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(3);
            }
            return;
        }

        byte[] buffer = getByteBuffer(
                NetConst.STTP_LOAD_TYPE_IR_KEY,
                0,
                0
        );
        buffer[8] = Integer.valueOf(keyCode).byteValue();
        buffer[9] = 0;/*是否长按键 0:否*/

        SendRunnale sendRunnale = new SendRunnale(buffer);
        mPool.submit(sendRunnale);
    }

    public void sendLongKey(int keyCode, boolean isLongKeyFlag) {

        if (ipClient == null) {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(3);
            }
            return;
        }

        this.isLongKeyFlag = isLongKeyFlag;
        byte[] buffer = getByteBuffer(
                NetConst.STTP_LOAD_TYPE_IR_KEY,
                0,
                1
        );
        buffer[8] = Integer.valueOf(keyCode).byteValue();
        buffer[9] = (byte) (isLongKeyFlag ? 1:2) ;/*长按键开始结束1:开始2:结束*/

        subBuffer = buffer;/*因为需要TV端确认,则保存发送数据*/
        Message msg = obtainMessage();
        msg.what = SUSPENDED_STATE;
        sendMessageDelayed(msg,2000);/*数据挂起，等待重传，1500毫秒后*/

        SendRunnale sendRunnale = new SendRunnale(buffer);
        mPool.submit(sendRunnale);
    }

    public void sendMsg(String msg) {

        if (ipClient == null) {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(3);
            }
            return;
        }

        int length = msg.getBytes().length;
        if (length > 1330 ) {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(4);
            }
            return;
        }
        byte[] buffer = getByteBuffer(
                NetConst.STTP_LOAD_TYPE_CMD_INPUT_TEXT,
                length,
                0
        );
        try {
            ByteArrayInputStream bip = new ByteArrayInputStream(msg.getBytes());
            int byteLength = bip.read(buffer, 8, length);
            Log.d("gky","byteLength is "+byteLength+" msgLength is "+length);
            bip.close();
        }catch (IOException e) {
            e.printStackTrace();
            if (mHandler != null) {
                mHandler.sendEmptyMessage(4);
            }
            return;
        }
        if (ipClient == null) {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(3);
            }
            return;
        }
        SendRunnale sendRunnale = new SendRunnale(buffer);
        mPool.submit(sendRunnale);
    }

    public byte[] getByteBuffer(int load_type, int sn, int receive_flag) {
        byte[] buffer = new byte[1400];

        /*初始化版本*/
        int version = 1 << 6;
        /*初始化设备ID*/
        int deviceId = 55;/*TV:56,Phone:55*/
        buffer[0] = Integer.valueOf((version | deviceId)).byteValue();
        buffer[1] = (byte) (Integer.valueOf(load_type & 0x7F).byteValue()
                            | (receive_flag != 0 ? 0x80:0x00));

        /*SN:传输序列*/
        for (int i = 2; i <= 3; i++) {
            buffer[i] = Integer.valueOf(sn & 0xFF).byteValue();
            sn = sn >> 8;
        }

        /*java.util.UUID*/
        buffer[4] = (byte) new Random().nextInt(255);/*是否需要回传确认*/

        return buffer;
    }

    private void parseReceiveBuffer(byte[] buffer, DatagramPacket datagramPacket) throws UnsupportedEncodingException {
        int version = (buffer[0] & 0xC0) >> 6;
        int deviceId = (buffer[0] & 0x3f);
        int load_type = buffer[1] & 0x7F;
        int receive_flag = (buffer[1] & 0x80) >> 7;
        int sn = (buffer[2] & 0xFF) | ((buffer[3] & 0xFF) << 8);
        Log.d("gky", "parseReceiveBuffer::version[" + version + "] deviceId[" + deviceId
                + "] load_type[" + load_type + "] SN[" + sn + "] receive_flag[" + receive_flag + "]");

        int randomFlag = buffer[4] & 0xFF;
        if (subBuffer != null) {
            Log.i("gky","randomFlag: "+randomFlag);
            Log.i("gky", "subBuffer::randomFlag: " + (subBuffer[4] & 0xFF));
            if ((subBuffer[4] & 0xFF) == randomFlag) {
                if (hasMessages(SUSPENDED_STATE)) {
                    Log.i("gky", "remove suspended long key message");
                    removeMessages(SUSPENDED_STATE);
                    subBuffer = null;
                }
                return;
            }
        }

        if (load_type == NetConst.STTP_LOAD_TYPE_REQUEST_CONNECTION && deviceId == 56) {
            String ip = datagramPacket.getAddress().getHostAddress();
            if (ipList != null && !ipList.contains(ip)) {
                int length = buffer[8] & 0xFF | (buffer[9] & 0xFF) << 8;
                String deviceName = new String(buffer,10,length,"utf-8");
                Log.i("gky","REVEIVE CONNECTION FROM "+deviceName+"["+ip+"]");
                if (!deviceName.equals("")) {
                    if (ipClient == null) {
                        ipClient = ip;
                    }
                    ipList.add(ip);
                    Message msg = mHandler.obtainMessage();
                    DeviceInfo deviceInfo = new DeviceInfo(ip, deviceName, true, ip.equals(ipClient));
                    msg.obj = deviceInfo;
                    msg.what = 1;
                    if (mHandler != null) {
                        mHandler.sendMessage(msg);
                    }
                }
            }
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SUSPENDED_STATE:
                if (subBuffer != null) {
                    Log.i("gky", "resend longKey Message to Tv");
                    SendRunnale sendRunnale = new SendRunnale(subBuffer);
                    mPool.submit(sendRunnale);

                    Message message = obtainMessage();
                    message.what = SUSPENDED_STATE;
                    sendMessageDelayed(message, 2000);
                }
                break;
        }
    }

    public class InitGetClient implements Runnable {

        private static final int BROADCAST_PORT = 5555;

        private volatile boolean isFlag = true;

        public InitGetClient() {
            super();
        }

        public void stop() {
            isFlag = false;
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
                        NetConst.STTP_LOAD_TYPE_BROADCAST,
                        0,
                        0
                );
                int length = Build.PRODUCT.getBytes().length;
                buffer[8] = Integer.valueOf(length & 0xFF).byteValue();
                buffer[9] = Integer.valueOf((length >> 8) & 0xFF).byteValue();
                ByteArrayInputStream bip = new ByteArrayInputStream(Build.PRODUCT.getBytes());
                bip.read(buffer,10,length);
                bip.close();

                DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length,
                        InetAddress.getByName(broadcastIp),BROADCAST_PORT);
                boolean flag = false;
                while (isFlag) {/*循环发送广播,直到与TV建立连接或App退出*/
                    initClientSocket.send(datagramPacket);
                    Thread.sleep(2500);
                    Log.i("gky","send broadcast our ip ");
                    if (mHandler != null && !flag) {
                        mHandler.sendEmptyMessage(0);
                        flag = true;
                    }

                }
                Log.i("gky","########ipClient is "+ipClient+"########");
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

        public void stop() {
            this.isFlag = false;
        }

        @Override
        public void run() {
            byte[] reviveBuffer = new byte[1400];
            DatagramPacket datagramPacket = new DatagramPacket(reviveBuffer, reviveBuffer.length);
            try {
                if (receiveSocket == null || receiveSocket.isClosed()) {
                    receiveSocket = new DatagramSocket(null);
                    receiveSocket.setReuseAddress(true);
                    receiveSocket.bind(new InetSocketAddress(BROADCAST_PORT));
                }
                while (isFlag) {
                    Log.d("gky", "---------->enter loop and wait receive data<----------");
                    receiveSocket.receive(datagramPacket);
                    Log.i("gky","receive from: "+datagramPacket.getAddress().getHostAddress());
                    parseReceiveBuffer(reviveBuffer, datagramPacket);
                }
            } catch (SocketException e) {
                Log.e("gky",getClass()+":"+e.toString());
                e.printStackTrace();
            }catch (IOException e) {
                Log.e("gky",getClass()+":"+e.toString());
                e.printStackTrace();
            }finally {
                if (!receiveSocket.isClosed()) {
                    Log.w("gky", "close ReceiveRunnale socket");
                    receiveSocket.close();
                }
            }
        }
    }

    public class SendRunnale implements Runnable {

        byte[] bf = null;
        private static final int BROADCAST_PORT = 5555;

        public SendRunnale(byte[] buffer) {
            bf = buffer;
        }

        @Override
        public void run() {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(bf,bf.length,
                    InetAddress.getByName(ipClient),BROADCAST_PORT);
                if (sendSocket == null || sendSocket.isClosed()) {
                    sendSocket = new DatagramSocket(null);
                    sendSocket.setReuseAddress(true);
                    sendSocket.bind(new InetSocketAddress(BROADCAST_PORT));
                }
                sendSocket.send(datagramPacket);
                Log.i("gky", "send broadcast key success!");
            } catch (SocketException e) {
                Log.e("gky",getClass()+":"+e.toString());
                e.printStackTrace();
            }catch (IOException e) {
                Log.e("gky",getClass()+":"+e.toString());
                e.printStackTrace();
            } finally {
                Log.w("gky","close SendKeyRunnale socket");
                sendSocket.close();
            }
        }
    }
}
