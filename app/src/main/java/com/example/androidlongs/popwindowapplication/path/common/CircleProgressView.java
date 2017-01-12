package com.example.androidlongs.popwindowapplication.path.common;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.SweepGradient;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.androidlongs.popwindowapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by androidlongs on 17/1/12.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class CircleProgressView extends View {


    private SweepGradient mSweepGradient;

    public CircleProgressView(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }


    private ValueAnimator mValueAnimator;
    //加载进度显示文字
    private String mProgressWaitText;
    //加载进度显示文字颜色
    private int mProgressWaitTextColor;
    //加载进度显示文字大小
    private int mProgressWaitTextSize;


    private Paint mPaint;
    //View的宽与高
    private int mWidth, mHeigth;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private float mAnimatorValue;
    private Path mDst;
    private float mLength;

    //进度圆框颜色
    private List<Integer> mCircleColorList = new ArrayList<>();
    private int mCircleColor;

    //进度圆框 过渡颜色组
    private int mCircleStartColor;
    private int mCircleMiddleColor;
    private int mCircleEndColor;
    private List<Integer> mCircleArr;


    private boolean isSelectCircleColor;


    //进度圆框背景颜色
    private int mCircleBackGroundColor;
    //显示背景标识
    private boolean mIsShowCircleBackGround;
    //时序圆框宽度
    private int mCircleWidth;
    //圆形半径
    private int mCircleRadius;

    //一周时间
    private int mCircleProgressDuration;

    //加载圆圈中间小点大小
    private int mCirclePointSize;
    //加载圆圈中间小点颜色
    private int mCirclePointColor1;
    private int mCirclePointColor2;
    private int mCirclePointColor3;
    private List<Integer> mCirclePointColorList = new ArrayList<>();
    //显示圆圈中间小点标识
    private boolean mIsShowCirclePoint;


    //显示 加载进度标识
    private boolean mIsShowProgress = false;
    //当前进度
    private String mCurrentProgressText = "";
    //总进度
    private int mTotalProgress = 100;
    //显示进度文字颜色
    private int mProgressTextColor;
    //显示进度文字大小
    private int mProgressTextSize;


    //是否结束加载
    private boolean mIsClose = false;

    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.CircleProgressView);
            mCircleColor = typedArray.getColor(R.styleable.CircleProgressView_circleColor, Color.BLUE);
            mCircleColorList.add(mCircleColor);

            mCircleStartColor = typedArray.getColor(R.styleable.CircleProgressView_circleStartColor, mCircleColor);
            mCircleMiddleColor = typedArray.getColor(R.styleable.CircleProgressView_circleCenterColor, mCircleColor);
            mCircleEndColor = typedArray.getColor(R.styleable.CircleProgressView_circleEndColor, mCircleColor);

            mCircleArr = new ArrayList<>();
            mCircleArr.add(mCircleStartColor);
            mCircleArr.add(mCircleMiddleColor);
            mCircleArr.add(mCircleEndColor);

            mProgressTextSize = (int) typedArray.getDimension(R.styleable.CircleProgressView_progressTextSize, 14);

            mProgressTextColor = typedArray.getColor(R.styleable.CircleProgressView_progressTextColor, Color.BLUE);

            mCircleBackGroundColor = typedArray.getColor(R.styleable.CircleProgressView_circleBackgroundColor, Color.parseColor("#f5f5f5"));

            mCircleWidth = (int) typedArray.getDimension(R.styleable.CircleProgressView_circleWidth, 10);
            mCircleRadius = (int) typedArray.getDimension(R.styleable.CircleProgressView_circleRadius, 80);

            mCircleProgressDuration = typedArray.getInteger(R.styleable.CircleProgressView_circleProgressDuration, 2000);

            mCirclePointColor1 = typedArray.getColor(R.styleable.CircleProgressView_circlePointColor, Color.GRAY);
            mCirclePointColor2 = mCirclePointColor1;
            mCirclePointColor3 = mCirclePointColor1;
            mCirclePointColorList.add(mCirclePointColor1);
            mCirclePointSize = (int) typedArray.getDimension(R.styleable.CircleProgressView_circlePointSize, 10);


            mIsShowCircleBackGround = typedArray.getBoolean(R.styleable.CircleProgressView_isShowCircleBackground, true);
            mIsShowCirclePoint = typedArray.getBoolean(R.styleable.CircleProgressView_isShowCirclePoint, true);


            mProgressWaitText = typedArray.getString(R.styleable.CircleProgressView_cicrleProgressWaitText);
            mProgressWaitTextColor = typedArray.getColor(R.styleable.CircleProgressView_cicrleProgressWaitTextColor, Color.BLUE);
            mProgressWaitTextSize = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_cicrleProgressWaitTextSize, (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            if (!TextUtils.isEmpty(mProgressWaitText)) {
                mIsShowCirclePoint = false;
            }
        }


        int[] sweepColorArr = new int[mCircleArr.size()];
        for (int i = 0; i < mCircleArr.size(); i++) {
            sweepColorArr[i] = mCircleArr.get(i);
        }
        mSweepGradient = new SweepGradient(0, 0, sweepColorArr, null);
        mPaint = new Paint();
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        //设置防抖动
        mPaint.setDither(true);

        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mCircleColorList.get(0));


        mPathMeasure = new PathMeasure();

        mPath = new Path();
        mPath.addCircle(0, 0, mCircleRadius, Path.Direction.CW);


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
                mCircleColor = mCircleColorList.get((int) (Math.random() * mCircleColorList.size()));

            }
        });
        mValueAnimator.setDuration(mCircleProgressDuration);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeigth = h;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeigth / 2);


        //绘制等待点
        mPaint.setStrokeWidth(mCirclePointSize);


        if (mIsShowCirclePoint) {
            if (mAnimatorValue < 0.2) {
                mPaint.setColor(mCirclePointColor1);
                canvas.drawPoint(0 - 20, 0, mPaint);
                mCirclePointColor2 = mCirclePointColorList.get((int) (Math.random() * mCirclePointColorList.size()));
                mCirclePointColor3 = mCirclePointColorList.get((int) (Math.random() * mCirclePointColorList.size()));
            } else if (mAnimatorValue >= 0.2 && mAnimatorValue < 0.4) {
                mPaint.setColor(mCirclePointColor1);
                canvas.drawPoint(0 - 20, 0, mPaint);
                mPaint.setColor(mCirclePointColor2);
                canvas.drawPoint(0, 0, mPaint);
            } else if (mAnimatorValue >= 0.4 && mAnimatorValue < 0.6) {
                mPaint.setColor(mCirclePointColor1);
                canvas.drawPoint(0 - 20, 0, mPaint);
                mPaint.setColor(mCirclePointColor2);
                canvas.drawPoint(0, 0, mPaint);
                mPaint.setColor(mCirclePointColor3);
                canvas.drawPoint(0 + 20, 0, mPaint);

            } else if (mAnimatorValue >= 0.6 && mAnimatorValue < 0.8) {
                mPaint.setColor(mCirclePointColor2);
                canvas.drawPoint(0, 0, mPaint);
                mPaint.setColor(mCirclePointColor3);
                canvas.drawPoint(0 + 20, 0, mPaint);
                mCirclePointColor1 = mCirclePointColorList.get((int) (Math.random() * mCirclePointColorList.size()));
            } else {
                mPaint.setColor(mCirclePointColor3);
                canvas.drawPoint(0 + 20, 0, mPaint);
            }
        } else {
            //绘制等待文字
            if (!TextUtils.isEmpty(mProgressWaitText)) {
                //样式
                mPaint.setStrokeWidth(0);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(mProgressWaitTextColor);
                mPaint.setTextAlign(Paint.Align.LEFT);
                mPaint.setTextSize(mProgressWaitTextSize);
                //测量 文字
                int width = (int) mPaint.measureText(mProgressWaitText);
                float height = (mPaint.descent() + mPaint.ascent()) / 2;
                //绘制文字
                canvas.drawText(mProgressWaitText, -width / 2, -height, mPaint);
            }
        }

        if (mIsShowCircleBackGround) {
            //绘制背景
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mCircleWidth + 2);
            mPaint.setColor(mCircleBackGroundColor);
            canvas.drawPath(mPath, mPaint);

        }


        if (mIsShowProgress) {

            //设置绘制文字样式
            mPaint.setStrokeWidth(0);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextAlign(Paint.Align.LEFT);
            mPaint.setTextSize(mProgressTextSize);
            mPaint.setColor(mProgressTextColor);

            //测量文字
            //文字的宽度
            float textWidth = mPaint.measureText(mCurrentProgressText);
            //文字的高度
            float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

            //绘制 加载进度文字
            canvas.drawText(mCurrentProgressText, -textWidth / 2, -textHeight, mPaint);
        }


        //绘制进度圆圈样式
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setColor(mCircleColor);

        mDst.reset();
        // 硬件加速的BUG
        mDst.lineTo(0, 0);

        //绘制进度结束部位
        float stop = mLength * mAnimatorValue;
        //绘制进度开始部位
        float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength));
        //当结束时 绘制完整
        if (mIsClose) {
            start = 0;
            stop = mLength;
        }
        //测量
        mPathMeasure.getSegment(start, stop, mDst, true);
        //渐变姿色设置
        mPaint.setShader(mSweepGradient);
        //绘制path
        canvas.drawPath(mDst, mPaint);
        mPaint.setShader(null);

    }


    /**
     * @param b 是否显示 加载进度文字
     */
    public void setShowProgress(boolean b) {
        this.mIsShowProgress = b;
    }

    /**
     * @param totalProgress 设置总加载进度
     */
    public void setTotalProgress(int totalProgress) {
        mTotalProgress = totalProgress;
    }

    /**
     * @param currentProgress 设置当前进度
     */
    public void setCurrentProgress(int currentProgress) {
        if (mTotalProgress == 100) {
            if (currentProgress >= 100) {
                currentProgress = 100;
            }
        } else {
            if (currentProgress >= mTotalProgress) {
                currentProgress = mTotalProgress;
            }
        }
        mCurrentProgressText = (((int) (currentProgress * 1.0f / mTotalProgress * 10000)) * 1.0f / 100) + " %";


    }

    /**
     * 添加 进度圆圈颜色
     *
     * @param color 颜色
     */
    public void addCircleColor(int color) {
        this.mCircleColorList.add(color);
    }

    //添加 中间等待点颜色
    public void addCirclePointColor(int color) {
        this.mCirclePointColorList.add(color);
    }


    public void addCircleSweepColor(int color){
        addCircleSweepColor(color,false);
    }

    /**
     * @param flag true 为清空以前的颜色
     */
    public void addCircleSweepColor(int color,boolean flag){
        if (mCircleArr == null) {
            mCircleArr = new ArrayList<>();
        }
        if (flag) {
            mCircleArr.clear();
        }
        mCircleArr.add(color);

        int[] sweepColorArr = new int[mCircleArr.size()];
        for (int i = 0; i < mCircleArr.size(); i++) {
            sweepColorArr[i] = mCircleArr.get(i);
        }
        mSweepGradient = new SweepGradient(0, 0, sweepColorArr, null);
    }

    //结束加载
    public void close() {
        //完成标识
        this.mIsClose = true;
        //完成文字
        mCurrentProgressText = "100%";
        //结束更新
        mValueAnimator.cancel();
        //最后一次刷新
        invalidate();
    }
}

