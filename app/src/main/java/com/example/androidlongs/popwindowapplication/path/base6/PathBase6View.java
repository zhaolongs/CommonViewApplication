package com.example.androidlongs.popwindowapplication.path.base6;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PathBase6View extends View {

    private ValueAnimator mValueAnimator;

    public PathBase6View(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public PathBase6View(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public PathBase6View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }


    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mAnimatorValue;
    private Path mDst;
    private float mLength;


    private int mWidth, mHeight;

    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {


        mPathMeasure = new PathMeasure();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPath = new Path();
        mPath.addCircle(0, 0, 100, Path.Direction.CW);
        mPathMeasure.setPath(mPath, true);
        mLength = mPathMeasure.getLength();
        mDst = new Path();

        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
    }

    private int mDownx, mDownY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownx = (int) event.getX();
                mDownY = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                int flagX = (int) (event.getX() - mDownx);
                int flagY = (int) (event.getY() - mDownY);


                break;
        }


        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth / 2, mHeight / 2);
        mDst.reset();
        // 硬件加速的BUG
        mDst.lineTo(0, 0);
        float stop = mLength * mAnimatorValue;
        float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength));
        mPathMeasure.getSegment(start, stop, mDst, true);
        canvas.drawPath(mDst, mPaint);

    }

    public void postupdate() {
        postInvalidate();
    }

    public void update() {
        invalidate();
    }

    public void stop() {
        mValueAnimator.cancel();
    }


}
