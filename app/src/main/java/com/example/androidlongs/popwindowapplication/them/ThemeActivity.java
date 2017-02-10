package com.example.androidlongs.popwindowapplication.them;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.widget.TextView;

import com.example.androidlongs.popwindowapplication.R;
import com.example.androidlongs.popwindowapplication.base.BaseActivity;

import java.util.List;

/**
 * Created by androidlongs on 17/2/10.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class ThemeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_theme);

        final TextView view1 = (TextView) findViewById(R.id.view1);
        final TextView view2 = (TextView) findViewById(R.id.view2);
        final TextView view3 = (TextView) findViewById(R.id.view3);
        final TextView view4 = (TextView) findViewById(R.id.view4);
        final TextView view5 = (TextView) findViewById(R.id.view5);
        final TextView view6 = (TextView) findViewById(R.id.view6);


        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher);
        //同步获取，需要在子线程中使用
        Palette palette = Palette.from(drawable.getBitmap()).generate();
        //异步获取，可以在主线程中使用
        Palette.from(drawable.getBitmap()).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

                //获取所有的颜色
                List<Palette.Swatch> swatches = palette.getSwatches();
                // 提取完毕
                // 有活力的颜色
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                // 有活力的暗色
                Palette.Swatch darkVibrant = palette.getDarkVibrantSwatch();
                // 有活力的亮色
                Palette.Swatch lightVibrant = palette.getLightVibrantSwatch();
                // 柔和的颜色
                Palette.Swatch muted = palette.getMutedSwatch();
                // 柔和的暗色
                Palette.Swatch darkMuted = palette.getDarkMutedSwatch();
                // 柔和的亮色
                Palette.Swatch lightMuted = palette.getLightMutedSwatch();

                //样本中的像素数量
                int population = vibrant.getPopulation();
                // 颜色的RBG值
                int rgb = vibrant.getRgb();
                // 颜色的HSL值
                float[] hsl = vibrant.getHsl();
                // 主体文字的颜色值
                int bodyTextColor = vibrant.getBodyTextColor();
                //标题文字的颜色值
                int titleTextColor = vibrant.getTitleTextColor();


                if (vibrant == null) {
                    view1.setText("无有活力的颜色");
                } else {
                    view1.setText("有活力的颜色");
                    view1.setBackgroundColor(vibrant.getRgb());
                }

                if (darkVibrant == null) {
                    view2.setText("无有活力的暗色");
                } else {
                    view2.setText("有有活力的暗色");
                    view2.setBackgroundColor(darkVibrant.getRgb());
                }
                if (lightVibrant == null) {
                    view3.setText("无有活力的亮色");
                } else {
                    view3.setText("有有活力的亮色");
                    view3.setBackgroundColor(lightVibrant.getRgb());
                }

                if (muted == null) {
                    view4.setText("无柔和的颜色");
                } else {
                    view4.setText("有柔和的颜色色");
                    view4.setBackgroundColor(muted.getRgb());
                }

                if (darkMuted == null) {
                    view5.setText("无柔和的暗色");
                } else {
                    view5.setText("有柔和的暗色");
                    view5.setBackgroundColor(darkMuted.getRgb());
                }

                if (lightMuted == null) {
                    view6.setText("无柔和的亮色");
                } else {
                    view6.setText("柔和的亮色");
                    view6.setBackgroundColor(muted.getRgb());
                }


                System.out.println("vibrant " + vibrant + "   darkVibrant   " + darkVibrant + "  lightVibrant  " + lightVibrant + "  muted  " + muted + "  darkMuted  " + darkMuted + "");
                //view1.setBackgroundColor(vibrant.getTitleTextColor());
//                view2.setBackgroundColor(vibrantSwatch.getRgb());
//                view3.setBackgroundColor(vibrantSwatch.getHsl());
//                view4.setBackgroundColor(vibrantSwatch.getPopulation());
            }
        });
    }

}
