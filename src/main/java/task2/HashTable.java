package task2;

import java.util.LinkedList;

public class HashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private LinkedList<Entry<K, V>>[] buckets;
    private int size;
    private int threshold;

    @SuppressWarnings("unchecked")
    public HashTable() {
        buckets = (LinkedList<Entry<K, V>>[]) new LinkedList[DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            buckets[i] = new LinkedList<>();
        }
        threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR); // Инициализация порога
    }

    public void put(K key, V value) {
        int index = getBucketIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Entry<>(key, value));
        size++;

        if (size > threshold) {
            resize();
        }
    }

    public V get(K key) {
        int index = getBucketIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public V remove(K key) {
        int index = getBucketIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                bucket.remove(entry);
                size--;
                return oldValue;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % buckets.length); // Без изменений
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = buckets.length * 2;
        LinkedList<Entry<K, V>>[] newBuckets = (LinkedList<Entry<K, V>>[]) new LinkedList[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new LinkedList<>();
        }

        for (LinkedList<Entry<K, V>> oldBucket : buckets) {
            for (Entry<K, V> entry : oldBucket) {
                int newIndex = Math.abs(entry.key.hashCode() % newCapacity);
                newBuckets[newIndex].add(entry);
            }
        }

        this.buckets = newBuckets;
        this.threshold = (int) (newCapacity * LOAD_FACTOR); // Обновление порога
    }

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}