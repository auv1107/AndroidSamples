package com.sctdroid.app.samples.modules.retro.library;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lixindong on 2018/3/31.
 */

public class HttpCall {

    private ServiceMethod mServiceMethod;

    interface Callback {
        void onResponse(Response response);
    }

    public HttpCall(ServiceMethod serviceMethod) {
        mServiceMethod = serviceMethod;
    }

    public Response execute() {
        Response response;
        try {
            URL url = new URL(mServiceMethod.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);

            connection.setRequestMethod(mServiceMethod.getHttpMethod());
            mServiceMethod.applyHeader(connection);

            response = new Response(connection);

            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void submit(final Callback callback) {
        Response response = execute();
        if (callback != null) {
            callback.onResponse(response);
        }
    }

}
