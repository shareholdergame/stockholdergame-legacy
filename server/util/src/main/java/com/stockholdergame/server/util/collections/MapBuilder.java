package com.stockholdergame.server.util.collections;

import java.util.*;

/**
 * @author Alexander Savin
 *         Date: 16.10.2010 18.31.30
 */
public class MapBuilder<K, V> {

    private class MapEntry {
        K key;
        V value;

        private MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private List<MapEntry> entries = new ArrayList<>();

    public MapBuilder<K, V> append(K key, V value) {
        entries.add(new MapEntry(key, value));
        return this;
    }

    public Map<K, V> toHashMap() {
        return toMap(new HashMap<K, V>());
    }

    public Map<K, V> toLinkedHashMap() {
        return toMap(new LinkedHashMap<K, V>());
    }

    private Map<K, V> toMap(Map<K, V> map) {
        for (MapEntry entry : entries) {
            map.put(entry.key, entry.value);
        }
        return map;
    }
}
