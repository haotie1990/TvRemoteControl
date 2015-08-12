package com.tv.remote.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tv.remote.R;
import com.tv.remote.net.NetUtils;
import com.tv.remote.utils.DrawerLayoutInstaller;
import com.tv.remote.utils.KeyEvent;
import com.tv.remote.utils.Utils;
import com.tv.remote.view.GlobalMenu;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 凯阳 on 2015/8/6.
 */
public class TvRemoteActivity extends AppCompatActivity
            implements GlobalMenu.OnHeaderClickListener, AdapterView.OnItemClickListener{

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

    private NetHandler netHandler;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        ButterKnife.inject(this);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        netHandler = new NetHandler(this);

        initToolbar();
        initDrawer();

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

    private void initDrawer() {
        GlobalMenu menuView = new GlobalMenu(this);
        menuView.setOnHeaderClickListener(this);
        menuView.setOnItemClickListener(this);

        drawerLayout = DrawerLayoutInstaller.from(this)
                .drawerRoot(R.layout.drawer_root)
                .drawerLeftView(menuView)
                .drawerLeftWidth(Utils.dpToPx(300))
                .withNavigationIconToggler(toolbar)
                .build();
    }

    @OnClick(R.id.btn_ch_plus)
    public void onClickChPlus() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_CHANNEL_UP);
    }

    @OnClick(R.id.btn_ch_minus)
    public void onClickChMinus() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_CHANNEL_DOWN);
    }

    @OnClick(R.id.btn_power)
    public void onClickPower() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_POWER);
    }

    @OnClick(R.id.btn_input)
    public void onClickInput() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_TV_INPUT);
    }

    @OnClick(R.id.btn_vol_plus)
    public void onClickVolPlus() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_VOLUME_UP);
    }

    @OnClick(R.id.btn_vol_minus)
    public void onClickVolMinus() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_VOLUME_DOWN);
    }

    @OnClick(R.id.btn_red)
    public void onClickRed() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_PROG_RED);
    }

    @OnClick(R.id.btn_green)
    public void onClickGreen() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_PROG_GREEN);
    }

    @OnClick(R.id.btn_yellow)
    public void onClickYellow() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_PROG_YELLOW);
    }

    @OnClick(R.id.btn_blue)
    public void onClickBlue() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_PROG_BLUE);
    }

    @OnClick(R.id.btn_menu)
    public void onClickMenu() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_MENU);
    }

    @OnClick(R.id.btn_mute)
    public void onClickMute() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_VOLUME_MUTE);
    }

    @OnClick(R.id.btn_back)
    public void onClickBack() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_BACK);
    }

    @OnClick(R.id.btn_exit)
    public void onClickExit() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(-1);
    }

    @OnClick(R.id.btn_ok)
    public void onClickOk() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_DPAD_CENTER);
    }

    @OnClick(R.id.btn_left)
    public void onClickLeft() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_DPAD_LEFT);
    }

    @OnClick(R.id.btn_right)
    public void onClickRight() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_DPAD_RIGHT);
    }

    @OnClick(R.id.btn_up)
    public void onClickUp() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_DPAD_UP);
    }

    @OnClick(R.id.btn_down)
    public void onClickDown() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
    }

    @OnClick(R.id.btn_1)
    public void onClickOne() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_1);
    }

    @OnClick(R.id.btn_2)
    public void onClickTwo() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_2);
    }

    @OnClick(R.id.btn_3)
    public void onClickThree() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_3);
    }

    @OnClick(R.id.btn_4)
    public void onClickFour() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_4);
    }

    @OnClick(R.id.btn_5)
    public void onClickFive() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_5);
    }

    @OnClick(R.id.btn_6)
    public void onClickSix() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_6);
    }

    @OnClick(R.id.btn_7)
    public void onClickSeven() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_7);
    }

    @OnClick(R.id.btn_8)
    public void onClickEight() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_8);
    }

    @OnClick(R.id.btn_9)
    public void onClickNight() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_9);
    }

    @OnClick(R.id.btn_n_back)
     public void onClickNBack() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_BACK);
    }

    @OnClick(R.id.btn_0)
     public void onClickZero() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_0);
    }

    @OnClick(R.id.btn_n_ok)
    public void onClickNOk() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_DPAD_CENTER);
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
            Log.i("gky", "runAnimationSwitchRight");
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
        if (!NetUtils.getInstance().isConnectToClient()) {
            NetUtils.getInstance().init(netHandler);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetUtils.getInstance().release();
    }

    @Override
    public void onGlobalMenuHeaderClick(final View v) {
        Log.i("gky","onGlobalMenuHeaderClick");
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("gky","onItemClick position:"+position);
    }

    public static class NetHandler extends Handler {

        private WeakReference<TvRemoteActivity> mActivity;

        public NetHandler(TvRemoteActivity activity) {
            mActivity = new WeakReference<TvRemoteActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(mActivity.get(),"初始化完成，等待链接...",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(mActivity.get(),"连接到电视，可以使用！",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mActivity.get(),"连接已经断开，App不可用。",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(mActivity.get(),"未连接到TV，App不可用。",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
