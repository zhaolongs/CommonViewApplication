package com.example.androidlongs.popwindowapplication;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        function1();
    }

    /***
     * 蒙版Pop
     */
    private void function1() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop1();
            }
        });
    }

    private void showPop1() {
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.pop_progress_loading, null,
                false);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        PopupWindow popupWindow = new PopupWindow(popupWindow_view,  ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);


        LinearLayout popContentLinearLayout =(LinearLayout)popupWindow_view.findViewById(R.id.ll_pop_progress_content);
        popupWindow.setFocusable(true);

        popupWindow.setOutsideTouchable(true);
        // 设置透明背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT));
        popupWindow.setAnimationStyle(R.style.popwin_chat_clini_anim_style);

        popupWindow.showAtLocation(button1, Gravity.NO_GRAVITY, 0, 0);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0f,1f,1f,1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f) ;

        AnimationSet animationSet = new AnimationSet(true);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);

        animationSet.setInterpolator(new OvershootInterpolator());
        animationSet.setDuration(300);
        animationSet.setStartOffset(270);

        popContentLinearLayout.setAnimation(animationSet);
        popContentLinearLayout.setAnimation(animationSet);


    }
}
