package com.tv.remote.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class TvRemoteService extends Service {

    private MyHandler mHandler;

    public TvRemoteService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("gky","------------>TvRemoteService onCreate<------------");
        if (mHandler == null) {
            mHandler = new MyHandler();
        }

        if (!NetUtils.getInstance().isConnectToClient()) {
            NetUtils.getInstance().init(mHandler);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class MyHandler extends Handler {

        public MyHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
