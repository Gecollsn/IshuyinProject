package com.ishuyin.gecollsn.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

/**
 * @author gecollsn
 * @create 5/14/2016
 * @company www.ishuyin.com
 */
public class BackGroud {
    private static Drawable sysBackgroud;

    public static Drawable getSystemBackgroud() {
        if (sysBackgroud == null) {
            sysBackgroud = new ColorDrawable(Color.parseColor("#ffffff"));
        }

        return sysBackgroud;
    }

    public static void setSystemBackgroud(int resId){

    }
}
