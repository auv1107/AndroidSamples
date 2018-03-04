package com.sctdroid.app.samples.common;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by lixindong on 2018/3/4.
 */

public class AppFolderManager {

    public static final String APP_FOLDER_PATH = Environment.getExternalStorageDirectory().getPath() + "/AppFolder";

    protected final Context mContext;

    public AppFolderManager(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    private static AppFolderManager INSTANCE;

    public synchronized static AppFolderManager get(Context context) {

        if (INSTANCE == null) {
            INSTANCE = new AppFolderManager(context);
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public void makeAppFolder(String path) {
        FileUtils.ensureFolder(path);
    }

    public void makeAppFolders(String... paths) {
        for (String path : paths) {
            makeAppFolder(path);
        }
    }

    public File getDefaultAppFolder() {
        String defaultPath = APP_FOLDER_PATH + "/" + getContext().getPackageName();
        makeAppFolder(defaultPath);
        return new File(defaultPath);
    }

    public String getDefaultAppFolderPath() {
        return APP_FOLDER_PATH + "/" + getContext().getPackageName();
    }
}
