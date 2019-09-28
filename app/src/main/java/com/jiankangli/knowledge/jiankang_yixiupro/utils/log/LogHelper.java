package com.jiankangli.knowledge.jiankang_yixiupro.utils.log;

import android.content.Context;

/**
 * @author : chenxin
 * @date: 2019-05-05 18:39
 * Description:
 */
public interface LogHelper {


    /**
     * 编写主标题
     *
     * @param title 主标题
     * @return 需要打印的内容
     */
    String writeTitle(String title);

    /**
     * 编写标题1
     *
     * @param h1 标题1
     * @return 需要打印的内容
     */
    String writeH1(String h1);

    /**
     * 编写内容
     *
     * @return 需要打印的内容
     */
    String writeContent();


    /**
     * 系统信息
     * @return 系统信息
     */
    String writeSystemInfo(Context context);

    /**
     * 写入本地文件
     *
     * @param content 写入内容
     */
    void writeLog2Native(String content);
}
