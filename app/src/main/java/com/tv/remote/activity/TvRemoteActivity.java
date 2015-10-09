package com.tv.remote.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.tv.remote.R;
import com.tv.remote.net.NetUtils;
import com.tv.remote.utils.KeyEvent;
import com.tv.remote.utils.Utils;
import com.tv.remote.view.KeyBoardDialog;
import com.tv.remote.view.SendCommentButton;
import com.tv.remote.view.TouchPadView;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;

/**
 * Created by 凯阳 on 2015/8/6.
 */
public class TvRemoteActivity extends BaseActivity
        implements SendCommentButton.OnSendClickListener, TextWatcher, View.OnKeyListener{

    @InjectView(R.id.relativeLayout_navi)
    RelativeLayout relativeLayout_navi;

    @InjectView(R.id.relativeLayout_num)
    RelativeLayout relativeLayout_num;

    @InjectView(R.id.touchPadview)
    TouchPadView touchPadview;

    @InjectView(R.id.relativeLayout_msg)
    RelativeLayout relativeLayout_msg;

    @InjectView(R.id.etComment)
    EditText etComment;

    @InjectView(R.id.btnSendComment)
    SendCommentButton btnSendComment;

    @InjectView(R.id.relativeLayout_touchpad)
    RelativeLayout relativeLayout_touchpad;

    @InjectView(R.id.btn_n_left)
    ImageButton btn_n_left;

    @InjectView(R.id.btn_n_right)
    ImageButton btn_n_right;

    private int curIndexID;
    private boolean longPressState = false;

    private Vibrator vibrator;

    private KeyBoardDialog keyBoardDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        setTitle(this.getClass().getSimpleName());
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        curIndexID = relativeLayout_navi.getId();
        keyBoardDialog = new KeyBoardDialog(this);
        initSendCommentBtn();
        add(this);
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

    @OnClick(R.id.btn_home)
    public void onClickHome() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_HOME);
    }

    @OnClick(R.id.btn_input)
    public void onClickInput() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_TV_INPUT);
    }

    @OnClick(R.id.btn_vol_plus)
    public void onClickVolPlus() {
        Log.i("gky","onClickVolPlus");
        vibrator.vibrate(100);
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_VOLUME_UP);
    }

    @OnLongClick({R.id.btn_vol_plus,R.id.btn_vol_minus,
                R.id.btn_up,R.id.btn_down,
                R.id.btn_left,R.id.btn_right})
    public boolean onLongClick(View view) {
        Log.i("gky", "onLongClick");
        vibrator.vibrate(100);
        longPressState = true;
        switch (view.getId()) {
            case R.id.btn_vol_plus:
                NetUtils.getInstance().sendLongKey(KeyEvent.KEYCODE_VOLUME_UP, true);
                break;
            case R.id.btn_vol_minus:
                NetUtils.getInstance().sendLongKey(KeyEvent.KEYCODE_VOLUME_DOWN, true);
                break;
            case R.id.btn_up:
                NetUtils.getInstance().sendLongKey(KeyEvent.KEYCODE_DPAD_UP, true);
                break;
            case R.id.btn_down:
                NetUtils.getInstance().sendLongKey(KeyEvent.KEYCODE_DPAD_DOWN, true);
                break;
            case R.id.btn_left:
                NetUtils.getInstance().sendLongKey(KeyEvent.KEYCODE_DPAD_LEFT, true);
                break;
            case R.id.btn_right:
                NetUtils.getInstance().sendLongKey(KeyEvent.KEYCODE_DPAD_RIGHT, true);
                break;
            default:
                break;
        }
        return true;
    }

    @OnTouch({R.id.btn_vol_plus,R.id.btn_vol_minus,
            R.id.btn_up,R.id.btn_down,
            R.id.btn_left,R.id.btn_right})
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (longPressState) {
                longPressState = false;
                Log.i("gky","onTouch stop longKey");
                switch (view.getId()) {
                    case R.id.btn_vol_plus:
                        NetUtils.getInstance().sendLongKey(KeyEvent.KEYCODE_VOLUME_UP, false);
                        break;
                    case R.id.btn_vol_minus:
                        NetUtils.getInstance().sendLongKey(KeyEvent.KEYCODE_VOLUME_DOWN, false);
                        break;
                    case R.id.btn_up:
                        NetUtils.getInstance().sendLongKey(KeyEvent.KEYCODE_DPAD_UP, false);
                        break;
                    case R.id.btn_down:
                        NetUtils.getInstance().sendLongKey(KeyEvent.KEYCODE_DPAD_DOWN, false);
                        break;
                    case R.id.btn_left:
                        NetUtils.getInstance().sendLongKey(KeyEvent.KEYCODE_DPAD_LEFT, false);
                        break;
                    case R.id.btn_right:
                        NetUtils.getInstance().sendLongKey(KeyEvent.KEYCODE_DPAD_RIGHT, false);
                        break;
                    default:
                        break;
                }
            }
        }
        return false;
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
        NetUtils.getInstance().sendKey(KeyEvent.KEYCODE_ESCAPE);
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

    @OnClick(R.id.btnMouseLeft)
    public void onClickMouseLeft() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendVirtualMotionEvents(0, 0, 1);
    }

    @OnClick(R.id.btnMouseRight)
    public void onClickMouseRight() {
        vibrator.vibrate(100);
        NetUtils.getInstance().sendVirtualMotionEvents(0, 0, 2);
    }

    private void runAnimationSwitchLeft() {
        Log.i("gky", "runAnimationSwitch");
        if (curIndexID == relativeLayout_navi.getId()) {
            relativeLayout_navi.animate()
                    .translationX(-Utils.getScreenWidth(this))
                    .setDuration(300)
                    .setInterpolator(new AccelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            relativeLayout_navi.setVisibility(View.GONE);
                            relativeLayout_touchpad.setVisibility(View.GONE);
                            relativeLayout_msg.setVisibility(View.GONE);
                            relativeLayout_num.setTranslationX(0);
                            relativeLayout_num.setVisibility(View.VISIBLE);
                            curIndexID = relativeLayout_num.getId();
                        }
                    })
                    .start();
        }else if (curIndexID == relativeLayout_num.getId()) {
            relativeLayout_num.animate()
                    .translationX(-Utils.getScreenWidth(this))
                    .setDuration(300)
                    .setInterpolator(new AccelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            relativeLayout_num.setVisibility(View.GONE);
                            relativeLayout_navi.setVisibility(View.GONE);
                            relativeLayout_msg.setVisibility(View.GONE);
                            relativeLayout_touchpad.setTranslationX(0);
                            relativeLayout_touchpad.setVisibility(View.VISIBLE);
                            curIndexID = relativeLayout_touchpad.getId();
                        }
                    })
                    .start();
        }else if (curIndexID == relativeLayout_touchpad.getId()) {
            relativeLayout_touchpad.animate()
                    .translationX(-Utils.getScreenWidth(this))
                    .setDuration(300)
                    .setInterpolator(new AccelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            relativeLayout_num.setVisibility(View.GONE);
                            relativeLayout_touchpad.setVisibility(View.GONE);
                            relativeLayout_navi.setVisibility(View.GONE);
                            relativeLayout_msg.setTranslationX(0);
                            relativeLayout_msg.setVisibility(View.VISIBLE);
                            /*if (getDevices().getCurDeviceInfo() != null
                                    && getDevices().getCurDeviceInfo().type != 56) {
                                etComment.setVisibility(View.GONE);
                                btnSendComment.setVisibility(View.GONE);
                                if (!keyBoardDialog.isShowing()) {
                                    keyBoardDialog.show();
                                }
                            } else {
                                if (keyBoardDialog.isShowing()) {
                                    keyBoardDialog.dismiss();
                                }
                                etComment.setVisibility(View.VISIBLE);
                                btnSendComment.setVisibility(View.VISIBLE);
                                etComment.setInputType(InputType.TYPE_CLASS_TEXT);
                            }*/
                            curIndexID = relativeLayout_msg.getId();
                        }
                    })
                    .start();
        }else if (curIndexID == relativeLayout_msg.getId()) {
            relativeLayout_msg.animate()
                    .translationX(-Utils.getScreenWidth(this))
                    .setDuration(300)
                    .setInterpolator(new AccelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            relativeLayout_msg.setVisibility(View.GONE);
                            relativeLayout_touchpad.setVisibility(View.GONE);
                            relativeLayout_num.setVisibility(View.GONE);
                            relativeLayout_navi.setTranslationX(0);
                            relativeLayout_navi.setVisibility(View.VISIBLE);
                            curIndexID = relativeLayout_navi.getId();
                        }
                    })
                    .start();
        }
    }

    private void runAnimationSwitchRight() {
            Log.i("gky", "runAnimationSwitchRight");
            if (curIndexID == relativeLayout_navi.getId()) {
                relativeLayout_navi.animate()
                        .translationX(Utils.getScreenWidth(this))
                        .setDuration(300)
                        .setInterpolator(new AccelerateInterpolator())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                relativeLayout_navi.setVisibility(View.GONE);
                                relativeLayout_touchpad.setVisibility(View.GONE);
                                relativeLayout_msg.setVisibility(View.GONE);
                                relativeLayout_num.setTranslationX(0);
                                relativeLayout_num.setVisibility(View.VISIBLE);
                                curIndexID = relativeLayout_num.getId();
                            }
                        })
                        .start();
            }else if (curIndexID == relativeLayout_num.getId()) {
                relativeLayout_num.animate()
                        .translationX(Utils.getScreenWidth(this))
                        .setDuration(300)
                        .setInterpolator(new AccelerateInterpolator())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                relativeLayout_num.setVisibility(View.GONE);
                                relativeLayout_navi.setVisibility(View.GONE);
                                relativeLayout_msg.setVisibility(View.GONE);
                                relativeLayout_touchpad.setTranslationX(0);
                                relativeLayout_touchpad.setVisibility(View.VISIBLE);
                                curIndexID = relativeLayout_touchpad.getId();
                            }
                        })
                        .start();
            }else if (curIndexID == relativeLayout_touchpad.getId()) {
                relativeLayout_touchpad.animate()
                        .translationX(Utils.getScreenWidth(this))
                        .setDuration(300)
                        .setInterpolator(new AccelerateInterpolator())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                relativeLayout_num.setVisibility(View.GONE);
                                relativeLayout_touchpad.setVisibility(View.GONE);
                                relativeLayout_navi.setVisibility(View.GONE);
                                relativeLayout_msg.setTranslationX(0);
                                relativeLayout_msg.setVisibility(View.VISIBLE);
                                /*if (getDevices().getCurDeviceInfo() != null
                                        && getDevices().getCurDeviceInfo().type != 56) {
                                    etComment.setVisibility(View.GONE);
                                    btnSendComment.setVisibility(View.GONE);
                                    if (!keyBoardDialog.isShowing()) {
                                        keyBoardDialog.show();
                                    }
                                } else {
                                    if (keyBoardDialog.isShowing()) {
                                        keyBoardDialog.dismiss();
                                    }
                                    etComment.setVisibility(View.VISIBLE);
                                    btnSendComment.setVisibility(View.VISIBLE);
                                    etComment.setInputType(InputType.TYPE_CLASS_TEXT);
                                }*/
                                curIndexID = relativeLayout_msg.getId();
                            }
                        })
                        .start();
            }else if (curIndexID == relativeLayout_msg.getId()) {
                relativeLayout_msg.animate()
                        .translationX(Utils.getScreenWidth(this))
                        .setDuration(300)
                        .setInterpolator(new AccelerateInterpolator())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                relativeLayout_num.setVisibility(View.GONE);
                                relativeLayout_touchpad.setVisibility(View.GONE);
                                relativeLayout_msg.setVisibility(View.GONE);
                                relativeLayout_navi.setTranslationX(0);
                                relativeLayout_navi.setVisibility(View.VISIBLE);
                                curIndexID = relativeLayout_navi.getId();
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
        remove(this);
    }

    @Override
    public Activity getActivityBySuper() {
        return this;
    }

    private void initSendCommentBtn() {
        etComment.addTextChangedListener(this);
        etComment.setOnKeyListener(this);
        if (btnSendComment != null) {
            btnSendComment.setOnSendClickListener(this);
        }
    }
    @Override
    public void onSendClickListener(View v) {
        if (!keyBoardDialog.isShowing()) {
            keyBoardDialog.show();
        }
        /*if (vallidateComment()) {
            etComment.setText(null);
            btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
        }*/
    }

    private boolean vallidateComment() {
        if (TextUtils.isEmpty(etComment.getText())) {
            btnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.i("gky","beforeTextChanged------->"+s.toString());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i("gky","onTextChanged------->"+s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.i("gky","afterTextChanged------->"+s.toString());
        if (getDevices().getCurDeviceInfo() != null
                && getDevices().getCurDeviceInfo().type == 56) {
            NetUtils.getInstance().sendMsg(s.toString());
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, android.view.KeyEvent event) {
        Log.i("gky","onKey::event is "+event);
        NetUtils.getInstance().sendKey(keyCode);
        return false;
    }
}
