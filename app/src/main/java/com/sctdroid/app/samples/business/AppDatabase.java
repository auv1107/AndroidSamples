package com.sctdroid.app.samples.business;

import com.sctdroid.app.samples.common.ExecutorThread;
import com.sctdroid.app.samples.data.DB;
import com.sctdroid.app.samples.data.entity.Category;
import com.sctdroid.app.samples.data.entity.Gif;

import java.util.List;

/**
 * Created by lixindong on 2018/3/4.
 */

public class AppDatabase {
    public static final Category NOT_SPECIFIED_CATEGORY;
    static {
        Category category = new Category();
        category.id = "-1";
        category.name = "NOT_SPECIFIED";
        NOT_SPECIFIED_CATEGORY = category;
    }

    public static void ensureUnspecifiedCategory() {
        ExecutorThread.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                DB.getDB().CategoryDao().insert(NOT_SPECIFIED_CATEGORY);
            }
        });
    }

    public static void addGif(String url) {
        Gif gif = new Gif();
        gif.categoryId = NOT_SPECIFIED_CATEGORY.id;
        gif.url = url;
        gif.synced = false;

        DB.getDB().GifDao().insert(gif);
    }

    public static void addGifAsync(final String url) {
        ExecutorThread.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                addGif(url);
            }
        });
    }

    public static List<Gif> getAllGifs() {
        return DB.getDB().GifDao().getGifs();
    }

}
