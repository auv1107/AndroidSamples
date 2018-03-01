package com.sctdroid.app.samples.common;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by lixindong on 2018/1/24.
 */

public class FileUtils {
    public static String fileName(@NonNull String path) {
        File file = new File(path);
        return file.getName();
    }

    public static File saveBitmapTemporary(@NonNull Bitmap bitmap, String prefix) {
        try {
            File tempFile = File.createTempFile(prefix, ".png");
            OutputStream os = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();

            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File saveBitmap(@NonNull Bitmap bitmap, String path) {
        try {
            File tempFile = new File(path);
            if (tempFile.exists()) {
                tempFile.delete();
                tempFile.createNewFile();
            }
            OutputStream os = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
