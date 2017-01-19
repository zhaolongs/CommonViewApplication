package com.example.androidlongs.popwindowapplication.path.base9;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.androidlongs.popwindowapplication.R;
import com.example.androidlongs.popwindowapplication.utils.LogUtils;


/**
 * Created by androidlongs on 17/1/17.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class CirCleSwitchButton extends View {

    //默认画笔
    private Paint mSwitchButtonMainPaint;
    //宽
    private int mSwitchButtonWidth;
    //高
    private int mSwitchButtonHeight;
    //绘制半径
    private int mSwitchButtonSlideRadius = 60;
    //画笔宽度
    private float mSwitchButtonSlideWidth = mSwitchButtonSlideRadius * 1f / 1.5f;
    //背景
    private RectF mSwitchButtonBackGroundRectF;
    //点击范围
    private Region mSwitchButtonRightRegion;
    private Region mSwitchButtonLeftRegion;
    private ValueAnimator mSwitchButtonValueAnimator;
    private float mAnimatorValue;
    //开关 切换时间
    private long mSwitchButtonSlideDuration = 1000;
    //开关 滑块颜色
    private int mSwitchButtonSlideColor;
    //开关关闭背景颜色
    private int mSwitchButtonSlideCloseBackgroundColor;
    //开关打开背景颜色
    private int mSwitchButtonSlideOpenBackgroundColor;
    //开关第一次绘制标识
    private boolean mIsSwitchButtonOnceDraw = true;

    //开关的状态
    public enum SWITCH_STATUE {
        OPEN, CLOSE
    }

    //开关的当前状态
    public SWITCH_STATUE mSwitchButtonCurrentSwitch = SWITCH_STATUE.CLOSE;
    //开关的上一次点击状态
    public SWITCH_STATUE mSwitchButtonPreSwitch = SWITCH_STATUE.CLOSE;


    public CirCleSwitchButton(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public CirCleSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public CirCleSwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }

    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {

        //画笔
        mSwitchButtonMainPaint = new Paint();
        //画笔颜色
        mSwitchButtonMainPaint.setColor(Color.WHITE);
        //画笔模式
        mSwitchButtonMainPaint.setStyle(Paint.Style.FILL);
        //抗锯齿
        mSwitchButtonMainPaint.setAntiAlias(true);
        //抗抖动
        mSwitchButtonMainPaint.setDither(true);

        //开关点击区域控制
        mSwitchButtonLeftRegion = new Region();
        mSwitchButtonRightRegion = new Region();


        mSwitchButtonValueAnimator = ValueAnimator.ofFloat(0, 1);
        mSwitchButtonValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });


        mSwitchButtonValueAnimator.addListener(new Animator.AnimatorListener() {
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
        mSwitchButtonValueAnimator.setDuration(mSwitchButtonSlideDuration);
        mSwitchButtonValueAnimator.setRepeatCount(0);
        mSwitchButtonValueAnimator.setInterpolator(new OvershootInterpolator());


        //自定义属性控制
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.CirCleSwitchButton);
            //滑动时间
            mSwitchButtonSlideDuration = typedArray.getInt(R.styleable.CirCleSwitchButton_switchButtonDuaration, 1000);
            //设置时间
            setSlideDuation(mSwitchButtonSlideDuration);

            //滑块颜色
            try {
                mSwitchButtonSlideColor = typedArray.getColor(R.styleable.CirCleSwitchButton_switchButtonColor, Color.parseColor("fff"));
            } catch (Exception e) {
                mSwitchButtonSlideColor = Color.parseColor("#ffffff");
            }

            //滑块背景颜色
            try {
                mSwitchButtonSlideOpenBackgroundColor = typedArray.getColor(R.styleable.CirCleSwitchButton_switchButtonOpenColor, Color.parseColor("f48c0b"));
            } catch (Exception e) {
                mSwitchButtonSlideOpenBackgroundColor = Color.parseColor("#f48c0b");
            }
            //滑块背景颜色
            try {
                mSwitchButtonSlideCloseBackgroundColor = typedArray.getColor(R.styleable.CirCleSwitchButton_switchButtonOpenColor, Color.parseColor("0f0f0f"));
            } catch (Exception e) {
                mSwitchButtonSlideCloseBackgroundColor = Color.parseColor("#0f0f0f");
            }

            //半径
            mSwitchButtonSlideRadius = (int) typedArray.getDimension(R.styleable.CirCleSwitchButton_switchButtonRadius, 20);
            setSwitchButtonSlideRadius(mSwitchButtonSlideRadius);
            //状态设置
            int statue = typedArray.getInt(R.styleable.CirCleSwitchButton_switchButtonStatue, 1);
            if (statue == 1) {
                mSwitchButtonCurrentSwitch = SWITCH_STATUE.CLOSE;
            } else {
                mSwitchButtonCurrentSwitch = SWITCH_STATUE.OPEN;
            }

            // 回收
            typedArray.recycle();

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSwitchButtonWidth = w;
        mSwitchButtonHeight = h;
        //滑块背景区域
        mSwitchButtonBackGroundRectF = new RectF(-2 * mSwitchButtonSlideRadius, -mSwitchButtonSlideRadius, 2 * mSwitchButtonSlideRadius, mSwitchButtonSlideRadius);
        //点击有效区域
        mSwitchButtonLeftRegion.set(-2 * mSwitchButtonSlideRadius, -mSwitchButtonSlideRadius, 0, mSwitchButtonSlideRadius);
        mSwitchButtonRightRegion.set(0, -mSwitchButtonSlideRadius, 2 * mSwitchButtonSlideRadius, mSwitchButtonSlideRadius);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mSwitchButtonWidth / 2, mSwitchButtonHeight / 2);
        //保存图层到默认栈中
        canvas.save();

        // 返回true，如果canvas在绘制的时候启用了硬件加速
        // 尽量采用此方法来判断是否开启了硬件加速
        if (canvas.isHardwareAccelerated()) {
            this.setLayerType(View.LAYER_TYPE_NONE, null);
        }

        if (mIsSwitchButtonOnceDraw) {
            //第一次绘制
            onceStartDrawFunction(canvas);
        } else {
            //非第一次绘制
            defaultDrawFunction(canvas);
        }

        //重置图层
        canvas.restore();
    }

    private void defaultDrawFunction(Canvas canvas) {
        //变换手指按下坐标
        float[] downPoint = new float[] {mDownx, mDowny};
        Matrix matrix = new Matrix();
        canvas.getMatrix().invert(matrix);
        matrix.mapPoints(downPoint);

        int x = (int) downPoint[0];
        int y = (int) downPoint[1];

        //点按有效区域
        if (mSwitchButtonLeftRegion.contains(x, y) || mSwitchButtonRightRegion.contains(x, y)) {
            LogUtils.d("down left " + mDownx + " - " + downPoint[0]);

            if (mSwitchButtonCurrentSwitch == SWITCH_STATUE.OPEN) {
                //开启
                //绘制背景
                defaulDrawBackgroundFunction(canvas, mSwitchButtonSlideOpenBackgroundColor);
                //圆环
                defaulDrawSlide(canvas, mAnimatorValue, +1);
            } else if (mSwitchButtonCurrentSwitch == SWITCH_STATUE.CLOSE) {

                //关闭
                //绘制背景
                defaulDrawBackgroundFunction(canvas, mSwitchButtonSlideCloseBackgroundColor);
                //圆环
                defaulDrawSlide(canvas, mAnimatorValue, -0.5f);
            }
        } else {
            LogUtils.e("down is outsite  " + x + " y " + y);
            mSwitchButtonCurrentSwitch = mSwitchButtonPreSwitch;
            if (mSwitchButtonCurrentSwitch == SWITCH_STATUE.OPEN) {
                defaulDrawBackgroundFunction(canvas, mSwitchButtonSlideOpenBackgroundColor);
                //圆环
                defaulDrawSlide(canvas, 1);
            } else {

                defaulDrawBackgroundFunction(canvas, mSwitchButtonSlideCloseBackgroundColor);
                //圆环
                defaulDrawSlide(canvas, 0);
            }


        }

    }

    /**
     * 第一次加载控件绘制
     */
    private void onceStartDrawFunction(Canvas canvas) {
        //设置标识
        mIsSwitchButtonOnceDraw = false;
        if (mSwitchButtonCurrentSwitch == SWITCH_STATUE.OPEN) {
            //开启
            //绘制背景
            defaulDrawBackgroundFunction(canvas, mSwitchButtonSlideOpenBackgroundColor);
            //绘制圆环
            defaulDrawSlide(canvas, 1);

        } else if (mSwitchButtonCurrentSwitch == SWITCH_STATUE.CLOSE) {
            //绘制背景
            defaulDrawBackgroundFunction(canvas, mSwitchButtonSlideCloseBackgroundColor);
            defaulDrawSlide(canvas, 0);
        }
    }

    //绘制背景
    private void defaulDrawBackgroundFunction(Canvas canvas, int color) {
        //设置背景绘制样式
        mSwitchButtonMainPaint.setStyle(Paint.Style.FILL);
        mSwitchButtonMainPaint.setColor(color);
        //绘制矩形背景
        canvas.drawRoundRect(mSwitchButtonBackGroundRectF, mSwitchButtonSlideRadius, mSwitchButtonSlideRadius, mSwitchButtonMainPaint);
    }

    //绘制圆环
    private void defaulDrawSlide(Canvas canvas, float i) {
        defaulDrawSlide(canvas, i, +1);
    }

    private void defaulDrawSlide(Canvas canvas, float i, float flag) {
        //设置圆环画笔样式
        mSwitchButtonMainPaint.setStyle(Paint.Style.STROKE);
        mSwitchButtonMainPaint.setColor(mSwitchButtonSlideColor);
        mSwitchButtonMainPaint.setStrokeWidth(mSwitchButtonSlideWidth);
        //动态移动
        if (flag > 0) {
            canvas.translate((mSwitchButtonBackGroundRectF.width() - mSwitchButtonBackGroundRectF.height()) * (i), 0);

        } else {
            canvas.translate((mSwitchButtonBackGroundRectF.width() - mSwitchButtonBackGroundRectF.height()) * (1 - i), 0);
        }
        //绘制圆环
        canvas.drawCircle(-mSwitchButtonSlideRadius, 0, (mSwitchButtonBackGroundRectF.height() - mSwitchButtonSlideWidth) / 2, mSwitchButtonMainPaint);
    }

    private float mDownx;
    private float mDowny;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        //上下左右 内边距
        int left = getPaddingLeft();
        int bottom = getPaddingBottom();
        int top = getPaddingTop();
        int right = getPaddingRight();

        //定义默认宽
        int defaulWidth = mSwitchButtonSlideRadius * 5 + left + right;
        //定义默认高
        int defaulHeight = mSwitchButtonSlideRadius * 3 + top + bottom;

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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownx = event.getX();
                mDowny = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int moveY = (int) event.getY();

                int flagX = (int) (moveX - mDownx);

                break;
            case MotionEvent.ACTION_UP:

                if (mSwitchButtonCurrentSwitch == SWITCH_STATUE.CLOSE) {
                    mSwitchButtonCurrentSwitch = SWITCH_STATUE.OPEN;
                    mSwitchButtonPreSwitch = SWITCH_STATUE.CLOSE;
                    if (mLiserner != null) {
                        mLiserner.onOpen();
                    }
                } else {
                    mSwitchButtonCurrentSwitch = SWITCH_STATUE.CLOSE;
                    mSwitchButtonPreSwitch = SWITCH_STATUE.OPEN;
                    if (mLiserner != null) {
                        mLiserner.onClose();
                    }
                }

                if (mSwitchButtonValueAnimator.isRunning()) {
                    mSwitchButtonValueAnimator.cancel();
                }
                mSwitchButtonValueAnimator.start();
                invalidate();
                break;
        }
        return true;
    }


    /**
     * 设置滑动时间
     */
    public void setSlideDuation(long slideDuation) {
        if (slideDuation > 3000) {
            slideDuation = 3000;
        }
        if (slideDuation < 0) {
            slideDuation = 100;
        }
        Log.d("switch_button", "duration " + mSwitchButtonSlideDuration);
        mSwitchButtonSlideDuration = slideDuation;
        mSwitchButtonValueAnimator.setDuration(mSwitchButtonSlideDuration);
    }

    /**
     * 获取滑动时间
     */
    public long getSlideDuation() {
        return mSwitchButtonSlideDuration;
    }


    /**
     * 设置滑块颜色
     */
    public void setSwitchButtonSlideColor(int color) {
        this.mSwitchButtonSlideColor = color;
    }

    /**
     * 设置滑块背景颜色
     */
    public void setSwitchButtonOpenBackgroundColor(int color) {
        this.mSwitchButtonSlideOpenBackgroundColor = color;
    }

    public void setSwitchButtonCloseBackgroundColor(int color) {
        this.mSwitchButtonSlideCloseBackgroundColor = color;
    }

    /**
     * 设置半径
     */
    public void setSwitchButtonSlideRadius(int radius) {

        mSwitchButtonSlideRadius = radius;
        mSwitchButtonSlideWidth = mSwitchButtonSlideRadius * 1f / 1.5f;
    }

    /**
     * 状态设置
     */
    public void setSwitchButtonStatue(SWITCH_STATUE tpye) {
        this.mSwitchButtonCurrentSwitch = tpye;
    }

    interface OnSwitchButtonStatueLiserner {
        //close
        void onClose();

        //open
        void onOpen();
    }

    public OnSwitchButtonStatueLiserner mLiserner;

    public void setSwitchButtonLiserner(OnSwitchButtonStatueLiserner liserner) {
        this.mLiserner = liserner;
    }
}
