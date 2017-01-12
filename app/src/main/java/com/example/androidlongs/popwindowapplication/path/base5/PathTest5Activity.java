package com.example.androidlongs.popwindowapplication.path.base5;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.example.androidlongs.popwindowapplication.R;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PathTest5Activity extends Activity {

    private PathBase5View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_path_5);

        mView = (PathBase5View) findViewById(R.id.pathBase5);
       mHandler.post(mRunnable);
    }

    private Handler mHandler = new Handler();
    private  Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mView.update();
            mHandler.postDelayed(mRunnable,100);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(0);
    }
}
