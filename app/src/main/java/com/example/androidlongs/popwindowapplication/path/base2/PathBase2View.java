package com.example.androidlongs.popwindowapplication.path.base2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PathBase2View extends View {
    public PathBase2View(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public PathBase2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public PathBase2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }


    private Paint mPaint;
    private int centerX, centerY;

    private PointF start, end, control1, control2;
    private boolean mode = true;

    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control1 = new PointF(0, 0);
        control2 = new PointF(0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

        // 初始化数据点和控制点的位置
        start.x = centerX - 200;
        start.y = centerY;
        end.x = centerX + 200;
        end.y = centerY;

        control1.x = centerX-50;
        control1.y = centerY - 100;

        control2.x = centerX+50;
        control2.y = centerY - 100;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        if (mode) {
            control1.x = event.getX();
            control1.y = event.getY();
        } else {
            control2.x = event.getX();
            control2.y = event.getY();
        }
        invalidate();
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        //绘制起始点
        canvas.drawPoint(start.x, start.y, mPaint);
        //绘制结束点
        canvas.drawPoint(end.x, end.y, mPaint);
        //绘制控制点一
        canvas.drawPoint(control1.x, control1.y, mPaint);
        //绘制控制点二
        canvas.drawPoint(control2.x, control2.y, mPaint);

        // 绘制辅助线
        mPaint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control1.x, control1.y, mPaint);
        canvas.drawLine(control1.x, control1.y,control2.x, control2.y, mPaint);
        canvas.drawLine(control2.x, control2.y,end.x, end.y, mPaint);

        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path path = new Path();

        //移动到起点
        path.moveTo(start.x, start.y);
        //创建Path
        path.cubicTo(control1.x, control1.y, control2.x,control2.y, end.x, end.y);

        //绘制path
        canvas.drawPath(path, mPaint);
    }


    public void setMode(boolean mode) {
        this.mode = mode;
    }
}
