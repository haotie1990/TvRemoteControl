package com.tv.remote.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tv.remote.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 凯阳 on 2015/8/24.
 */
public class DevicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;

    private OnItemClickListener onItemClickListener = null;

    private List<DeviceInfo> mDevices;
    private DeviceInfo curDeviceInfo;

    public DevicesAdapter(Context context) {
        mContext = context;
        mDevices = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_device, parent, false);
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
        mDevices.add(deviceInfo);
        notifyDataSetChanged();
    }

    public void removeItem(DeviceInfo deviceInfo) {
        mDevices.remove(deviceInfo);
        notifyDataSetChanged();
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
            if (!mDeviceInfo.isActivated) {
                mDeviceInfo.isSelected = !mDeviceInfo.isSelected;
                int resId = mDeviceInfo.isSelected ? R.drawable.btn_check_button_square_on : R.drawable.btn_check_button_square_off;
                ibCheckState.setImageResource(resId);
                if (mDeviceInfo.isSelected) {
                    curDeviceInfo = mDeviceInfo;
                }
                for (DeviceInfo deviceInfo : mDevices) {
                    if (!curDeviceInfo.ip.equals(deviceInfo.ip)) {
                        deviceInfo.isSelected = false;
                    }else {
                        deviceInfo.isSelected = true;
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

    public class DeviceInfo{
        public String ip;
        public String name;
        public boolean isActivated;
        public boolean isSelected;

        public DeviceInfo(String ip, String name, boolean isActivated, boolean isSelected) {
            this.ip = ip;
            this.isActivated = isActivated;
            this.isSelected = isSelected;
            this.name = name;
        }
    }

    public interface OnItemClickListener {
        public void onItemClickListener(String device, String ip, boolean isSelected);
    }
}
