package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.math.BigDecimal;

/**
 * @author lihao
 * @date 2019-06-27 13:52
 * @description :最小宽度限定符适配
 */
public class smallestWidthUtils {
    /**
     *
     * @param dpValue  设计图上面的控件尺寸，单位为dp
     * @param designWidth 设计图的尺寸,单位为dp
     * @return
     */
    public static float getFinDp(float dpValue, int designWidth, WindowManager manager) {
        //在当前设备上面的展示的尺寸
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int width = Math.min(dm.widthPixels,dm.heightPixels);
        float sw= (float) (width/(dm.densityDpi/160.0));
        float DpValue =  (dpValue/(float)designWidth) * sw;
        BigDecimal bigDecimal = new BigDecimal(DpValue);
        float finDp = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return finDp;
    }

}
