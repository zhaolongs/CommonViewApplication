package com.example.androidlongs.popwindowapplication.path.base6;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.androidlongs.popwindowapplication.R;
import com.example.androidlongs.popwindowapplication.path.common.FindPathView;

/**
 * Created by androidlongs on 17/1/13.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PathFindTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_find);
        FindPathView pathView1 = (FindPathView) findViewById(R.id.anmation_restart);
        FindPathView pathView2 = (FindPathView) findViewById(R.id.anmation_reserve);

        String pathView1Tpye = pathView1.getLevelType();
        String pathView2Tpye = pathView2.getLevelType();

        Log.d("pathView1Tpye","1 : "+pathView1Tpye+"  2 : "+pathView2Tpye);
    }
}
