package com.tv.remote.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tv.remote.R;

/**
 * Created by 凯阳 on 2015/8/17.
 */
public class TouchPadView extends View{

    private Paint mPaint;
    private Path mPath;
    private Bitmap mBitmap;

    private int lastX;
    private int lastY;

    public TouchPadView(Context context) {
        this(context, null);
    }

    public TouchPadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchPadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_adjust_white_24dp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = (int) event.getX();
            lastY = (int) event.getY();
            mPath.moveTo(lastX, lastY);
        }else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (getSpace(lastX, lastY, (int)event.getX(), (int)event.getY())) {
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                mPath.lineTo(lastX, lastY);
            }
        }else if (event.getAction() == MotionEvent.ACTION_UP) {
            mPath.reset();
        }
        invalidate();
        return true;
    }

    private boolean getSpace(int x, int y, int pX, int pY) {
        int w = Math.abs(pX - x);
        int h = Math.abs(pY - y);
        int space = (int) Math.sqrt((w*w+h*h));
        if (space > 2) {
            return true;
        }
        return false;
    }
}
