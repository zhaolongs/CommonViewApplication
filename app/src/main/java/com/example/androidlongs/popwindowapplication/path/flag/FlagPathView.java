package com.example.androidlongs.popwindowapplication.path.flag;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.androidlongs.popwindowapplication.R;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class FlagPathView extends View {

    private Path mPath;

    private Path mTextPath;
    private PathMeasure mPathMeasure;
    private Path mDstPath;
    private ValueAnimator mValueAnimator;
    private float mAnimatorValue;


    private long mDuration = 3000;
    private int mCircleWidth = 10;
    private float mLength;
    private PathMeasure mTextPathMeasure;
    private float mTextPathMeasureLength;


    public FlagPathView(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public FlagPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public FlagPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }


    private Paint mPaint;

    private int mViewHeight;
    private int mViewWidth;

    private int mStatue = 0;


    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {


        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.FlagPathView);


            mTextSize = (int) typedArray.getDimension(R.styleable.FlagPathView_textSize, 25);
            mTextColor = typedArray.getColor(R.styleable.FlagPathView_textColor, Color.BLACK);
            mFlagColor = typedArray.getColor(R.styleable.FlagPathView_flagColor, Color.GRAY);

            mCircleWidth = (int) typedArray.getDimension(R.styleable.FlagPathView_flagWidth, 10);

            mDuration = typedArray.getInt(R.styleable.FlagPathView_duration, 1000);
        }


        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDstPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setColor(mFlagColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(mTextSize);

        mPath = new Path();

        mTextPath = new Path();

        mPathMeasure = new PathMeasure(mPath, false);

        mTextPathMeasure = new PathMeasure(mPath, false);

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

                mStatue++;

                if (mStatue == 2) {
                    mValueAnimator.cancel();
                }

            }
        });
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewHeight = h;
        mViewWidth = w;

        int flag = mViewHeight / 5;

        mPath.moveTo(flag, flag);

        mPath.lineTo(mViewWidth * 1f / 6 * 4, flag);

        mPath.lineTo(mViewWidth * 1f / 6 * 5, mViewHeight / 2);

        mPath.lineTo(mViewWidth * 1f / 6 * 4, mViewHeight - flag);

        mPath.lineTo(flag, mViewHeight - flag);
        mPath.lineTo(flag, flag - mCircleWidth / 2);


        mTextPath.moveTo(flag, mViewHeight / 2);
        mTextPath.lineTo(mViewWidth, mViewHeight / 2);
        mTextPathMeasure.setPath(mTextPath, false);

        mPathMeasure.setPath(mPath, false);

        mLength = mPathMeasure.getLength();
        mTextPathMeasureLength = mTextPathMeasure.getLength();

    }


    private String text = "java基础 01";
    private Rect textBounds = new Rect();

    private int mTextColor = Color.BLACK;
    private int mFlagColor = Color.GRAY;

    private int mTextSize = 25;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //常规绘制

        canvas.save();
        if (mStatue == 0) {
            mDstPath.reset();
            mPathMeasure.getSegment(0, mLength * mAnimatorValue, mDstPath, true);

            canvas.drawPath(mDstPath, mPaint);
        } else if (mStatue == 1) {
            if (mAnimatorValue == 1) {
                return;
            }
            mDstPath.reset();
            mPathMeasure.getSegment(0, mLength, mDstPath, true);
            mPaint.setColor(mFlagColor);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mDstPath, mPaint);


            mPaint.setColor(mTextColor);
            mPaint.setStyle(Paint.Style.FILL);


            mPaint.getTextBounds(text, 0, text.length(), textBounds);
            int mTextHeight = textBounds.height();


            // 绘制文本
            //canvas.drawText( text,mViewHeight/5*2,mViewHeight/2+mTextHeight/4f, mPaint);
            mDstPath.reset();
            mTextPathMeasure.getSegment(0, mTextPathMeasureLength * mAnimatorValue, mDstPath, true);
            canvas.drawTextOnPath(text, mDstPath, mTextHeight / 5 * 2, mTextHeight / 4f, mPaint);
        } else {
            mDstPath.reset();
            mPathMeasure.getSegment(0, mLength, mDstPath, true);
            mPaint.setColor(mFlagColor);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mDstPath, mPaint);


            mPaint.setColor(mTextColor);
            mPaint.setStyle(Paint.Style.FILL);


            mPaint.getTextBounds(text, 0, text.length(), textBounds);
            int mTextHeight = textBounds.height();


            // 绘制文本
            //canvas.drawText( text,mViewHeight/5*2,mViewHeight/2+mTextHeight/4f, mPaint);
            mDstPath.reset();
            mTextPathMeasure.getSegment(0, mTextPathMeasureLength, mDstPath, true);
            canvas.drawTextOnPath(text, mDstPath, mTextHeight / 5 * 2, mTextHeight / 4f, mPaint);

        }

        canvas.restore();
    }


}


