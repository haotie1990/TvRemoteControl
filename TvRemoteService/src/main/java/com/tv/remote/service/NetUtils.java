package com.tv.remote.service;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.KeyEvent;

import com.android.internal.view.IInputMethodManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 凯阳 on 2015/8/7.
 */
public class NetUtils {

    private static final int MAX_THREAD_NUM = 10;

    public static final int STTP_LOAD_TYPE_IR_KEY = 51;

    public static final int STTP_LOAD_TYPE_BROADCAST = 52;

    public static final int STTP_LOAD_TYPE_CMD_INPUT_TEXT = 53;

    public static final int STTP_LOAD_TYPE_CMD_REVEIVE = 54;

    public static final int STTP_LOAD_TYPE_CMD_VIRTUAL_MOUSE = 55;

    /*54~72预留添加更多的命令*/

    public static final int STTP_LOAD_TYPE_CMD_XXX = 73;

    public static final int STTP_LOAD_TYPE_REQUEST_CONNECTION = 120;

    public static final int STTP_LOAD_TYPE_REQUEST_DISCONNECTION = 121;

    public static final int STTP_LOAD_TYPE_REQUEST_CONNECTSTATUS = 122;

    public static final int NORMAL_KEY = 0;

    public static final int LONG_KEY_START = 1;

    public static final int LONG_KEY_END = 2;

    public static final int NEED_REPLY = 1;

    private static final int PACKET_TITLE_LENGTH = 8;

    private static NetUtils instance = null;

    private ExecutorService mPool;

    private IInputMethodManager mService;

    private volatile String ipClient = null;
    private Map<String, String> ipDevMap = null;

    private DatagramSocket receiveSocket = null;
    private DatagramSocket sendSocket = null;

    private ReceiveRunnale receiveRunnale = null;
    private LongKeyRunnale longKeyRunnale = null;

    private Handler mHandler = null;

    private int randomCounter = 0;/*循环计数器,用于标识每个数据包,0~254*/

    private NetUtils() {
        IBinder b = ServiceManager.getService(Context.INPUT_METHOD_SERVICE);
        mService = IInputMethodManager.Stub.asInterface(b);
        mPool = Executors.newCachedThreadPool();
        ipDevMap = new HashMap<String, String>();
    }

    public static NetUtils getInstance() {
        if (instance == null) {
            instance = new NetUtils();
        }
        return instance;
    }

    public void release() {
        Log.i("gky", "close all socket and release all resources.");
        ipClient = null;
        ipDevMap.clear();
        ipDevMap = null;
        mHandler = null;
        if (receiveSocket != null && !receiveSocket.isClosed()) {
            receiveRunnale.setFlag(false);
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
        return ipClient != null ? true : false;
    }

    public void init(Handler handler) {
        Log.i("gky", "------------------------NetUtils::init----------------------------");
        mHandler = handler;
        receiveRunnale = new ReceiveRunnale();
        mPool.submit(receiveRunnale);
    }

    public void send(byte[] bf, int length, String dstIp) {
        if (dstIp == null) {
            Log.i("gky", "dstIp is null");
            return;
        }

        SendRunnale sendRunnale = new SendRunnale(bf, PACKET_TITLE_LENGTH + length, dstIp);
        mPool.submit(sendRunnale);
    }

    public byte[] getByteBuffer(int load_type, int sn, int receive_flag) {
        byte[] buffer = new byte[1400];

        /*初始化版本*/
        int version = 1 << 6;
        /*初始化设备ID*/
        int deviceId = 56;/*TV:56,Phone:55*/
        buffer[0] = Integer.valueOf((version | deviceId)).byteValue();
        buffer[1] = (byte) (Integer.valueOf(load_type & 0x7F).byteValue()
                | (receive_flag != 0 ? 0x80 : 0x00));

        /*SN:传输序列*/
        for (int i = 2; i <= 3; i++) {
            buffer[i] = Integer.valueOf(sn & 0xFF).byteValue();/*取低八位*/
            sn = sn >> 8;
        }

        buffer[4] = (byte) (randomCounter != 255 ? ++randomCounter : (randomCounter -= 255));

        return buffer;
    }

    private void parseReceiveBuffer(byte[] buffer, DatagramPacket datagramPacket) {
        int version = (buffer[0] & 0xC0) >> 6;
        int deviceId = (buffer[0] & 0x3f);
        int load_type = buffer[1] & 0x7F;
        int receive_flag = (buffer[1] & 0x80) >> 7;
        int sn = (buffer[2] & 0xFF) | ((buffer[3] & 0xFF) << 8);
        Log.i("gky", "parseReceiveBuffer::version[" + version + "] deviceId[" + deviceId
                + "] load_type[" + load_type + "] SN[" + sn + "] receive_flag[" + receive_flag + "]");

        if (receive_flag == NEED_REPLY) {
            send(buffer, datagramPacket.getLength() - PACKET_TITLE_LENGTH,
                    datagramPacket.getAddress().getHostAddress());
        }

        if (load_type == STTP_LOAD_TYPE_IR_KEY && deviceId == 55) {
            int keyCode = buffer[8] & 0xFF;
            int longKeyState = buffer[9] & 0xFF;
            Log.i("gky", "KeyCode[" + keyCode + "] isLongKey[" + (longKeyState != 0 ? "true" : "false") + "]");

            if (longKeyState == NORMAL_KEY) {/*短按键直接注入键值*/
                long now = System.currentTimeMillis();
                KeyEvent down = new KeyEvent(now, now, KeyEvent.ACTION_DOWN, keyCode, 0);
                KeyEvent up = new KeyEvent(now, now, KeyEvent.ACTION_UP, keyCode, 0);
                injectKeyEvent(down);
                injectKeyEvent(up);
            } else if (longKeyState == LONG_KEY_START) {/*长按键开始*/
                if (longKeyRunnale != null && longKeyRunnale.isRunning()) {
                    longKeyRunnale.stop();
                    longKeyRunnale = null;
                }
                longKeyRunnale = new LongKeyRunnale(keyCode);
                mPool.submit(longKeyRunnale);
            } else if (longKeyState == LONG_KEY_END) {/*长按键结束*/
                if (longKeyRunnale != null && longKeyRunnale.isRunning()) {
                    longKeyRunnale.stop();
                    longKeyRunnale = null;
                }
            }
        } else if (load_type == STTP_LOAD_TYPE_BROADCAST && deviceId == 55) {
            try {
                String ip = datagramPacket.getAddress().getHostAddress();
                int dataLength = datagramPacket.getLength() - PACKET_TITLE_LENGTH;
                String deviceName = new String(buffer, 8, dataLength, "utf-8");
                if (ipDevMap != null && !ipDevMap.containsKey(ip)) {
                    if (!deviceName.equals("")) {
                        ipDevMap.put(ip, deviceName);
                    }
                }
                Log.i("gky", "REVEIVE BROADCAST FROM " + deviceName + "[" + ip + "]");
                /* 向手机端发送TV的IP,
                *  每次收到连接广播都会发连接请求,
                *  避免有时手机收不到连接请求的情况*/
                byte[] sendBuffer = getByteBuffer(STTP_LOAD_TYPE_REQUEST_CONNECTION, 0, 0);
                try {
                    int nameLength = Build.PRODUCT.getBytes().length;
                    ByteArrayInputStream bip = new ByteArrayInputStream(Build.PRODUCT.getBytes());
                    bip.read(sendBuffer, 8, nameLength);
                    bip.close();
                    send(sendBuffer, nameLength, ip);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (load_type == STTP_LOAD_TYPE_CMD_INPUT_TEXT) {
            try {
                int length = datagramPacket.getLength() - PACKET_TITLE_LENGTH;
                String text = new String(buffer, 8, length, "utf-8");
                Log.i("gky", "receive Message text:" + text);

                try {
                    if (mService != null) {
                        mService.sendContextToFocusView(text);
                    } else {
                        Log.e("gky", "IInputMethodManagerService is null");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (load_type == STTP_LOAD_TYPE_CMD_VIRTUAL_MOUSE && deviceId == 55) {
            int x = (buffer[8] & 0xFF) | ((buffer[9] & 0xFF) << 8)
                    | ((buffer[10] & 0xFF) << 16) | ((buffer[11] & 0xFF) << 24);
            int y = (buffer[12] & 0xFF) | ((buffer[13] & 0xFF) << 8)
                    | ((buffer[14] & 0xFF) << 16) | ((buffer[15] & 0xFF) << 24);
            int type = buffer[16] & 0xFF;
            if (type == 0) {
                MouseRunnale mouseRunnale = new MouseRunnale(new int[]{x, y});
                mPool.submit(mouseRunnale);
            } else if (type == 1) {
                sendVirtualMouseLeft();
            } else if (type == 2) {
                sendVirtualMouseRight();
            }
        }
    }

    private void injectKeyEvent(KeyEvent event) {
        InputManager.getInstance().injectInputEvent(event, InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH);
    }

    private void sendVirtualMouseLeft() {
        Log.i("gky", "---------->sendVirtualMouseLeft");
        String[] events = new String[4];

        events[0] = "sendevent /dev/input/event7 1 272 1";
        events[1] = "sendevent /dev/input/event7 0 0 0";
        events[2] = "sendevent /dev/input/event7 1 272 0";
        events[3] = "sendevent /dev/input/event7 0 0 0";

        try {
            for (String event : events) {
                Runtime.getRuntime().exec(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendVirtualMouseRight() {
        Log.i("gky", "---------->sendVirtualMouseRight");
        String[] events = new String[4];

        events[0] = "sendevent /dev/input/event7 1 273 1";
        events[1] = "sendevent /dev/input/event7 0 0 0";
        events[2] = "sendevent /dev/input/event7 1 273 0";
        events[3] = "sendevent /dev/input/event7 0 0 0";

        try {
            for (String event : events) {
                Runtime.getRuntime().exec(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ReceiveRunnale implements Runnable {

        private static final int BROADCAST_PORT = 5555;

        private volatile boolean isFlag = true;

        public ReceiveRunnale() {
        }

        public void setFlag(boolean isFlag) {
            this.isFlag = isFlag;
        }

        @Override
        public void run() {
            byte[] receiveBuffer = new byte[1400];
            DatagramPacket datagramPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            try {
                if (receiveSocket == null || receiveSocket.isClosed()) {
                    receiveSocket = new DatagramSocket(null);
                    receiveSocket.setReuseAddress(true);
                    receiveSocket.bind(new InetSocketAddress(BROADCAST_PORT));
                }
                while (isFlag) {
                    Log.i("gky", "------------------enter loop and wait receive data---------------");
                    receiveSocket.receive(datagramPacket);
                    parseReceiveBuffer(receiveBuffer, datagramPacket);
                    Log.e("gky", "reveive data from ------------->>" + datagramPacket.getAddress().getHostAddress());
                }
            } catch (SocketException e) {
                Log.e("gky", getClass() + ":" + e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("gky", getClass() + ":" + e.toString());
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                Log.e("gky", getClass() + ":" + e.toString());
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("gky", getClass() + ":" + e.toString());
                e.printStackTrace();
            } finally {
                if (!receiveSocket.isClosed()) {
                    Log.w("gky", "close ReceiveRunnale socket");
                    receiveSocket.close();
                }

            }
        }
    }

    public class SendRunnale implements Runnable {

        byte[] bf = null;
        int length;
        String ip = null;
        private static final int BROADCAST_PORT = 5556;

        public SendRunnale(byte[] buffer, int length, String dstIp) {
            bf = buffer;
            ip = dstIp;
            this.length = length;
        }

        @Override
        public void run() {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(bf, length,
                        InetAddress.getByName(ip), BROADCAST_PORT);

                if (sendSocket == null || sendSocket.isClosed()) {
                    sendSocket = new DatagramSocket(null);
                    sendSocket.setReuseAddress(true);
                    sendSocket.bind(new InetSocketAddress(BROADCAST_PORT));
                }
                sendSocket.send(datagramPacket);
                Log.i("gky", "send message: " + (bf[4] & 0xFF) + " To: " + ip);
            } catch (SocketException e) {
                Log.e("gky", getClass() + ":" + e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("gky", getClass() + ":" + e.toString());
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("gky", getClass() + ":" + e.toString());
                e.printStackTrace();
            } finally {
                Log.w("gky", "close SendRunnale socket");
                bf = null;
                sendSocket.close();
            }
        }
    }

    public class LongKeyRunnale implements Runnable {

        private volatile boolean isFlag = false;
        private int keyCode;

        public LongKeyRunnale(int keyCode) {
            this.keyCode = keyCode;
        }

        public void stop() {
            isFlag = false;
        }

        public boolean isRunning() {
            return isFlag;
        }

        @Override
        public void run() {
            isFlag = true;/*线程开始运行,设置标志位进入轮询*/
            while (isFlag) {
                try {
                    long now = System.currentTimeMillis();
                    KeyEvent down = new KeyEvent(now, now, KeyEvent.ACTION_DOWN, keyCode, 0);
                    KeyEvent up = new KeyEvent(now, now, KeyEvent.ACTION_UP, keyCode, 0);
                    InputManager.getInstance().injectInputEvent(down, InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH);
                    InputManager.getInstance().injectInputEvent(up, InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH);
                    Log.i("gky", "--------->injectKeyEvent " + keyCode);
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    isFlag = false;/*异常结束线程运行*/
                }
            }
            Log.i("gky", "---------->stop LongKeyRunnale");
        }
    }

    public class MouseRunnale implements Runnable {

        private int[] location;

        public MouseRunnale(int[] location) {
            this.location = location;
        }

        @Override
        public void run() {

            String[] events = new String[3];
            events[0] = "sendevent /dev/input/event7 2 0 " + location[0];
            events[1] = "sendevent /dev/input/event7 2 1 " + location[1];
            events[2] = "sendevent /dev/input/event7 0 0 0";
            Log.i("gky", "---------->make virtual mouse(" + location[0] + "," + location[1] + ")");
            try {
                for (String event : events) {
                    Runtime.getRuntime().exec(event);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
