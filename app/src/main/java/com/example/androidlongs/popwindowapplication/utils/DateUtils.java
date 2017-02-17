package com.example.androidlongs.popwindowapplication.utils;

import java.util.Date;
import java.util.Locale;

/**
 * Created by androidlongs on 17/2/17.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class DateUtils {

    public String getYear() {

        Date date = new Date();
        Locale locale = Locale.CHINA;
        return String.format(locale, "%tY", date);

    }

    public String getMonth() {
        Date date = new Date();
        Locale locale = Locale.CHINA;
        return String.format(locale, "%tB", date);

    }

    public String getDay() {

        Date date = new Date();
        Locale locale = Locale.CHINA;

        return String.format(locale, "%td", date);

    }
}
