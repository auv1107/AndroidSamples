package com.sctdroid.app.samples.common;

import java.util.Collection;
import java.util.List;

/**
 * Created by lixindong on 2018/4/13.
 */

public interface Section<K, V> extends Collection<V> {

    K header(int index);

    int size(boolean withHeader);

    void add(K key, V value);

    V remove(K key, V value);

    void set(K key, int index, V value);

    V get(K key, int index);

}
