package com.tv.remote.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by 凯阳 on 2015/6/23.
 */
public class Utils {
    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int dpToPx(int dp){
        return (int)(dp * Resources.getSystem().getDisplayMetrics().densityDpi);
    }

    public static int getScreenHeight(Context context){
        if(screenHeight == 0){
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display dp = wm.getDefaultDisplay();
            Point size = new Point();
            dp.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context context){
        if(screenWidth == 0){
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static boolean isAndroid5(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static String parseIntIp(int ip) {
        return new StringBuffer().append((ip >> 24) & 0xFF).append(".")
                .append((ip >> 16) & 0xFF).append(".")
                .append((ip >> 8) & 0xFF).append(".")
                .append(ip & 0xFF).toString();
    }

    public static int parseStrIp(String ip) {
        String[] ipAddr = ip.split(".");
        if (ipAddr.length != 4) {
            throw new IllegalArgumentException("invalid ip with:"+ip);
        }
        int ipInt = Integer.valueOf(ipAddr[3]) & 0xFF;
        ipInt |= (Integer.valueOf(ipAddr[2]) << 8) & 0xFF00;
        ipInt |= (Integer.valueOf(ipAddr[1]) << 16) & 0xFF0000;
        ipInt |= (Integer.valueOf(ipAddr[0]) << 24) & 0xFF000000;

        return ipInt;
    }
}
