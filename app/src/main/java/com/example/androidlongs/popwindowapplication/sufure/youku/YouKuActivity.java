package com.example.androidlongs.popwindowapplication.sufure.youku;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.androidlongs.popwindowapplication.R;

/**
 * Created by androidlongs on 17/1/24.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class YouKuActivity extends Activity {

    private YouKuProgressView mView;
    private YouKuProgressView mView1;
    private YouKuProgressView mView2;
    private Button mSelectSpeedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_ku);
        mView = (YouKuProgressView) findViewById(R.id.id_youku_home);
        mView1 = (YouKuProgressView) findViewById(R.id.id_youku_home1);
        mView2 = (YouKuProgressView) findViewById(R.id.id_youku_home2);
        ImageView backImageView = (ImageView) findViewById(R.id.id_youku_home_back);
        backImageView.setOnClickListener(mBackOnClickListener);
        Button stopButton = (Button) findViewById(R.id.youku_stop);
        Button startButton = (Button) findViewById(R.id.youku_start);
        mSelectSpeedButton = (Button) findViewById(R.id.youku_select_speed);
        Button showButton1 = (Button) findViewById(R.id.youku_show_1);


        startButton.setOnClickListener(mStartOnClickListener);

        stopButton.setOnClickListener(mStopOnClickListener);
        mSelectSpeedButton.setOnClickListener(mSelectDurationtOnClickListener);

        showButton1.setOnClickListener(mShowCase1OnClickListener);

    }

    private View.OnClickListener mStopOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mView.setYouKuStop();
        }
    };
    private View.OnClickListener mStartOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mView.setYouKuStart();
        }
    };
    private int mDuration = 2000;
    private View.OnClickListener mSelectDurationtOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (mDuration){
                case 2000:
                    mDuration = 3000;
                    mView1.setDuration(3000);
                    mView1.setYouKuStart();
                    break;
                case 3000:
                    mDuration = 4000;
                    mView2.setDuration(4000);
                    mView2.setYouKuStart();
                    break;
                case 4000 :
                    mView1.setYouKuStop();
                    mView2.setYouKuStop();
                    mDuration = 2000;
                    break;
            }
        }
    };

    private View.OnClickListener mShowCase1OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            YouKuProgressFunction.getInstance().showFunction1(YouKuActivity.this, mView);
        }
    };
    private View.OnClickListener mBackOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            YouKuActivity.this.finish();
        }
    };
}

