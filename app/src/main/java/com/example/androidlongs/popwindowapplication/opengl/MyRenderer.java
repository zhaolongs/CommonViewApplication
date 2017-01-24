package com.example.androidlongs.popwindowapplication.opengl;

import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by androidlongs on 17/1/20.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class MyRenderer implements GLSurfaceView.Renderer {
    private String TAG = "GLRender";
    float roateTri;//用于三角形的角度
    float roateQuad;//用于四边形的角度
    int one = 0x10000;
    /*//三角形三个顶点
    private IntBuffer triggerBuffer = IntBuffer.wrap(new int[]{
        0,one,0,
        -one,-one,0,
        one,-one,0,
    });
    //四边形四个顶点
    private IntBuffer quaterBuffer = IntBuffer.wrap(new int[]{
            -one,one,0,
            one,one,0,
            one,-one,0,
            -one,-one,0,
    });*/
    int[] colorArray = {
            one, 0, 0, one,
            0, one, 0, one,
            0, 0, one, one,
    };
    float[] triggerArray = {
            100f, 100f, 0.0f, // 上顶点
            50f, 50f, 0.0f, // 左顶点
            150f, 150f, 0.0f // 右顶点
    };


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // 关闭抗抖动
        gl.glDisable(GL10.GL_DITHER);
        // 设置系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0, 0, 0, 0);
        // 设置阴影平滑模式
        gl.glShadeModel(GL10.GL_SMOOTH);
        // 启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // 设置深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // TODO Auto-generated method stub
        Log.i(TAG, "onSurfaceChanged width:" + width + " height:" + height);//1920 944

        // 设置3D视窗的大小及位置
        gl.glViewport(0, 0, width, height);
        /**
         *将当前矩阵模式设为投影矩阵
         * GL_PROJECTION，是投影的意思，就是要对投影相关进行操作，也就是把物体投影到一个平面上，就像我们照相一样，把3维物体投到2维的平面上。这样，接下来的语句可以是跟透视相关的函数，比如glFrustum()或gluPerspective()；
         *GL_MODELVIEW，是对模型视景的操作，接下来的语句描绘一个以模型为基础的适应，这样来设置参数，接下来用到的就是像gluLookAt()这样的函数；
         *GL_TEXTURE，就是对纹理相关进行操作；
         **/


        gl.glMatrixMode(GL10.GL_PROJECTION);
        // 初始化单位矩阵
        gl.glLoadIdentity();
        // 计算透视视窗的宽度、高度比
        float ratio = (float) width / height;
        //Surface和坐标系之间的映射关系
        // 调用此方法设置透视视窗的空间大小。     前四个参数去顶窗口的大小，分别是左，右，下，上，后两个参数分别是在场景中所能绘制深度的起点和终点
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);


    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // TODO Auto-generated method stub
        // 清除屏幕缓存和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // 启用顶点座标数据
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // 启用顶点颜色数据
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        // 设置当前矩阵堆栈为模型堆栈，
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // --------------------绘制第一个图形---------------------
        // 重置当前的模型视图矩阵
        gl.glLoadIdentity();
        gl.glTranslatef(-0.32f, 0.35f, -1f);
        /**
         * 设置顶点的位置数据
         * 第一个参数表示的是每个点有几个坐标
         */
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferUtil(triggerArray));
        // 设置顶点的颜色数据
        gl.glColorPointer(4, GL10.GL_FIXED, 0, bufferUtil(colorArray));
        /**根据顶点数据绘制平面图形
         * 第一个参数指明了画图的类型——三角形（android 似乎只支持画三角形、点、线，不支持画多边形）。
         * 后面两个参数指明，从哪个顶点开始画，画多少个顶点
         */
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        // 绘制结束
        gl.glFinish();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    /*
     * OpenGL 是一个非常底层的画图接口，它所使用的缓冲区存储结构是和我们的 java 程序中不相同的。
     * Java 是大端字节序(BigEdian)，而 OpenGL 所需要的数据是小端字节序(LittleEdian)。
     * 所以，我们在将 Java 的缓冲区转化为 OpenGL 可用的缓冲区时需要作一些工作。建立buff的方法如下
     * */
    public Buffer bufferUtil(int[] arr) {
        IntBuffer mBuffer;

        //先初始化buffer,数组的长度*4,因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        //数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());

        mBuffer = qbb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);

        return mBuffer;
    }

    public Buffer bufferUtil(float[] arr) {
        FloatBuffer mBuffer;

        //先初始化buffer,数组的长度*4,因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        //数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());

        mBuffer = qbb.asFloatBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);

        return mBuffer;
    }
}
