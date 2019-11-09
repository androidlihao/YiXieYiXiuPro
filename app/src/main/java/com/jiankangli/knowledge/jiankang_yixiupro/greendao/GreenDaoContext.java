package com.jiankangli.knowledge.jiankang_yixiupro.greendao;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;


import java.io.File;
import java.io.IOException;

/**
 * Created by chenxin on 2017/9/7.
 * 用于修改GreenDao数据的初始位置
 */

public class GreenDaoContext extends ContextWrapper {

    private Context mContext;
    //数据库的位置，避免再创建数据库的时候多次指定dbName
    private String path= Environment.getExternalStorageDirectory()+"/yixiuPro/DB/";
    //数据库名称
    private String dbName;

    public GreenDaoContext(Context context,String dbName) {
        super(context);
        this.mContext = context;
        this.dbName=dbName;
    }

    /**
     * 获取数据库名称
     * @return
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * 获得数据库路径，如果不存在，则创建对象
     *
     * @param dbName
     */
    @Override
    public File getDatabasePath(String dbName) {
        // 数据库文件是否创建成功
        boolean isFileCreateSuccess = false;
        // 判断文件是否存在，不存在则创建该文件
        File dbFile = new File(path + File.separator + dbName);
        File file = dbFile.getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!dbFile.exists()) {
            try {
                // 创建文件
                isFileCreateSuccess = dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            isFileCreateSuccess = true;
        }
        // 返回数据库文件对象
        if (isFileCreateSuccess) {
            return dbFile;
        } else {
            return super.getDatabasePath(dbName);
        }
    }

    /**
     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     *
     * @param name
     * @param mode
     * @param factory
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        return result;
    }

    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @param name
     * @param mode
     * @param factory
     * @param errorHandler
     * @see ContextWrapper#openOrCreateDatabase(String, int,
     * SQLiteDatabase.CursorFactory,
     * DatabaseErrorHandler)
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory,DatabaseErrorHandler errorHandler) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        return result;
    }

}
