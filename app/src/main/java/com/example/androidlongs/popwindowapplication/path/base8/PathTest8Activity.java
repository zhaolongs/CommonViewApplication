package com.example.androidlongs.popwindowapplication.path.base8;

import android.app.Activity;
import android.os.Bundle;

import com.example.androidlongs.popwindowapplication.R;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PathTest8Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_8);

        PathCoreView view = (PathCoreView) findViewById(R.id.pathBase8);

        view.setRoteSpeed(PathCoreView.ROTE_SPEED.FAST_SPEED);
    }
}
