package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;


/**
 *
 * @author 陈鑫
 * @date 2017/12/25
 */

public class VibrateUtil {
    /**
     *  震动milliseconds毫秒
     * @param milliseconds 毫秒
     */
    public static void vibrate(long milliseconds, Context context) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        if (vib != null) {
            vib.vibrate(milliseconds);
        }
    }



    /**
     * 以pattern[]方式震动
     * @param pattern
     * @param repeat
     */
    public static void vibrate(long[] pattern, int repeat, Context context) {
        Vibrator vib = (Vibrator)context.getSystemService(Service.VIBRATOR_SERVICE);
        if (vib != null) {
            vib.vibrate(pattern, repeat);
        }
    }

    /**
     * 取消震动
     */
    public static void virateCancle(Context context) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        if (vib != null) {
            vib.cancel();
        }
    }


}
