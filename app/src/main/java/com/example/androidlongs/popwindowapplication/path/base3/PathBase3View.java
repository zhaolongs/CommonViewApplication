package com.example.androidlongs.popwindowapplication.path.base3;

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

public class PathBase3View extends View {
    public PathBase3View(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public PathBase3View(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public PathBase3View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }


    private Paint mPaint;
    private int centerX, centerY;

    private PointF p1_1, p1,p1_2;
    private PointF p2_1, p2,p2_2;
    private PointF p3_1, p3,p3_2;
    private PointF p4_1, p4,p4_2;


    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        p1 = new PointF(0, 0);
        p1_1 = new PointF(0, 0);
        p1_2 = new PointF(0, 0);

        p2 = new PointF(0, 0);
        p2_1 = new PointF(0, 0);
        p2_2 = new PointF(0, 0);

        p3 = new PointF(0, 0);
        p3_1 = new PointF(0, 0);
        p3_2 = new PointF(0, 0);

        p4 = new PointF(0, 0);
        p4_1 = new PointF(0, 0);
        p4_2 = new PointF(0, 0);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

        // 初始化数据点和控制点的位置

        p1_1.x = centerX - 100;
        p1_2.x = centerX + 100;
        p1.x = centerX;

        p1_1.y =centerY - 200;
        p1_2.y = centerY - 200;
        p1.y = centerY - 200;




        p2.x = centerX + 200;
        p2_1.x = centerX + 200;
        p2_2.x = centerX + 200;

        p2_1.y = centerY - 100;
        p2.y = centerY;
        p2_2.y = centerY + 100;


        p3_1.x = centerX - 100;
        p3_2.x = centerX + 100;
        p3.x = centerX;

        p3.y = centerY + 200;
        p3_1.y = centerY + 200;
        p3_2.y = centerY + 200;

        p4.x = centerX - 200;
        p4_1.x = centerX - 200;
        p4_2.x = centerX - 200;

        p4_1.y = centerY - 100;
        p4_2.y = centerY + 100;
        p4.y = centerY;

    }

    private int mDownx, mDownY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownx = (int) event.getX();
                mDownY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int flagX = (int) (event.getX() - mDownx);
                int flagY = (int) (event.getY() - mDownY);


                p1.y +=flagY/10;
                p3.y +=flagY/14;

                invalidate();
                break;
        }


        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        mPaint.setStrokeWidth(1);

        //设置画笔风格
        mPaint.setStyle(Paint.Style.FILL);
        //设置字体大小
        mPaint.setTextSize(26);
        //设置绘制字体的颜色
        mPaint.setColor(Color.BLUE);
        //设置字体的对齐方式
        mPaint.setTextAlign(Paint.Align.LEFT);
        //绘制每个点的坐标
        canvas.drawText("p1("+(int)p1.x+","+(int)p1.y+")", p1.x, p1.y-30, mPaint);
        canvas.drawText("p2("+(int)p2.x+","+(int)p2.y+")", p2.x, p2.y-30, mPaint);
        canvas.drawText("p3("+(int)p3.x+","+(int)p3.y+")", p3.x-20, p3.y+40, mPaint);
        canvas.drawText("p4("+(int)p4.x+","+(int)p4.y+")", p4.x-100, p4.y-40, mPaint);


        // 绘制数据点和控制点的画笔风格
        mPaint.setStyle(Paint.Style.STROKE);
        //设置绘制点的颜色
        mPaint.setColor(Color.GRAY);
        //设置边框宽度
        mPaint.setStrokeWidth(20);

        //绘制点
        canvas.drawPoint(p1.x, p1.y, mPaint);
        canvas.drawPoint(p1_1.x, p1_1.y, mPaint);
        canvas.drawPoint(p1_2.x, p1_2.y, mPaint);

        canvas.drawPoint(p2.x, p2.y, mPaint);
        canvas.drawPoint(p2_1.x, p2_1.y, mPaint);
        canvas.drawPoint(p2_2.x, p2_2.y, mPaint);

        canvas.drawPoint(p3.x, p3.y, mPaint);
        canvas.drawPoint(p3_1.x, p3_1.y, mPaint);
        canvas.drawPoint(p3_2.x, p3_2.y, mPaint);

        canvas.drawPoint(p4.x, p4.y, mPaint);
        canvas.drawPoint(p4_1.x, p4_1.y, mPaint);
        canvas.drawPoint(p4_2.x, p4_2.y, mPaint);


        // 绘制辅助线
        mPaint.setStrokeWidth(4);

        canvas.drawLine(p1_1.x, p1_1.y, p1.x, p1.y, mPaint);
        canvas.drawLine(p1_2.x, p1_2.y, p1.x, p1.y, mPaint);


        canvas.drawLine(p2_1.x, p2_1.y, p2.x, p2.y, mPaint);
        canvas.drawLine(p2_2.x, p2_2.y, p2.x, p2.y, mPaint);


        canvas.drawLine(p3_1.x, p3_1.y, p3.x, p3.y, mPaint);
        canvas.drawLine(p3_2.x, p3_2.y, p3.x, p3.y, mPaint);


        canvas.drawLine(p4_1.x, p4_1.y, p4.x, p4.y, mPaint);
        canvas.drawLine(p4_2.x, p4_2.y, p4.x, p4.y, mPaint);

        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path path = new Path();

        //移动到起点
        path.moveTo(p1.x, p1.y);
        //创建Path
        path.cubicTo(p1_2.x, p1_2.y, p2_1.x,p2_1.y,p2.x,p2.y);
        path.cubicTo(p2_2.x, p2_2.y, p3_2.x,p3_2.y, p3.x,p3.y);

        path.cubicTo(p3_1.x, p3_1.y, p4_2.x,p4_2.y, p4.x,p4.y);
        path.cubicTo(p4_1.x, p4_1.y, p1_1.x,p1_1.y, p1.x,p1.y);
        //绘制path
        canvas.drawPath(path, mPaint);
    }

}
