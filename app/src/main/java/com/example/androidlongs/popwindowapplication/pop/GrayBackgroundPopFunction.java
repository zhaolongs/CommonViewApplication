package com.example.androidlongs.popwindowapplication.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.androidlongs.popwindowapplication.R;

/**
 * Created by androidlongs on 16/11/29.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class GrayBackgroundPopFunction {

    private PopupWindow mPopupWindow;

    private GrayBackgroundPopFunction() {

    }

    private static class SingPopFunction {
        private static GrayBackgroundPopFunction sGrayBackgroundPopFunction = new GrayBackgroundPopFunction();
    }

    public static GrayBackgroundPopFunction getInstance() {
        return SingPopFunction.sGrayBackgroundPopFunction;
    }

    public void show(Context context, View view) {

        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = View.inflate(context, R.layout.pop_progress_loading, null);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        mPopupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);


        LinearLayout popContentLinearLayout = (LinearLayout) popupWindow_view.findViewById(R.id.ll_pop_progress_content);
        //焦点设置
        mPopupWindow.setFocusable(true);
        //设置点击背景不消失
        mPopupWindow.setOutsideTouchable(true);
        // 设置透明背景
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT));
        //设置动画
        mPopupWindow.setAnimationStyle(R.style.popwin_chat_clini_anim_style);
        //显示
        mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);

        //设置控件动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        AnimationSet animationSet = new AnimationSet(true);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);

        animationSet.setInterpolator(new OvershootInterpolator());
        animationSet.setDuration(300);
        animationSet.setStartOffset(270);

        popContentLinearLayout.setAnimation(animationSet);
        popContentLinearLayout.setAnimation(animationSet);


    }
    public void close(){
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow =null;
        }
    }
}
