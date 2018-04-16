package com.sctdroid.app.samples.common;

import android.support.annotation.NonNull;

import java.util.AbstractList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lixindong on 2018/4/13.
 */

public class LinkedSection<K,V> extends AbstractSection<K,V> {

    LinkedHashMap<K,Node<V>> table;

    transient Node<V> first;

    transient Node<V> last;

    transient int size = 0;

    transient int sectionSize = 0;

    final ArrayList<LinkedSectionEntry> list = new ArrayList<>();

    static class Node<V> {
        List<V> value;

        void add(V e) {
            if (value == null) return;
            value.add(e);
        }
        int size() {
            return value == null ? 0 : value.size();
        }

        public V get(int index) {
            return value == null ? null : value.get(index);
        }
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
        if (table == null) {
            table = new LinkedHashMap<>();
        }
        Node<V> n = findKey(key);
        if (n == null) {
            n = addNode(key);
        }
        addValue(n, value);
        afterInsertion(n, key, value);
    }

    private void afterInsertion(Node<V> node, K key, V value) {
        int index = list.indexOf(LinkedSectionEntry.valueOf(true, key));
        if (index >= 0) {
            int lastIndex = index + node.size();
            list.add(lastIndex, LinkedSectionEntry.valueOf(false, value));
        }
    }

    private void addValue(Node<V> node, V value) {
        node.add(value);
        size++;
    }

    private Node<V> addNode(K key) {
        Node<V> n = newNode();
        table.put(key, n);
        afterNodeInsertion(key);
        return n;
    }

    private void afterNodeInsertion(K key) {
        list.add(new LinkedSectionEntry(true, key));
    }

    private Node<V> newNode() {
        Node<V> n = new Node<>();
        n.value = new ArrayList<>();
        return n;
    }

    private Node<V> findKey(K key) {
        return table.get(key);
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

    public static final class LinkedSectionEntry {
        boolean isHeader;
        Object object;

        public LinkedSectionEntry(boolean isHeader, Object object) {
            this.isHeader = isHeader;
            this.object = object;
        }

        public boolean isHeader() {
            return isHeader;
        }

        public <V> V getValue() {
            return (V) object;
        }

        public static LinkedSectionEntry valueOf(boolean header, Object obj) {
            return new LinkedSectionEntry(header, obj);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof LinkedSectionEntry)) return false;

            return object == ((LinkedSectionEntry) obj).object && ((LinkedSectionEntry) obj).isHeader == isHeader;
        }
    }

    private SectionIncludeEntrySet entrySet;

    public SectionIncludeEntrySet entrySet() {
        SectionIncludeEntrySet es;
        return ((es = entrySet) == null) ? (entrySet = new SectionIncludeEntrySet()) : es;
    }

    public final class SectionIncludeEntrySet extends AbstractList<LinkedSectionEntry> {

        @Override
        public LinkedSectionEntry get(int index) {
           /* if (table == null) return null;
            Set<Map.Entry<K, Node<V>>> entrySet = table.entrySet();
            int n = 0;
            Map.Entry<K, Node<V>> nodeEntry = null;
            for (Map.Entry<K, Node<V>> entry : entrySet) {
                // add header count
                if (n == index) {
                    return new LinkedSectionEntry(true, entry.getKey());
                }
                if (n + entry.getValue().size() >= index) {
                    nodeEntry = entry;
                    break;
                }
                // add collection size
                n += entry.getValue().size() + 1;
            }
            if (nodeEntry == null) return null;

            V value = nodeEntry.getValue().get(index - n - 1);
            if (value == null) return null;

            return new LinkedSectionEntry(false, value);*/
           if (index < list.size()) {
               return list.get(index);
           }
           return null;
        }

        @Override
        public int size() {
//            return size + (table == null ? 0 : table.size());
            return list.size();
        }

    }
}
