package com.tv.remote.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tv.remote.R;

/**
 * Created by 凯阳 on 2015/10/13.
 */
public class AboutActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
        setTitle(getClass().getSimpleName());
        add(this);
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
