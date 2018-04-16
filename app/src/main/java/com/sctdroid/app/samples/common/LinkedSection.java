package com.sctdroid.app.samples.common;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lixindong on 2018/4/13.
 */

public class LinkedSection<K,V> extends AbstractSection<K,V> {

    transient Node<K,V> first;

    transient Node<K,V> last;

    transient int size = 0;

    transient int sectionSize = 0;

    static class Node<K,V> {
        int hash;
        K key;
        List<V> value;

        Node<K,V> next;
        Node<K,V> prev;
    }

    @Override
    public K header(int index) {
        return null;
    }

    @Override
    public int size(boolean withHeader) {
        return withHeader ? size + sectionSize : size;
    }

    @Override
    public void add(K key, V value) {
        if (first == null) {
            first = newNode(key, value);
            last = first;
        } else {
            Node node = findKey(key);
            if (node == null) {
                last.next = newNode(key, value);
                last.next.prev = last;
            } else {
                if (node.value == null) {
                    node.value = new ArrayList();
                }
                node.value.add(value);
            }
        }

    }

    private Node<K, V> newNode(K key, V value) {
        Node<K, V> n = new Node<>();
        n.key = key;
        n.value = new ArrayList<>();
        n.value.add(value);
        n.hash = key.hashCode();
        return n;
    }

    private Node findKey(K key) {
        Node n = first;
        if (n == null) {
            return null;
        }
        if (key == null) {
            for (; n != null; n = n.next) {
                if (n.key == null) return n;
            }
        } else {
            for (; n != null; n = n.next) {
                if (key == n.key || key.equals(n.key))
                    return n;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public void set(K key, int index, V value) {

    }

    @Override
    public V get(K key, int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @NonNull
    @Override
    public Iterator<V> iterator() {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] a) {
        return null;
    }

    @Override
    public boolean add(V v) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends V> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    static final class SectionIterator<V> implements Iterator<V> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public V next() {
            return null;
        }
    }

    static final class SectionIncludeIterator<V> implements Iterator<V> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public V next() {
            return null;
        }
    }
}
