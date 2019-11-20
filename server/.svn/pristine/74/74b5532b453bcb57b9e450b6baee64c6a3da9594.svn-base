package com.stockholdergame.server.util.registry.impl;

import com.stockholdergame.server.util.registry.Registry;
import com.stockholdergame.server.util.registry.RegistryAlmostFullException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 */
public class AdvancedTimeSizeEvictionRegistry<K, V> implements Registry<K, V> {

    private static final String KEY_MUST_NOT_BE_NULL = "Key must not be null";

    private class Node {

        private long lastAccessTime;

        private K key;

        private Node(K key, long lastAccessTime) {
            this.key = key;
            this.lastAccessTime = lastAccessTime;
        }

        private long getLastAccessTime() {
            return lastAccessTime;
        }

        private K getKey() {
            return key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (key != null ? !key.equals(node.key) : node.key != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return key != null ? key.hashCode() : 0;
        }
    }

    private int maxSize = 100;

    private double fillFactor = 0.75;

    private long evictionTimeout = 60000; // one minute

    private ConcurrentHashMap<K, V> nodesMap = new ConcurrentHashMap<>(maxSize);
    private ConcurrentLinkedQueue<Node> evictionQueue = new ConcurrentLinkedQueue<>();

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public double getFillFactor() {
        return fillFactor;
    }

    public void setFillFactor(double fillFactor) {
        this.fillFactor = fillFactor;
    }

    public long getEvictionTimeout() {
        return evictionTimeout;
    }

    public void setEvictionTimeout(long evictionTimeout) {
        this.evictionTimeout = evictionTimeout;
    }

    public AdvancedTimeSizeEvictionRegistry() {
    }

    public AdvancedTimeSizeEvictionRegistry(int maxSize, double fillFactor, long evictionTimeout) {
        this.maxSize = maxSize;
        this.fillFactor = fillFactor;
        this.evictionTimeout = evictionTimeout;
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException(KEY_MUST_NOT_BE_NULL);
        }

        return nodesMap.containsKey(key);
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException(KEY_MUST_NOT_BE_NULL);
        }

        if (nodesMap.containsKey(key)) {
            long currentTime = System.currentTimeMillis();
            updateAccessTime(key, currentTime);
            return nodesMap.get(key);
        } else {
            return null;
        }
    }

    @Override
    public void put(K key, V value) throws RegistryAlmostFullException {
        if (key == null) {
            throw new IllegalArgumentException(KEY_MUST_NOT_BE_NULL);
        }

        long currentTime = System.currentTimeMillis();
        boolean fillFactorAchieved = false;
        if (achieveFillFactor()) {
            int removedNodes = evict(currentTime);
            fillFactorAchieved = removedNodes == 0;
        }

        nodesMap.put(key, value);
        updateAccessTime(key, currentTime);
        if (fillFactorAchieved) {
            throw new RegistryAlmostFullException("Max size = " + maxSize + ", current size = " + nodesMap.size());
        }
    }

    public void evict() {
        evict(System.currentTimeMillis());
    }

    private boolean achieveFillFactor() {
        int size = nodesMap.size();
        return size > 0 && ((double) size / (double) maxSize >= fillFactor);
    }

    private int evict(long currentTime) {
        int removedNodes = 0;
        Node node = evictionQueue.peek();
        if (node != null) {
            long lastAccessTime = node.getLastAccessTime();
            while (currentTime - lastAccessTime >= evictionTimeout) {
                nodesMap.remove(node.getKey());
                evictionQueue.poll();
                removedNodes++;

                node = evictionQueue.peek();
                if (node == null) {
                    break;
                }
                lastAccessTime = node.getLastAccessTime();
            }
        }
        return removedNodes;
    }

    private void updateAccessTime(K key, long currentTime) {
        Node node = new Node(key, currentTime);
        evictionQueue.remove(node);
        evictionQueue.add(node);
    }

    @Override
    public void remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException(KEY_MUST_NOT_BE_NULL);
        }

        if (nodesMap.containsKey(key)) {
            nodesMap.remove(key);
            evictionQueue.remove(new Node(key, 0));
        }
    }

    @Override
    public int size() {
        return nodesMap.size();
    }
}
