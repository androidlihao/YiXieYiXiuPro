package com.jiankangli.knowledge.jiankang_yixiupro.utils.log;

import android.annotation.SuppressLint;
import android.content.Context;


import com.jiankangli.knowledge.jiankang_yixiupro.utils.AndroidInfoUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : chenxin
 * @date: 2019-05-05 12:00
 * Description:
 */
public class LogImpl implements LogHelper{
    private String mUser;
    protected String mFilePath;
    private static LogImpl flowpathlog;
    /**
     * 分割线列数
     */
    private static final int CUT_OFF_LINE_COUNT = 100;
    /**
     * 分割线行数
     */
    private static final int CUT_OFF_ROW_COUNT = 5;


    public void setUser(String user) {
        this.mUser = user;
    }

    public LogImpl(String filePath) {
        this.mFilePath = filePath;
    }

    @Override
    public void writeLog2Native(String content) {
        File file = new File(mFilePath);
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            osw.write(content);
            osw.write("\r\n");
            osw.flush();
            osw.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 编写标题1
     *
     * @param h1 标题1
     * @return 打印内容
     */
    @Override
    public String writeH1(String h1) {

        StringBuilder stringBuilder = new StringBuilder();
        int lineStarIndex = (CUT_OFF_LINE_COUNT - h1.getBytes().length) / 2;
        int lineEndIndex = lineStarIndex + h1.getBytes().length;
        for (int i = 0; i < CUT_OFF_LINE_COUNT; i++) {
            if (i <= lineStarIndex || i >= lineEndIndex) {
                stringBuilder.append("=");
            }
            if (i == lineStarIndex) {
                stringBuilder.append(h1);
            }

        }
        return stringBuilder.toString();
    }

    /**
     * 编写内容
     *
     * @return 需要打印的内容
     */
    @Override
    public String writeContent() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Time:");
        stringBuilder.append(getTime());
        stringBuilder.append(" ");
        stringBuilder.append("User:");
        stringBuilder.append(mUser);
        stringBuilder.append("\r\n");
        return stringBuilder.toString();
    }



    @Override
    public String writeSystemInfo(Context context) {
        String appInfo = AndroidInfoUtil.getAppInfo(context);
        String deviceInfo = AndroidInfoUtil.getDeviceInfo();
        return appInfo+deviceInfo;
    }

    /**
     * 编写标题
     *
     * @param title 标题
     */
    @Override
    public String writeTitle(String title) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < CUT_OFF_ROW_COUNT; i++) {
            for (int j = 0; j < CUT_OFF_LINE_COUNT; j++) {
                if (i == 0 || i == CUT_OFF_ROW_COUNT - 1) {
                    stringBuilder.append("=");
                } else {
                    int rowIndex = CUT_OFF_ROW_COUNT / 2;
                    if (j == 0 || j == CUT_OFF_LINE_COUNT - 1) {
                        stringBuilder.append("|");

                    } else {
                        int lineStarIndex = (CUT_OFF_LINE_COUNT - title.getBytes().length) / 2;
                        int lineEndIndex = lineStarIndex + title.getBytes().length;
                        if (i == rowIndex && j == lineStarIndex) {
                            stringBuilder.append(title);

                        } else {
                            if (i != rowIndex || j <= lineStarIndex || j >= lineEndIndex) {
                                stringBuilder.append(" ");
                            }
                        }
                    }


                }


                if (j == CUT_OFF_LINE_COUNT - 1) {
                    stringBuilder.append("\n");
                }
            }
        }

        return stringBuilder.toString();

    }


    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    @SuppressLint("SimpleDateFormat")
    protected String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }


}
