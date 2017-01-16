package com.example.androidlongs.popwindowapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidlongs.popwindowapplication.base.BaseActivity;
import com.example.androidlongs.popwindowapplication.dialog.DialogFunction;
import com.example.androidlongs.popwindowapplication.path.base8.PathTest8Activity;
import com.example.androidlongs.popwindowapplication.pop.GrayBackgroundPopFunction;

public class PopActivity extends BaseActivity {


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


        dialogDeleteFunctionShow();
    }


    private Button mBackGroundGrayPopFromTopButton;
    private Button mBackGroundGrayPopFromBottomButton;
    private Button mBackGroundGrayPopFromLeftButton;
    private Button mBackGroundGrayPopFromRightButton;
    private Button mAlertDeleteButton;

    private Button mPathBase8Button;

    private void initViewFunction() {
        mBackGroundGrayPopFromTopButton = (Button) findViewById(R.id.bt_pop_from_top);
        mBackGroundGrayPopFromBottomButton = (Button) findViewById(R.id.bt_pop_from_bottom);
        mBackGroundGrayPopFromLeftButton = (Button) findViewById(R.id.bt_pop_from_left);
        mBackGroundGrayPopFromRightButton = (Button) findViewById(R.id.bt_pop_from_right);
        mAlertDeleteButton = (Button) findViewById(R.id.bt_dialog_base);

        mPathBase8Button = (Button) findViewById(R.id.bt_path_view_8);


        mPathBase8Button.setOnClickListener(mView8OnClickListener);
    }

    /***
     * 蒙版Pop
     * 从底部弹出
     */
    private void grayBackgroundPopBottomFunction() {
        mBackGroundGrayPopFromBottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GrayBackgroundPopFunction.getInstance().fromBottomShow(PopActivity.this, mBackGroundGrayPopFromTopButton);
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
                GrayBackgroundPopFunction.getInstance().fromTopShow(PopActivity.this, mBackGroundGrayPopFromTopButton);
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
                GrayBackgroundPopFunction.getInstance().fromRightShow(PopActivity.this, mBackGroundGrayPopFromRightButton);
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
                GrayBackgroundPopFunction.getInstance().fromLeftShow(PopActivity.this, mBackGroundGrayPopFromLeftButton);
            }
        });
    }

    /**
     *
     */
    private void dialogDeleteFunctionShow() {
        mAlertDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFunction.getInstance().commonDeleteShow(PopActivity.this);
            }
        });


    }

    private View.OnClickListener mView8OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PopActivity.this.startActivity(new Intent(PopActivity.this, PathTest8Activity.class));
        }
    };
}
