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
        Log.i("gky","TvRemoteReceiver System started to startService TvRemoteService");
        Intent startIntent = new Intent("com.tv.remote.service");
        context.startService(startIntent);
    }
}
