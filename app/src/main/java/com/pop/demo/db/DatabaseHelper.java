package com.pop.demo.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.pop.demo.App;
import com.pop.demo.bean.PopMedia;
import com.pop.demo.util.L;

import java.sql.SQLException;

/**
 * Created by pengfu on 09/07/2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "popMedia.db";

    private static final int DATABASE_VERSION = 1;

    private RuntimeExceptionDao<PopMedia, Integer> popMediaIntegerDaoDao = null;

    private static DatabaseHelper instance;

    /**
     * 双重加锁检查
     * @return 单例
     */
    public static synchronized DatabaseHelper getInstance() {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(App.getInstance());
                }
            }
        }
        return instance;
    }


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            L.i(DatabaseHelper.class.getName()+" onCreate");
            TableUtils.createTable(connectionSource, PopMedia.class);
        } catch (SQLException e) {
            L.e(DatabaseHelper.class.getName() + " Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public RuntimeExceptionDao<PopMedia, Integer> getPopMediaDao() {
        if (popMediaIntegerDaoDao == null) {
            popMediaIntegerDaoDao = getRuntimeExceptionDao(PopMedia.class);
        }
        return popMediaIntegerDaoDao;
    }
    @Override
    public void close() {
        super.close();
        popMediaIntegerDaoDao = null ;
    }
}
