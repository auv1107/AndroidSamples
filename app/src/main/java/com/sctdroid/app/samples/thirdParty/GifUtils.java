package com.sctdroid.app.samples.thirdParty;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sctdroid.app.samples.common.Result;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.dxjia.ffmpeg.library.FFmpegNativeHelper;

/**
 * Created by lixindong on 2018/2/28.
 */

public class GifUtils {

    private static ExecutorService service = Executors.newSingleThreadExecutor();
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static boolean GifByFFmpge(@NonNull List<String> input, @NonNull String output) {
        StringBuilder builder = new StringBuilder();
        for (String name : input) {
            builder.append("-i ");
            builder.append(name);
            builder.append(" ");
        }
        String command = "ffmpeg -f image2 -framerate 2 " + builder.toString() + " " + output;
        Log.d("GifUtils", "GifByFFmpge: " + command);
        String result = FFmpegNativeHelper.runCommand(command);
        return result.contains("successful");
    }

    public static boolean GifByFFmpge(@NonNull String input, @NonNull String output) {
        String result = FFmpegNativeHelper.runCommand("ffmpeg -f image2 -framerate 2 -i " + input + " " + output);
        return result.contains("successful");
    }

    public static void GifByFFmpge(@NonNull final String input, @NonNull final String output, @NonNull final Result result) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                boolean gifResult = GifByFFmpge(input, output);
                postResult(result, gifResult ? Result.RESULT.OK : Result.RESULT.ERROR);
            }
        });
    }

    public static void postResult(@NonNull final Result result, @NonNull final Result.RESULT gifResult) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                result.onResult(gifResult);
            }
        });
    }
}
