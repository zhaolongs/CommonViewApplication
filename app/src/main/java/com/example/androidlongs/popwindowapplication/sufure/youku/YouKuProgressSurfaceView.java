package com.example.androidlongs.popwindowapplication.sufure.youku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.androidlongs.popwindowapplication.R;
import com.example.androidlongs.popwindowapplication.utils.LogUtils;

/**
 * Created by androidlongs on 17/1/22.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class YouKuProgressSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Thread mThread;
    //运行标识
    private boolean mIsRunning;
    private boolean mIsStop = false;

    private Canvas mCanvas;
    //宽度
    private float mYouKuStrokeWidth = 16;
    //颜色
    private int mYouKuColor = Color.BLUE;
    private Paint mPaint;
    private Path mPath;
    private float mRadius = 50;
    private float mRadiusWidth = mRadius / 5;
    private Path mDstPath;
    private Path mDst2Path;
    private PathMeasure mPathMeasure;
    private float mLength;

    //加载控件 开始绘制标识
    private boolean mIsDefaultStart;


    //private SweepGradient mSweepGradient;


    public YouKuProgressSurfaceView(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public YouKuProgressSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public YouKuProgressSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
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

        mPath = new Path();
        mPath.addCircle(0, 0, mRadius, Path.Direction.CW);
        mPath.close();

        mDstPath = new Path();
        mDst2Path = new Path();

        mPathMeasure = new PathMeasure(mPath, false);
        mLength = mPathMeasure.getLength();

        //焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

       // this.setBackgroundColor(Color.WHITE);


        //自定义属性控制
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.YouKuProgressSurfaceView);

            //半径
            int radius = (int) typedArray.getDimension(R.styleable.YouKuProgressSurfaceView_YouKuRadius, 20);
            setRadius(radius);

            //时间
            int duration = typedArray.getInteger(R.styleable.YouKuProgressSurfaceView_YouKuDuration, 600);
            setDuration(duration);

            //时间
            int unitNumber = typedArray.getInteger(R.styleable.YouKuProgressSurfaceView_YouKuUnitNumber, 10);
            setUnitNumber(unitNumber);

            //颜色
            int color1 = typedArray.getColor(R.styleable.YouKuProgressSurfaceView_YouKuColor1, Color.BLUE);
            int color2 = typedArray.getColor(R.styleable.YouKuProgressSurfaceView_YouKuColor2, Color.RED);

            setYouKuColor(color1, color2);

            mIsDefaultStart = typedArray.getBoolean(R.styleable.YouKuProgressSurfaceView_YouKuIsDefaultStart,false);
        }

    }

    private int mColor1 = Color.BLUE;
    private int mColor2 = Color.RED;

    private void setYouKuColor(int color1, int color2) {
        this.mColor1 = color1;
        this.mColor2 = color2;
    }


    private int mIsRoate = 0;
    private int mCurrentAngle = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        //上下左右 内边距
        int left = getPaddingLeft();
        int bottom = getPaddingBottom();
        int top = getPaddingTop();
        int right = getPaddingRight();

        //定义默认宽
        int defaulWidth = (int) (mRadius * 3 + left + right);
        //定义默认高
        int defaulHeight = (int) (mRadius * 3 + top + bottom);

        LogUtils.v("v defaul width " + defaulWidth + "  " + defaulHeight);
        /**
         * MeasureSpec封装了父布局传递给子布局的布局要求，每个MeasureSpec代表了一组宽度和高度的要求
         * MeasureSpec由size和mode组成。
         * 三种Mode：
         * 1.UNSPECIFIED
         * 父不没有对子施加任何约束，子可以是任意大小（也就是未指定）
         * (UNSPECIFIED在源码中的处理和EXACTLY一样。当View的宽高值设置为0的时候或者没有设置宽高时，模式为UNSPECIFIED
         * 2.EXACTLY
         * 父决定子的确切大小，子被限定在给定的边界里，忽略本身想要的大小。
         * (当设置width或height为match_parent时，模式为EXACTLY，因为子view会占据剩余容器的空间，所以它大小是确定的)
         * 3.AT_MOST
         * 子最大可以达到的指定大小
         * (当设置为wrap_content时，模式为AT_MOST, 表示子view的大小最多是多少，这样子view会根据这个上限来设置自己的尺寸)
         *
         * MeasureSpecs使用了二进制去减少对象的分配。
         */
        //宽度
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        if (widthModel == MeasureSpec.AT_MOST) {
            LogUtils.e(" width model is at_most");
        } else if (widthModel == MeasureSpec.EXACTLY) {
            LogUtils.e("width model is exactly ");
            defaulWidth = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            LogUtils.e("width model is UNSPECIFIED");
        }

        //高度
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        if (heightModel == MeasureSpec.AT_MOST) {
            LogUtils.e("height model  at_most ");
        } else if (heightModel == MeasureSpec.EXACTLY) {
            LogUtils.e("height model is  exatly ");
            defaulHeight = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            LogUtils.e("height model is UNSPECIFIED  ");
        }
        //设置
        setMeasuredDimension(defaulWidth, defaulHeight);
    }

    @Override
    public void run() {
        LogUtils.e("you ku thread run  ");
        while (mIsRunning) {
            commonDrawFunction();
        }


    }

    private   void  commonDrawFunction() {
        long preTimeMillis = SystemClock.currentThreadTimeMillis();

        if (mCurrentTimeUnit >= 1) {
            mCurrentTimeUnit = 1;
        }

        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {


                mCanvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
                //清除画布
                mCanvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(Color.WHITE);
                mCanvas.drawRect(-getMeasuredWidth() / 2, -getMeasuredHeight() / 2, getMeasuredWidth() / 2, getMeasuredHeight() / 2, mPaint);

                mCanvas.save();

                if (mIsRoate < 1) {
                    //----------------------------------------------------------------------
                    defaulDrawFunction1();
                    //----------------------------------------------------------------------
                    defaulDrawFunction2();

                } else {
                    //角度计算
                    mCurrentAngle += (360f / mDurationCount * 1.3);
                    LogUtils.d("angle " + mCurrentAngle + " radius " + mRadius);
                    mCanvas.rotate(mCurrentAngle);
                    //----------------------------------------------------------------------
                    mCurrentTimeUnit = 0.5f;
                    defaulDrawFunction1();
                    //----------------------------------------------------------------------
                    defaulDrawFunction2();
                }

                mCanvas.restore();
            }

        } catch (Exception e) {
            LogUtils.e("you ku exception : " + e.getMessage());
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }

            long currentTimeMillis = SystemClock.currentThreadTimeMillis();
            long flagTimeMillis = currentTimeMillis - preTimeMillis;
            if (flagTimeMillis < mDurationUnit) {
                SystemClock.sleep((long) (mDurationUnit - flagTimeMillis));
            }

            if (mCurrentTimeUnit >= 0.5) {
                mCurrentTimeUnit = 0.0f;
                mIsRoate++;
            }
        }

        if (mIsStop) {
            LogUtils.e("isStop "+mIsStop + " isrunnign  "+mIsRunning +" timUnit "+mCurrentTimeUnit);
            if ((mCurrentAngle%90)==0){
                mIsRunning=false;
            }
        }

        //更新进度
        mCurrentTimeUnit += mDrawUnit;

    }

    private void defaulDrawFunction2() {
        //曲线二 起始点
        float p2start = mLength * 0.5f;
        //曲线二 终点
        float p2_stop = mLength * (0.5f + mCurrentTimeUnit);
        //曲线二 变化周期单位
        float p2_unit = (p2_stop - p2start) / mUnitNumber;
        //曲线二 绘制颜色设置
        mPaint.setColor(mColor2);
        //曲线二 绘制
        drawFunction(p2start, p2_unit, 2);
    }

    private void defaulDrawFunction1() {
        //曲线一起始点
        float p1start = 0;
        //曲线一终点
        float p1_stop = mLength * mCurrentTimeUnit;
        //曲线一变化周期单位
        float p1_unit = p1_stop / mUnitNumber;
        //曲线一 绘制颜色设置
        mPaint.setColor(mColor1);
        //曲线一 绘制
        drawFunction(p1start, p1_unit, 1);
    }


    //绘制功能
    private void drawFunction(float p1start, float p1_unit, int flag, int sign) {


        //透明度
        float alpha = (flag - 1) * 255f / mUnitNumber;
        //宽度
        float strokeWidth = flag * (this.mRadiusWidth / mUnitNumber);
        if (alpha >= 255) {
            alpha = 254;
        }
        if (flag == mUnitNumber) {
            alpha = 255;
            strokeWidth = this.mRadiusWidth;
        }
        //初始化 path
        mDst2Path.reset();
        //测量终点
        float v9 = p1start + p1_unit * flag;
        //限制第一部分曲线 终点
        if (sign == 1) {
            if (v9 >= mLength * 0.5f) {
                v9 = mLength * 0.5f;
            }
        } else {
            //限制第二部分曲线 终点
            if (v9 >= mLength) {
                v9 = mLength;
            }
        }
        //测量
        mPathMeasure.getSegment(p1start + p1_unit * (flag - 1), v9, mDst2Path, true);
        //第一段曲线 终点绘制
        if (mCurrentTimeUnit == 0.5 && sign == 1) {
            mPaint.setStyle(Paint.Style.FILL);
            mCanvas.drawCircle(-mRadius, 0, mRadiusWidth / 2, mPaint);
        } else if (mCurrentTimeUnit == 0.5 && sign == 2) {
            //第二段曲线 终点绘制
            mPaint.setStyle(Paint.Style.FILL);
            mCanvas.drawCircle(mRadius, 0, mRadiusWidth / 2, mPaint);
        }
        LogUtils.d("--sign  " + sign + "  current time unit " + mCurrentTimeUnit);
        //设置透明度
        mPaint.setAlpha((int) alpha);
        //设置Style
        mPaint.setStyle(Paint.Style.STROKE);
        //调制宽度
        mPaint.setStrokeWidth(mRadiusWidth);
        //绘制
        mCanvas.drawPath(mDst2Path, mPaint);


    }

    private void drawFunction(float p1start, float p1_unit, int flag) {
        for (int i = 1; i <= mUnitNumber; i++) {
            drawFunction(p1start, p1_unit, i, flag);
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        LogUtils.e("surfaceCreated  " +mIsDefaultStart);
        mThread = new Thread(this);
        if (mIsDefaultStart) {
            mThread.start();
            mIsRunning = true;
        }
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


    //更新一周期时间
    private int mDuration = 2000;
    //单位绘制时间
    private int mDurationUnit = 20;
    //周期内绘制次数
    private int mDurationCount = mDuration / mDurationUnit;
    //变化比率
    private float mDrawUnit = 0.5f / mDurationCount;

    //当前比例
    private float mCurrentTimeUnit = 0.5f;

    private int mUnitNumber = 14;

    /**
     * 设置转动时间
     */
    public void setDuration(long duration) {
        if (duration <= 100) {
            duration = 100;
        } else if (duration >= 2000) {
            duration = 2000;
        }
        //更新一周期时间
        this.mDuration = (int) duration;
        //单位绘制时间
        this.mDurationUnit = 20;
        //周期内绘制次数
        this.mDurationCount = mDuration / mDurationUnit;
        //变化比率
        this.mDrawUnit = 0.5f / mDurationCount;

        //当前比例
        this.mCurrentTimeUnit = 0.0f;

        LogUtils.e("duration " + duration);
    }


    /**
     * 设置半径
     *
     * @param radius 半径
     */
    public void setRadius(int radius) {
        if (radius <= 20) {
            radius = 20;
        } else if (radius >= 80) {
            radius = 80;
        }
        this.mRadius = radius;
        this.mRadiusWidth = radius / 3;
        //设置
        mPaint.setStrokeWidth(mRadiusWidth);
        //重设path
        mPath.reset();
        mPath.addCircle(0, 0, mRadius, Path.Direction.CW);
        mPath.close();
        mPathMeasure.setPath(mPath, false);
        mLength = mPathMeasure.getLength();

    }

    public float getRadius() {
        return mRadius;
    }

    public void setUnitNumber(int unitNumber) {
        if (unitNumber <= 8) {
            unitNumber = 8;
        } else if (unitNumber > 25) {
            unitNumber = 25;
        }
        this.mUnitNumber = unitNumber;
    }


    public void stopYouKuView() {
        mIsStop = true;
        mCurrentTimeUnit = 0;
    }

    public void startYouKuView() {
        if (!mIsRunning) {
            mIsRoate=0;
            mIsStop=false;
            mIsRunning = true;
            mCurrentAngle = 0;
            mCurrentTimeUnit = 0;
            mThread.start();
        }
    }
}
