package com.tv.remote.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tv.remote.R;
import com.tv.remote.app.AppContext;
import com.tv.remote.net.NetUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 凯阳 on 2015/8/24.
 */
public class DevicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private OnItemClickListener onItemClickListener = null;

    private List<DeviceInfo> mDevices;
    private DeviceInfo curDeviceInfo;

    public DevicesAdapter() {
        mDevices = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(AppContext.getContext()).inflate(R.layout.view_item_device, parent, false);
        return new DeviceItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DeviceItemViewHolder viewHolder = (DeviceItemViewHolder) holder;
        DeviceInfo deviceInfo = mDevices.get(position);
        viewHolder.bindDeviceInfo(deviceInfo);
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    public void addItem(DeviceInfo deviceInfo) {
        if (!mDevices.contains(deviceInfo)) {
            mDevices.add(deviceInfo);
            notifyItemInserted(mDevices.size() - 1);
        }else {
            Log.e("gky","invalid device "+deviceInfo.name+"["+deviceInfo.ip+"]");
        }
    }

    public DeviceInfo removeItem(DeviceInfo deviceInfo) {
        int position = mDevices.indexOf(deviceInfo);
        if (position != -1) {
            mDevices.remove(deviceInfo);
            notifyItemRemoved(position);
            return deviceInfo;
        }
        return null;
    }

    public DeviceInfo removeItem(String ip) {
        DeviceInfo devInfo = null;
        for (DeviceInfo deviceInfo : mDevices) {
            if (deviceInfo.ip.equals(ip)) {
                devInfo = deviceInfo;
                break;
            }
        }
        if (devInfo != null) {
            return removeItem(devInfo);
        }
        return null;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class DeviceItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @InjectView(R.id.tvDeviceName)
        TextView tvDeviceName;
        @InjectView(R.id.tvDeviceIp)
        TextView tvDeviceIp;
        @InjectView(R.id.ibCheckState)
        ImageButton ibCheckState;

        private DeviceInfo mDeviceInfo;

        public DeviceItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindDeviceInfo(DeviceInfo deviceInfo) {
            mDeviceInfo = deviceInfo;
            tvDeviceName.setText(deviceInfo.name);
            tvDeviceIp.setText(deviceInfo.ip);
            int resId = deviceInfo.isSelected ? R.drawable.btn_check_button_square_on : R.drawable.btn_check_button_square_off;
            ibCheckState.setImageResource(resId);
        }

        public void setSelected(boolean isSelected) {
            mDeviceInfo.isSelected = isSelected;
        }

        @Override
        public void onClick(View v) {
            if (mDeviceInfo.isActivated && !mDeviceInfo.isSelected) {
                mDeviceInfo.isSelected = !mDeviceInfo.isSelected;
                if (mDeviceInfo.isSelected) {
                    NetUtils.getInstance().setIpClient(mDeviceInfo.ip);
                }
                for (DeviceInfo deviceInfo : mDevices) {
                    if (NetUtils.getInstance().getIpClient().equals(deviceInfo.ip)) {
                        deviceInfo.isSelected = true;
                    }else {
                        deviceInfo.isSelected = false;
                    }
                }
                notifyDataSetChanged();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(tvDeviceName.getText().toString(),
                            tvDeviceIp.getText().toString(), mDeviceInfo.isSelected);
                }
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClickListener(String device, String ip, boolean isSelected);
    }
}
