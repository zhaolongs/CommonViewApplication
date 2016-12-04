package com.example.androidlongs.popwindowapplication.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.androidlongs.popwindowapplication.R;

/**
 * Created by androidlongs on 16/12/4.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class DialogFunction {
    private DialogFunction() {
    }

    private static class SingleDialog {
        private static DialogFunction sDialogFunction = new DialogFunction();
    }

    public static DialogFunction getInstance() {
        return SingleDialog.sDialogFunction;
    }


    public void commonDeleteShow(Context context) {
        Dialog alertDialog = new AlertDialog.Builder(context).
                setTitle("确定删除？").
                setMessage("您确定删除该条信息吗？").
                setIcon(R.mipmap.ic_launcher).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).
                create();
        alertDialog.show();
    }
}
