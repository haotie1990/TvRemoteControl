package com.tv.remote.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tv.remote.R;
import com.tv.remote.net.NetUtils;
import com.tv.remote.view.DividerItemDecoration;

import butterknife.InjectView;

/**
 * Created by 凯阳 on 2015/8/24.
 */
public class TvDevicesActivity extends BaseActivity{

    @InjectView(R.id.rvDevices)
    RecyclerView rvDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        setTitle(TvDevicesActivity.class.getSimpleName());
        initDevices();
        add(this);
    }

    private void initDevices() {
        if (rvDevices != null) {
            rvDevices.setAdapter(getDevices());
            rvDevices.setLayoutManager(new LinearLayoutManager(this));
            rvDevices.setHasFixedSize(true);
            rvDevices.setOverScrollMode(View.OVER_SCROLL_NEVER);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
            rvDevices.addItemDecoration(itemDecoration);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remove(this);
    }

    @Override
    public Activity getActivityBySuper() {
        return this;
    }
}
