package com.sctdroid.app.samples.data.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.sctdroid.app.samples.data.AppDatabase;
import com.sctdroid.app.samples.data.DB;
import com.sctdroid.app.samples.data.entity.Category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lixindong on 2018/3/4.
 */
@RunWith(AndroidJUnit4.class)
public class CategoryDaoTest {
    AppDatabase mDb;

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DB.init(appContext);
        mDb = DB.getDB();
    }

    @Test
    public void insert() throws Exception {
        mDb.categoryDao().insert(buildCategory("4"));
    }

    @Test
    public void insertAll() throws Exception {
        Category[] categories = new Category[]{
                buildCategory("0"),
                buildCategory("1"),
                buildCategory("2"),
                buildCategory("3")
        };

        mDb.categoryDao().insertAll(categories);
    }

    @Test
    public void delete() throws Exception {
        mDb.categoryDao().delete(buildCategory("4"));
    }

    @Test
    public void deleteAll() throws Exception {
        List<Category> categoryList = mDb.categoryDao().getAll();
        Category[] categories = new Category[categoryList.size()];
        categoryList.toArray(categories);
        mDb.categoryDao().deleteAll(categories);
    }

    @Test
    public void getAll() throws Exception {
        List<Category> list = mDb.categoryDao().getAll();
        assertEquals(4, list.size());
    }

    @Test
    public void getAllByIds() throws Exception {
        List<Category> list = mDb.categoryDao().getAllByIds(new String[]{"0", "1"});
        assertEquals(list.size(), 2);
    }

    Category buildCategory(String id) {
        Category category = new Category();
        category.date = new Date();
        category.id = id;
        category.name = "name " + id;
        return category;
    }

    @Test
    public void persistent() throws Exception {
        GifDaoTest gifDaoTest = new GifDaoTest();
        gifDaoTest.setUp();
        gifDaoTest.deleteAll();
        deleteAll();
        insertAll();
        getAll();
        getAllByIds();
        delete();
    }
}