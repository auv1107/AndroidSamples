package com.sctdroid.app.samples.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.sctdroid.app.samples.data.dao.CategoryDao;
import com.sctdroid.app.samples.data.dao.GifDao;
import com.sctdroid.app.samples.data.entity.Category;
import com.sctdroid.app.samples.data.entity.Gif;

@Database(entities = {Category.class, Gif.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract GifDao GifDao();
}
