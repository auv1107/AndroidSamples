package com.sctdroid.app.samples.common;

/**
 * Created by lixindong on 2018/4/13.
 */

public abstract class AbstractSection<K, V> implements Section<K, V> {

    protected transient int modCount = 0;

}
