package com.example.androidlongs.popwindowapplication.path.base9;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.example.androidlongs.popwindowapplication.R;
import com.example.androidlongs.popwindowapplication.opengl.line.LineRenderer;
import com.example.androidlongs.popwindowapplication.opengl.point.PointRenderer;
import com.example.androidlongs.popwindowapplication.opengl.square.SquareRenderer;
import com.example.androidlongs.popwindowapplication.opengl.triangle.TriangleRenderer;
import com.example.androidlongs.popwindowapplication.utils.LogUtils;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PathTest9Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_9);


//        CirCleSwitchButton cirCleSwitchButton = (CirCleSwitchButton) findViewById(R.id.switch_button);
//        cirCleSwitchButton.setSwitchButtonLiserner(mLiserner);


        // 创建一个GLSurfaceView，用于显示OpenGL绘制的图形
        GLSurfaceView glView = new GLSurfaceView(this);
        // 创建GLSurfaceView的内容绘制器
        SquareRenderer myRender = new SquareRenderer();
        PointRenderer pointRenderer = new PointRenderer();
        LineRenderer lineRenderer = new LineRenderer();
        TriangleRenderer triangleRenderer = new TriangleRenderer();
        // 为GLSurfaceView设置绘制器
        glView.setRenderer(triangleRenderer);


        //YouKuProgressView youKuProgressView = new YouKuProgressView(this);


        //setContentView(youKuProgressView);
    }

    private CirCleSwitchButton.OnSwitchButtonStatueLiserner mLiserner = new CirCleSwitchButton.OnSwitchButtonStatueLiserner() {
        @Override
        public void onClose() {
            LogUtils.d(" switch close ");
        }

        @Override
        public void onOpen() {
            LogUtils.d(" switch open ");
        }
    };
}
