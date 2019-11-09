package com.jiankangli.knowledge.jiankang_yixiupro.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.jiankangli.knowledge.jiankang_yixiupro.gen.DaoMaster;
import com.jiankangli.knowledge.jiankang_yixiupro.gen.MaintainDataBeanDao;

import org.greenrobot.greendao.database.Database;

/**
 * @author lihao
 * @date 2019-08-24 11:03
 * @description :
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, MaintainDataBeanDao.class);
    }
}
