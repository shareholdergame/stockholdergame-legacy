package com.stockholdergame.server.util.registry.impl;

import com.stockholdergame.server.util.registry.Registry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexander Savin
 *         Date: 20.11.2010 20.54.59
 */
public class RegistryImpl<K, V> implements Registry<K, V> {

    private Map<K, V> map;

    public RegistryImpl() {
        map = new ConcurrentHashMap<>();
    }

    public RegistryImpl(int size) {
        map = new ConcurrentHashMap<>(size);
    }

    public boolean contains(K key) {
        return map.containsKey(key);
    }

    public V get(K key) {
        return map.get(key);
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public void remove(K key) {
        map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }
}
