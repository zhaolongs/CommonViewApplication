package com.example.androidlongs.popwindowapplication.sufure.youku;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidlongs.popwindowapplication.R;

/**
 * Created by androidlongs on 17/1/24.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class YouKuTextActivity extends Activity {

    private YouKuProgressView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_ku);
        mView = (YouKuProgressView) findViewById(R.id.id_youku_home);

        Button stopButton = (Button) findViewById(R.id.youku_stop);
        Button startButton = (Button) findViewById(R.id.youku_start);
        Button selectSpeedButton = (Button) findViewById(R.id.youku_select_speed);



        startButton.setOnClickListener(mStartOnClickListener);
        stopButton.setOnClickListener(mStopOnClickListener);
        selectSpeedButton.setOnClickListener(mSelectOnClickListener);

    }

    private View.OnClickListener mStopOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mView.stopYouKuView();
        }
    };
    private View.OnClickListener mStartOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mView.startYouKuView();


        }
    };
    private float mDuration = 300;
    private View.OnClickListener mSelectOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mDuration>=3000){
                mDuration = 100;
            }
            mView.setDuration((long) (mDuration+=300));


        }
    };


}
