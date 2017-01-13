package com.example.androidlongs.popwindowapplication.path.common;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.androidlongs.popwindowapplication.R;

import static com.example.androidlongs.popwindowapplication.path.common.FindPathView.Model.ANIMATION_Y;
import static com.example.androidlongs.popwindowapplication.path.common.FindPathView.Model.DEFAUL_CIL;
import static com.example.androidlongs.popwindowapplication.path.common.FindPathView.Model.DEFAUL_LINE;
import static com.example.androidlongs.popwindowapplication.path.common.FindPathView.Type.DEFAUL;
import static com.example.androidlongs.popwindowapplication.path.common.FindPathView.Type.TYPE_LEVEL1;
import static com.example.androidlongs.popwindowapplication.path.common.FindPathView.Type.TYPE_LEVEL2;
import static com.example.androidlongs.popwindowapplication.path.common.FindPathView.Type.TYPE_LEVEL3_ANMATION_Y_RESERVER;
import static com.example.androidlongs.popwindowapplication.path.common.FindPathView.Type.TYPE_LEVEL3_ANMATION_Y_RESTART;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class FindPathView extends View {

    private Path mPath;
    private PathMeasure mPathMeasure;
    private Path mDstPath;
    private ValueAnimator mValueAnimator;
    private float mAnimatorValue;


    //find 圆圈半径
    private float mCircleRadius = 60;
    //find 圆圈宽度
    private float mCircleWidth = 15;
    private float mCircleInnerWidth = 14;
    //find 直线长度
    private float mLength2;
    //find 圆圈长度
    private float mLength1;

    //循环周期长度时间
    private long mDuration = 2000;


    //圆圈背景颜色
    private int mFindBackGroundColor;
    //圆圈颜色
    private int mFindColor;


    //显示坐标原点
    private boolean mIsShowCoordOrigin = true;
    //显示坐标原点 颜色
    private int mIsShowCoordOriginColor;
    //显示坐标
    private boolean mIsShowCoord = true;


    //起始时角度
    private float mFromDegrees;
    private Camera mCamera;
    float scale = 1;    // <------- 像素密度
    private Matrix mMatrix;


    public FindPathView(Context context) {
        super(context);
        initFunction(context, null, 0);
    }

    public FindPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFunction(context, attrs, 0);
    }

    public FindPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFunction(context, attrs, defStyleAttr);
    }


    private Paint mPaint;

    private int mViewHeight;
    private int mViewWidth;


    private void initFunction(Context context, AttributeSet attrs, int defStyleAttr) {


        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.FindPathView);
            //圆圈背景颜色
            mFindBackGroundColor = typedArray.getColor(R.styleable.FindPathView_findBackgroundColor, Color.GRAY);
            //圆圈颜色
            mFindColor = typedArray.getColor(R.styleable.FindPathView_findColor, Color.BLUE);

            //加载 圆圈的宽度
            mCircleWidth = typedArray.getDimension(R.styleable.FindPathView_findCicrleWidth, 8);
            mCircleInnerWidth = mCircleWidth - 2;

            //加载圆圈的半径
            mCircleRadius = typedArray.getDimension(R.styleable.FindPathView_findCicrleRadius, 20);

            //显示坐标相关
            mIsShowCoordOrigin = typedArray.getBoolean(R.styleable.FindPathView_isShowCenterOrigin, false);
            mIsShowCoord = typedArray.getBoolean(R.styleable.FindPathView_isShowcoord, false);
            //圆圈  坐标圆点颜色
            mIsShowCoordOriginColor = typedArray.getColor(R.styleable.FindPathView_findCenterOriginColor, Color.RED);

            //周期时间
            mDuration = typedArray.getInteger(R.styleable.FindPathView_findCicrleDuation,2000);


            int modelType = typedArray.getInt(R.styleable.FindPathView_typeModel, 1);
            switch (modelType) {
                case 1:
                    mCurrentModel = Model.DEFAUL_CIL;
                    mCurrentType = DEFAUL;
                    break;
                case 2:
                    mCurrentModel = Model.DEFAUL_LINE;
                    mCurrentType = TYPE_LEVEL1;
                    break;
                case 3:
                    mCurrentModel = Model.DEFAUL_LINE;
                    mCurrentType = TYPE_LEVEL2;
                    break;
                case 4:
                    mCurrentModel = Model.DEFAUL_CIL;
                    mCurrentType = TYPE_LEVEL3_ANMATION_Y_RESTART;
                    break;
                case 5:
                    mCurrentModel = Model.DEFAUL_CIL;
                    mCurrentType = TYPE_LEVEL3_ANMATION_Y_RESERVER;
                    break;
            }

        }


        mCamera = new Camera();
        mMatrix = new Matrix();

        // 获取手机像素密度 （即dp与px的比例）
        scale = context.getResources().getDisplayMetrics().density;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDstPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();

        mPath.addArc(new RectF(-mCircleRadius, -mCircleRadius, mCircleRadius, mCircleRadius), 45, 359.999f);
        // mPath.addCircle(0, 0, 60, Path.Direction.CW);
        float lineY = (float) (Math.sin(45) * mCircleRadius) - mCircleWidth / 2;
        float lineX = (float) (Math.cos(45) * mCircleRadius) + mCircleWidth;
        mPath.moveTo(lineX, lineY);
        mPath.lineTo(lineX + mCircleRadius, lineY + mCircleRadius * 9 / 10);

        mPathMeasure = new PathMeasure(mPath, false);
        mLength1 = mPathMeasure.getLength();
        Log.d("--", "length1 " + mLength1);
        mPathMeasure.nextContour();
        mLength2 = mPathMeasure.getLength();


        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.addListener(new Animator.AnimatorListener() {
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

                switch (mCurrentType) {
                    case DEFAUL:
                        switch (mCurrentModel) {
                            case DEFAUL_CIL:
                                mCurrentModel = DEFAUL_LINE;
                                break;
                            case DEFAUL_LINE:
                                mCurrentModel = DEFAUL_CIL;
                                break;
                        }
                        break;
                    case TYPE_LEVEL1:
                    case TYPE_LEVEL2:
                        switch (mCurrentModel) {
                            case DEFAUL_CIL:
                                mCurrentModel = DEFAUL_CIL;
                                break;
                            case DEFAUL_LINE:
                                mCurrentModel = DEFAUL_CIL;
                                break;
                        }
                        break;
                    case TYPE_LEVEL3_ANMATION_Y_RESERVER:
                    case TYPE_LEVEL3_ANMATION_Y_RESTART:
                        switch (mCurrentModel) {
                            case DEFAUL_CIL:
                                mCurrentModel = DEFAUL_LINE;
                                break;
                            case DEFAUL_LINE:
                                mCurrentModel = ANIMATION_Y;
                                break;
                        }
                        break;


                }

            }
        });
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.start();
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
                Log.e("pathfind", "path length " + mPathMeasure.getLength());

                switch (mCurrentType) {

                    case DEFAUL:
                        mCurrentType = TYPE_LEVEL1;
                        mCurrentModel = Model.DEFAUL_LINE;
                        break;
                    case TYPE_LEVEL1:
                        mCurrentType = DEFAUL;

                        break;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int flagX = (int) (event.getX() - mDownx);
                int flagY = (int) (event.getY() - mDownY);


                break;
        }


        return true;
    }

    public String getLevelType() {
        return mCurrentType.toString();
    }

    public enum Model {
        DEFAUL_CIL, DEFAUL_LINE, ANIMATION_Y
    }


    public enum Type {
        DEFAUL, TYPE_LEVEL1, TYPE_LEVEL2, TYPE_LEVEL3_ANMATION_Y_RESTART, TYPE_LEVEL3_ANMATION_Y_RESERVER
    }

//    public Model mCurrentModel = DEFAUL_LINE;
//    public Type mCurrentType = Type.TYPE_LEVEL1;

    public Model mCurrentModel = DEFAUL_LINE;
    public Type mCurrentType = TYPE_LEVEL2;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //常规绘制
        commonCanvasFunction(canvas);


        if (mCurrentType == DEFAUL) {
            defaulCanvasFunction(canvas);
        } else if (mCurrentType == TYPE_LEVEL1) {
            leve1CanvasFunction(canvas);
        } else if (mCurrentType == TYPE_LEVEL2) {
            leve2CanvasFunction(canvas);
        } else {
            leve3AnimationYCanvasFunction(canvas);
        }


    }

    private void leve3AnimationYCanvasFunction(Canvas canvas) {

        if (mCurrentModel == DEFAUL_CIL) {
            float defaul_start = (float) (2 * Math.PI * mCircleRadius * (45f / 360));
            Log.i("--", " " + (mLength1) + " value " + mAnimatorValue);
            float end1 = mAnimatorValue * (mLength1 + defaul_start);
            Log.e("--", "end " + end1);
            mPathMeasure.getSegment(0, end1, mDstPath, true);
        } else if (mCurrentModel == ANIMATION_Y) {

            mCamera.save();
            if (mCurrentType == TYPE_LEVEL3_ANMATION_Y_RESTART) {
                mFromDegrees = mAnimatorValue * 360;
                mCamera.rotateY(mFromDegrees);
            } else {
                mFromDegrees = -  (mAnimatorValue* 360);
                mCamera.rotate(0,mFromDegrees,0);
            }

            mCamera.getMatrix(mMatrix);
            canvas.concat(mMatrix);

            mCamera.restore();

            mPathMeasure.getSegment(0, mLength1, mDstPath, true);
            mPathMeasure.nextContour();
            mPathMeasure.getSegment(0, mLength2, mDstPath, true);
        } else {

            mPathMeasure.getSegment(0, (float) (mLength1 + (2 * Math.PI * mCircleRadius * (45f / 360))), mDstPath, true);
            mPathMeasure.nextContour();

            float start2 = 0;
            float end2 = mAnimatorValue * mLength2;
            mPathMeasure.getSegment(start2, end2, mDstPath, true);
        }


        mPaint.setStrokeWidth(mCircleInnerWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mFindColor);

        canvas.drawPath(mDstPath, mPaint);


        canvas.restore();


        ;
    }

    private void leve2CanvasFunction(Canvas canvas) {
        if (mCurrentModel == DEFAUL_LINE) {
            float start2 = 0;
            float end2 = (1 - mAnimatorValue) * mLength2;
            mPathMeasure.getSegment(0, 0, mDstPath, true);
            mPathMeasure.nextContour();
            mPathMeasure.getSegment(end2, mLength2, mDstPath, true);
        } else {
            float defaul_start = (float) (2 * Math.PI * mCircleRadius * (45f / 360));

            float end1 = mAnimatorValue * (mLength1 + defaul_start);

            float start = (float) (end1 - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength1));
            mPathMeasure.getSegment(start, end1, mDstPath, true);
            mPathMeasure.nextContour();
            mPathMeasure.getSegment(0, mLength2, mDstPath, true);
        }


        mPaint.setStrokeWidth(mCircleInnerWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mFindColor);


        mCamera.rotateX(60);
        mCamera.getMatrix(mMatrix);

        canvas.drawPath(mDstPath, mPaint);


        canvas.restore();
    }

    private void leve1CanvasFunction(Canvas canvas) {


//
//        mMatrix.preTranslate(20, 20);
//        mMatrix.postTranslate(100, 100);


        if (mCurrentModel == DEFAUL_LINE) {
            float start2 = 0;
            float end2 = (1 - mAnimatorValue) * mLength2;
            mPathMeasure.getSegment(0, 0, mDstPath, true);
            mPathMeasure.nextContour();
            mPathMeasure.getSegment(end2, mLength2, mDstPath, true);
        } else {
            float defaul_start = (float) (2 * Math.PI * mCircleRadius * (45f / 360));
            Log.i("--", " " + (mLength1) + " value " + mAnimatorValue);
            float end1 = mAnimatorValue * (mLength1 + defaul_start);
            Log.e("--", "end " + end1);
            mPathMeasure.getSegment(0, end1, mDstPath, true);
            mPathMeasure.nextContour();
            mPathMeasure.getSegment(0, mLength2, mDstPath, true);
        }


        mPaint.setStrokeWidth(mCircleInnerWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mFindColor);

        canvas.drawPath(mDstPath, mPaint);


        canvas.restore();
    }

    private void defaulCanvasFunction(Canvas canvas) {
        if (mCurrentModel == DEFAUL_CIL) {
            float defaul_start = (float) (2 * Math.PI * mCircleRadius * (45f / 360));
            Log.i("--", " " + (mLength1) + " value " + mAnimatorValue);
            float end1 = mAnimatorValue * (mLength1 + defaul_start);
            Log.e("--", "end " + end1);
            mPathMeasure.getSegment(0, end1, mDstPath, true);
        } else {

            mPathMeasure.getSegment(0, (float) (mLength1 + (2 * Math.PI * mCircleRadius * (45f / 360))), mDstPath, true);
            mPathMeasure.nextContour();

            float start2 = 0;
            float end2 = mAnimatorValue * mLength2;
            mPathMeasure.getSegment(start2, end2, mDstPath, true);
        }


        mPaint.setStrokeWidth(mCircleInnerWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mFindColor);

        canvas.drawPath(mDstPath, mPaint);


        canvas.restore();
    }

    private void commonCanvasFunction(Canvas canvas) {
        canvas.save();
        canvas.translate(mViewWidth / 2, mViewHeight / 2);      // 平移坐标系

        if (mIsShowCoordOrigin) {
            //绘制圆点
            mPaint.setStrokeWidth(10);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mIsShowCoordOriginColor);
            canvas.drawPoint(0, 0, mPaint);
        }

        if (mIsShowCoord) {
            //绘制坐标
            mPaint.setStrokeWidth(1);
            mPaint.setColor(mFindBackGroundColor);
            //X轴
            canvas.drawLine(-mViewWidth / 2, 0, mViewWidth / 2 - mCircleWidth, 0, mPaint);

            canvas.drawLine(mViewWidth / 2 - 2 * mCircleWidth, -mCircleWidth / 2, mViewWidth / 2 - mCircleWidth, 0, mPaint);
            canvas.drawLine(mViewWidth / 2 - 2 * mCircleWidth, mCircleWidth / 2, mViewWidth / 2 - mCircleWidth, 0, mPaint);


            //Y轴
            canvas.drawLine(0, -mViewHeight / 2, 0, mViewHeight / 2 - mCircleWidth, mPaint);

            canvas.drawLine(-mCircleWidth / 2, mViewHeight / 2 - 2 * mCircleWidth, 0, mViewHeight / 2 - mCircleWidth, mPaint);
            canvas.drawLine(mCircleWidth / 2, mViewHeight / 2 - 2 * mCircleWidth, 0, mViewHeight / 2 - mCircleWidth, mPaint);
        }

        //绘制背景
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mFindBackGroundColor);

        canvas.drawPath(mPath, mPaint);


        mDstPath.reset();
        mDstPath.lineTo(0, 0);
        mPathMeasure.setPath(mPath, false);
    }

    public void update() {
        postInvalidate();
    }


}
