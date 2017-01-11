package com.example.androidlongs.popwindowapplication.path.base2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.androidlongs.popwindowapplication.R;
import com.example.androidlongs.popwindowapplication.button.AnimationButton;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PathTest2Activity extends Activity {
    private boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_2);
        AnimationButton button = (AnimationButton) findViewById(R.id.button);
        final PathBase2View pathBase2View = (PathBase2View) findViewById(R.id.pathBase2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=!flag;
                pathBase2View.setMode(flag);
            }
        });
    }
}
