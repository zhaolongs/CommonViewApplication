package com.example.androidlongs.popwindowapplication.sufure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.androidlongs.popwindowapplication.utils.LogUtils;

/**
 * Created by androidlongs on 17/1/19.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 * <p>
 * 放大镜搜索框
 */

public class FindSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;
    private boolean mIsRunning;
    private Thread mThread;
    private Canvas mCanvas;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private float mRadius=100;
    private Path mDstPath;
    private float mProgress = 0f;

    public FindSurfaceView(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public FindSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public FindSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }

    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {


        mHolder = getHolder();
        mHolder.addCallback(this);
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);

        //焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(16);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);


        mPath = new Path();

        mPath.reset();
        mPath.moveTo( (float) (mRadius*Math.cos(44)),(float) (mRadius*Math.sin(44)));
        mPath.arcTo(new RectF(-mRadius,-mRadius,mRadius,mRadius),45,359.999f,true);
        mPath.arcTo(new RectF(-mRadius,-mRadius,mRadius,mRadius),0,45,true);
        mPath.lineTo(mRadius*2,mRadius*2);
        mPath.close();


        mDstPath = new Path();

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath,false);

        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();

        mIsRunning = true;
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsRunning = false;
    }

    @Override
    public void run() {

        while (mIsRunning) {

            drawFunction();
            if (mProgress==1f){
                mProgress=0.01f;
                if (mNumber==1){
                    mNumber=2;
                }else {
                    mNumber=1;
                }

            }else {
                mProgress+=0.01f;
            }
        }
    }

    private int mNumber = 1;

    private void drawFunction() {
        SystemClock.sleep(1);
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                mCanvas.translate(mWidth/2,mHeight/2);
                //绘制
                mCanvas.drawColor(Color.WHITE);

                mPaint.setColor(Color.BLACK);
                mPaint.setStrokeWidth(6);
                mCanvas.drawLine(-mWidth/2,0,mHeight/2,0,mPaint);
                mCanvas.drawLine(0,-mHeight/2,0,mHeight/2,mPaint);

                mPaint.setColor(Color.GRAY);
                mPaint.setStrokeWidth(10);
                mCanvas.drawPath(mPath,mPaint);

                mPaint.setColor(Color.BLUE);
                mDstPath.reset();
                if (mNumber==1){
                    mPathMeasure.getSegment(0,mPathMeasure.getLength()*mProgress,mDstPath,true);
                }else {
                    mPathMeasure.getSegment(0,mPathMeasure.getLength()*mProgress,mDstPath,true);
                    mPathMeasure.nextContour();
                    mPathMeasure.getSegment(0,mPathMeasure.getLength()*mProgress,mDstPath,true);
                }


                mCanvas.drawPath(mDstPath,mPaint);

            } else {
                LogUtils.e("canvas is null");
            }
        } catch (Exception e) {

        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
}
