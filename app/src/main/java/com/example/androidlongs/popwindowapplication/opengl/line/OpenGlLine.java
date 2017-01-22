package com.example.androidlongs.popwindowapplication.opengl.line;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by androidlongs on 17/1/20.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class OpenGlLine {

    //构建直线
    private float[] mLinesArr = new float[]
            {
                    -0.8f, -0.8f, 0f,//左上角点
                    -0.8f, 0.8f, 0f,//左下角点
                    0.8f, -0.8f, 0f,//右上角点
                    0.8f, 0.8f, 0f//右下角点
            };


    private final FloatBuffer mVertexBuffer;
    public OpenGlLine(){
        //将float数组点数据 转保存到Buffer中
        ByteBuffer vbb = ByteBuffer.allocateDirect(mLinesArr.length * 4);

        vbb.order(ByteOrder.nativeOrder());

        mVertexBuffer = vbb.asFloatBuffer();

        mVertexBuffer.put(mLinesArr);

        mVertexBuffer.position(0);
    }

    public void draw(GL10 gl){
        //初始化
        gl.glLoadIdentity();
        //移动变换
        gl.glTranslatef(0, 0, -4);
        //打开顶点
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        //绘制
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        //设置绘制颜色
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glPointSize(10);
        //绘制
        /**
         * GL_LINES 绘制两条竖直方向的直线
         * GL_LINE_STRIP    绘制两条竖直方向的直线 并通过斜线连接
         * GL_LINE_LOOP     绘制两条竖直方向的直线 并通过两两斜线连接
         */
        //gl.glDrawArrays(GL10.GL_LINES, 0, 4);
        //gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 4);
        gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);

        //关闭
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }
}
