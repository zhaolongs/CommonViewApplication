package com.example.androidlongs.popwindowapplication.path.base8;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidlongs.popwindowapplication.R;
import com.example.androidlongs.popwindowapplication.button.AnimationButton;

/**
 * Created by androidlongs on 17/1/16.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PathCoreMainActivity extends Activity {

    private Button mSwitchColorButton;
    private PathCoreView mPcvView;
    private Button mSwitchSpeedButton;
    private Button mShowSpeedButton;
    private Button mSwitchExplodSpeedButton;
    private Button mShowExplodSpeedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_home);
        mSwitchColorButton = (AnimationButton) findViewById(R.id.bt_switch_color_core_main);
        mSwitchSpeedButton = (AnimationButton) findViewById(R.id.bt_switch_speed_core_main);
        mShowSpeedButton = (AnimationButton) findViewById(R.id.bt_show_speed);

        mSwitchExplodSpeedButton = (AnimationButton) findViewById(R.id.bt_switch_explod_speed_core_main);
        mShowExplodSpeedButton = (AnimationButton) findViewById(R.id.bt_explod_show_speed);

        mPcvView = (PathCoreView) findViewById(R.id.pcv_main);
        mSwitchColorButton.setOnClickListener(mSwitchColorClickListener);
        mSwitchSpeedButton.setOnClickListener(mSwitchSpeedClickListener);

        mSwitchExplodSpeedButton.setOnClickListener(mSwitchExploadSpeedClickListener);
    }

    private View.OnClickListener mSwitchColorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switchCoroColorFunction();
        }
    };
    private View.OnClickListener mSwitchSpeedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switchCoroSpeedFunction();
        }
    };
    private View.OnClickListener mSwitchExploadSpeedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switchCoroExploadSpeedFunction();
        }
    };

    private void switchCoroExploadSpeedFunction() {
        mPcvView.stop(true);
        long circleProgressDuration = mPcvView.getCircleProgressDuration();

        System.out.println("" + circleProgressDuration);
        if (circleProgressDuration < 1000) {
            mShowExplodSpeedButton.setText("默认速度");
            circleProgressDuration = 1000;
            mShowExplodSpeedButton.setText("慢速度");
        } else if (circleProgressDuration <= 1500 && circleProgressDuration >= 1000) {
            circleProgressDuration = 2000;
            mShowExplodSpeedButton.setText("中慢速度");
        } else if (circleProgressDuration <= 2000 && circleProgressDuration > 1500) {
            circleProgressDuration = 800;
            mShowExplodSpeedButton.setText("快速度");
        } else {
            circleProgressDuration = 800;
            mShowExplodSpeedButton.setText("快速度");
        }
        System.out.println("" + circleProgressDuration);
        mPcvView.start((int) circleProgressDuration);
    }

    private void switchCoroSpeedFunction() {
        PathCoreView.ROTE_SPEED currentSpeedModel = mPcvView.getCurrentSpeedModel();
        switch (currentSpeedModel) {
            case DEFAULE_SPEED:
                currentSpeedModel = PathCoreView.ROTE_SPEED.MIDDIL_SPEED;
                mShowSpeedButton.setText("中速度");
                break;
            case LOW_SPEED:
                mShowSpeedButton.setText("默认速度");
                currentSpeedModel = PathCoreView.ROTE_SPEED.DEFAULE_SPEED;
                break;
            case MIDDIL_SPEED:
                mShowSpeedButton.setText("快速度");
                currentSpeedModel = PathCoreView.ROTE_SPEED.FAST_SPEED;

                break;
            case FAST_SPEED:
                mShowSpeedButton.setText("极快速度");
                currentSpeedModel = PathCoreView.ROTE_SPEED.VERY_FAST_SPEED;

                break;
            case VERY_FAST_SPEED:
                mShowSpeedButton.setText("慢速度");
                currentSpeedModel = PathCoreView.ROTE_SPEED.LOW_SPEED;

                break;
        }
        mPcvView.setRoteSpeed(currentSpeedModel);
    }

    private void switchCoroColorFunction() {
        int blue = (int) Math.floor(Math.random() * 255);
        int red = (int) Math.floor(Math.random() * 255);
        int green = (int) Math.floor(Math.random() * 255);

        int color = Color.rgb(red, green, blue);

        mPcvView.setCoreColor(color);
    }

}
