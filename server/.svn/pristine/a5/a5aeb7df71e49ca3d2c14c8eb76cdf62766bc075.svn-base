package com.stockholdergame.server.util.collections;

import java.util.Arrays;

/**
 *
 */
public final class Comparer {

    private Comparer() {
    }

    @SafeVarargs
    public static <T> boolean in(T o1, T... objects) {
        if (objects == null || objects.length == 0) {
            return false;
        }
        if (o1 == null) {
            return Arrays.asList(objects).contains(null);
        }
        for (T object : objects) {
            if (o1.equals(object)) {
                return true;
            }
        }
        return false;
    }
}
