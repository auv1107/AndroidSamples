package com.sctdroid.app.samples.common;

/**
 * Created by lixindong on 2018/2/28.
 */

public interface Result {
    void onResult(RESULT result);

    enum RESULT {
        OK, ERROR, CANCEL
    }
}
