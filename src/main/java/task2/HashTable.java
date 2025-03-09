package task2;

import java.util.LinkedList;

public class HashTable {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private LinkedList<Integer>[] table;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be > 0");
        }
        this.capacity = initialCapacity;
        this.size = 0;
        this.table = new LinkedList[capacity];

        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public void insert(int key) {
        if (size >= capacity * LOAD_FACTOR) {
            resize();
        }

        int index = hash(key);
        LinkedList<Integer> bucket = table[index];

        if (!bucket.contains(key)) {
            bucket.add(key);
            size++;
        }
    }

    public boolean search(int key) {
        int index = hash(key);
        return !table[index].isEmpty(); // Просто проверяем, есть ли что-то в этом бакете
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

    private int hash(int key) {
        return Math.abs(Integer.hashCode(key)) % capacity;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        LinkedList<Integer>[] newTable = new LinkedList[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newTable[i] = new LinkedList<>();
        }

        for (LinkedList<Integer> bucket : table) {
            for (int key : bucket) {
                int newIndex = Math.abs(Integer.hashCode(key)) % newCapacity;
                newTable[newIndex].add(key);
            }
        }

        this.capacity = newCapacity;
        this.table = newTable;
    }
}
