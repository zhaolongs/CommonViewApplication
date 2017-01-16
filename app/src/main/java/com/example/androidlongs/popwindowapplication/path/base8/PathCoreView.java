package com.example.androidlongs.popwindowapplication.path.base8;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.androidlongs.popwindowapplication.R;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PathCoreView extends View {
    private ValueAnimator mValueAnimator;
    private long mCircleProgressDuration = 1000;
    private float mAnimatorValue;
    private Path mPath, mPath1;


    //起始时角度
    private float mFromDegrees;
    private Camera mCamera;
    float scale = 1;    // <------- 像素密度
    private Matrix mMatrix;
    private int mWidth;
    private int mHeight;
    private RectF mBorderRectF;

    public PathCoreView(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public PathCoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public PathCoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }


    private Paint mPaint;
    private int centerX, centerY;

    private int mRadius = 60;
    private int mRadiusFlag = 5;
    private int mCircleWidth = 4;
    private int mTrlateHeight = mRadius * 2 + mRadiusFlag;



    private int mNumber = 0;

    private int mCoreColor= Color.RED;

private Context mContext;
    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {


        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.PathCoreView);

            //半径
            mRadius = typedArray.getDimensionPixelOffset(R.styleable.PathCoreView_core_radius,15);
            setRadius(mRadius);

            //颜色设置
            mCoreColor = typedArray.getColor(R.styleable.PathCoreView_core_color,Color.RED);
            //展开时间 设置
            mCircleProgressDuration = typedArray.getInteger(R.styleable.PathCoreView_explor_duration,1000);
            if (mCircleProgressDuration>4000){
                mCircleProgressDuration =4000;
            }

            //自定义属性 旋转速度设置
            int modelType = typedArray.getInt(R.styleable.PathCoreView_typeSpeed, 1);
            switch (modelType) {
                case 1:
                    setRoteSpeed(ROTE_SPEED.DEFAULE_SPEED);
                    break;
                case 2:
                    setRoteSpeed(ROTE_SPEED.LOW_SPEED);
                    break;
                case 3:
                    setRoteSpeed(ROTE_SPEED.MIDDIL_SPEED);
                    break;
                case 4:
                    setRoteSpeed(ROTE_SPEED.FAST_SPEED);
                    break;
                case 5:
                    setRoteSpeed(ROTE_SPEED.VERY_FAST_SPEED);
                    break;
            }
        }

        mContext = context;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(mCoreColor);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(60);


        mPath = new Path();
        mPath1 = new Path();

        mCamera = new Camera();
        mMatrix = new Matrix();


        mBorderRectF = new RectF(-mWidth / 2 + mCircleWidth, -mRadius - mRadius, mWidth / 2 - mCircleWidth, mRadius + mRadius + mRadius * 2 / 3);

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

                mNumber++;

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
        centerX = 0;
        centerY = 0;

        mPath.moveTo(0, -mRadius / 2);
        //1
        mPath.cubicTo(mRadius / 2, -mRadius, mRadius, -mRadius / 2, mRadius, 0);
        //2
        mPath.cubicTo(mRadius, mRadius / 2, mRadius / 2, mRadius, 0, mRadius + mRadiusFlag);
        //3
        mPath.cubicTo(-mRadius / 2, mRadius, -mRadius, mRadius / 2, -mRadius, 0);
        //4
        mPath.cubicTo(-mRadius, -mRadius / 2, -mRadius / 2, -mRadius, 0, -mRadius / 2);

        mPath.close();


        mPath1.moveTo(0, -mRadius / 2 - mTrlateHeight);
        //1
        mPath1.cubicTo(mRadius / 2, -mRadius - mTrlateHeight, mRadius, -mRadius / 2 - mTrlateHeight, mRadius, 0 - mTrlateHeight);
        //2
        mPath1.cubicTo(mRadius, mRadius / 2 - mTrlateHeight, mRadius / 2, mRadius - mTrlateHeight, 0, mRadius + mRadiusFlag - mTrlateHeight);
        //3
        mPath1.cubicTo(-mRadius / 2, mRadius - mTrlateHeight, -mRadius, mRadius / 2 - mTrlateHeight, -mRadius, 0 - mTrlateHeight);
        //4
        mPath1.cubicTo(-mRadius, -mRadius / 2 - mTrlateHeight, -mRadius / 2, -mRadius - mTrlateHeight, 0, -mRadius / 2 - mTrlateHeight);

        mPath1.close();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         *
         * 依据specMode的值，（MeasureSpec有3种模式分别是UNSPECIFIED, EXACTLY和AT_MOST）
         * 如果是AT_MOST，specSize 代表的是最大可获得的空间；
         * 如果是EXACTLY，specSize 代表的是精确的尺寸；
         * 如果是UNSPECIFIED，对于控件尺寸来说，没有任何参义。
         */

        /**
         * View默认的测量规则是android:layout_width和android:layout_height为match_parent或者wrap_content时，是填充全屏的
         * android:layout_width和android:layout_height设置为具体值时，那么是多少，宽高就是多少
         */

        int width = 50;
        if (MeasureSpec.getMode(widthMeasureSpec)==MeasureSpec.UNSPECIFIED){
            width = 50;
        }else {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }

        int height = 50;
        if (MeasureSpec.getMode(widthMeasureSpec)==MeasureSpec.UNSPECIFIED){
            height = 50;
        }else {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }

        setMeasuredDimension(width,height);
        //setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //设置一个默认值，就是这个View的默认宽度为500，这个看我们自定义View的要求
        int result = 100;
        if (specMode == MeasureSpec.AT_MOST) {//相当于我们设置为wrap_content
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {//相当于我们设置为match_parent或者为一个具体的值
            result = specSize;
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 100;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        //移动画布中心
        canvas.translate(mWidth / 2, mHeight / 2);

        if (mNumber == 0) {
            canvas.translate(0, -mTrlateHeight);
            canvas.scale(mAnimatorValue, mAnimatorValue);
            canvas.drawPath(mPath, mPaint);
        } else if (mNumber == 1) {
            if (mAnimatorValue == 1) {
                mAnimatorValue = 0;
            }
            canvas.rotate(90 * mAnimatorValue, 0, 0);
            canvas.drawPath(mPath1, mPaint);

            canvas.rotate(-90 * mAnimatorValue, 0, 0);
            canvas.translate(0, -mTrlateHeight);
            canvas.drawPath(mPath, mPaint);

        } else if (mNumber == 2) {
            if (mAnimatorValue == 1) {
                mAnimatorValue = 0;
            }
            canvas.rotate(90, 0, 0);
            canvas.drawPath(mPath1, mPaint);

            canvas.rotate(90 * mAnimatorValue, 0, 0);
            canvas.drawPath(mPath1, mPaint);

            canvas.rotate(-90, 0, 0);
            canvas.rotate(-90 * mAnimatorValue, 0, 0);
            canvas.translate(0, -mTrlateHeight);
            canvas.drawPath(mPath, mPaint);

        } else if (mNumber == 3) {
            if (mAnimatorValue == 1) {
                mAnimatorValue = 0;
            }
            canvas.rotate(90, 0, 0);
            canvas.drawPath(mPath1, mPaint);

            canvas.rotate(90, 0, 0);
            canvas.drawPath(mPath1, mPaint);

            canvas.rotate(90 * mAnimatorValue, 0, 0);
            canvas.drawPath(mPath1, mPaint);

            canvas.rotate(-90, 0, 0);
            canvas.rotate(-90, 0, 0);
            canvas.rotate(-90 * mAnimatorValue, 0, 0);
            canvas.translate(0, -mTrlateHeight);
            canvas.drawPath(mPath, mPaint);

        } else {

            if (mNumber >= 5) {
                mNumber = 5;
            }

            canvas.rotate(mFromDegrees);

            mPath1.reset();
            float scalFlag;
            if (mAnimatorValue > 0.5) {
                scalFlag = (float) (-mRadius * (mAnimatorValue - 0.5));
            } else {
                scalFlag = mRadius * mAnimatorValue;
            }


            mPath1.moveTo(0, -mRadius / 2 - mTrlateHeight + scalFlag);
            //1
            mPath1.cubicTo(mRadius / 2, -mRadius - mTrlateHeight + scalFlag, mRadius, -mRadius / 2 - mTrlateHeight + scalFlag, mRadius, 0 - mTrlateHeight + scalFlag);
            //2
            mPath1.cubicTo(mRadius, mRadius / 2 - mTrlateHeight + scalFlag, mRadius / 2, mRadius - mTrlateHeight + scalFlag, 0, mRadius + mRadiusFlag - mTrlateHeight + scalFlag);
            //3
            mPath1.cubicTo(-mRadius / 2, mRadius - mTrlateHeight + scalFlag, -mRadius, mRadius / 2 - mTrlateHeight + scalFlag, -mRadius, 0 - mTrlateHeight + scalFlag);
            //4
            mPath1.cubicTo(-mRadius, -mRadius / 2 - mTrlateHeight + scalFlag, -mRadius / 2, -mRadius - mTrlateHeight + scalFlag, 0, -mRadius / 2 - mTrlateHeight + scalFlag);

            mPath1.close();


            for (int i = 0; i < mNumber; i++) {
                canvas.rotate(90, 0, 0);
                canvas.drawPath(mPath1, mPaint);

            }
            mFromDegrees += mCurrentSpeed;

        }

        canvas.restore();

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    private ROTE_SPEED mCurrentSpeedModel = ROTE_SPEED.DEFAULE_SPEED;
    private float mCurrentSpeed = 1f;

    public enum ROTE_SPEED {
        DEFAULE_SPEED, LOW_SPEED, MIDDIL_SPEED, FAST_SPEED, VERY_FAST_SPEED;
    }

    /**
     * 设置旋转速度
     */
    public void setRoteSpeed(ROTE_SPEED speed) {
        switch (speed) {
            case DEFAULE_SPEED:
                mCurrentSpeed = 1;
                mCurrentSpeedModel=ROTE_SPEED.DEFAULE_SPEED;
                break;
            case LOW_SPEED:
                mCurrentSpeed = 0.5f;
                mCurrentSpeedModel=ROTE_SPEED.LOW_SPEED;
                break;
            case MIDDIL_SPEED:
                mCurrentSpeed = 1.5f;
                mCurrentSpeedModel=ROTE_SPEED.MIDDIL_SPEED;
                break;
            case FAST_SPEED:
                mCurrentSpeedModel=ROTE_SPEED.FAST_SPEED;
                mCurrentSpeed = 2f;
                break;
            case VERY_FAST_SPEED:
                mCurrentSpeedModel=ROTE_SPEED.VERY_FAST_SPEED;
                mCurrentSpeed = 2.5f;
                break;
            default:
                mCurrentSpeed = 1f;
                mCurrentSpeedModel=ROTE_SPEED.DEFAULE_SPEED;
                break;
        }

    }
    /**
     * 获取旋转速度
     */
    public ROTE_SPEED getCurrentSpeedModel(){
        return mCurrentSpeedModel;
    }
    /**
     * 半径设置
     */
    public void setRadius(int radius){
        mRadius = radius;
        mTrlateHeight=mRadius * 2 + mRadiusFlag;
    }
    /**
     * 颜色设置
     */
    public void setCoreColor(int color){
        mCoreColor=color;
        mPaint.setColor(color);
    }
    /**
     * 展开时间 设置
     */
    public void setCircleProgressDuration(int duration){
        mCircleProgressDuration = duration;
    }

    /**
     * 重新开始
     */
    public void reStart(){
        mNumber=0;
    }
    /**
     * 停止
     */
    public void stop(){
        mValueAnimator.cancel();
    }
    public void stop(boolean flag){
        if (flag) {
            mNumber=0;
        }
        stop();
    }
    /**
     * 获取当前的展开时间
     */
    public long getCircleProgressDuration() {
        return mCircleProgressDuration;
    }

    /**
     * 开始转动
     */
    public void start(){
        mValueAnimator.start();
    }
    /**
     * 开始转动
     */
    public void start(int duation){
        mCircleProgressDuration = duation;
        mValueAnimator.setDuration(duation);
        start();
    }
}

