package com.example.androidlongs.popwindowapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidlongs.popwindowapplication.button.AnimationButton;
import com.example.androidlongs.popwindowapplication.path.base8.PathCoreMainActivity;

/**
 * Created by androidlongs on 17/1/16.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class HomeActivity extends Activity {

    private AnimationButton mPopButton;
    private AnimationButton mCoreButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mPopButton = (AnimationButton) findViewById(R.id.pop_home);
        mCoreButton = (AnimationButton) findViewById(R.id.core_home);


        mCoreButton.setOnClickListener(mCoreClickListener);
        mPopButton.setOnClickListener(mPopClickListener);
    }
    //pop主页面
    private View.OnClickListener mPopClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HomeActivity.this.startActivity(new Intent(HomeActivity.this,PopActivity.class));
        }
    };

    //可爱的心形主页面
    private View.OnClickListener mCoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HomeActivity.this.startActivity(new Intent(HomeActivity.this,PathCoreMainActivity.class));
        }
    };
}
