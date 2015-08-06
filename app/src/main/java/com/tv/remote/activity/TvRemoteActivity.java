package com.tv.remote.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tv.remote.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 凯阳 on 2015/8/6.
 */
public class TvRemoteActivity extends AppCompatActivity{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        ButterKnife.inject(this);

        initToolbar();
    }

    private void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setSubtitle(null);
            toolbar.setNavigationIcon(R.drawable.btn_option);
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
    }
}
