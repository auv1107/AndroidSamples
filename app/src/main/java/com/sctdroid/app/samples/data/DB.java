package com.sctdroid.app.samples.data;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by lixindong on 2018/3/4.
 */

public class DB {

    private static DB INSTANCE;

    public synchronized static DB getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new DB();
        }

        return INSTANCE;
    }

    public synchronized static AppDatabase getDB() {
        return getInstance().mDb;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static String DATABASE_NAME;
    private AppDatabase mDb;

    public static void init(Context context) {
        getInstance().initByContext(context);
    }

    private void initByContext(Context context) {
        mDb = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, getDatabaseName(context)).build();
    }

    public String getDatabaseName(Context context) {
        return context.getPackageName();
    }

}
