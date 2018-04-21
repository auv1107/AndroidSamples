package com.sctdroid.app.samples.modules.retro.library;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lixindong on 2018/3/30.
 */

public class ServiceMethod {

    private String baseUrl;
    private String httpMethod;
    private CallAdapter callAdapter;

    public ServiceMethod(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.httpMethod = builder.httpMethod;
        this.callAdapter = builder.callAdapter;
    }

    public Object call() {
        return callAdapter.adapter(new HttpCall(this));
    }

    public void applyHeader(HttpURLConnection connection) {

    }

    public static class Builder {
        private String baseUrl;
        private String httpMethod;
        private Object args[];

        private Class<?>[] methodParamsTypes;

        private final Annotation[] methodAnnotations;
        private final Annotation[][] methodParamsAnnotations;

        private CallAdapter callAdapter;

        public Builder(Retro retro, Method method, Object[] args) {
            this.methodAnnotations = method.getAnnotations();
            this.methodParamsAnnotations = method.getParameterAnnotations();
            this.args = args;
            this.methodParamsTypes = method.getParameterTypes();
        }

        public Builder callAdapter(CallAdapter callAdapter) {
            this.callAdapter = callAdapter;
            return this;
        }

        public ServiceMethod build() {
            for (Annotation methodAnnotation : methodAnnotations) {
                parseMethodAnnotations(methodAnnotation);
            }

            for (int i = 0; i < methodParamsAnnotations.length; i++) {
                parseMethodParamsAnnotations(methodParamsAnnotations[i], methodParamsTypes[i], args[i]);
            }

            return new ServiceMethod(this);
        }

        private void parseMethodParamsAnnotations(Annotation[] methodParamsAnnotation, Class<?> methodParamsType, Object arg) {
            if (methodParamsAnnotation.length == 1) {
                Annotation annotation = methodParamsAnnotation[0];
                if (annotation instanceof Url) {
                    this.baseUrl = String.valueOf(arg);
                }
            }
        }

        private void parseMethodAnnotations(Annotation methodAnnotation) {
            if (methodAnnotation instanceof Get) {
                parseHttpMethodAndPath("GET", ((Get) methodAnnotation).value(), false);
            }
        }

        private void parseHttpMethodAndPath(String method, String value, boolean hasBody) {
            this.httpMethod = method;
        }
    }

    public String getUrl() {
        return baseUrl;
    }

    public String getHttpMethod() {
        return httpMethod;
    }
}
