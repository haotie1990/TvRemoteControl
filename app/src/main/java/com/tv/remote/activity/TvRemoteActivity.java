package com.tv.remote.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Vibrator;
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

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        ButterKnife.inject(this);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

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

    @OnClick(R.id.btn_ch_plus)
    public void onClickChPlus() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_ch_minus)
    public void onClickChMinus() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_power)
    public void onClickPower() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_input)
    public void onClickInput() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_vol_plus)
    public void onClickVolPlus() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_vol_minus)
    public void onClickVolMinus() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_red)
    public void onClickRed() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_green)
    public void onClickGreen() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_yellow)
    public void onClickYellow() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_blue)
    public void onClickBlue() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_menu)
    public void onClickMenu() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_mute)
    public void onClickMute() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_back)
    public void onClickBack() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_exit)
    public void onClickExit() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_ok)
    public void onClickOk() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_left)
    public void onClickLeft() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_right)
    public void onClickRight() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_up)
    public void onClickUp() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_down)
    public void onClickDown() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_1)
    public void onClickOne() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_2)
    public void onClickTwo() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_3)
    public void onClickThree() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_4)
    public void onClickFour() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_5)
    public void onClickFive() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_6)
    public void onClickSix() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_7)
    public void onClickSeven() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_8)
    public void onClickEight() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_9)
    public void onClickNight() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_n_back)
     public void onClickNBack() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_0)
     public void onClickZero() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_n_ok)
    public void onClickNOk() {
        vibrator.vibrate(100);
    }

    @OnClick(R.id.btn_n_left)
    public void switchLeft() {
        Log.i("gky","switchLeft");
        vibrator.vibrate(100);
        runAnimationSwitchLeft();
    }

    @OnClick(R.id.btn_n_right)
    public void switchRight() {
        Log.i("gky","switchRight");
        vibrator.vibrate(100);
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
