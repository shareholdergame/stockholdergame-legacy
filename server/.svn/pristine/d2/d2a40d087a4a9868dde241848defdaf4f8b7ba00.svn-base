package com.stockholdergame.server.util.collections;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * MultiKey map.
 */
public class MultiKeyMap<K1, K2, V> {

    private Map<K1, V> map1 = new TreeMap<>();

    private Map<K2, V> map2 = new TreeMap<>();

    public void put(K1 key1, K2 key2, V value) {
        map1.put(key1, value);
        map2.put(key2, value);
    }

    public V get(K1 key1, K2 key2) {
        return key1 != null ? map1.get(key1) : key2 != null ? map2.get(key2) : null;
    }

    public boolean containsKey(K1 key1, K2 key2) {
        return key1 != null ? map1.containsKey(key1) : key2 != null && map2.containsKey(key2);
    }

    public void remove(K1 key1, K2 key2) {
        map1.remove(key1);
        map2.remove(key2);
    }

    public int size() {
        return map1.size();
    }

    public Iterator<K1> iterateByKey1() {
        return map1.keySet().iterator();
    }

    public Iterator<K2> iterateByKey2() {
        return map2.keySet().iterator();
    }
}
