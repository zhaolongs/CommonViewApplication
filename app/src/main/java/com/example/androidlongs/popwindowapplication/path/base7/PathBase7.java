package com.example.androidlongs.popwindowapplication.path.base7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PathBase7 extends View {

    private int mHeight;
    private int mWidth;

    public PathBase7(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public PathBase7(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public PathBase7(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }


    private Paint mPaint;
    private int centerX, centerY;

    private PointF start, end, control;

    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control = new PointF(0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

        mWidth = w;
        mHeight = h;

        // 初始化数据点和控制点的位置
        start.x = centerX - 200;
        start.y = centerY;
        end.x = centerX + 200;
        end.y = centerY;
        control.x = centerX;
        control.y = centerY - 100;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        control.x = event.getX();
        control.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //
        canvas.translate(mWidth / 2, mHeight / 2);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        canvas.drawLine(-mWidth / 2, 0, mWidth / 2, 0, mPaint);
        canvas.drawLine(0, -mHeight / 2, 0, mHeight / 2, mPaint);
        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(10);
        //绘制开始点

        Path path1 = new Path();
        Path path2 = new Path();

        path1.addCircle(0,0,50, Path.Direction.CW);
        path2.lineTo(100,100);
        path2.addPath(path1);

        //绘制曲线
        canvas.drawPath(path2, mPaint);
    }

    private void addRoundRectFunction(Path path) {
        //path.addRoundRect(new RectF(-200, -100, 200, 100), 10, 10, Path.Direction.CW);

        path.addRoundRect(
                new RectF(-200, -100, 200, 100),
                new float[]{
                        10,10,
                        14,14,
                        10,10,
                        20,20
                }
                ,
                Path.Direction.CW
        );
    }

    private void arcToFunction(Path path) {
        path.moveTo(0, 0);
        path.lineTo(100, -100);
        path.arcTo(new RectF(-200, -100, 200, 100), 0, 45,false);
    }

    private void addArcFunction(Path path) {
        path.moveTo(0, 0);
        path.lineTo(100, -100);
        path.addArc(new RectF(-200, -100, 200, 100), 0, 45);
    }
}
