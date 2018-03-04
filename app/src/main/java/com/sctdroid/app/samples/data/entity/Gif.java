package com.sctdroid.app.samples.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by lixindong on 2018/3/2.
 */

@Entity(foreignKeys = @ForeignKey(entity = Category.class,
                                    parentColumns = "id",
                                    childColumns = "categoryId"))
public class Gif {

    @PrimaryKey
    public String id;

    public String url;

    public boolean synced;

    public String categoryId;

    public Date createdAt;

}
