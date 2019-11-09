package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.jiankangli.knowledge.jiankang_yixiupro.gen.DaoSession;
import com.jiankangli.knowledge.jiankang_yixiupro.greendao.DaoManager;
import com.jiankangli.knowledge.jiankang_yixiupro.greendao.GreenDaoContext;

import org.greenrobot.greendao.AbstractDao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihao
 * @date 2019-06-06 14:40
 * @description :数据库操作工具类（增删改查）
 */
public class GreenDaoUtil {


    //============================================================================================//
    //====================================GreenDao Util===========================================//
    //============================================================================================//
    private DaoManager daoManager;

    /**
     * 初始化对象
     *
     * @param context
     */
    private GreenDaoUtil(GreenDaoContext context) {
        daoManager = new DaoManager(context, false);
        daoManager.getDaoSession();
        //开始自由判断是否开启debug
        daoManager.setDebug(true);
    }

    /**
     * 连接或者初始化，打开数据库操作
     * 默认不加密
     *
     * @param context
     * @return
     */
    public static GreenDaoUtil getInstance(GreenDaoContext context) {
        return new GreenDaoUtil(context);
    }

    /**
     * 自主选择是否加密
     *
     * @param context
     * @param isEncrypt
     * @return
     */
    public static GreenDaoUtil getInstance(GreenDaoContext context, boolean isEncrypt) {
        return new GreenDaoUtil(context, isEncrypt);
    }

    /**
     * @param context
     * @param isEncrypt
     */
    private GreenDaoUtil(GreenDaoContext context, boolean isEncrypt) {
        daoManager = new DaoManager(context, isEncrypt);
        daoManager.getDaoSession();
        //开始自由判断是否开启debug
        daoManager.setDebug(true);
    }

    /**
     * 对外提供DaoSession，用户可通过这个查看表
     */
    public DaoSession getDaoSession() {
        return daoManager.getDaoSession();
    }

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    public <T> Boolean insert(T entity) {
        boolean flag = false;
        try {
            daoManager.getDaoSession().insert(entity);
            flag = true;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return flag;
    }

    /**
     * 插入多条数据对象
     * * 可能会存在耗时 操作 所以new 一个线程
     *
     * @param list
     * @return
     */
    public <T> Boolean insertMult(final List<T> list) {
        boolean flag = false;
        try {
            daoManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (T bean : list) {
                        daoManager.getDaoSession().insert(bean);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return flag;
    }


    /**
     * 根据id更新数据
     *
     * @param t
     * @return
     */
    public <T> boolean update(T t) {
        boolean flag = false;
        try {
            daoManager.getDaoSession().update(t);
            flag = true;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return flag;
    }

    /**
     * 批量更新数据
     * 异步操作
     *
     * @param objects
     * @return
     */
    public <T> void updateMultObject(final List<T> objects, Class clss) {
        if (null == objects || objects.isEmpty()) {
            return;
        }
        try {
            daoManager.getDaoSession().getDao(clss).updateInTx(objects);
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

    /**
     * 删除数据
     *
     * @param t
     * @return
     */
    public <T> boolean delete(T t) {
        boolean flag = false;
        try {
            daoManager.getDaoSession().delete(t);
            flag = true;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return flag;
    }

    /**
     * 异步批量删除数据
     *
     * @param objects
     * @return
     */
    public <T> boolean deleteMultObject(final List<T> objects, Class clss) {
        boolean flag = false;
        if (null == objects || objects.isEmpty()) {
            return false;
        }
        try {
            daoManager.getDaoSession().getDao(clss).deleteInTx(new Runnable() {
                @Override
                public void run() {
                    for (T object : objects) {
                        daoManager.getDaoSession().delete(object);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            Log.e("TAG", e.toString());
            flag = false;
        }
        return flag;
    }

    /**
     * 删除某个数据库表
     *
     * @param clss
     * @return
     */
    public boolean deleteTable(Class clss) {
        boolean flag = false;
        try {
            daoManager.getDaoSession().deleteAll(clss);
            flag = true;
        } catch (Exception e) {
            Log.e("TAG", e.toString());
            flag = false;
        }
        return flag;
    }

    /**
     * 查询 某一个表 的 所有记录
     */
    public <T> List<T> listAll(Class object) {
        List<T> objects = null;
        try {
            objects = (List<T>) daoManager.getDaoSession().getDao(object).loadAll();
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
        return objects;
    }
    /**
     * 查询 某一表 的 条件搜索
     */
    /**
     * 查询某条件下的对象
     *
     * @param object
     * @return
     */
    public <T> List<T> queryObject(Class object, String where, String... params) {
        AbstractDao obj = null;
        List<T> objects = null;
        try {
            obj = daoManager.getDaoSession().getDao(object);

            if (null == obj) {
                return null;
            }
            objects = obj.queryRaw(where, params);
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }

        return objects;
    }

    /**
     * @param object 泛型参数
     * @return
     */
    public String getTablename(Class object) {
        String tablename = daoManager.getDaoSession().getDao(object).getTablename();
        return tablename;
    }

    /**
     * 关闭数据库
     */
    public void closeDb() {
        daoManager.closeDataBase();
    }


    //============================================================================================//
    //======================================Sqlite Util===========================================//
    //============================================================================================//

    public List<Map<String, Object>> query(String filePath, String tableName, String where) {
        List<Map<String, Object>> maps = new ArrayList<>();
        LinkedHashMap<String, Object> attrs = new LinkedHashMap<>();
        String sql = "select * from " + tableName + " where " + where;
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(filePath, null);
        Cursor cursor = database.rawQuery(sql, null);
//        cursor.moveToFirst();
        String[] columnNames = cursor.getColumnNames();
        while (cursor.moveToNext()) {
            for (String columnName : columnNames) {
                int columnIndex = cursor.getColumnIndex(columnName);
                int type = cursor.getType(columnIndex);
                switch (type) {
                    case Cursor.FIELD_TYPE_NULL:
                        attrs.put(columnName, null);
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        attrs.put(columnName, cursor.getInt(columnIndex));
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        attrs.put(columnName, cursor.getFloat(columnIndex));
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        attrs.put(columnName, cursor.getString(columnIndex));
                        break;
                    case Cursor.FIELD_TYPE_BLOB:
                        attrs.put(columnName, cursor.getBlob(columnIndex));
                        break;
                }
            }
            maps.add(attrs);
        }

        cursor.close();
        database.close();
        if (maps.size() == 0) {
            for (String columnName : columnNames) {
                attrs.put(columnName, "");
            }
            maps.add(attrs);
        }
        return maps;
    }


}
