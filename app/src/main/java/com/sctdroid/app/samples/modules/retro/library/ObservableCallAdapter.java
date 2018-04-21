package com.sctdroid.app.samples.modules.retro.library;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by lixindong on 2018/3/31.
 */

public class ObservableCallAdapter implements CallAdapter<Observable> {
    @Override
    public Observable adapter(final HttpCall httpCall) {
        return new Observable() {
            @Override
            public synchronized void addObserver(Observer o) {
                super.addObserver(o);
                httpCall.submit(new HttpCall.Callback() {
                    @Override
                    public void onResponse(Response response) {
                        setChanged();
                        notifyObservers(response);
                    }
                });
            }
        };
    }
}
