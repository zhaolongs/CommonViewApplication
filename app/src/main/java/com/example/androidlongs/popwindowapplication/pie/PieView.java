package com.example.androidlongs.popwindowapplication.pie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PieView extends View {
    public PieView(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }

    private Paint mPaint;

    //绘制起始角度
    private float mStartAngle = 0;

    private int mWidth, mHeight;

    private List<PieData> mPieDataList = new ArrayList<>();

    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制起始角度
        float currentAngle = mStartAngle;
        //移动画布中心
        canvas.translate(mWidth / 2, mHeight / 2);
        // 饼状图半径
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        // 饼状图绘制区域
        RectF rect = new RectF(-r, -r, r, r);

        for (int i = 0; i < mPieDataList.size(); i++) {
            PieData pieData = mPieDataList.get(i);

            mPaint.setColor(pieData.color);

            //绘制角度大小
            float childAngle = pieData.number * 1.0f / pieData.total * 360;
            //进行绘制
            canvas.drawArc(rect, currentAngle, childAngle, true, mPaint);
            //设置角度
            currentAngle += childAngle;
        }

    }


    private int mDownx;
    private int mDownY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownx = (int) event.getX();
                mDownY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                int mMoveX = (int) event.getX();
                int mMoveY = (int) event.getY();

                double delta_x = event.getX() - mDownx;
                double delta_y = event.getY() - mDownY;

                double a = Math.sqrt((mDownx - mWidth / 2) * (mDownx - mWidth / 2) + (mDownY - mHeight / 2) * (mDownY - mHeight / 2));
                double b = Math.sqrt((mMoveX-mWidth/2)*(mMoveX-mWidth/2)+(mMoveY-mHeight/2)*(mMoveY-mHeight/2));
                double c = Math.sqrt((mMoveX-mDownx)*(mMoveX-mDownx)+(mMoveY-mDownY)*(mMoveY-mDownY));

                double acos = Math.acos((a * a + b * b - c * c) / (2 * a * b));

                mStartAngle +=acos*2;

                System.out.println(" flage angle is "+acos + "startangle is "+mStartAngle);

                invalidate();


                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_CANCEL:

                break;
        }

        return true;
    }

    public void setPieDataList(List<PieData> pieDataList) {
        mPieDataList = pieDataList;
        invalidate();
    }
}
