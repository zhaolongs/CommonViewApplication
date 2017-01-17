package com.example.androidlongs.popwindowapplication.path.base9;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by androidlongs on 17/1/17.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PathView9 extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Path mPath;
    private float mBx;
    private ValueAnimator mValueAnimator;
    private float mAnimatorValue;
    private long mCircleProgressDuration = 2000;
    private int mCentX;
    private int mCentY;
    private float mAx;
    private float mAy;
    private float mC1x;
    private float mC1y;
    private float mC2x;
    private float mC2y;
    private float mBy;
    private float mC3x;
    private float mC3y;
    private float mC4x;
    private float mC4y;
    private float mCx;
    private float mCy;
    private float mC5x;
    private float mC5y;
    private float mC6x;
    private float mC6y;
    private float mDy;
    private float mDx;
    private float mC7x;
    private float mC7y;
    private float mC8x;
    private float mC8y;

    public PathView9(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public PathView9(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public PathView9(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }

    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mPath = new Path();

        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });


        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //切换绘制颜色

            }
        });
        mValueAnimator.setDuration(mCircleProgressDuration);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;


        mCentX = (int) (-mWidth / 2 + mRadius * 2);
        mCentY = 0;

        mAx = mCentX;
        mAy = mCentY - mRadius;

        mC1x = mCentX + mRadius / 2;
        mC1y = mCentY - mRadius;

        mC2x = mCentX + mRadius;
        mC2y = mCentY - mRadius / 2;


        mBx = mCentX + mRadius;
        mBy = mCentY;

        mC3x = mCentX + mRadius;
        mC3y = mCentY + mRadius / 2;

        mC4x = mCentX + mRadius / 2;
        mC4y = mCentY + mRadius;


        mCx = mCentX;
        mCy = mCentY + mRadius;

        mC5x = mCentX - mRadius / 2;
        mC5y = mCentY + mRadius;

        mC6x = mCentX - mRadius;
        mC6y = mCentY + mRadius / 2;


        mDx = mCentX - mRadius;
        mDy = mCentY;

        mC7x = mCentX - mRadius;
        mC7y = mCentY - mRadius / 2;

        mC8x = mCentX - mRadius / 2;
        mC8y = mCentY - mRadius;


        mProgress = mBx;
    }

    private float mRadius = 40;

    private int mStatue = 1;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.save();


        if (mStatue == 1) {

            float flag = mCentX + mRadius + mAnimatorValue * mRadius * 2;

            mBx = flag;
            mC2x = flag;
            mC3x = flag;

        }


        mPath.moveTo(mAx, mAy);

        mPath.cubicTo(mC1x, mC1y, mC2x, mC2y, mBx, mBy);
        mPath.cubicTo(mC3x, mC3y, mC4x, mC4y, mCx, mCy);
        mPath.cubicTo(mC5x, mC5y, mC6x, mC6y, mDx, mDy);
        mPath.cubicTo(mC7x, mC7y, mC8x, mC8y, mAx, mAy);

        mPath.close();

        canvas.drawPath(mPath, mPaint);

        canvas.restore();
    }

    private int mDownx;
    private int mDowny;
    private float mProgress;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownx = (int) event.getX();
                mDowny = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int moveY = (int) event.getY();

                int flagX = moveX - mDownx;
                mBx += flagX;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
