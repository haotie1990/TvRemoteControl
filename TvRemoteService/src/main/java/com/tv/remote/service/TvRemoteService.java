package com.tv.remote.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class TvRemoteService extends Service {

    private MyHandler mHandler;

    private static final int NOTIFICATION_ID = 1;

    public TvRemoteService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("gky", "------------>TvRemoteService onCreate<------------");

        if (mHandler == null) {
            mHandler = new MyHandler();
        }

        if (!NetUtils.getInstance().isConnectToClient()) {
            NetUtils.getInstance().init(mHandler);
        }

        Notification.Builder builder = new Notification.Builder(this);
        PendingIntent intent = PendingIntent.getActivity(this, 0,
                new Intent(this, TvRemoteActivity.class), 0);
        builder.setContentIntent(intent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker(getClass().getSimpleName());
        builder.setContentTitle(getClass().getSimpleName());
        builder.setContentText(getClass().getSimpleName());
        Notification notification = builder.build();

        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w("gky","############WARNING######### TvRemoteService will destroy.");
        stopForeground(true);
        Intent intent = new Intent("com.tv.remote.service.destroy");
        sendBroadcast(intent);
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
