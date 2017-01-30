package com.example.androidlongs.popwindowapplication.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.androidlongs.popwindowapplication.utils.LogUtils;

/**
 * Created by androidlongs on 17/1/29.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 * <p>
 * <p>
 * 自定义View基类
 */

abstract class BaseView extends View implements ViewTreeObserver.OnGlobalLayoutListener {

    //画笔
    private Paint mMPaint;
    //View 宽
    private int mWidth;
    //View 高
    private int mHeight;
    //默认高
    private int mDefaulWidth;
    //默认宽
    private int mDefaulHeight;

    public BaseView(Context context) {
        super(context);
        initFunction(context, null, 0, 0);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0, 0);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initFunction(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mMPaint = new Paint();
        defaulInitFunction(context);

        if (attrs != null) {
            attrsInitFunction(context, attrs);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        sizeChangeInitFunction(w, h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //上下左右 内边距
        int left = getPaddingLeft();
        int bottom = getPaddingBottom();
        int top = getPaddingTop();
        int right = getPaddingRight();

        //定义默认宽
        mDefaulWidth = (mDefaulWidth + left + right);
        //定义默认高
        mDefaulHeight = (mDefaulHeight + top + bottom);

        LogUtils.v("v defaul width " + mDefaulWidth + "  " + mDefaulHeight);
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
            LogUtils.e(" view  width model is at_most");
        } else if (widthModel == MeasureSpec.EXACTLY) {
            LogUtils.e(" view width model is exactly ");
            mDefaulWidth = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            LogUtils.e(" view width model is UNSPECIFIED");
        }

        //高度
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        if (heightModel == MeasureSpec.AT_MOST) {
            LogUtils.e(" view  height model  at_most ");
        } else if (heightModel == MeasureSpec.EXACTLY) {
            LogUtils.e(" view  height model is  exatly ");
            mDefaulHeight = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            LogUtils.e(" view height model is UNSPECIFIED  ");
        }
        //设置
        setMeasuredDimension(mDefaulWidth, mDefaulHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        onDefaultDrawFunction(canvas);
        canvas.restore();
    }

    /**
     * OnGlobalLayoutListener 是ViewTreeObserver的内部类，当一个视图树的布局发生改变时，可以被ViewTreeObserver监听到，
     * 这是一个注册监听视图树的观察者(observer)，在视图树的全局事件改变时得到通知
     * 需要注意的是OnGlobalLayoutListener可能会被多次触发，因此在得到了高度之后，要将OnGlobalLayoutListener注销掉。
     */
    @Override
    public void onGlobalLayout() {
        defaultGlobalChangeFunction();
    }


    /**
     * View出现的时候执行这个方法
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //注册OnGlobalLayoutListener
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * View从屏幕上消失的时候执行这个方法
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //注销OnGlobalLayoutListener
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }


    protected abstract void defaulInitFunction(Context context);

    protected abstract void attrsInitFunction(Context context, AttributeSet attrs);

    protected abstract void sizeChangeInitFunction(int w, int h);

    protected abstract void onDefaultDrawFunction(Canvas canvas);

    protected abstract void defaultGlobalChangeFunction();


}
