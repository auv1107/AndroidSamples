package com.sctdroid.app.samples;

import android.app.Application;

import com.sctdroid.app.samples.data.DB;

/**
 * Created by lixindong on 2018/3/4.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initGlobals();
    }

    private void initGlobals() {
        DB.init(this);
    }
}
