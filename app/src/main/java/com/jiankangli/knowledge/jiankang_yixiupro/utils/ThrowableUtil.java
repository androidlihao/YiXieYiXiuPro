package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author lihao
 * @date 2019-04-10 11:08
 * @description :异常处理工具类
 */
public class ThrowableUtil {

    /**
     * 写入错误日志
     */
    public static void writeLog(Throwable throwable, String filepath){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String fileName = "LOG_" + sdf.format(new Date()) + ".txt";
        String filePath =filepath +"/"+ fileName;
        FileUtil.writeFile(filePath, Log.getStackTraceString(throwable),false);
    }
}
