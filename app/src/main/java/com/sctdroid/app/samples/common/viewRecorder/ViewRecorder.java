package com.sctdroid.app.samples.common.viewRecorder;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.View;

import com.sctdroid.app.samples.common.BitmapUtils;
import com.sctdroid.app.samples.common.Result;
import com.sctdroid.app.samples.thirdParty.gifflen.GifflenClient;
import com.sctdroid.app.samples.thirdParty.gifflen.TmpFileFrameCache;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lixindong on 2018/2/28.
 */

public class ViewRecorder {

    private View mView;
    private int mRate;
    private Timer mTimer;
    private RecorderHandler mHandler;
    private Listener mListener;
    private String mTarget;
    private float mScale;

    private int mCurrentFrame;
    private FrameCache mFrameCache;

    private boolean mIsRecording = false;

    private GifClient mGifClient;

    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public ViewRecorder(@NonNull View view, GifClient client, FrameCache frameCache, int rate, float scale, String target, Listener listener) {
        mTimer = new Timer();
        mHandler = new RecorderHandler();
        mCurrentFrame = 0;
        mView = view;
        mRate = rate;
        mListener = listener;
        mTarget = target;
        mGifClient = client;
        mFrameCache = frameCache;
        mScale = scale;
    }

    public void startRecord() {
        mCurrentFrame = 0;
        deleteFiles();
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
                List<File> files = getFrames();
                Result.RESULT result = mGifClient.newGifFromFiles(files, mTarget);
                if (result == Result.RESULT.OK) {
                    postMergeFinish(mTarget);
                } else {
                    // post error here
                    postMergeError("error");
                }
                deleteFiles();
            }
        });

    }

    public interface Listener {
        void onFrame(Bitmap bitmap, int currentFrame);
        void onMergeStart();
        void onMergeFinish(String path);
        void onMergeError(String error);
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
    private void postMergeError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onMergeError(error);
            }
        });
    }

    private void callAFrame(final int frame) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapUtils.getViewBitmap(mView, mScale);
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
                mFrameCache.saveFrame(bitmap, frame);
            }
        });
    }

    private void deleteFiles() {
        mFrameCache.deleteFrames();
    }

    private List<File> getFrames() {
        return mFrameCache.getFrames();
    }

    public static Builder For(View view) {
        return new Builder(view);
    }

    public static class Builder {

        private static GifClient DEFAULT_GIF_CLIENT = new GifflenClient();
        private static FrameCache DEFAULT_FRAME_CACHE = new TmpFileFrameCache();
        private static int DEFAULT_RATE = 12;
        private static float DEFAULT_SCALE = 1.0f;

        private View mView;
        private GifClient mGifClient = DEFAULT_GIF_CLIENT;
        private FrameCache mFrameCache = DEFAULT_FRAME_CACHE;
        private int mRate = DEFAULT_RATE;
        private String mTarget;
        private Listener mListener;
        private float mScale = DEFAULT_SCALE;

        private Builder(View view) {
            mView = view;
        }

        public Builder useGifClient(GifClient gifClient) {
            mGifClient = gifClient;
            return this;
        }

        public Builder useFrameCache(FrameCache frameCache) {
            mFrameCache = frameCache;
            return this;
        }

        public Builder to(String target) {
            mTarget = target;
            return this;
        }

        public Builder rate(int rate) {
            mRate = rate;
            return this;
        }

        public Builder listener(Listener listener) {
            mListener = listener;
            return this;
        }

        public Builder scale(float scale) {
            mScale = scale;
            return this;
        }

        public ViewRecorder build() {
            return new ViewRecorder(mView, mGifClient, mFrameCache, mRate, mScale, mTarget, mListener);
        }
    }

    class RecorderHandler extends Handler {
        public RecorderHandler() {
            super(Looper.getMainLooper());
        }
    }

}
