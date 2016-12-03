package com.example.androidlongs.popwindowapplication;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.androidlongs.popwindowapplication.pop.GrayBackgroundPopFunction;

public class MainActivity extends AppCompatActivity {

    private Button mBackGroundGrayPopFromTopButton;
    private Button mBackGroundGrayPopFromBottomButton;
    private Button mBackGroundGrayPopFromLeftButton;
    private Button mBackGroundGrayPopFromRightButton;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewFunction();

        grayBackgroundPopBottomFunction();
    }

    private void initViewFunction() {
        mBackGroundGrayPopFromTopButton = (Button) findViewById(R.id.bt_pop_from_top);
        mBackGroundGrayPopFromBottomButton = (Button) findViewById(R.id.bt_pop_from_bottom);
        mBackGroundGrayPopFromLeftButton = (Button) findViewById(R.id.bt_pop_from_left);
        mBackGroundGrayPopFromRightButton = (Button) findViewById(R.id.bt_pop_from_right);
    }

    /***
     * 蒙版Pop
     * 从底部弹出
     */
    private void grayBackgroundPopBottomFunction() {
        mBackGroundGrayPopFromTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GrayBackgroundPopFunction.getInstance().show(MainActivity.this, mBackGroundGrayPopFromTopButton);
            }
        });
    }


}
