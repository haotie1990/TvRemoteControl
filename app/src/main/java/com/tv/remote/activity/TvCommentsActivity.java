package com.tv.remote.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tv.remote.R;
import com.tv.remote.net.NetUtils;
import com.tv.remote.utils.Utils;
import com.tv.remote.view.CommentsAdapter;
import com.tv.remote.view.SendCommentButton;

import butterknife.InjectView;

/**
 * Created by 凯阳 on 2015/8/12.
 */
public class TvCommentsActivity extends BaseActivity
            implements SendCommentButton.OnSendClickListener{

    @InjectView(R.id.contentRoot)
    LinearLayout contentRoot;
    @InjectView(R.id.rvComments)
    RecyclerView rvComments;
    @InjectView(R.id.llAddComment)
    LinearLayout llAddComment;
    @InjectView(R.id.etComment)
    EditText etComment;
    @InjectView(R.id.btnSendComment)
    SendCommentButton btnSendComment;

    private CommentsAdapter commentsAdapter;
    private int drawingStartLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        initComments();
        initSendCommentButton();

        add(this);

        /*drawingStartLocation = getIntent().getIntExtra(BaseActivity.AGR_DRAWING_START_LOCATION, 0);
        if (savedInstanceState == null) {
            contentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation();
                    return true;
                }
            });
        }*/
    }

    private void startIntroAnimation() {
        ViewCompat.setElevation(getToolbar(), 0);
        contentRoot.setScaleY(0.1f);/*设置缩放的基准点*/
        contentRoot.setPivotY(drawingStartLocation);/*设置缩放的起始点*/
        contentRoot.setTranslationY(200);

        contentRoot.animate()
                .scaleX(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewCompat.setElevation(getToolbar(), Utils.dpToPx(8));
                        //animateContent();
                    }
                }).start();
    }

    private void animateContent() {
        /*llAddComment.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(200)
                .start();*/
    }

    @Override
    public void onBackPressed() {
        ViewCompat.setElevation(getToolbar(), 0);
        contentRoot.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        TvCommentsActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                    }
                }).start();
    }


    private void initComments() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setHasFixedSize(true);

        commentsAdapter = new CommentsAdapter(this);
        rvComments.setAdapter(commentsAdapter);
        rvComments.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    private void initSendCommentButton() {
        btnSendComment.setOnSendClickListener(this);
    }

    @Override
    public void onSendClickListener(View v) {
        if (vallidateComment()) {
            commentsAdapter.addItem(etComment.getText().toString());
            //rvComments.smoothScrollBy(0, rvComments.getChildAt(0).getHeight() * commentsAdapter.getItemCount());
            //NetUtils.getInstance().sendMsg(etComment.getText().toString());
            etComment.setText(null);
            btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
        }
    }

    private boolean vallidateComment() {
        if (TextUtils.isEmpty(etComment.getText())) {
            btnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
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
