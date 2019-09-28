package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.content.Context;

/**
 * @author : linjian
 * @date : 2019-01-16 16:17
 * @description :单位换算工具类
 */
public class UnitUtil {

    //        基本概述                        分类                   drawable大小
    //                                   ldpi:  0.75（低）               36*36
    //    px = dp*ppi/160                mdpi:  1.0 (baseline)           48*48
    //    dp = px / (ppi / 160)          hdpi:  1.5 (高)                 72*72
    //    px = sp*ppi/160                xhdpi: 2.0                      96*96
    //    sp = px / (ppi / 160)          xxhdpi:3.0                     144*144

    /**
     * sp转换px
     *
     * @param spValue sp数值
     * @return 转换后的px数值
     */
    public static int spTopx(Context context, float spValue) {
        return (int) (spValue * (context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    /**
     * px转换sp
     *
     * @param pxValue px数值
     * @return 转换后的sp数值
     */
    public static int pxTosp(Context context, float pxValue) {
        return (int) (pxValue / (context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    /**
     * dip转换px
     *
     * @param dpValue dp数值
     * @return 转换后的px数值
     */
    public static int dpTopx(Context context, float dpValue) {
        return (int) (dpValue * (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /**
     * px转换dp
     *
     * @param pxValue px数值
     * @return 转换后的dp数值
     */
    public static int pxTodp(Context context, float pxValue) {
        return (int) (pxValue / (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

}
