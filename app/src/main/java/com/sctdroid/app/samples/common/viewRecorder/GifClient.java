package com.sctdroid.app.samples.common.viewRecorder;

import com.sctdroid.app.samples.common.Result;

import java.io.File;
import java.util.List;

/**
 * Created by lixindong on 2018/3/1.
 */

public interface GifClient {
    Result.RESULT newGif(String input, String output);
    void newGif(String input, String output, Result result);

    Result.RESULT newGifFromFiles(List<File> input, String output);
}
