package com.example.androidlongs.popwindowapplication.button;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;

/**
 * Created by androidlongs on 17/1/10.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class AnimationButtonDrawable extends Drawable {

    //默认 透明度
    private int mAlpha = 255;

    //默认画笔
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //默认颜色
    private int mColor = 0;

    //绘制的区域
    private int mWidth, mHeight;

    //波纹圆形的 圆心与半径
    private float mCirculX, mCirculY, mCirculRadus;

    private Handler mHandler = new Handler(Looper.getMainLooper());


    public AnimationButtonDrawable() {
        //设置抗锯齿
        this.mPaint.setAntiAlias(true);
        //设置防抖动
        this.mPaint.setDither(true);
    }


    //绘制功能
    @Override
    public void draw(Canvas canvas) {

        if (canvas == null) {
            return;
        }

        canvas.save();
        //绘制区域
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        //新建图层
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);

        mPaint.setColor(mBackGroundColor);
        //绘制背景 圆角矩形
        if (mBackGroundRect != null) {
            canvas.drawRoundRect(mBackGroundRect, rx, ry, mPaint);
        }


        //设置图层重叠模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        mPaint.setColor(mAnimationCircleColor);
        //绘制圆形波纹
        canvas.drawCircle(mCirculX, mCirculY, mCirculRadus, mPaint);

        //最后将画笔去除Xfermode
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        canvas.restoreToCount(layerId);

    }

    @Override
    public int getAlpha() {
        return mAlpha;
    }

    //设置透明度
    @Override
    public void setAlpha(int alpha) {
        //设置 drawable的透明度
        mAlpha = alpha;
        onColorOrAlphaChange();
    }

    //设置颜色滤镜
    @Override
    public void setColorFilter(ColorFilter colorFilter) {

        if (mPaint.getColorFilter() != colorFilter) {
            mPaint.setColorFilter(colorFilter);
        }
    }

    //确认drawable是否有透明度
    @Override
    public int getOpacity() {
        int alpha = mPaint.getAlpha();
        if (alpha == 255) {
            //不透明
            return PixelFormat.OPAQUE;
        } else if (alpha == 0) {
            //全透明
            return PixelFormat.TRANSPARENT;
        } else {
            //半透明
            return PixelFormat.TRANSLUCENT;
        }
    }

    public void setCustomColor(int color) {
        this.mColor = color;
        onColorOrAlphaChange();
    }


    public void onColorOrAlphaChange() {
        mPaint.setColor(mColor);
        //获取画笔透明度
        if (mAlpha != 255) {
            int paintAlpha = mPaint.getAlpha();
            int realAppha = (int) (paintAlpha * (mAlpha / 255f));
            mPaint.setAlpha(realAppha);
        }

    }

    public void setTouchEvent(MotionEvent event) {
        //刷新
        invalidateSelf();
        //判断点击操作
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_MASK:
                mBackGroundColor = Color.RED;
                break;
            case MotionEvent.ACTION_HOVER_EXIT:
                mBackGroundColor = mBackGroundNormalColor;
                break;
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_CANCEL:
                onTouchCancel(event.getX(), event.getY());
                break;
        }


    }

    private void onTouchCancel(float x, float y) {
        mCirculX = 0;
        mCirculY = 0;

    }

    private void onTouchUp(float x, float y) {
        if (!isRun) {
            mCirculX = x;
            mCirculY = y;
            mHandler.post(mRunnable);
        }
        mBackGroundColor = mBackGroundNormalColor;
    }

    private void onTouchMove(float x, float y) {
        if (!isRun) {
            mCirculX = x;
            mCirculY = y;

        }


    }

    private void onTouchDown(float x, float y) {
        if (!isRun) {
            mBackGroundColor = mBackGroundDownColor;
            mCirculX = x;
            mCirculY = y;
            mCirculRadus = 0;
        }
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    private boolean isRun = false;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            invalidateSelf();
            if (mCirculRadus <= mWidth) {
                isRun = true;
                mCirculRadus += 18;
                mHandler.postDelayed(mRunnable, 2);
            } else {
                isRun = false;
                mCirculX = 0;
                mCirculY = 0;
                mCirculRadus = 0;
            }

        }
    };


    private int mBackGroundNormalColor = Color.parseColor("#ffffff");
    //背景颜色
    private int mBackGroundColor = mBackGroundNormalColor;

    //背景矩形
    private RectF mBackGroundRect;
    private float rx, ry;

    public void setBackGroundColor(int backGroundColor) {
        this.mBackGroundNormalColor = backGroundColor;
        this.mBackGroundColor = this.mBackGroundNormalColor;
        mBackGroundRect = new RectF(0, 0, mWidth, mHeight);
    }

    public void setBackGroundRounds(float rx, float ry) {
        this.rx = rx;
        this.ry = ry;
    }

    //点击圆形颜色
    private int mAnimationCircleColor = Color.BLUE;

    public void setAnimationCircleColor(int animationCircleColor) {
        this.mAnimationCircleColor = animationCircleColor;
    }

    //点击 按钮 down 颜色
    private int mBackGroundDownColor = Color.GRAY;

    public void setBackGroundDownColor(int backGroundDownColor) {
        this.mBackGroundDownColor = backGroundDownColor;
    }
}
