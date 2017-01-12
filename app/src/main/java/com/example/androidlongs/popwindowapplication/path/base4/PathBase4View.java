package com.example.androidlongs.popwindowapplication.path.base4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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

public class PathBase4View extends View {
    public PathBase4View(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public PathBase4View(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public PathBase4View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }


    private Paint mPaint;

    private int mViewWidth;
    private int mViewHeight;


    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(18);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewHeight = h;
        mViewWidth = w;
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


//        canvas.translate(mViewWidth / 2, mViewHeight / 2);
//        // 平移坐标系
//
//        Path path = new Path();
//        // 创建Path并添加了一个矩形
//        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
//
//        Path dst = new Path();                                      // 创建用于存储截取后内容的 Path
//
//        PathMeasure measure = new PathMeasure(path, false);         // 将 Path 与 PathMeasure 关联
//
//        // 截取一部分存入dst中，并使用 moveTo 保持截取得到的 Path 第一个点的位置不变
//        measure.getSegment(200, 600, dst, true);
//
//        canvas.drawPath(dst, mPaint);                        // 绘制 dst

        // 平移坐标系
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        //绘制坐标原点
        mPaint.setColor(Color.RED);
        canvas.drawPoint(0, 0, mPaint);
        //绘制 坐标系
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.BLACK);
        //绘制x轴
        canvas.drawLine(-mViewWidth / 2, 0, mViewWidth / 2, 0, mPaint);
        //绘制y轴
        canvas.drawLine(0, -mViewHeight / 2, 0, mViewHeight / 2, mPaint);

        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(10);
        Path path = new Path();
        // 创建Path并添加了一个矩形
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);

        //绘制矩形 path
        canvas.drawPath(path, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(20);


        Path dst = new Path();                                      // 创建用于存储截取后内容的 Path
        dst.lineTo(-300,-300);
        PathMeasure measure = new PathMeasure(path, false);         // 将 Path 与 PathMeasure 关联

        // 参数四 true 保证截取得到的 Path 片段不会发生形变
        //      false 保证dst 的连续性
        measure.getSegment(00, 400, dst, true);


        System.out.println("length " +measure.getLength());
        canvas.drawPath(dst, mPaint);

    }

}
