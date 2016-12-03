package com.example.androidlongs.popwindowapplication.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by androidlongs on 16/12/3.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
