package com.stockholdergame.server.util.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @author Alexander Savin
 *         Date: 27.8.2010 8.54.27
 */
public final class CollectionsUtil {

    private CollectionsUtil() {
    }

    @SafeVarargs
    public static <T> List<T> newList(T... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    @SafeVarargs
    public static <T> Set<T> newSet(T... items) {
        return new HashSet<>(Arrays.asList(items));
    }

    public static <K, V> Map<K, V> convertToMap(Collection<V> collection, Closure<K, V> closure) {
        MapBuilder<K, V> mb = new MapBuilder<>();
        for (V v : collection) {
            K key = closure.getKey(v);
            mb.append(key, v);
        }
        return mb.toHashMap();
    }

    public interface Closure<K, V> {
        K getKey(V value);
    }

    public static <T> List<T> shuffleObjects(Collection<T> objects, int times) {
        Map<String, T> shuffleMap = new TreeMap<>();
        for (T object : objects) {
            String randomString = UUID.randomUUID().toString();
            if (shuffleMap.containsKey(randomString)) {
                randomString = UUID.randomUUID().toString();
            }
            shuffleMap.put(randomString, object);
        }
        times--;
        if (times > 0) {
            return shuffleObjects(shuffleMap.values(), times);
        } else {
            return new ArrayList<>(shuffleMap.values());
        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValues(Map<K, V> map) {
        return sortMapByValues(map, false);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValues(Map<K, V> map, final boolean desc) {
        return sortMapByValues(map, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e, Map.Entry<K, V> e1) {
                return desc ? e1.getValue().compareTo(e.getValue()) : e.getValue().compareTo(e1.getValue());
            }
        });
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValues(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        List<Map.Entry<K, V>> entryList = new LinkedList<>(map.entrySet());
        Collections.sort(entryList, comparator);

        LinkedHashMap<K, V> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> sortedEntry : entryList) {
            sortedMap.put(sortedEntry.getKey(), sortedEntry.getValue());
        }
        return sortedMap;
    }
}
