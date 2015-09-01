package com.tv.remote.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TvRemoteReceiver extends BroadcastReceiver {
    public TvRemoteReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Log.i("gky", "TvRemoteReceiver System started to startService TvRemoteService");
        Log.i("gky", "TvRemoteReceiver Receiver Action: "+intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)
                || intent.getAction().equals("com.tv.remote.service.destroy")) {
            Intent startIntent = new Intent("com.tv.remote.service");
            context.startService(startIntent);
        }
    }
}
