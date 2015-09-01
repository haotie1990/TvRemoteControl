package com.tv.remote.net;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tv.remote.utils.ConfigConst;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private static final int PACKET_TITLE_LENGTH = 8;
    private static final int DATA_SEGMENT_START_INDEX = 8;
    private static final int DATE_SEGMENT_LENGTH = 1392;

    private static final int WAIT_RESPONSE_STATE = 0xFF;
    private int randomCount = 0;
    private Map<Integer, Object[]> suspendMap;

    private Handler mHandler = null;

    private NetUtils() {
        mPool = Executors.newCachedThreadPool();
        ipList = new ArrayList<>();
        suspendMap = new HashMap<>();
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
        Log.i("gky", "close all socket and release all resources.");
        ipClient = null;
        mHandler = null;
        suspendMap.clear();
        ipList.clear();
        suspendMap = null;
        ipList = null;

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
                mHandler.sendEmptyMessage(ConfigConst.MSG_DISCONNECTION);
            }
            return;
        }

        byte[] buffer = getByteBuffer(
                NetConst.STTP_LOAD_TYPE_IR_KEY,
                0,
                0
        );
        buffer[DATA_SEGMENT_START_INDEX] = Integer.valueOf(keyCode).byteValue();
        buffer[DATA_SEGMENT_START_INDEX + 1] = 0;/*是否长按键 0:否*/
        int packetLength = PACKET_TITLE_LENGTH + 2;
        SendRunnale sendRunnale = new SendRunnale(buffer, packetLength);
        mPool.submit(sendRunnale);
    }

    public void sendLongKey(int keyCode, boolean isLongKeyFlag) {

        if (ipClient == null) {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(ConfigConst.MSG_DISCONNECTION);
            }
            return;
        }

        this.isLongKeyFlag = isLongKeyFlag;
        byte[] buffer = getByteBuffer(
                NetConst.STTP_LOAD_TYPE_IR_KEY,
                0,
                1
        );
        buffer[DATA_SEGMENT_START_INDEX] = Integer.valueOf(keyCode).byteValue();
        buffer[DATA_SEGMENT_START_INDEX+1] = (byte) (isLongKeyFlag ? 1:2) ;/*长按键开始结束1:开始2:结束*/

        int key = buffer[4] & 0xFF;
        int msgWhat = WAIT_RESPONSE_STATE | (key << 8);
        Log.d("gky","wait 2000ms and send message:"+msgWhat+" again. (key:"+key+")");
        sendEmptyMessageDelayed(msgWhat, 2000);

        int packetLength = PACKET_TITLE_LENGTH + 2;
        SendRunnale sendRunnale = new SendRunnale(buffer, packetLength);
        mPool.submit(sendRunnale);

        Object[] objects = new Object[2];
        objects[0] = buffer;
        objects[1] = packetLength;
        suspendMap.put(key, objects);
    }

    public void sendMsg(String msg) {

        if (ipClient == null) {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(ConfigConst.MSG_DISCONNECTION);
            }
            return;
        }

        int length = msg.getBytes().length;
        if (length > DATE_SEGMENT_LENGTH ) {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(ConfigConst.MSG_INVALIED_INPUT_TEXT);
            }
            return;
        }
        byte[] buffer = getByteBuffer(
                NetConst.STTP_LOAD_TYPE_CMD_INPUT_TEXT,
                0,
                0
        );
        try {
            ByteArrayInputStream bip = new ByteArrayInputStream(msg.getBytes());
            bip.read(buffer, DATA_SEGMENT_START_INDEX, length);
            bip.close();
        }catch (IOException e) {
            e.printStackTrace();
            if (mHandler != null) {
                mHandler.sendEmptyMessage(4);
            }
            return;
        }

        int packetLength = PACKET_TITLE_LENGTH + length;
        SendRunnale sendRunnale = new SendRunnale(buffer, packetLength);
        mPool.submit(sendRunnale);
    }

    public void sendVirtualMotionEvents(int dstX,int dstY, int type){

        if (ipClient == null) {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(ConfigConst.MSG_DISCONNECTION);
            }
            return;
        }

        byte[] buffer = getByteBuffer(
                NetConst.STTP_LOAD_TYPE_CMD_VIRTUAL_MOUSE,
                0,
                0
        );

        buffer[8] = Integer.valueOf(dstX & 0xFF).byteValue();
        buffer[9] = Integer.valueOf((dstX >> 8) & 0xFF).byteValue();
        buffer[10] = Integer.valueOf((dstX >> 16) & 0xFF).byteValue();
        buffer[11] = Integer.valueOf((dstX >> 24) & 0xFF).byteValue();

        buffer[12] = Integer.valueOf(dstY & 0xFF).byteValue();
        buffer[13] = Integer.valueOf((dstY >> 8) & 0xFF).byteValue();
        buffer[14] = Integer.valueOf((dstY >> 16) & 0xFF).byteValue();
        buffer[15] = Integer.valueOf((dstY >> 24) & 0xFF).byteValue();

        buffer[16] = Integer.valueOf(type & 0xFF).byteValue();

        int packetLength = PACKET_TITLE_LENGTH + 12;
        Log.i("gky","make virtual mouse("+dstX+","+dstY+")");
        SendRunnale sendRunnale = new SendRunnale(buffer, packetLength);
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

        buffer[4] = (byte) (randomCount != 255?++randomCount:(randomCount-=255));

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

        int randomKey = buffer[4] & 0xFF;
        int msgWhat = WAIT_RESPONSE_STATE | (randomKey << 8);
        if (suspendMap.size() != 0) {
            Log.i("gky","randomFlag: "+randomKey);
            if (suspendMap.containsKey(randomKey)) {
                if (hasMessages(msgWhat)) {
                    Log.i("gky", "remove suspend message: "+msgWhat);
                    removeMessages(msgWhat);
                    suspendMap.remove(randomKey);
                }
                return;/*应答的确认信息不再解析，直接返回*/
            }
        }

        if (load_type == NetConst.STTP_LOAD_TYPE_REQUEST_CONNECTION && deviceId == 56) {
            String ip = datagramPacket.getAddress().getHostAddress();
            if (ipList != null && !ipList.contains(ip)) {
                int length = datagramPacket.getLength() - PACKET_TITLE_LENGTH;
                String deviceName = new String(buffer, DATA_SEGMENT_START_INDEX, length,"utf-8");
                Log.i("gky","REVEIVE CONNECTION FROM "+deviceName+"["+ip+"]");
                if (ipClient == null) {
                    ipClient = ip;
                }
                ipList.add(ip);
                Message msg = mHandler.obtainMessage();
                DeviceInfo deviceInfo = new DeviceInfo(ip, deviceName, true, ip.equals(ipClient));
                msg.obj = deviceInfo;
                msg.what = ConfigConst.MSG_FIND_OUT_DEVICE;
                if (mHandler != null) {
                    mHandler.sendMessage(msg);
                }
            }
        }
    }

    @Override
    public void handleMessage(Message msg) {
        int what = msg.what & 0xFF;
        int randomKey = (msg.what >> 8) & 0xFF;
        if (what == WAIT_RESPONSE_STATE && suspendMap.containsKey(randomKey)) {
            Object[] objects = suspendMap.get(randomKey);
            int packetLength = (int) objects[1];
            byte[] bf = (byte[]) objects[0];
            SendRunnale sendRunnale = new SendRunnale(bf, packetLength);
            mPool.submit(sendRunnale);
            Log.i("gky","resend message: "+randomKey);
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
                ByteArrayInputStream bip = new ByteArrayInputStream(Build.PRODUCT.getBytes());
                bip.read(buffer, 8, length);
                bip.close();

                int packetLength = PACKET_TITLE_LENGTH + length;
                DatagramPacket datagramPacket = new DatagramPacket(buffer,packetLength,
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
                    Log.i("gky", "receive from: " + datagramPacket.getAddress().getHostAddress());
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

        private byte[] bf = null;
        private int length = -1;
        private static final int BROADCAST_PORT = 5555;

        public SendRunnale(byte[] buffer, int length) {
            bf = buffer;
            this.length = length;
        }

        @Override
        public void run() {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(bf,length,
                    InetAddress.getByName(ipClient),BROADCAST_PORT);
                if (sendSocket == null || sendSocket.isClosed()) {
                    sendSocket = new DatagramSocket(null);
                    sendSocket.setReuseAddress(true);
                    sendSocket.bind(new InetSocketAddress(BROADCAST_PORT));
                }
                sendSocket.send(datagramPacket);
                Log.i("gky", "send message: " + (bf[4] & 0xFF) + " to: " + ipClient);
            } catch (SocketException e) {
                Log.e("gky",getClass()+":"+e.toString());
                e.printStackTrace();
            }catch (IOException e) {
                Log.e("gky",getClass()+":"+e.toString());
                e.printStackTrace();
            } finally {
                sendSocket.close();
            }
        }
    }
}
