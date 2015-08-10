package com.tv.remote.app;

import android.app.Application;

/**
 * Created by 凯阳 on 2015/8/6.
 */
public class TvRemoteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.setContext(getApplicationContext());
    }
}
