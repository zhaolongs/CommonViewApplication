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


/**
 * Created by androidlongs on 17/1/17.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class CirCleSwitchButton extends View {

    private Paint mSwitchButtonMainPaint;
    private int mSwitchButtonWidth;
    private int mSwitchButtonHeight;

    private int mSwitchButtonSlideRadius = 60;
    private float mSwitchButtonSlideWidth = mSwitchButtonSlideRadius * 1f / 1.5f;
    private RectF mSwitchButtonBackGroundRectF;
    private Region mSwitchButtonRightRegion;
    private Region mSwitchButtonLeftRegion;
    private ValueAnimator mSwitchButtonValueAnimator;
    private float mAnimatorValue;
    private long mSwitchButtonSlideDuration = 1000;

    private int mSwitchButtonSlideColor;
    private int mSwitchButtonSlideCloseBackgroundColor;
    private int mSwitchButtonSlideOpenBackgroundColor;

    public enum SWITCH_STATUE {
        OPEN, CLOSE
    }

    public SWITCH_STATUE mSwitchButtonCurrentSwitch = SWITCH_STATUE.CLOSE;
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

        mSwitchButtonMainPaint = new Paint();
        mSwitchButtonMainPaint.setColor(Color.RED);
        mSwitchButtonMainPaint.setStyle(Paint.Style.FILL);
        mSwitchButtonMainPaint.setAntiAlias(true);
        mSwitchButtonMainPaint.setDither(true);

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

            //状态设置
            int statue = typedArray.getInt(R.styleable.CirCleSwitchButton_switchButtonStatue,2);
            if (statue==1){
                mSwitchButtonCurrentSwitch=SWITCH_STATUE.CLOSE;
            }else {
                mSwitchButtonCurrentSwitch=SWITCH_STATUE.OPEN;
            }

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
        canvas.save();

        // 方法二
        // 返回true，如果canvas在绘制的时候启用了硬件加速
        // 尽量采用此方法来判断是否开启了硬件加速
        if (canvas.isHardwareAccelerated()) {
            this.setLayerType(View.LAYER_TYPE_NONE, null);
        }


        //变换手指按下坐标
        float[] downPoint = new float[] {mDownx, mDowny};
        Matrix matrix = new Matrix();
        canvas.getMatrix().invert(matrix);
        matrix.mapPoints(downPoint);

        int x = (int) downPoint[0];
        int y = (int) downPoint[1];

        //点按有效区域
        if (mSwitchButtonLeftRegion.contains(x, y) || mSwitchButtonRightRegion.contains(x, y)) {
            Log.d("down ", "down left " + mDownx + " - " + downPoint[0]);

            if (mSwitchButtonCurrentSwitch == SWITCH_STATUE.OPEN) {
                //开启
                //绘制背景
                mSwitchButtonMainPaint.setStyle(Paint.Style.FILL);
                mSwitchButtonMainPaint.setColor(mSwitchButtonSlideOpenBackgroundColor);
                canvas.drawRoundRect(mSwitchButtonBackGroundRectF, mSwitchButtonSlideRadius, mSwitchButtonSlideRadius, mSwitchButtonMainPaint);

                //绘制圆环
                mSwitchButtonMainPaint.setStyle(Paint.Style.STROKE);
                mSwitchButtonMainPaint.setColor(mSwitchButtonSlideColor);
                mSwitchButtonMainPaint.setStrokeWidth(mSwitchButtonSlideWidth);
                //动态移动
                canvas.translate((mSwitchButtonBackGroundRectF.width() - mSwitchButtonBackGroundRectF.height()) * mAnimatorValue, 0);
                //绘制圆环
                canvas.drawCircle(-mSwitchButtonSlideRadius, 0, (mSwitchButtonBackGroundRectF.height() - mSwitchButtonSlideWidth) / 2, mSwitchButtonMainPaint);

            } else if (mSwitchButtonCurrentSwitch == SWITCH_STATUE.CLOSE) {

                //关闭
                //绘制背景
                mSwitchButtonMainPaint.setStyle(Paint.Style.FILL);
                mSwitchButtonMainPaint.setColor(mSwitchButtonSlideCloseBackgroundColor);
                canvas.drawRoundRect(mSwitchButtonBackGroundRectF, mSwitchButtonSlideRadius, mSwitchButtonSlideRadius, mSwitchButtonMainPaint);

                //绘制圆环
                mSwitchButtonMainPaint.setStyle(Paint.Style.STROKE);
                mSwitchButtonMainPaint.setColor(mSwitchButtonSlideColor);
                mSwitchButtonMainPaint.setStrokeWidth(mSwitchButtonSlideWidth);

                canvas.translate(-(mSwitchButtonBackGroundRectF.width() - mSwitchButtonBackGroundRectF.height()) * mAnimatorValue, 0);
                canvas.drawCircle(mSwitchButtonSlideRadius, 0, (mSwitchButtonBackGroundRectF.height() - mSwitchButtonSlideWidth) / 2, mSwitchButtonMainPaint);
            }
        } else {
            Log.e("down ", "down is outsite  " + x + " y " + y);
            //绘制背景
            mSwitchButtonMainPaint.setStyle(Paint.Style.FILL);
            mSwitchButtonMainPaint.setColor(mSwitchButtonSlideCloseBackgroundColor);
            canvas.drawRoundRect(mSwitchButtonBackGroundRectF, mSwitchButtonSlideRadius, mSwitchButtonSlideRadius, mSwitchButtonMainPaint);

            //绘制圆环
            mSwitchButtonMainPaint.setStyle(Paint.Style.STROKE);
            mSwitchButtonMainPaint.setColor(mSwitchButtonSlideColor);
            mSwitchButtonMainPaint.setStrokeWidth(mSwitchButtonSlideWidth);
            canvas.drawCircle(-mSwitchButtonSlideRadius, 0, (mSwitchButtonBackGroundRectF.height() - mSwitchButtonSlideWidth) / 2, mSwitchButtonMainPaint);
            mSwitchButtonCurrentSwitch = mSwitchButtonPreSwitch;
        }


        canvas.restore();
    }

    private float mDownx;
    private float mDowny;
    private float mProgress;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownx = event.getX();
                mDowny = event.getY();

                invalidate();
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
                } else {
                    mSwitchButtonCurrentSwitch = SWITCH_STATUE.CLOSE;
                    mSwitchButtonPreSwitch = SWITCH_STATUE.OPEN;
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
     * 状态设置
     */
    public void setSwitchButtonStatue(SWITCH_STATUE tpye){
        this.mSwitchButtonCurrentSwitch=tpye;
    }
}
