package com.example.androidlongs.popwindowapplication.path.base5;

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

public class PathBase5View extends View {
    public PathBase5View(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public PathBase5View(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public PathBase5View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }


    private Paint mPaint;

    private int mViewHeight;
    private int mViewWidth;

    private float currentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度

    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度


    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {


        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);

        pos = new float[2];
        tan = new float[2];

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
        canvas.translate(mViewWidth / 2, mViewHeight / 2);      // 平移坐标系


        //绘制圆心
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPoint(0,0,mPaint);

        mPaint.setColor(Color.BLUE);

        Path path = new Path();                                 // 创建 Path

        path.addCircle(0, 0, 200, Path.Direction.CW);           // 添加一个圆形

        PathMeasure measure = new PathMeasure(path, false);     // 创建 PathMeasure


        // 计算当前的位置在总长度上的比例[0,1]
        if (currentValue<0.5){
            currentValue += 0.001;
        }else if (currentValue>=0.5&&currentValue<0.8){
            currentValue += 0.011;
        }else {
            currentValue += 0.001;
        }
        if (currentValue >= 1) {
            currentValue = 0;
        }

        measure.getPosTan(measure.getLength() * currentValue, pos, tan);        // 获取当前位置的坐标以及趋势

        canvas.drawPath(path, mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawPoint(pos[0],pos[1],mPaint);

        System.out.println(pos[0] +"  "+pos[1]+" -- "+tan);



    }

    public void update(){
        postInvalidate();
    }


}
