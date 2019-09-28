package com.jiankangli.knowledge.jiankang_yixiupro.utils.log;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * @author : chenxin
 * @date: 2019-05-06 9:57
 * Description:
 */
public class ErrorLog extends LogImpl {
    private static ErrorLog errorLog;


    private ErrorLog(String filePath, Context context) {
        super(filePath);
        createTitle(context);
    }

    public static ErrorLog getInstance(String filePath, Context context) {
        if (errorLog == null) {
            synchronized (ErrorLog.class) {
                if (errorLog == null) {
                    errorLog = new ErrorLog(filePath, context);
                }
            }
        }
        return errorLog;
    }

    private void createTitle(Context context) {
        File file = new File(mFilePath);
        if (!file.exists()) {
            writeLog2Native(writeTitle("ERROR_LOG"));
            writeLog2Native(writeSystemInfo(context));
        }
    }


    /**
     *  写入错误信息
     * @param e 错误信息
     * @return 错误信息
     */
    public String writeContent(Throwable e){
        writeH1(e.toString());
        String s = super.writeContent();
        StringBuilder stringBuilder = new StringBuilder(s);
        stringBuilder.append(Log.getStackTraceString(e));
        writeLog2Native(stringBuilder.toString());
        return stringBuilder.toString();
    }

    @Override
    public String writeH1(String h1) {
        writeLog2Native(super.writeH1(h1));
        return super.writeH1(h1);
    }
}
