package com.jiankangli.knowledge.jiankang_yixiupro.utils.log;

import android.content.Context;

import java.io.File;

/**
 * @author : chenxin
 * @date: 2019-05-05 18:35
 * Description:
 */
public class NetWorkLog extends LogImpl {
    private static NetWorkLog netWorkLog;

    private NetWorkLog(String filePath, Context context) {
        super(filePath);
        createTitle(context);

    }

    private void createTitle(Context context) {
        File file = new File(mFilePath);
        if (!file.exists()) {
            writeLog2Native(writeTitle("NETWORK"));
            writeLog2Native(writeSystemInfo(context));
        }

    }


    public static NetWorkLog getInstance(String filePath, Context context) {
        if (netWorkLog == null) {
            synchronized (NetWorkLog.class) {
                if (netWorkLog == null) {
                    netWorkLog = new NetWorkLog(filePath,context);
                }
            }
        }
        return netWorkLog;
    }


    public static NetWorkLog getInstance() {
        return netWorkLog;
    }

    public String writeContent(String address, String params, String result) {
        String s = super.writeContent();
        StringBuilder stringBuilder = new StringBuilder(s);

        stringBuilder.append("address:");
        stringBuilder.append(address);
        stringBuilder.append("\r\n");
        stringBuilder.append("params:");
        stringBuilder.append(params);
        stringBuilder.append("\r\n");
        stringBuilder.append("result:");
        stringBuilder.append(result);
        stringBuilder.append("\r\n");
        writeLog2Native(stringBuilder.toString());
        return stringBuilder.toString();
    }


    @Override
    public String writeH1(String h1) {
        writeLog2Native(super.writeH1(h1));
        return super.writeH1(h1);
    }
}
