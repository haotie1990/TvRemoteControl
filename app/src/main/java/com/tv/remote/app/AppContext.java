package com.tv.remote.app;

import android.content.Context;

/**
 * Created by 凯阳 on 2015/8/7.
 */
public class AppContext {

    private static Context context = null;

    public static void setContext(Context context){
        AppContext.context = context;
    }

    public static Context getContext() {
        if (context != null) {
            return context;
        }else {
            throw new NullPointerException("unkonw null context");
        }
    }
}
