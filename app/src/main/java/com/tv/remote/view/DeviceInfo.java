package com.tv.remote.view;

/**
 * Created by 凯阳 on 2015/8/25.
 */
public class DeviceInfo {
    public int type;
    public String ip;
    public String name;
    public boolean isActivated;
    public boolean isSelected;

    public DeviceInfo(String ip, String name, int type, boolean isActivated, boolean isSelected) {
        this.ip = ip;
        this.type = type;
        this.isActivated = isActivated;
        this.isSelected = isSelected;
        this.name = name;
    }
}
