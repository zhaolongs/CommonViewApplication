package com.example.androidlongs.popwindowapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidlongs.popwindowapplication.bitmap.SaveViewToBitmapActivity;
import com.example.androidlongs.popwindowapplication.button.AnimationButton;
import com.example.androidlongs.popwindowapplication.path.base8.PathCoreMainActivity;
import com.example.androidlongs.popwindowapplication.phone.PhoneActivity;
import com.example.androidlongs.popwindowapplication.sufure.youku.YouKuActivity;
import com.example.androidlongs.popwindowapplication.them.ThemeActivity;

/**
 * Created by androidlongs on 17/1/16.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class HomeActivity extends Activity {

    private AnimationButton mPopButton;
    private AnimationButton mCoreButton;
    private AnimationButton mYouKuButton;
    private AnimationButton mThemeButton;
    private AnimationButton mPhoneButton;
    private AnimationButton mViewSaveBitmapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mPopButton = (AnimationButton) findViewById(R.id.pop_home);
        mCoreButton = (AnimationButton) findViewById(R.id.core_home);

        mYouKuButton = (AnimationButton) findViewById(R.id.youku_home);

        mThemeButton = (AnimationButton) findViewById(R.id.them_home);
        mPhoneButton = (AnimationButton) findViewById(R.id.phone_home);

        mViewSaveBitmapButton = (AnimationButton) findViewById(R.id.view_save_to_bitmap_home);


        mCoreButton.setOnClickListener(mCoreClickListener);
        mPopButton.setOnClickListener(mPopClickListener);
        mYouKuButton.setOnClickListener(mYouKuClickListener);
        mThemeButton.setOnClickListener(mThemeClickListener);
        mPhoneButton.setOnClickListener(mPhoneClickListener);
        mViewSaveBitmapButton.setOnClickListener(mViewSaveToBitmapClickListener);



    }

    //pop主页面
    private View.OnClickListener mPopClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HomeActivity.this.startActivity(new Intent(HomeActivity.this, PopActivity.class));
        }
    };

    //可爱的心形主页面
    private View.OnClickListener mCoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HomeActivity.this.startActivity(new Intent(HomeActivity.this, PathCoreMainActivity.class));
        }
    };


    //可爱的心形主页面
    private View.OnClickListener mYouKuClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HomeActivity.this.startActivity(new Intent(HomeActivity.this, YouKuActivity.class));
        }
    };

    //Theme
    private View.OnClickListener mThemeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HomeActivity.this.startActivity(new Intent(HomeActivity.this, ThemeActivity.class));
        }
    };
    private View.OnClickListener mPhoneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HomeActivity.this.startActivity(new Intent(HomeActivity.this, PhoneActivity.class));
        }
    };
    private View.OnClickListener mViewSaveToBitmapClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HomeActivity.this.startActivity(new Intent(HomeActivity.this, SaveViewToBitmapActivity.class));
        }
    };
}
