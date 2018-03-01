package com.sctdroid.app.samples.common;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lixindong on 2018/2/28.
 */

public class ViewRecorder {

    private static final String FILE_FORMAT = "recorder%010d.png";
    private View mView;
    private int mRate;
    private Timer mTimer;
    private Handler mHandler;
    private Listener mListener;
    private String mOutput;

    private int mCurrentFrame;
    private List<File> mFrameList = new ArrayList<>();

    private boolean mIsRecording = false;

    GifClient mGifClient;

    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public ViewRecorder(@NonNull View view, GifClient client, int rate, String output, Listener listener) {
        mTimer = new Timer();
        mHandler = new Handler(Looper.getMainLooper());
        mCurrentFrame = 0;
        mView = view;
        mRate = rate;
        mListener = listener;
        mOutput = output;
        mGifClient = client;
    }

    public void startRecord() {
        mCurrentFrame = 0;
        mFrameList.clear();
        mIsRecording = true;
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // do something
                callAFrame(mCurrentFrame ++);
            }
        }, 0, 1000 / mRate);
    }

    public void stopRecord() {
        // use single executor to ensure all works are finished
        if (!mIsRecording) {
            return;
        }
        mIsRecording = false;
        postMergeStart();
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTimer.cancel();
                mTimer.purge();
//                boolean success = GifUtils.GifByFFmpge(mView.getContext().getCacheDir() + "/" + FILE_FORMAT, mOutput);
                Result.RESULT result = mGifClient.newGifFromFiles(mFrameList, mOutput);
                if (result == Result.RESULT.OK) {
                    postMergeFinish(mOutput);
                } else {
                    // post error here
                    postMergeFinish("error");
                }
            }
        });

    }

    public interface Listener {
        void onFrame(Bitmap bitmap, int currentFrame);
        void onMergeStart();
        void onMergeFinish(String path);
    }

    private void postBitmap(final Bitmap bitmap, final int currentFrame) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFrame(bitmap, currentFrame);
            }
        });
    }

    private void postMergeStart() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onMergeStart();
            }
        });
    }
    private void postMergeFinish(final String path) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onMergeFinish(path);
            }
        });
    }

    private void callAFrame(final int frame) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapUtils.getViewBitmap(mView);
                if (bitmap != null) {
                    saveBitmap(bitmap, frame);
                }
                mListener.onFrame(bitmap, frame);
            }
        });
    }

    private void saveBitmap(@NonNull final Bitmap bitmap, final int frame) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                File f = FileUtils.saveBitmap(bitmap, filename(frame));
                bitmap.recycle();
                if (f != null) {
                    mFrameList.add(f);
                }
            }
        });
    }

    private String filename(int frame) {
        String name = String.format(Locale.getDefault(), FILE_FORMAT, frame);
        Log.d("ViewRecorder", "filename: " + name);
        return mView.getContext().getCacheDir() + "/" + name;
    }

}
