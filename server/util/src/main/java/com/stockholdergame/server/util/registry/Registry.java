package com.stockholdergame.server.util.registry;

/**
 * @author Alexander Savin
 *         Date: 18.11.2010 22.05.33
 */
public interface Registry<K, V> {

    boolean contains(K key);

    V get(K key);

    void put(K key, V value);

    void remove(K key);

    int size();
}
