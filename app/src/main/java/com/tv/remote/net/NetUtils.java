package com.tv.remote.net;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tv.remote.utils.ConfigConst;
import com.tv.remote.view.DeviceInfo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    private volatile List<String> ipList;

    private DatagramSocket initClientSocket = null;
    private ReceiveRunnale receiveRunnale = null;
    private InitGetClient initGetClient = null;

    private static final int DATA_PACKET_TITLE_SIZE = 8;
    private static final int DATA_PACKET_SIZE = 1400;
    private static final int DATA_SEGMENT_START_INDEX = 8;
    private static final int DATE_SEGMENT_LENGTH = 1392;

    private static final int WHAT_RESPONSE_STATE = 0xFF;
    private static final int WHAT_CHECKOUT_DEVICE_STATUS_REMOTE = 0xA0;
    private static final int WHAT_CHECKOUT_DEVICE_STATUS_LOCAL = 0xB0;

    private static final int DELAY_CHECKOUT_TIME = 10 * 1000;

    private static final int DEVICE_TYPE_PHONE = 55;
    private static final int DEVICE_TYPE_TV = 56;

    private int UUIDCounter = 0;
    private Map<Integer, BufferInfo> dataMap;
    private Map<String, Integer> stateMap;

    private Handler mHandler = null;

    private NetUtils() {
        mPool = Executors.newCachedThreadPool();
        ipList = new ArrayList<>();
        dataMap = new HashMap<>();
        stateMap = new HashMap<>();
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
        dataMap.clear();
        ipList.clear();
        dataMap = null;
        ipList = null;

        if (hasMessages(WHAT_CHECKOUT_DEVICE_STATUS_REMOTE)) {
            removeMessages(WHAT_CHECKOUT_DEVICE_STATUS_REMOTE);
        }

        if (initClientSocket != null && !initClientSocket.isClosed()) {
            initGetClient.stop();
            initClientSocket.close();
            initClientSocket = null;
            initGetClient = null;
        }
        if (receiveRunnale != null) {
            receiveRunnale.stop();
            receiveRunnale = null;
        }
    }

    public boolean isConnectToClient() {
        return ipClient != null ? true :false;
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

        byte[] buffer = getByteBuffer(NetConst.STTP_LOAD_TYPE_IR_KEY,0,0);
        buffer[DATA_SEGMENT_START_INDEX] = Integer.valueOf(keyCode).byteValue();
        buffer[DATA_SEGMENT_START_INDEX + 1] = NetConst.FLAG_NORMAL_PRESS;
        int packetLength = DATA_PACKET_TITLE_SIZE + 2;
        SendRunnable sendRunnable = new SendRunnable(buffer, packetLength, ipClient);
        mPool.submit(sendRunnable);
    }

    public void sendLongKey(int keyCode, boolean longPressState) {

        if (ipClient == null) {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(ConfigConst.MSG_DISCONNECTION);
            }
            return;
        }

        byte[] buffer = getByteBuffer(NetConst.STTP_LOAD_TYPE_IR_KEY, 0, 1);
        buffer[DATA_SEGMENT_START_INDEX] = Integer.valueOf(keyCode).byteValue();
        buffer[DATA_SEGMENT_START_INDEX+1] = Integer.valueOf(longPressState ?
                NetConst.FLAG_LONG_PRESS_ENABLE:NetConst.FLAG_LONG_PRESS_DISENABLE).byteValue();/*长按键开始结束1:开始2:结束*/

        int UUID = buffer[4] & 0xFF;
        if (!dataMap.containsKey(UUID)) {
            int msgWhat = WHAT_RESPONSE_STATE | (UUID << 8);
            Log.d("gky", "wait 2000ms and send message:" + msgWhat + " again. (UUID:" + UUID + ")");
            sendEmptyMessageDelayed(msgWhat, 1500);

            int packetLength = DATA_PACKET_TITLE_SIZE + 2;
            SendRunnable SendRunnable = new SendRunnable(buffer, packetLength, ipClient);
            mPool.submit(SendRunnable);

            BufferInfo info = new BufferInfo(buffer, packetLength, ipClient);
            dataMap.put(UUID, info);
        }else {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(ConfigConst.MSG_EXCEPTION);
            }
        }
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
        byte[] data = getByteBuffer(NetConst.STTP_LOAD_TYPE_CMD_INPUT_TEXT,0,0);
        ByteArrayInputStream byteArrayip = new ByteArrayInputStream(msg.getBytes());
        try {
            byteArrayip.read(data, DATA_SEGMENT_START_INDEX, length);
            byteArrayip.close();
        }catch (IOException e) {
            e.printStackTrace();
            if (mHandler != null) {
                mHandler.sendEmptyMessage(ConfigConst.MSG_INVALIED_INPUT_TEXT);
            }
            return;
        }

        int packetLength = DATA_PACKET_TITLE_SIZE + length;
        SendRunnable SendRunnable = new SendRunnable(data, packetLength, ipClient);
        mPool.submit(SendRunnable);
    }

    public void sendVirtualMotionEvents(int dstX,int dstY, int type){

        if (ipClient == null) {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(ConfigConst.MSG_DISCONNECTION);
            }
            return;
        }

        byte[] data = getByteBuffer(NetConst.STTP_LOAD_TYPE_CMD_VIRTUAL_MOUSE,0,0);

        data[DATA_SEGMENT_START_INDEX] = Integer.valueOf(dstX & 0xFF).byteValue();
        data[DATA_SEGMENT_START_INDEX + 1] = Integer.valueOf((dstX >> 8) & 0xFF).byteValue();
        data[DATA_SEGMENT_START_INDEX + 2] = Integer.valueOf((dstX >> 16) & 0xFF).byteValue();
        data[DATA_SEGMENT_START_INDEX + 3] = Integer.valueOf((dstX >> 24) & 0xFF).byteValue();

        data[DATA_SEGMENT_START_INDEX + 4] = Integer.valueOf(dstY & 0xFF).byteValue();
        data[DATA_SEGMENT_START_INDEX + 5] = Integer.valueOf((dstY >> 8) & 0xFF).byteValue();
        data[DATA_SEGMENT_START_INDEX + 6] = Integer.valueOf((dstY >> 16) & 0xFF).byteValue();
        data[DATA_SEGMENT_START_INDEX + 7] = Integer.valueOf((dstY >> 24) & 0xFF).byteValue();

        data[DATA_SEGMENT_START_INDEX + 8] = Integer.valueOf(type & 0xFF).byteValue();

        int packetLength = DATA_PACKET_TITLE_SIZE + 9;
        SendRunnable SendRunnable = new SendRunnable(data, packetLength, ipClient);
        mPool.submit(SendRunnable);
    }

    public void sendFile(String filePath) {
        String fileName = filePath.substring(filePath.lastIndexOf("/"), 1);
    }

    public byte[] getByteBuffer(int load_type, int sn, int receive_flag) {
        byte[] buffer = new byte[1400];

        /*初始化版本*/
        int version = 1 << 6;

        /*初始化设备ID*/
        int deviceId = DEVICE_TYPE_PHONE;
        buffer[0] = Integer.valueOf((version | deviceId)).byteValue();
        buffer[1] = (byte) (Integer.valueOf(load_type & 0x7F).byteValue()
                            | (receive_flag != 0 ? 0x80:0x00));

        /*SN:传输序列*/
        buffer[2] = Integer.valueOf(sn & 0xFF).byteValue();
        buffer[3] = Integer.valueOf((sn >> 8) & 0xFF).byteValue();

        buffer[4] = Integer.valueOf(UUIDCounter != 255?++UUIDCounter:(UUIDCounter-=255)).byteValue();

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

        int UUID = buffer[4] & 0xFF;

        if (dataMap.size() != 0) {
            if (dataMap.containsKey(UUID) && deviceId == DEVICE_TYPE_PHONE) {

                BufferInfo info = dataMap.get(UUID);
                int type = info.buffer[1] & 0x7F;
                Log.d("gky","get suspendBuffer: "+UUID+"/"+type+" and remove it");
                if (type != NetConst.STTP_LOAD_TYPE_REQUEST_CONNECTSTATUS) {

                    int msgWhat = WHAT_RESPONSE_STATE | (UUID << 8);
                    if (hasMessages(msgWhat)) {
                        Log.i("gky", "remove suspend message: " + msgWhat);
                        removeMessages(msgWhat);
                    }
                }else {
                    String ip = datagramPacket.getAddress().getHostAddress();
                    if (stateMap.containsKey(ip)) {
                        stateMap.put(ip,0);
                        Log.i("gky","reset state counter to 0"+" IP "+ip);
                    }
                }
                dataMap.remove(UUID);
                return;/*应答的确认信息不再解析，直接返回*/
            }
        }

        if (load_type == NetConst.STTP_LOAD_TYPE_REQUEST_CONNECTION && deviceId == DEVICE_TYPE_TV) {
            String ip = datagramPacket.getAddress().getHostAddress();
            if (ipList != null && !ipList.contains(ip)) {
                int length = datagramPacket.getLength() - DATA_PACKET_TITLE_SIZE;
                String deviceName = new String(buffer, DATA_SEGMENT_START_INDEX, length,"utf-8");
                Log.i("gky", "REVEIVE CONNECTION FROM " + deviceName + "[" + ip + "]");
                if (ipClient == null) {
                    ipClient = ip;
                }
                ipList.add(ip);
                stateMap.put(ip,0);

                Message msg = mHandler.obtainMessage();
                DeviceInfo deviceInfo = new DeviceInfo(ip, deviceName, true, ip.equals(ipClient));
                msg.obj = deviceInfo;
                msg.what = ConfigConst.MSG_FIND_OUT_DEVICE;
                if (mHandler != null) {
                    mHandler.sendMessage(msg);
                }

                if (!hasMessages(WHAT_CHECKOUT_DEVICE_STATUS_REMOTE)) {
                    sendEmptyMessageDelayed(WHAT_CHECKOUT_DEVICE_STATUS_REMOTE,
                            DELAY_CHECKOUT_TIME);
                }
            }
        }
    }

    @Override
    public void handleMessage(Message msg) {
        int what = msg.what & 0xFF;
        if (what == WHAT_RESPONSE_STATE) {
            Log.i("gky","WHAT_RESPONSE_STATE");
            int randomKey = (msg.what >> 8) & 0xFF;
            if (dataMap.containsKey(randomKey)) {
                BufferInfo info = dataMap.get(randomKey);
                SendRunnable SendRunnable = new SendRunnable(info.buffer, info.length, ipClient);
                mPool.submit(SendRunnable);
                Log.i("gky", "resend message: " + randomKey);
            }
        } else if (what == WHAT_CHECKOUT_DEVICE_STATUS_REMOTE) {
            if (ipList.size() > 0) {
                Log.i("gky", "WHAT_CHECKOUT_DEVICE_STATUS_REMOTE");
                for (String ip : ipList) {
                    byte[] buffer = getByteBuffer(NetConst.STTP_LOAD_TYPE_REQUEST_CONNECTSTATUS, 0, 1);
                    SendRunnable SendRunnable = new SendRunnable(buffer, DATA_PACKET_TITLE_SIZE, ip);
                    mPool.submit(SendRunnable);

                    int UUID = buffer[4] & 0xFF;
                    BufferInfo info = new BufferInfo(buffer, DATA_PACKET_TITLE_SIZE, ip);
                    dataMap.put(UUID, info);
                    Log.i("gky", "dataMap put " + UUID);
                }

                if (hasMessages(WHAT_CHECKOUT_DEVICE_STATUS_REMOTE)) {
                    removeMessages(WHAT_CHECKOUT_DEVICE_STATUS_REMOTE);
                }
                sendEmptyMessageDelayed(WHAT_CHECKOUT_DEVICE_STATUS_REMOTE,
                        DELAY_CHECKOUT_TIME);

                if (hasMessages(WHAT_CHECKOUT_DEVICE_STATUS_LOCAL)) {
                    removeMessages(WHAT_CHECKOUT_DEVICE_STATUS_LOCAL);
                }
                sendEmptyMessageDelayed(WHAT_CHECKOUT_DEVICE_STATUS_LOCAL, 2000);
            }
        } else if (what == WHAT_CHECKOUT_DEVICE_STATUS_LOCAL) {
            Log.i("gky","WHAT_CHECKOUT_DEVICE_STATUS_LOCAL");
            checkDeviceStatus();
        }
    }

    private void checkDeviceStatus() {
        Iterator iter = dataMap.entrySet().iterator();
        List<Integer> dels = new ArrayList<>();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            int key = (int) entry.getKey();
            BufferInfo info = (BufferInfo) entry.getValue();
            int load_type = info.buffer[1] & 0x7F;
            if (load_type == NetConst.STTP_LOAD_TYPE_REQUEST_CONNECTSTATUS) {
                int counter = stateMap.get(info.dstIp);
                counter++;
                Log.i("gky", "UUID " + key+" counter "+counter+" IP "+info.dstIp);
                if (counter > 5) {
                    Log.d("gky", "checkDeviceStatus ip: " + info.dstIp + " connect time out");
                    ipList.remove(info.dstIp);
                    if (ipClient != null && ipClient.equals(info.dstIp)) {
                        ipClient = null;
                    }

                    Message msg = mHandler.obtainMessage();
                    msg.what = ConfigConst.MSG_DEVICE_DISCONNECTION | (key << 8);
                    msg.obj = info.dstIp;
                    mHandler.sendMessage(msg);
                }else {
                    stateMap.put(info.dstIp,counter);
                }

                dels.add(key);
            }
         }

        for (int key : dels) {
            dataMap.remove(key);
        }
    }

    private static class BufferInfo {
        public byte[] buffer;
        public int length;
        public String dstIp;

        public BufferInfo(byte[] buffer, int length, String dstIp) {
            this.buffer = buffer;
            this.length = length;
            this.dstIp = dstIp;
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

                byte[] buffer = getByteBuffer(NetConst.STTP_LOAD_TYPE_BROADCAST,0,0);
                int length = Build.PRODUCT.getBytes().length;
                ByteArrayInputStream bip = new ByteArrayInputStream(Build.PRODUCT.getBytes());
                bip.read(buffer, 8, length);
                bip.close();

                int packetLength = DATA_PACKET_TITLE_SIZE + length;
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

        private DatagramSocket receiveSocket;
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
                    receiveSocket = null;
                }
            }
        }
    }

    public class SendRunnable implements Runnable {

        private byte[] buffer = null;
        private int length = -1;
        private String dstIp = null;

        private DatagramSocket sendSocket;
        private static final int BROADCAST_PORT = 5555;

        public SendRunnable(byte[] buffer, int length, String dstIp) {
            this.buffer = buffer;
            this.length = length;
            this.dstIp = dstIp;
        }

        @Override
        public void run() {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer,length,
                    InetAddress.getByName(dstIp),BROADCAST_PORT);
                if (sendSocket == null) {
                    sendSocket = new DatagramSocket();
                }
                sendSocket.send(datagramPacket);
                Log.i("gky", "send message: " + (buffer[4] & 0xFF) + " to: " + dstIp);
            } catch (SocketException e) {
                Log.e("gky",getClass()+":"+e.toString());
                e.printStackTrace();
            }catch (IOException e) {
                Log.e("gky",getClass()+":"+e.toString());
                e.printStackTrace();
            } finally {
                sendSocket.close();
                sendSocket = null;
            }
        }
    }

    public class SendFileRunnale implements Runnable {

        private String fileName = null;
        private String dstIp = null;

        private DatagramSocket sendSocket;
        private DatagramSocket receiveSocket;

        public SendFileRunnale(String fileName, String dstIp) {
            this.fileName = fileName;
            this.dstIp = dstIp;
        }

        @Override
        public void run() {
            try {
                sendSocket = new DatagramSocket();
                receiveSocket = new DatagramSocket(null);
                receiveSocket.setReuseAddress(true);
                receiveSocket.bind(new InetSocketAddress(5558));

                File file = new File(fileName);
                long totalLength = file.length();
                long sendSize = 0;
                if (file.exists()) {

                    FileInputStream fileInputStream = new FileInputStream(file);
                    int sendSN = 0;
                    int receiveSN = 0;
                    while (true) {
                        byte[] data = null;
                        int length = 0;
                        if (sendSN == receiveSN) {
                            sendSN++;
                            data = getByteBuffer(NetConst.STTP_LOAD_TYPE_CMD_FILE, sendSN, 1);
                            if ((length = fileInputStream.read(data, DATA_SEGMENT_START_INDEX, DATE_SEGMENT_LENGTH)) > 0) {
                                DatagramPacket sendPacket = new DatagramPacket(data, DATA_PACKET_TITLE_SIZE + length,
                                        InetAddress.getByName(dstIp), 5557);
                                sendSocket.send(sendPacket);

                                byte[] rData = new byte[DATA_PACKET_SIZE];
                                DatagramPacket receivePacket = new DatagramPacket(rData, rData.length);
                                receiveSocket.receive(receivePacket);
                                receiveSN = (rData[2] & 0xFF) | ((rData[3] & 0xFF) << 8);
                                sendSize += length;
                                if (mHandler != null) {
                                    float percent = (float)(((double)sendSize/totalLength)*100);
                                    mHandler.sendMessage(mHandler.obtainMessage(ConfigConst.MSG_PROGRESS_PERCENT,percent));
                                }
                            }else {
                                if (mHandler != null) {
                                    mHandler.sendMessage(mHandler.obtainMessage(ConfigConst.MSG_PROGRESS_PERCENT,100));
                                }
                                DatagramPacket sendPacket = new DatagramPacket(data, DATA_PACKET_TITLE_SIZE,
                                        InetAddress.getByName(dstIp), 5557);
                                sendSocket.send(sendPacket);
                                break;
                            }
                        }else {//重传数据包
                            DatagramPacket sendPacket = new DatagramPacket(data, DATA_PACKET_TITLE_SIZE + length,
                                    InetAddress.getByName(dstIp), 5557);
                            sendSocket.send(sendPacket);

                            byte[] rData = new byte[DATA_PACKET_SIZE];
                            DatagramPacket receivePacket = new DatagramPacket(rData, rData.length);
                            receiveSocket.receive(receivePacket);
                            receiveSN = (rData[2] & 0xFF) | ((rData[3] & 0xFF) << 8);
                        }
                    }
                    fileInputStream.close();
                }
                sendSocket.close();
                receiveSocket.close();
            } catch (SocketException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
