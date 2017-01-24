package com.example.androidlongs.popwindowapplication.opengl.point;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by androidlongs on 17/1/20.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class OpenGlPoint {
    //构建点数据
    private float[] mRriangleArr = new float[]
            {
                    0.8f, 0.8f, 0f,
                    -0.8f, 0.8f, 0f,
                    0f, 0f, 0f,
            };
    private final FloatBuffer mVertexBuffer;

    public OpenGlPoint() {
        //将float数组点数据 转保存到Buffer中
        ByteBuffer vbb = ByteBuffer.allocateDirect(mRriangleArr.length * 4);

        vbb.order(ByteOrder.nativeOrder());

        mVertexBuffer = vbb.asFloatBuffer();

        mVertexBuffer.put(mRriangleArr);

        mVertexBuffer.position(0);
    }

    public void draw(GL10 gl) {
        //设置当前绘制颜色
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        //设置绘制点的大小
        gl.glPointSize(18f);
        //初始化操作
        gl.glLoadIdentity();
        //移动变换
        gl.glTranslatef(0, 0, -4);

        //打开顶点
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //设置顶点
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        //绘制顶点
        gl.glDrawArrays(GL10.GL_POINTS, 0, 3);
        //关闭
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }
}
