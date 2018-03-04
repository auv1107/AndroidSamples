package com.sctdroid.app.samples.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by lixindong on 2018/3/2.
 */

@Entity(indices = {@Index("id")})
public class Category {
    @PrimaryKey
    public @NonNull String id;

    public String name;

    public Date date;
}
