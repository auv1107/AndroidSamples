package com.sctdroid.app.samples.modules.retro.library;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by lixindong on 2018/3/31.
 */

public class Response {

    private HttpURLConnection connection;

    private int code;

    private String errorMsg;

    public Response(HttpURLConnection connection) throws IOException {
        this.connection = connection;
        this.code = connection.getResponseCode();
    }

    public boolean isError() {
        return code >= 400;
    }

    public int getResponseCode() {
        return code;
    }

    public String getResponseBodyAsString() {
        if (code >= 400) {
            throw new RuntimeException("response error, code = " + code);
        }

        InputStream is = null;

        try {
            return getInputStreamString(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getInputStreamString(InputStream is) {
        ByteArrayOutputStream bos = null;
        try {
            is = connection.getInputStream();
            bos = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }

            String body = new String(bos.toByteArray());
            return body;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();
        }
        return "";
    }

    public String getResponseErrorMsg() {
        if (code < 400) {
            throw new RuntimeException("not error, code = " + code);
        }

        return getInputStreamString(connection.getErrorStream());
    }

}
