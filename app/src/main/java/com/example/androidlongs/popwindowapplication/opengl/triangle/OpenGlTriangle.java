package com.example.androidlongs.popwindowapplication.opengl.triangle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by androidlongs on 17/1/20.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class OpenGlTriangle {
    //单一三角形

    private float[] mOneTriangleArr = new float[]
            {
                    0f, 0.8f, 0f,
                    -0.4f, 0, 0,
                    0.4f, 0f, 0f
            };
    //多个三角形
    private float[] mTwoTriangleArr = new float[]
            {
                    -0.8f, -0.4f * 1.732f, 0.0f,

                    0.0f, -0.4f * 1.732f, 0.0f,

                    -0.4f, 0.4f * 1.732f, 0.0f,


                    0.0f, -0.0f * 1.732f, 0.0f,

                    0.8f, -0.0f * 1.732f, 0.0f,

                    0.4f, 0.4f * 1.732f, 0.0f,
            };
    private final FloatBuffer mOnceFloatBuffer;
    private final ByteBuffer mVbb;

    public OpenGlTriangle() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(mOneTriangleArr.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        mOnceFloatBuffer = byteBuffer.asFloatBuffer();
        mOnceFloatBuffer.put(mOneTriangleArr);
        mOnceFloatBuffer.position(0);


        mVbb = ByteBuffer.allocateDirect(mTwoTriangleArr.length * 4);
        mVbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertex = mVbb.asFloatBuffer();
        vertex.put(mTwoTriangleArr);
        vertex.position(0);

    }

    //绘制
    public void drawOnceTriangle(GL10 gl) {


        //初始化
        gl.glLoadIdentity();
        //移动
        gl.glTranslatef(0, 0, -4);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mOnceFloatBuffer);

        //设置颜色
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        //绘制
        //gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);
        //gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,3);
        //
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        //关闭
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    //绘制
    public void drawTwoTriangle(GL10 gl) {


        gl.glLoadIdentity();

        gl.glTranslatef(0, 0, -4);


        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);



        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVbb);
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    }
