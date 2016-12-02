package com.example.androidlongs.popwindowapplication.img.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by androidlongs on 16/12/1.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class GlideConfiguration implements GlideModule {

    /**
     * 用来在Glide单例创建之前应用所有的选项配置，
     * 该方法每次实现只会被调用一次。
     * 通过GlideBuilder(Glide的创造者)我们可以对Glide进行各种配置
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //配制加载图片的格式
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        /**
         * MemoryCache和BitmapPool的默认大小由MemorySizeCalculator类决定，
         * MemorySizeCalculator会根据给定屏幕大小可用内存算出合适的缓存大小，这也是推荐的缓存大小
         */
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);

        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();


        /**
         *  Bitmap池用来允许不同尺寸的Bitmap被重用，
         *  这可以显著地减少因为图片解码像素数组分配内存而引发的垃圾回收。
         *  默认情况下Glide使用LruBitmapPool作为Bitmap池，
         *  LruBitmapPool采用LRU算法保存最近使用的尺寸的Bitmap。
         *  我们可以通过它的构造器设置最大缓存内存大小：
         */
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        /**
         * MemoryCache用来把resources 缓存在内存里，以便能马上能拿出来显示。
         * 默认情况下Glide使用LruResourceCache，我们可以通过它的构造器设置最大缓存内存大小：
         */
        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));


        /**
         * 可 设置一个用来存储Resource数据和缩略图的DiskCache实现，不过该设置已过时，不推荐使用
         */
        //缓存目录名字
        String cacheDirectoryName = "glideImage";
        /**
         * 设置一个用来创建DiskCache的工厂。
         * 默认情况下Glide使用InternalCacheDiskCacheFactory内部工厂类创建DiskCache，
         * 缓存目录为程序内部缓存目录/data/data/your_package_name/image_manager_disk_cache/(不能被其它应用访问)且缓存最大为250MB。
         * 当然，可以通过InternalCacheDiskCacheFactory构造器更改缓存的目录和最大缓存大小
         * builder.setDiskCache(new InternalCacheDiskCacheFactory(context,
         cacheDirectoryName, 1024*1024*30));
         *还可以指定缓存到外部磁盘SD卡上：
         *
         */

        /**
         * 存放在data/data/xxxx/cache/
         * 参数一  存放在目录下的名称
         * 参数二  存放目录大小
         */
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheDirectoryName, 1024 * 1024 * 30));
        //存放在外置文件浏览器
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "glide", 1024 * 1024 * 30));

//        builder.setDiskCache(
//                new DiskCache.Factory() {
//                    @Override
//                    public DiskCache build() {
//                        File cacheLocation = getMyCacheLocationBlockingIO();
//                        cacheLocation.mkdirs();
//                        return DiskLruCacheWrapper.get(cacheLocation, 1024 * 1024 * 30);
//                    }
//                });

    }

    /**
     * 用来在Glide单例创建之后但请求发起之前注册组件，该方法每次实现只会被调用一次。通常在该方法中注册ModelLoader。
     */
    @Override
    public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.
        //glide.register(GlideConfiguration.class, InputStream.class,
        //new GlideUrlLoader(context).Factory());
    }
}

