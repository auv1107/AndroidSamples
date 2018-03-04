package com.sctdroid.app.samples.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sctdroid.app.samples.data.entity.Gif;

import java.util.List;

/**
 * Created by lixindong on 2018/3/3.
 */

@Dao
public interface GifDao {
    @Query("select * from gif")
    List<Gif> getGifs();

    @Query("select * from gif where id in (:ids)")
    List<Gif> loadAllByIds(String[] ids);

    @Query("select * from gif where categoryId like :categoryId")
    List<Gif> loadAllByCategory(String categoryId);

    @Query("select * from gif where categoryId like :categoryId limit 1")
    Gif findFirst(String categoryId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Gif... gifs);

    @Delete
    void delete(Gif gif);
}
