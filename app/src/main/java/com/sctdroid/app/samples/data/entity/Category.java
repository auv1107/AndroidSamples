package com.sctdroid.app.samples.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by lixindong on 2018/3/2.
 */

@Entity
public class Category {
    @PrimaryKey
    String id;

    String name;

    Date date;
}
