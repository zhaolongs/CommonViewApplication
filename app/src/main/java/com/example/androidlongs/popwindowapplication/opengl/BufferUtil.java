package com.example.androidlongs.popwindowapplication.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by androidlongs on 17/1/20.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class BufferUtil {
    public static FloatBuffer floatBuffer;
    private static IntBuffer sIntBuffer;

    public static FloatBuffer fBuffer(float[] a)
    {
        // 先初始化buffer,数组的长度*4,因为一个float占4个字节
        ByteBuffer mbb=ByteBuffer.allocateDirect(a.length*4);
        // 数组排列用nativeOrder
        mbb.order(ByteOrder.nativeOrder());
        floatBuffer=mbb.asFloatBuffer();
        floatBuffer.put(a);
        floatBuffer.position(0);
        return floatBuffer;
    }

    public static IntBuffer iBuffer(int[] a)
    {
        // 先初始化buffer,数组的长度*4,因为一个float占4个字节
        ByteBuffer mbb=ByteBuffer.allocateDirect(a.length*4);
        // 数组排列用nativeOrder
        mbb.order(ByteOrder.nativeOrder());
        sIntBuffer = mbb.asIntBuffer();
        sIntBuffer.put(a);
        sIntBuffer.position(0);
        return sIntBuffer;
    }
}
