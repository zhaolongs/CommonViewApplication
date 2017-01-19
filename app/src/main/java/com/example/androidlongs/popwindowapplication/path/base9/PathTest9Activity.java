package com.example.androidlongs.popwindowapplication.path.base9;

import android.app.Activity;
import android.os.Bundle;

import com.example.androidlongs.popwindowapplication.R;
import com.example.androidlongs.popwindowapplication.utils.LogUtils;

/**
 * Created by androidlongs on 17/1/11.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PathTest9Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_9);


        CirCleSwitchButton cirCleSwitchButton = (CirCleSwitchButton) findViewById(R.id.switch_button);
        cirCleSwitchButton.setSwitchButtonLiserner(mLiserner);
    }

    private CirCleSwitchButton.OnSwitchButtonStatueLiserner mLiserner = new CirCleSwitchButton.OnSwitchButtonStatueLiserner() {
        @Override
        public void onClose() {
            LogUtils.d(" switch close ");
        }

        @Override
        public void onOpen() {
            LogUtils.d(" switch open ");
        }
    };
}
