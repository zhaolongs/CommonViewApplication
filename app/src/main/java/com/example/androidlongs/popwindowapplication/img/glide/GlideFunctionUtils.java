package com.example.androidlongs.popwindowapplication.img.glide;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.androidlongs.popwindowapplication.R;

import java.io.File;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by androidlongs on 16/12/1.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class GlideFunctionUtils {

    /**
     * 使用单例模式来调用工具类方法
     */
    private GlideFunctionUtils() {
    }

    private static class SingleGlideFunction {
        private static GlideFunctionUtils glideFunctionUtils = new GlideFunctionUtils();
    }

    public static GlideFunctionUtils getInstance() {
        return SingleGlideFunction.glideFunctionUtils;
    }

    /**
     * 初始化操作
     */
    private void initFunction() {

    }


    /**
     * 基本加载图片
     */
    private void baseLoadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    /**
     * 基本加载图片
     *
     * @param url    图片地址
     * @param height 加载图片的高度
     * @param width  加载图片的宽度
     */
    private void baseLoadImage(Context context, String url, int width, int height, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .override(width, height)
                .into(imageView);
    }
    /**
     * 基本加载图片
     * 设置缓存模式
     * @param url    图片地址
     */
    private void baseLoadAndCacheImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载SD卡目录上的图片
     */
    public void loadSdcardFile(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load("file://" + path)
                .into(imageView);
    }

    /**
     * 加载asset目录下的图片
     */
    public void loadAssetFile(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load("file:///android_asset/" + path)
                .into(imageView);
    }

    /**
     * 加载 raw目录下的图片
     */
    public void loadRawFile(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load("Android.resource://com.frank.glide/raw/" + path)
                .into(imageView);
    }

    /**
     * 加载 raw目录下的图片
     */
    public void loadRawFile(Context context, int path, ImageView imageView) {
        Glide.with(context)
                .load("Android.resource://com.frank.glide/raw/" + path)
                .into(imageView);
    }

    /**
     * 加载 drawable 目录下的图片
     *
     * @param path String 类型路径
     */
    public void loadDrawableFile(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load("android.resource://com.frank.glide/drawable/" + path)
                .into(imageView);
    }

    /**
     * 加载drawable目录下图片
     *
     * @param path int 类型路径 例如 资源ID
     */
    public void loadDrawableFile(Context context, int path, ImageView imageView) {
        Glide.with(context)
                .load("android.resource://com.frank.glide/drawable/" + path)
                .into(imageView);
    }

    /**
     * 加载 File 类型文件
     *
     * @param file 文件
     */
    public void loadFile(Context context, File file, ImageView imageView) {
        Glide.with(context)
                .load(file)
                .into(imageView);
    }

    /**
     * 加载 Uri 类型文件
     *
     * @param uri 文件
     */
    public void loadUriFile(Context context, Uri uri, ImageView imageView) {
        Glide.with(context)
                .load(uri)
                .into(imageView);
    }

    /**
     * 加载 URL 类型文件
     *
     * @param url 文件
     */
    public void loadURLFile(Context context, URL url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    /**
     * 加载 Byte数组 类型文件
     *
     * @param bytes 文件
     */
    public void loadURLFile(Context context, byte[] bytes, ImageView imageView) {
        Glide.with(context)
                .load(bytes)
                .into(imageView);
    }


    /**
     * 加载图片 并设置加载略缩图
     * 先显示的 略缩图为原图的 1/10
     */
    public void loadImageAndOriginFunction(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .into(imageView);
    }

    /**
     * 加载图片 并设置加载略缩图
     * 先显示的 略缩图为指定的 显示的图片
     */
    public void loadImageAndDefaultFunction(Context context, String url, ImageView imageView) {

        DrawableRequestBuilder<Integer> builder = Glide
                .with(context)
                .load(R.mipmap.defaul_image);

        Glide.with(context)
                .load(url)
                .thumbnail(builder)
                .into(imageView);
    }

    /**
     * 加载图片 并设置加载略缩图
     * 先显示的 略缩图为指定的 显示的图片
     * 高优先加载顺序
     */
    public void loadImageAndHighPriority(Context context, String url, ImageView imageView) {

        DrawableRequestBuilder<Integer> builder = Glide
                .with(context)
                .load(R.mipmap.defaul_image);

        Glide.with(context)
                .load(url)
                .thumbnail(builder)
                .priority(Priority.HIGH)
                .into(imageView);
    }


    /**
     * 加载图片 并设置加载略缩图
     * 先显示的 略缩图为指定的 显示的图片
     * 低优先加载顺序
     */
    public void loadImageAndLowPriority(Context context, String url, ImageView imageView) {

        DrawableRequestBuilder<Integer> builder = Glide
                .with(context)
                .load(R.mipmap.defaul_image);

        Glide.with(context)
                .load(url)
                .thumbnail(builder)
                .priority(Priority.LOW)
                .into(imageView);
    }


    /**
     * 加载图片 结合使用弱引用方式
     * 默认情况下，Glide会根据with()使用的Activity或Fragment的生命周期自动调整资源请求以及资源回收。
     * 但是如果有很占内存的Fragment或Activity不销毁而仅仅是隐藏视图，那么这些图片资源就没办法及时回收，即使是GC的时候
     */
    public void loadWeakImage(Context context, String url, ImageView imageView) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imageView);
        ImageView target = imageViewWeakReference.get();
        if (target != null) {
            Glide.with(context)
                    .load(url)
                    .into(target);
        }
    }


    /**
     * 清除内存缓存
     */
    public void clerMemory(Context context) {
        // 必须在UI线程中调用
        Glide.get(context).clearMemory();
    }

    /**
     * 清除磁盘缓存
     */
    public void clerDisCache(Context context) {
        // 必须在后台线程中调用
        //Glide.get(context.getApplicationContext()).clearDiskCache();
    }
}
