package com.sctdroid.app.samples.common;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixindong on 2018/1/24.
 */

public class ExecutorThread {

    private static ExecutorThread INSTANCE;

    public synchronized static ExecutorThread getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new ExecutorThread();
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    private final ExecutorService mExecutorService;
    private final BlockingQueue mQuene = new LinkedBlockingQueue();

    public ExecutorThread() {
        mExecutorService = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS, mQuene);
    }

    public void execute(Runnable runnable) {
        mExecutorService.submit(runnable);
    }
}
