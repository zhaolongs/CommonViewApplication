package com.example.androidlongs.popwindowapplication.path.base6;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.androidlongs.popwindowapplication.R;
import com.example.androidlongs.popwindowapplication.path.common.CircleProgressView;

import java.util.List;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PathTest6Activity extends Activity {


    private CircleProgressView mView;
    private CircleProgressView mView2;
    private CircleProgressView mView6;
    private CircleProgressView mView4;
    private CircleProgressView mView1;
    private CircleProgressView mView8;

    private Button mAddColorButton;
    private Button mClearColorButton;
    private Button mStartLoadButton;
    private Button mStopLoadButton;

    private Button mAddSpeedButton;
    private Button mReduceButton;

    private int total = 100;
    private int current = 0;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_path_6);

        mView = (CircleProgressView) findViewById(R.id.pathBase6);
        mView1 = (CircleProgressView) findViewById(R.id.pathBase6_1);
        mView2 = (CircleProgressView) findViewById(R.id.pathBase6_2);
        mView4 = (CircleProgressView) findViewById(R.id.pathBase6_4);
        mView6 = (CircleProgressView) findViewById(R.id.pathBase6_6);
        mView8 = (CircleProgressView) findViewById(R.id.pathBase6_8);


        mAddColorButton = (Button) findViewById(R.id.button_add_color);
        mClearColorButton = (Button) findViewById(R.id.button_clear_color);


        mAddSpeedButton = (Button) findViewById(R.id.button_add_speed);
        mReduceButton = (Button) findViewById(R.id.button_reduce);

        final View view = findViewById(R.id.view_color_sign);

        mStartLoadButton = (Button) findViewById(R.id.button_start_load);
        mStopLoadButton = (Button) findViewById(R.id.button_stop_load);

        //过渡颜色圈设置功能
        TransitionColorViewFunction(view);

        mView6.setTotalProgress(total);
        mView6.setShowProgress(true);
        mView6.setCurrentProgress(0);


        mHandler.post(mRunnable);

        mView.addCircleColor(Color.RED);
        mView.addCircleColor(Color.GRAY);
        mView.addCircleColor(Color.BLUE);
        mView.addCircleColor(Color.GREEN);

        for (int i = 0; i < 50; i++) {
            int color = Color.rgb((int) Math.floor(Math.random() * 255 + 1), (int) Math.floor(Math.random() * 255 + 1), (int) Math.floor(Math.random() * 255 + 1));

            mView.addCircleColor(color);
            mView1.addCirclePointColor(color);

            mView6.addCirclePointColor(color);
        }
    }

    private void TransitionColorViewFunction(final View view) {
        setSingViewColorFunction(view);


        mAddColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = Color.rgb((int) Math.floor(Math.random() * 255 + 1), (int) Math.floor(Math.random() * 255 + 1), (int) Math.floor(Math.random() * 255 + 1));

                mView8.addCircleSweepColor(color);

                setSingViewColorFunction(view);
            }
        });


        mClearColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView8.clearCircleSweepColor();
                setSingViewColorFunction(view);
            }
        });

        mStopLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView8.stop();
            }
        });
        mStartLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView8.start();
            }
        });

        //加快转动速度
        mAddSpeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView8.addrotationalSpeed();
            }
        });
        //减慢转动速度
        mReduceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView8.reduceRotationalSpeed();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setSingViewColorFunction(final View view) {
        List<Integer> sweepColorList = mView8.getSweepColorList();
        int[] sweepColorArr = new int[sweepColorList.size()];
        for (int i = 0; i < sweepColorList.size(); i++) {
            sweepColorArr[i] = sweepColorList.get(i);
        }


        GradientDrawable bg = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, sweepColorArr);
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(bg);
        } else {
            view.setBackground(bg);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mView.close();
        mView1.close();
        mView2.close();

        mView4.close();
        mView6.close();

    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            current++;
            System.out.println("current progress " + current);
            if (current != total) {
                mView6.setCurrentProgress(current);
                mHandler.postDelayed(mRunnable, 200);
            } else {
                System.out.println("current progress  close ");
                mView6.close();
            }
        }
    };
}
