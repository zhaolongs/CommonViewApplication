package com.example.androidlongs.popwindowapplication.opengl.point;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by androidlongs on 17/1/20.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PointRenderer implements GLSurfaceView.Renderer {


    private OpenGlPoint mPoint;

    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
// Set the background color to black ( rgba ).
        //设置背景颜色
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);  // OpenGL docs.
        /**
         * 设置Model
         */
        gl.glShadeModel(GL10.GL_SMOOTH);// OpenGL docs.
        /**
         * 设置深度测试
         */
        gl.glClearDepthf(1.0f);// OpenGL docs.

        gl.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.

        gl.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
        // 设置系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, // OpenGL docs.
                GL10.GL_NICEST);

        mPoint = new OpenGlPoint();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);// OpenGL docs.
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);// OpenGL docs.
        // Reset the projection matrix
        gl.glLoadIdentity();// OpenGL docs.
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);// OpenGL docs.
        // Reset the modelview matrix
        gl.glLoadIdentity();// OpenGL docs.
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);


        mPoint.draw(gl);
    }
}
