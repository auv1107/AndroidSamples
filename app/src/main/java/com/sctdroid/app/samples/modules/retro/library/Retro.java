package com.sctdroid.app.samples.modules.retro.library;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lixindong on 2018/3/30.
 */

public class Retro {

    private CallAdapter mCallAdapter;

    public Retro(Builder builder) {
        mCallAdapter = builder.callAdapter;
    }

    public <T> T create(final Class<T> server) {
        return (T) Proxy.newProxyInstance(server.getClassLoader(), new Class<?>[]{server}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ServiceMethod serviceMethod = new ServiceMethod.Builder(Retro.this, method, args).callAdapter(mCallAdapter).build();
                return serviceMethod.call();
            }
        });
    }

    public static class Builder {
        private CallAdapter callAdapter;

        public Retro build() {
            return new Retro(this);
        }

        public Builder callAdapter(CallAdapter callAdapter) {
            this.callAdapter = callAdapter;
            ExecutorService service =  Executors.newSingleThreadExecutor();
            return this;
        }
    }
}
