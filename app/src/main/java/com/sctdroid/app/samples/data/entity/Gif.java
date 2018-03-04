package com.sctdroid.app.samples.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.sctdroid.app.samples.common.Encryption;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by lixindong on 2018/3/2.
 */

@Entity(foreignKeys = @ForeignKey(entity = Category.class,
                                    parentColumns = "id",
                                    childColumns = "categoryId"),
        indices = {@Index("categoryId")})
public class Gif {

    @PrimaryKey
    @NonNull public String id;

    public String url;

    public boolean synced;

    public String categoryId;

    public Date createdAt;

    public Gif() {
        createdAt = new Date();
        synced = false;
        try {
            id = Encryption.md5(toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            id = "" + System.currentTimeMillis() + (int) (Math.random() * 1024);
        }
    }
}
