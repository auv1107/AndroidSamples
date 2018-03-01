package com.sctdroid.app.samples.thirdParty;

import com.lchad.gifflen.Gifflen;
import com.sctdroid.app.samples.common.GifClient;
import com.sctdroid.app.samples.common.Result;

import java.io.File;
import java.util.List;

/**
 * Created by lixindong on 2018/3/1.
 */

public class GifflenClient implements GifClient {

    private Gifflen mGifflen;

    public GifflenClient() {
        mGifflen = new Gifflen.Builder()
                .color(256)	 //色域范围是2~256,且必须是2的整数次幂.
                .delay(1000/15) //每相邻两帧之间播放的时间间隔.
                .quality(10) //色彩量化时的quality值.
                .width(320)	    //生成Gif文件的宽度(像素).
                .height(240)	   //生成Gif文件的高度(像素).
                .build();
    }

    @Override
    public Result.RESULT newGif(String input, String output) {
        return null;
    }

    @Override
    public void newGif(String input, String output, Result result) {
    }

    @Override
    public Result.RESULT newGifFromFiles(List<File> input, String output) {
        boolean success = mGifflen.encode(output, input);
        return success ? Result.RESULT.OK : Result.RESULT.ERROR;
    }
}
