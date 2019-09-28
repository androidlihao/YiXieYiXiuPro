package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.util.Log;

import com.jiankangli.knowledge.jiankang_yixiupro.BuildConfig;


/**
 * @author : lihao
 * @date: 2019-01-17 11:21
 * @description : 日志管理工具类（code from 《第一行代码》）
 */
public class LogUtil {

    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;
    private static final String TAG = "LogUtil";

    /**
     * 设置当前日志打印等级（默认全部打印）
     * <br> mLEVEL = VERBOSE（全部打印）
     * <br> mLEVEL = NOTHING（屏蔽打印）
     */
    private static final int mLEVEL = VERBOSE;

    /**
     * 判断当前是否是debug 模式，根据编译模式直接判断
     *
     * @return
     */
    private static Boolean isDebugModel() {
        return BuildConfig.DEBUG;
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebugModel() && mLEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebugModel() && mLEVEL <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebugModel() && mLEVEL <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebugModel() && mLEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebugModel() && mLEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }

}
