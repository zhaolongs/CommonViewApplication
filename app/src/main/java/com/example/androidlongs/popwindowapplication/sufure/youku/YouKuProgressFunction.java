package com.example.androidlongs.popwindowapplication.sufure.youku;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.androidlongs.popwindowapplication.R;

/**
 * Created by androidlongs on 17/1/24.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class YouKuProgressFunction {
    private PopupWindow mPopupWindow;

    private Handler mHandler = new Handler();
    private YouKuProgressView mProgressView;
    private View.OnClickListener mCloseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            close();
        }
    };

    private static class SingleYouKu {
        private static YouKuProgressFunction sYouKuProgressFunction = new YouKuProgressFunction();
    }

    public static YouKuProgressFunction getInstance() {
        return SingleYouKu.sYouKuProgressFunction;
    }


    public void showFunction1(Context context, View parentView) {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = View.inflate(context, R.layout.item_youku_pro_1, null);

        mProgressView = (YouKuProgressView) popupWindow_view.findViewById(R.id.id_youku_show_case1);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        mPopupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        //关闭按钮
        Button closeCase1Button = (Button) popupWindow_view.findViewById(R.id.id_youku_close_case1);

        closeCase1Button.setOnClickListener(mCloseClickListener);
        //焦点设置
        mPopupWindow.setFocusable(true);
        //设置点击背景不消失
        mPopupWindow.setOutsideTouchable(true);
        // 设置透明背景
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT));
        //设置动画
        mPopupWindow.setAnimationStyle(R.style.popwin_from_bottom_anim_style);
        //显示
        mPopupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY, 0, 0);


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mProgressView != null) {
                    mProgressView.setYouKuStart();
                }
            }
        }, 600);

    }

    public void close() {
        if (mProgressView != null) {
            mProgressView.setYouKuStop();
        }
        mProgressView.setYouKuCloseListener(new YouKuProgressView.OnYouKuCloseListener() {
            @Override
            public void onClose() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mPopupWindow != null) {
                            mPopupWindow.dismiss();
                            mPopupWindow = null;
                        }
                    }
                }, 400);

            }
        });
    }
}
