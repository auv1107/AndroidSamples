package com.sctdroid.app.samples.common.viewRecorder;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

/**
 * Created by lixindong on 2018/3/1.
 */

public interface FrameCache {
    void deleteFrames();
    void saveFrame(Bitmap bitmap, int frame);
    List<File> getFrames();
}
