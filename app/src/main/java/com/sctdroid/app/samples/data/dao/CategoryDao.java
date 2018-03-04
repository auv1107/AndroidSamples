package com.sctdroid.app.samples.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sctdroid.app.samples.data.entity.Category;

import java.util.List;

/**
 * Created by lixindong on 2018/3/3.
 */

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category category);

    @Insert
    void insertAll(Category... category);

    @Delete
    void delete(Category category);

    @Delete
    void deleteAll(Category... categories);

    @Query("select * from Category")
    List<Category> getAll();

    @Query("select * from Category where id in (:ids)")
    List<Category> getAllByIds(String[] ids);
}
