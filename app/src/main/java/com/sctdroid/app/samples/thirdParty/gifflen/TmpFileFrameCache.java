package com.sctdroid.app.samples.thirdParty.gifflen;

import android.graphics.Bitmap;

import com.sctdroid.app.samples.common.FileUtils;
import com.sctdroid.app.samples.common.viewRecorder.FrameCache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixindong on 2018/3/1.
 */

public class TmpFileFrameCache implements FrameCache {

    private List<File> files = new ArrayList<>();

    @Override
    public void deleteFrames() {
        // tmp files are managed automatically
        files.clear();
    }

    @Override
    public void saveFrame(Bitmap bitmap, int frame) {
        File file = FileUtils.saveBitmapTemporary(bitmap, "recorder");
        files.add(file);
    }

    @Override
    public List<File> getFrames() {
        return files;
    }
}
