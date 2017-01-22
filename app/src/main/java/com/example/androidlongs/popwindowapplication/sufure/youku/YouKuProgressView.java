package com.example.androidlongs.popwindowapplication.sufure.youku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.androidlongs.popwindowapplication.utils.LogUtils;

/**
 * Created by androidlongs on 17/1/22.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class YouKuProgressView extends SurfaceView implements Runnable,SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Thread mThread;
    //运行标识
    private boolean mIsRunning ;
    private Canvas mCanvas;
    //宽度
    private float mYouKuStrokeWidth=16;
    //颜色
    private int mYouKuColor = Color.BLUE;
    private Paint mPaint;

    public YouKuProgressView(Context context) {
        super(context);
        initFunction(context,null,0);
    }

    public YouKuProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context,attrs,0);
    }

    public YouKuProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context,attrs,defStyleAttr);
    }

    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {
        mHolder = this.getHolder();
        mHolder.addCallback(this);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(mYouKuColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mYouKuStrokeWidth);
    }

    @Override
    public void run() {

        mCanvas = mHolder.lockCanvas();
        try {
            mCanvas.translate(getMeasuredWidth()/2,getMeasuredHeight()/2);
            //绘制坐标轴
            mCanvas.drawCircle(0,0,200,mPaint);
        }catch (Exception e){
            LogUtils.e("you ku exception : "+e.getMessage());
        }finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new Thread(this);
        mThread.start();
        mIsRunning = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        mIsRunning = false;

        if (mCanvas != null) {
            mHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
