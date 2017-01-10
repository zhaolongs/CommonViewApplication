package com.example.androidlongs.popwindowapplication.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.example.androidlongs.popwindowapplication.R;

/**
 * Created by androidlongs on 17/1/10.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class AnimationButton extends Button {

    private AnimationButtonDrawable mDrawable;
    private int mTextColor;


    public AnimationButton(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public AnimationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public AnimationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //设置drawable绘制区域
        mDrawable.setBounds(0, 0, getWidth(), getHeight());
        mDrawable.setWidth(getWidth());
        mDrawable.setHeight(getHeight());
        mDrawable.setBackGroundColor(mBackGroundColor);
        mDrawable.setBackGroundRounds(mRx, mRy);
        mDrawable.setAnimationCircleColor(mAnimationCircleColor);
        mDrawable.setBackGroundDownColor(mBackGroundDownColor);
    }

    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.AnimationButton);

            //背景颜色
            mBackGroundColor = typedArray.getColor(R.styleable.AnimationButton_backGroundColor, Color.WHITE);
            //点击按下背景颜色
            mBackGroundDownColor = typedArray.getColor(R.styleable.AnimationButton_backGroundDownColor, Color.GRAY);

            //按钮文字颜色
            mTextColor = typedArray.getColor(R.styleable.AnimationButton_buttonTextColor, Color.BLUE);
            //背影圆角
            mRx = typedArray.getDimension(R.styleable.AnimationButton_backGroundRoundRx, 0);
            mRy = typedArray.getDimension(R.styleable.AnimationButton_backGroundRoundRy, 0);

            //点击圆形波纹颜色
            mAnimationCircleColor = typedArray.getColor(R.styleable.AnimationButton_backGroundRoundColor, Color.BLUE);

        }
        mDrawable = new AnimationButtonDrawable();
        //设置刷新接口
        mDrawable.setCallback(this);

        //设置按钮文字颜色
        setTextColor(mTextColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDrawable.draw(canvas);
        super.onDraw(canvas);
    }

    //验证 drawable
    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mDrawable || super.verifyDrawable(who);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //设置事件
        mDrawable.setTouchEvent(event);
        return super.onTouchEvent(event);
    }


    //背景颜色
    private int mBackGroundColor = Color.GRAY;

    public AnimationButton setBackGroundColor(int color) {
        this.mBackGroundColor = color;
        return this;
    }

    //按下背景颜色
    private int mBackGroundDownColor = Color.GRAY;

    public AnimationButton setBackGroundDownColor(int color) {
        this.mBackGroundDownColor = color;
        return this;
    }

    //圆角矩形的边界控制
    private float mRx, mRy, mRadus;

    public AnimationButton setBackGroundRounds(float rx, float ry) {
        this.mRx = rx;
        this.mRy = ry;
        this.mRadus = 0;
        return this;
    }

    //点击圆形波形颜色
    private int mAnimationCircleColor = Color.BLUE;

    public AnimationButton setAnimationCircleColor(int animationCircleColor) {
        this.mAnimationCircleColor = animationCircleColor;
        return this;
    }

}
