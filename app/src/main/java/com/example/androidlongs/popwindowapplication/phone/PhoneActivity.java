package com.example.androidlongs.popwindowapplication.phone;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

import com.example.androidlongs.popwindowapplication.R;
import com.example.androidlongs.popwindowapplication.base.BaseActivity;

/**
 * Created by androidlongs on 17/2/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PhoneActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_main);
        new Thread(){
            @Override
            public void run() {
                super.run();
                //查询联系人
                queryContactsFunction();
            }
        }.start();



    }

    private void queryContactsFunction() {
        //获取 ContentResolver
        ContentResolver contentResolver = this.getContentResolver();
        //查询
        Cursor queryCursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, new String[] {ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME}, null, null, null);
        if (queryCursor != null) {
            //遍历数据
            while (queryCursor.moveToNext()) {
                //获取联系人ID
                int id = queryCursor.getInt(queryCursor.getColumnIndex("_id"));
                Log.i("info", "_id:" + id);
                Log.i("info",
                        "name:" + queryCursor.getString(queryCursor.getColumnIndex("display_name")));
                //获取联系人数据
                Cursor peopleCursor = contentResolver.query(Phone.CONTENT_URI, new String[] {
                                Phone.NUMBER, Phone.TYPE},
                        Phone.CONTACT_ID + "=" + id, null, null);
                // 根据联系人ID查询出联系人的电话号码
                if (peopleCursor != null) {
                    while (peopleCursor.moveToNext()) {
                        int type = peopleCursor.getInt(peopleCursor.getColumnIndex(Phone.TYPE));
                        if (type == Phone.TYPE_HOME) {
                            Log.i("info",
                                    "家庭电话："
                                            + peopleCursor.getString(peopleCursor
                                            .getColumnIndex(Phone.NUMBER)));
                        } else if (type == Phone.TYPE_MOBILE) {
                            Log.i("info",
                                    "手机："
                                            + peopleCursor.getString(peopleCursor
                                            .getColumnIndex(Phone.NUMBER)));
                        }
                    }
                    peopleCursor.close();
                }
                // 根据联系人的ID去查询出联系人的邮箱地址
                Cursor c2 = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, new String[] {
                                ContactsContract.CommonDataKinds.Email.DATA, ContactsContract.CommonDataKinds.Email.TYPE}, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + id,
                        null, null);
                if (c2 != null) {
                    while (c2.moveToNext()) {
                        int type = c2.getInt(c2.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        if (type == ContactsContract.CommonDataKinds.Email.TYPE_WORK) {
                            Log.i("info",
                                    "工作邮箱："
                                            + c2.getString(c2
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                        }
                    }
                    c2.close();
                }
            }
            queryCursor.close();
        }else {
            Log.e("info","无联系人数据");
        }
    }
}
