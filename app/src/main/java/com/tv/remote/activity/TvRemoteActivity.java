package com.tv.remote.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.tv.remote.R;
import com.tv.remote.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 凯阳 on 2015/8/6.
 */
public class TvRemoteActivity extends AppCompatActivity{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.relativeLayout_navi)
    RelativeLayout relativeLayout_navi;

    @InjectView(R.id.relativeLayout_num)
    RelativeLayout relativeLayout_num;

    @InjectView(R.id.btn_n_left)
    ImageButton btn_n_left;

    @InjectView(R.id.btn_n_right)
    ImageButton btn_n_right;

    private int curIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        ButterKnife.inject(this);

        initToolbar();

        curIndex = relativeLayout_navi.getId();
    }

    private void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setSubtitle(null);
            toolbar.setNavigationIcon(R.drawable.btn_option);
        }
    }

    @OnClick(R.id.btn_n_left)
    public void switchLeft() {
        Log.i("gky","switchLeft");
        runAnimationSwitchLeft();
    }

    @OnClick(R.id.btn_n_right)
    public void switchRight() {
        Log.i("gky","switchRight");
        runAnimationSwitchRight();
    }

    private void runAnimationSwitchLeft() {
        Log.i("gky","runAnimationSwitch");
        if (curIndex == relativeLayout_navi.getId()) {
            relativeLayout_navi.animate()
                    .translationX(-Utils.getScreenWidth(this))
                    .setDuration(300)
                    .setInterpolator(new AccelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            relativeLayout_navi.setVisibility(View.GONE);
                            relativeLayout_num.setTranslationX(0);
                            relativeLayout_num.setVisibility(View.VISIBLE);
                            curIndex = relativeLayout_num.getId();
                        }
                    })
                    .start();
        }else if (curIndex == relativeLayout_num.getId()) {
            relativeLayout_num.animate()
                    .translationX(-Utils.getScreenWidth(this))
                    .setDuration(300)
                    .setInterpolator(new AccelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            relativeLayout_num.setVisibility(View.GONE);
                            relativeLayout_navi.setTranslationX(0);
                            relativeLayout_navi.setVisibility(View.VISIBLE);
                            curIndex = relativeLayout_navi.getId();
                        }
                    })
                    .start();
        }
    }

    private void runAnimationSwitchRight() {
            Log.i("gky","runAnimationSwitchRight");
            if (curIndex == relativeLayout_navi.getId()) {
                relativeLayout_navi.animate()
                        .translationX(Utils.getScreenWidth(this))
                        .setDuration(300)
                        .setInterpolator(new AccelerateInterpolator())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                relativeLayout_navi.setVisibility(View.GONE);
                                relativeLayout_num.setTranslationX(0);
                                relativeLayout_num.setVisibility(View.VISIBLE);
                                curIndex = relativeLayout_num.getId();
                            }
                        })
                        .start();
            }else if (curIndex == relativeLayout_num.getId()) {
                relativeLayout_num.animate()
                        .translationX(Utils.getScreenWidth(this))
                        .setDuration(300)
                        .setInterpolator(new AccelerateInterpolator())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                relativeLayout_num.setVisibility(View.GONE);
                                relativeLayout_navi.setTranslationX(0);
                                relativeLayout_navi.setVisibility(View.VISIBLE);
                                curIndex = relativeLayout_navi.getId();
                            }
                        })
                        .start();
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
