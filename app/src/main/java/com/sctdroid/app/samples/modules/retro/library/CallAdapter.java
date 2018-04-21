package com.sctdroid.app.samples.modules.retro.library;

/**
 * Created by lixindong on 2018/3/31.
 */

public interface CallAdapter<T> {
    T adapter(HttpCall httpCall);
}
