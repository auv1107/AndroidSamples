package com.sctdroid.app.samples.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sctdroid.app.samples.data.entity.Category;

import java.util.List;

/**
 * Created by lixindong on 2018/3/3.
 */

@Dao
public interface CategoryDao {
    @Insert
    void insert(Category category);

    @Delete
    void delete(Category category);

    @Query("select * from Category")
    List<Category> getAll();

    @Query("select * from Category where id (:ids)")
    List<Category> getAllByIds(String[] ids);
}
