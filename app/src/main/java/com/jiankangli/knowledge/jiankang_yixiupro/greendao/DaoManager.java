package com.jiankangli.knowledge.jiankang_yixiupro.greendao;


import com.jiankangli.knowledge.jiankang_yixiupro.gen.DaoMaster;
import com.jiankangli.knowledge.jiankang_yixiupro.gen.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * @author lihao
 * @date 2019-06-05 18:14
 * @description :管理数据库（打开或关闭、升级、存储位置）
 */
public class DaoManager {
    //数据库名称
    private MySQLiteOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private GreenDaoContext context;
    private boolean isEncrypt;

    /**
     * 主要是用来指向数据的位置和名称
     *
     * @param context
     */
    public DaoManager(GreenDaoContext context,boolean isEncrypt) {
        this.context = context;
        this.isEncrypt=isEncrypt;
    }


    /**
     * 获取DaoSession
     * 用来对数据库进行操作
     *
     * @return
     */
    public synchronized DaoSession getDaoSession() {
        if (null == mDaoSession) {
            mDaoSession = getDaoMaster().newSession();
        }
        return mDaoSession;
    }

    /**
     * 设置debug模式开启或关闭，默认关闭
     *
     * @param flag
     */
    public void setDebug(boolean flag) {
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;
    }


    /**
     * 关闭数据库
     */
    public synchronized void closeDataBase() {
        closeHelper();
        closeDaoSession();
    }

    /**
     * 判断数据库是否存在，如果不存在则创建
     *
     * @return
     */
    private DaoMaster getDaoMaster() {
        if (null == mDaoMaster) {
            mHelper = new MySQLiteOpenHelper(context, context.getDbName(), null);
            if (!isEncrypt){
                mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
            }else {
                mDaoMaster = new DaoMaster(mHelper.getEncryptedWritableDb("geostar"));
            }

        }
        return mDaoMaster;
    }

    private void closeDaoSession() {
        if (null != mDaoSession) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    private void closeHelper() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }
}

