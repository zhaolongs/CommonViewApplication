# CommonViewApplication
1、android 中常用控件集合 
2、初次更新一个常用的Popwindow,





##android 弹框 popWindow

![pop 从顶部弹出来](https://github.com/zhaolongs/CommonViewApplication/blob/master/picture/pop/pop_from_top.gif?raw=true)
![pop 从底部弹出来](https://github.com/zhaolongs/CommonViewApplication/blob/master/picture/pop/pop_from_bottom.gif?raw=true)
![pop 从左部弹出来](https://github.com/zhaolongs/CommonViewApplication/blob/master/picture/pop/pop_from_left.gif?raw=true)
![pop 从右部弹出来](https://github.com/zhaolongs/CommonViewApplication/blob/master/picture/pop/pop_from_right.gif?raw=true)

本demo 中主要说明了为POPwindow为基准来弹出弹出框的方式，并为其设置动画

###为popwindow设置动画的方式
  ####设置动画
    int  sytle = R.style.popwin_from_bottom_anim_style;

    mPopupWindow.setAnimationStyle(sytle);

    //动画样式 sytle
    <style name="popwin_from_bottom_anim_style">
            <item name="android:windowEnterAnimation">@anim/pop_from_bottom_in</item>
            <item name="android:windowExitAnimation">@anim/pop_from_bottom_out</item>
    </style>

    在这其中，windowEnterAnimation 是弹框进入页面时设置的动画 ，windowExitAnimation 是弹框退出页面时设置的动画
    其中pop_from_bottom_in 与 pop_from_bottom_out分别是动画资源文件

  ####动画资源文件 pop_from_bottom_in.xml

    <?xml version="1.0" encoding="utf-8"?>
    <set xmlns:android="http://schemas.android.com/apk/res/android" >

        <translate
            android:fromXDelta="0"
            android:fromYDelta="100%"
            android:toXDelta="0"
            android:duration="300"
            android:toYDelta="0" >
        </translate>
        <alpha
            android:duration="300"
            android:fromAlpha="0.2"
            android:toAlpha="1.0"/>
    </set>

  ####动画资源文件 pop_from_bottom_out.xml
    <?xml version="1.0" encoding="utf-8"?>
    <set xmlns:android="http://schemas.android.com/apk/res/android">

        <translate
            android:duration="300"
            android:fromXDelta="0"
            android:fromYDelta="0"
            android:toXDelta="0"
            android:toYDelta="100%">
        </translate>
        <alpha
            android:duration="300"
            android:fromAlpha="1.0"
            android:toAlpha="0.0"/>
    </set>

  #### 动画说明
  通过 set组全标签，组合平移动画与透明度动画



