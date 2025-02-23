package task2;

import java.util.LinkedList;

public class HashTable {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private LinkedList<Integer>[] table;
    private int capacity;
    private float loadFactor;
    private int size;

    @SuppressWarnings("unchecked")
    public HashTable() {
        this.capacity = DEFAULT_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.size = 0;
        this.table = new LinkedList[this.capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
    }

    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("initialCapacity must be > 0");
        }
        if (loadFactor <= 0) {
            throw new IllegalArgumentException("loadFactor must be > 0");
        }
        this.capacity = initialCapacity;
        this.loadFactor = loadFactor;
        this.size = 0;
        this.table = new LinkedList[this.capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
    }


    public void insert(int key) {
        int index = hash(key);
        LinkedList<Integer> bucket = table[index];
        if (!bucket.contains(key)) {
            bucket.add(key);
            size++;
            if ((float) size / capacity > loadFactor) {
                rehash();
            }
        }
    }


    public boolean search(int key) {
        int index = hash(key);
        LinkedList<Integer> bucket = table[index];
        return bucket.contains(key);
    }

    public void remove(int key) {
        int index = hash(key);
        LinkedList<Integer> bucket = table[index];
        if (bucket.remove((Integer) key)) {
            size--;
        }
    }


    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        int newCapacity = capacity * 2;
        LinkedList<Integer>[] oldTable = table;

        LinkedList<Integer>[] newTable = new LinkedList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newTable[i] = new LinkedList<>();
        }

        for (LinkedList<Integer> bucket : oldTable) {
            for (int key : bucket) {
                int newIndex = Math.abs(key) % newCapacity;
                newTable[newIndex].add(key);
            }
        }

        table = newTable;
        capacity = newCapacity;
    }

    private int hash(int key) {
        return Math.abs(key) % capacity;
    }
}
