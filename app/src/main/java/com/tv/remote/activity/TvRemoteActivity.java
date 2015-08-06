package com.tv.remote.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tv.remote.R;

/**
 * Created by 凯阳 on 2015/8/6.
 */
public class TvRemoteActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
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
    }
}
