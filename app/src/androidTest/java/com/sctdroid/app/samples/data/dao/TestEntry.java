package com.sctdroid.app.samples.data.dao;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by lixindong on 2018/3/4.
 */

@RunWith(AndroidJUnit4.class)
public class TestEntry {
    @Test
    public void testDb() throws Exception {
        CategoryDaoTest categoryDaoTest = new CategoryDaoTest();
        categoryDaoTest.setUp();

        GifDaoTest gifDaoTest = new GifDaoTest();
        gifDaoTest.setUp();

        categoryDaoTest.persistent();
        gifDaoTest.persistent();
    }
}
