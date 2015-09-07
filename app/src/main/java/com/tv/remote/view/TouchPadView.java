package com.tv.remote.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.tv.remote.R;
import com.tv.remote.net.NetUtils;

/**
 * Created by 凯阳 on 2015/8/17.
 */
public class TouchPadView extends View{

    private Paint mPaint;
    private Bitmap mBitmap;

    //private int width = getWidth();
    //private int height = getHeight();

    private int lastX = getWidth();
    private int lastY = getHeight();

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

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);


        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_adjust_white_24dp);

    }

    /*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        width = widthSize;
        height = width * 1080 /1920;

        lastX = width/2;
        lastY = height/2;

        setMeasuredDimension(width,height);
    }
    */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, lastX, lastY, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int[] location = getSpace(lastX, lastY, (int)event.getX(),  (int)event.getY());
            if (location != null) {
                lastX = (int) event.getX();
                lastY = (int) event.getY();
            }
        }
        invalidate();
        return true;
    }

    private int[] getSpace(int x, int y, int pX, int pY) {
        if (pX < 0 || pX > getWidth()|| pY < 0 || pY > getHeight()) {
            return  null;
        }
        int w = Math.abs(pX - x);
        int h = Math.abs(pY - y);
        int dstX = pX - x;
        int dstY = pY - y;
        int space = (int) Math.sqrt((w*w+h*h));
        if (space > 50) {
            if(space > 50 && space < 100) {
                NetUtils.getInstance().sendVirtualMotionEvents(dstX, dstY, 0);
            }
            return new int[]{dstX, dstY};
        }
        return null;
    }
}
