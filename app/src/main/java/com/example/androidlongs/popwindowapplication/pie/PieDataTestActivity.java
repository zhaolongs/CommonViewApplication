package com.example.androidlongs.popwindowapplication.pie;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.example.androidlongs.popwindowapplication.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PieDataTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_test);

        PieView pieView = (PieView) findViewById(R.id.pieview);
        PieView pieView2 = (PieView) findViewById(R.id.pieview2);

        ArrayList<PieData> list = new ArrayList<>();
        int totalNumber = 0;

        for (int i=0;i<6;i++){
            PieData pieData = new PieData();
            pieData.uuid = UUID.randomUUID().toString();
            pieData.name = "test"+i;
            pieData.color = Color.rgb((int) Math.floor(Math.random() * 255 + 1), (int) Math.floor(Math.random() * 255 + 1), (int) Math.floor(Math.random() * 255 + 1));

            pieData.number = (int) Math.floor(Math.random()*60+1);
            totalNumber+=pieData.number;
            list.add(pieData);
        }

        for (int i = 0; i < list.size(); i++) {
            PieData pieData = list.get(i);
            pieData.total = totalNumber;
        }

        pieView.setPieDataList(list);







        ArrayList<PieData> list2 = new ArrayList<>();
        int totalNumber2 = 0;

        for (int i=0;i<10;i++){
            PieData pieData = new PieData();
            pieData.uuid = UUID.randomUUID().toString();
            pieData.name = "test"+i;
            pieData.number = (int) Math.floor(Math.random()*90+1);
            //绘制随机颜色
            pieData.color = Color.rgb((int) Math.floor(Math.random() * 255 + 1), (int) Math.floor(Math.random() * 255 + 1), (int) Math.floor(Math.random() * 255 + 1));

            totalNumber2+=pieData.number;
            list2.add(pieData);
        }

        for (int i = 0; i < list2.size(); i++) {
            PieData pieData = list2.get(i);
            pieData.total = totalNumber2;
        }

        pieView2.setPieDataList(list2);
    }
}
