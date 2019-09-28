package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

public class PicUtil {


    /**
     * 给图片更换颜色
     * @param drawable 图片
     * @param color 颜色
     * @return 修改颜色后的图片
     */
    public static Drawable changePicBackground(Drawable drawable, int color) {
        Drawable wrapDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrapDrawable, color);
        return wrapDrawable;
    }
}
