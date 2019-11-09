package com.jiankangli.knowledge.jiankang_yixiupro;

import android.app.Application;

import me.yokeyword.fragmentation.Fragmentation;

/**
 * @author lihao
 * @date 2019-10-22 14:39
 * @description :
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        // 栈视图等功能，建议在Application里初始化
        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();
    }
}
