package com.ishuyin.gecollsn.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

/**
 * @author gecollsn
 * @create 5/14/2016
 * @company www.ishuyin.com
 */
public class BackGround {
    private static Drawable sysBackground;

    public static Drawable getSystemBackground() {
        if (sysBackground == null) {
            sysBackground = new ColorDrawable(Color.parseColor("#ffffff"));
        }

        return sysBackground;
    }

    /**
     *
     *
     * @param resId
     */
    public static void setSystemBackgroud(int resId){

    }
}
