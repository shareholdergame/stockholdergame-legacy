package com.stockholdergame.server.util.collections;

import java.util.List;

/**
 *
 */
public abstract class ChunkHandler<T> {

    public void perform(int limit) {
        int size;
        do {
            List<T> objects = find(limit);
            size = objects != null ? objects.size() : 0;

            if (objects != null) {
                for (T object : objects) {
                    process(object);
                }
            }

        } while (limit == size);
    }

    protected abstract void process(T object);

    protected abstract List<T> find(int limit);
}
