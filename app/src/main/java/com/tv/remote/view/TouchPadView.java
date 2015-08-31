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

    private int lastX = getWidth()/2;
    private int lastY = getHeight()/2;

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

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, lastX, lastY, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int[] location = getSpace(lastX, lastY, (int)event.getX(), (int)event.getY());
            if (location != null) {
                Log.i("gky","original location("+location[0]+","+location[1]+")");
                int dstX = (int) (location[0] * 8);//(location[0] * 1920) / getWidth();
                int dstY = (int) (location[1] * 8);//(location[1] * 1080) / getHeight();
                NetUtils.getInstance().sendVirtualMotionEvents(dstX, dstY, 0);
                lastX = (int) event.getX();
                lastY = (int) event.getY();
            }
        }
        invalidate();
        return true;
    }

    private int[] getSpace(int x, int y, int pX, int pY) {
        if (pX < 0 || pX > getWidth() || pY < 0 || pY > getHeight()) {
            return  null;
        }
        int w = Math.abs(pX - x);
        int h = Math.abs(pY - y);
        int dstX = pX - x;
        int dstY = pY - y;
        int space = (int) Math.sqrt((w*w+h*h));
        if (space > 5) {
            return new int[]{dstX, dstY};
        }
        return null;
    }
}
