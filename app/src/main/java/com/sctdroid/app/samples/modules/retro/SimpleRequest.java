package com.sctdroid.app.samples.modules.retro;

import com.sctdroid.app.samples.modules.retro.library.Get;
import com.sctdroid.app.samples.modules.retro.library.HttpCall;
import com.sctdroid.app.samples.modules.retro.library.ObservableCallAdapter;
import com.sctdroid.app.samples.modules.retro.library.Url;

import java.util.Observable;

/**
 * Created by lixindong on 2018/3/30.
 */

public interface SimpleRequest {
    @Get
    Observable body(@Url String url);
}
