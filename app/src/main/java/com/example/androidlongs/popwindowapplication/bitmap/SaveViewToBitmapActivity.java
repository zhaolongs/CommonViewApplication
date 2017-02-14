package com.example.androidlongs.popwindowapplication.bitmap;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidlongs.popwindowapplication.R;
import com.example.androidlongs.popwindowapplication.base.BaseActivity;

import java.io.FileOutputStream;

/**
 * Created by androidlongs on 17/2/14.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class SaveViewToBitmapActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_save_view_to_bitmap);
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.save_bitmap_header);
        TextView headerTextView = (TextView) headerLayout.findViewById(R.id.header_title);
        ImageView backImageView = (ImageView) headerLayout.findViewById(R.id.header_image);

        TextView textView = (TextView) findViewById(R.id.text);

        headerTextView.setText("Bitmap");
        backImageView.setVisibility(View.VISIBLE);
        backImageView.setOnClickListener(mbackClickListener);

        textView.setOnClickListener(mOnClickListener);


    }

    private View.OnClickListener mbackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SaveViewToBitmapActivity.this.finish();
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            View viewLayout = getLayoutInflater().inflate(R.layout.activity_bitmap_save_view_to_bitmap, null);
            //打开图像缓存
            viewLayout.setDrawingCacheEnabled(true);
            //测量大小
            viewLayout.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            //设置layout
            viewLayout.layout(0, 0, viewLayout.getMeasuredWidth(), viewLayout.getMeasuredHeight());

            try {
                String path = Environment.getExternalStorageDirectory().getPath();
                String fileName = path+"/"+System.currentTimeMillis()+".PNG";
                System.out.println(""+fileName);
                //获取组件截图
                Bitmap bitmap = viewLayout.getDrawingCache();
                //
                FileOutputStream fileOutStream = new FileOutputStream(fileName);
                //保存
                bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutStream);
                //
                fileOutStream.close();
            } catch (Exception e) {
            } finally {

            }
        }
    };
}
