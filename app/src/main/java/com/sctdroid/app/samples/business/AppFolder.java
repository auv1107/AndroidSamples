package com.sctdroid.app.samples.business;

import android.content.Context;

import com.sctdroid.app.samples.common.AppFolderManager;

/**
 * Created by lixindong on 2018/3/4.
 */

public class AppFolder {
    public static final String FOLDER_CACHE = "cache";
    public static final String FOLDER_GALLERY = "gallery";

    public static void initAppFolders(Context context) {
        initFolder(context, FOLDER_CACHE);
        initFolder(context, FOLDER_GALLERY);
    }

    private static void initFolder(Context context, String folder) {
        String path = getFolder(context, folder);
        AppFolderManager.get(context).makeAppFolder(path);
    }

    public static String getFolder(Context context, String folderName) {
        return AppFolderManager.get(context).getDefaultAppFolderPath() + "/" + folderName;
    }
}
