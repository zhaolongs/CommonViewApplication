package com.example.androidlongs.popwindowapplication;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidlongs.popwindowapplication.base.BaseActivity;
import com.example.androidlongs.popwindowapplication.pop.GrayBackgroundPopFunction;

public class MainActivity extends BaseActivity {


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewFunction();

        grayBackgroundPopBottomFunction();
        popFromTopShowFunction();
        popFromRightShowFunction();
        popFromLeftShowFunction();


    }

    private Button mBackGroundGrayPopFromTopButton;
    private Button mBackGroundGrayPopFromBottomButton;
    private Button mBackGroundGrayPopFromLeftButton;
    private Button mBackGroundGrayPopFromRightButton;

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
        mBackGroundGrayPopFromBottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GrayBackgroundPopFunction.getInstance().fromBottomShow(MainActivity.this, mBackGroundGrayPopFromTopButton);
            }
        });
    }

    /**
     * 从顶部弹出来
     */
    private void popFromTopShowFunction() {
        mBackGroundGrayPopFromTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GrayBackgroundPopFunction.getInstance().fromTopShow(MainActivity.this, mBackGroundGrayPopFromTopButton);
            }
        });
    }

    /**
     * 从右边弹出来
     */
    private void popFromRightShowFunction() {
        mBackGroundGrayPopFromRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GrayBackgroundPopFunction.getInstance().fromRightShow(MainActivity.this, mBackGroundGrayPopFromRightButton);
            }
        });
    }

    /**
     * 从左边弹出来
     */
    private void popFromLeftShowFunction() {
        mBackGroundGrayPopFromLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GrayBackgroundPopFunction.getInstance().fromLeftShow(MainActivity.this, mBackGroundGrayPopFromLeftButton);
            }
        });
    }


}
