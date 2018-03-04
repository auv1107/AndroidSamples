package com.sctdroid.app.samples.data.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.sctdroid.app.samples.data.AppDatabase;
import com.sctdroid.app.samples.data.DB;
import com.sctdroid.app.samples.data.entity.Gif;

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
public class GifDaoTest {
    AppDatabase mDb;

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DB.init(appContext);
        mDb = DB.getDB();
    }

    @Test
    public void getGifs() throws Exception {
        List<Gif> gifs = mDb.GifDao().getGifs();
        assertEquals(8, gifs.size());
    }

    @Test
    public void loadAllByIds() throws Exception {
        List<Gif> gifs = mDb.GifDao().loadAllByIds(new String[] {"1", "0"});
        assertEquals(2, gifs.size());
    }

    @Test
    public void loadAllByCategory() throws Exception {
        List<Gif> gifs = mDb.GifDao().loadAllByCategory("3");
        assertEquals(2, gifs.size());
    }

    @Test
    public void findFirst() throws Exception {
        Gif gif = mDb.GifDao().findFirst("1");
        assertEquals("0", gif.id);
    }

    @Test
    public void insertAll() throws Exception {
        Gif[] gifList = new Gif[] {
                buildGif("1", "0"),
                buildGif("1", "2"),
                buildGif("1", "3"),
                buildGif("2", "1"),
                buildGif("2", "4"),
                buildGif("2", "5"),
                buildGif("3", "6"),
                buildGif("3", "7"),
        };
        mDb.GifDao().insertAll(gifList);
    }

    private Gif buildGif() {
        return buildGif((int) (Math.random() * 3) + "");
    }

    private Gif buildGif(String categoryId) {
        return buildGif(categoryId, (new Date()).hashCode() + "");
    }

    private Gif buildGif(String categoryId, String id) {
        Gif gif = new Gif();
        gif.categoryId = categoryId;
        gif.createdAt = new Date();
        gif.id = id;
        gif.url = "http://a.b.c/g.gif";
        gif.synced = Math.random() * 2 == 1;
        return gif;
    }

    @Test
    public void delete() throws Exception {
        Gif gif = buildGif("1", "0");
        mDb.GifDao().delete(gif);
    }
    @Test
    public void deleteAll() throws Exception {
        List<Gif> gifList = mDb.GifDao().getGifs();
        Gif[] gifs = new Gif[gifList.size()];
        gifList.toArray(gifs);
        mDb.GifDao().deleteAll(gifs);
    }

    @Test
    public void persistent() throws Exception {
        deleteAll();
        insertAll();
        getGifs();
        findFirst();
        loadAllByIds();
        delete();
        loadAllByCategory();
    }
}